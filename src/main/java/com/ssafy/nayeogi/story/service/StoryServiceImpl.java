package com.ssafy.nayeogi.story.service;

import com.ssafy.nayeogi.common.exception.CustomException;
import com.ssafy.nayeogi.common.exception.ErrorCode;
import com.ssafy.nayeogi.image.service.ImageService;
import com.ssafy.nayeogi.story.model.dao.StoryDao;
import com.ssafy.nayeogi.story.model.dto.AiStoryRequest;
import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPlanDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StoryUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryDao storyDao;
    private final ImageService imageService;
    private final ChatClient chatClient;
    private final TransactionTemplate transactionTemplate;
    
    @Value("classpath:/prompts/test.txt")
    private Resource systemPrompt;

    // AI 분석에 사용할 최대 이미지 수 (안전하게 6장 제한)
    private static final int MAX_TOTAL_ANALYSIS_IMAGES = 6;
    // AI Vision 모델 표준 입력 크기 근사치 (224~256px이면 분석 충분, 용량 최소화)
    private static final int TARGET_IMAGE_WIDTH = 256; 
    
    @Override
    public int generateAndSaveStory(AiStoryRequest request, String memberId) {
        log.info(">> [성능 측정 시작] 스토리 생성 프로세스 개시");
        long processStartTime = System.currentTimeMillis();
        
        // 1. AI 컨텐츠 생성 (시간이 오래 걸림, DB 트랜잭션 없이 실행)
        long aiStartTime = System.currentTimeMillis();
        String generatedContent = generateAiContent(request);
        long aiEndTime = System.currentTimeMillis();
        long aiDuration = aiEndTime - aiStartTime;

        // 2. DB 저장 (순식간에 끝남, 여기서만 트랜잭션 실행)
        long dbStartTime = System.currentTimeMillis();
        int storyId = saveStory(request, generatedContent, memberId);
        long dbEndTime = System.currentTimeMillis();
        long dbDuration = dbEndTime - dbStartTime;
        
        long totalDuration = System.currentTimeMillis() - processStartTime;

        log.info("============================================================");
        log.info(">> [성능 측정 결과 리포트]");
        log.info(">> 1. AI 생성 소요 시간 (DB 비점유): {} ms", aiDuration);
        log.info(">> 2. DB 저장 소요 시간 (DB 점유): {} ms", dbDuration);
        log.info(">> 3. 전체 프로세스 시간: {} ms", totalDuration);
        log.info(">> * 최적화 성과: 전체 시간 중 DB 점유율 약 {}%", 
                String.format("%.2f", (double)dbDuration / totalDuration * 100));
        log.info("============================================================");

        return storyId;
    }
    
    private String generateAiContent(AiStoryRequest request) {
    	// 1. 파라미터 준비 (동행자, 분위기)
        String rawTones = (request.getTones() != null) ? String.join(", ", request.getTones()) : "";
        String rawCompanions = (request.getCompanions() != null) ? String.join(", ", request.getCompanions()) : "";
        
        String finalTones = (rawTones.isEmpty()) ? "감성적인" : rawTones;
        String finalCompanions = (rawCompanions.isEmpty()) ? "나 자신" : rawCompanions;
        
        log.debug(">> AI 생성 요청 정보 - 분위기: [{}], 동행: [{}]", finalTones, finalCompanions);
        
        // 2. AI에게 전달할 유저 메시지(여행 정보) 구성
        String userContext = buildUserContext(request);
        log.debug(">> AI 생성 user 요청 정보 - [{}]", userContext);

        // 3. 이미지 리스트 추출 및 초경량 압축 (Vision 기능 활용)
        List<Media> mediaList = extractAllImagesAsMedia(request);
        log.info(">> AI 분석 요청 컨텐츠 구성 완료 (텍스트 길이: {}, 분석 이미지: {}개)", userContext.length(), mediaList.size());

        // AI 호출
        log.info("AI 스토리 생성 시작 (Multimodal)...");
        ChatResponse response = chatClient.prompt()
                .system(sp -> sp.text(systemPrompt)
                        .params(Map.of("tones", finalTones, "companions", finalCompanions)))
                .user(u -> u.text(userContext).media(mediaList.toArray(new Media[0])))
                .call()
                .chatResponse();

        if (response != null && response.getMetadata() != null && response.getMetadata().getUsage() != null) {
            var usage = response.getMetadata().getUsage();
            log.info(">> [AI 토큰 사용량 결과]");
            log.info(">> - 입력(Prompt) 토큰: {}", usage.getPromptTokens());
            log.info(">> - 출력(Completion) 토큰: {}", usage.getCompletionTokens());
            log.info(">> - 전체(Total) 토큰: {}", usage.getTotalTokens());
            log.info(">> * 이미지 최적화를 통해 입력 토큰 비용을 대폭 절감했습니다.");
        }

        return (response != null && response.getResult() != null) 
                ? response.getResult().getOutput().getText() 
                : "";
    }

    // 모든 이미지 URL을 Media 객체로 변환 (리사이징 및 개수 제한 적용)
    // 개선된 로직: 각 장소(Section)의 첫 번째 사진을 우선순위로 수집 (Round-Robin 방식)
    private List<Media> extractAllImagesAsMedia(AiStoryRequest request) {
        List<Media> mediaList = new ArrayList<>();
        List<String> selectedUrls = new ArrayList<>();
        
        // 1. 모든 섹션의 이미지 리스트를 수집
        List<List<String>> allSectionsImages = new ArrayList<>();
        if (request.getStoryDays() != null) {
            for (var day : request.getStoryDays()) {
                if (day.getSections() != null) {
                    for (var section : day.getSections()) {
                        if (section.getImageUrls() != null && !section.getImageUrls().isEmpty()) {
                            allSectionsImages.add(section.getImageUrls());
                        }
                    }
                }
            }
        }

        // 2. Round-Robin 방식으로 이미지 선택
        // (각 장소의 1번 사진들 -> 각 장소의 2번 사진들 -> ... 순서)
        int maxDepth = 0;
        for (List<String> images : allSectionsImages) {
            maxDepth = Math.max(maxDepth, images.size());
        }

        for (int depth = 0; depth < maxDepth; depth++) {
            for (List<String> sectionImages : allSectionsImages) {
                if (depth < sectionImages.size()) {
                    selectedUrls.add(sectionImages.get(depth));
                    
                    if (selectedUrls.size() >= MAX_TOTAL_ANALYSIS_IMAGES) {
                        break;
                    }
                }
            }
            if (selectedUrls.size() >= MAX_TOTAL_ANALYSIS_IMAGES) {
                break;
            }
        }
        
        log.info(">> [이미지 선별 로직(Round-Robin)] 총 {}개 후보 중 {}개 선별 완료 (여행 전 구간 분포)", 
                allSectionsImages.stream().mapToInt(List::size).sum(), selectedUrls.size());

        // 3. 압축 및 변환
        for (String url : selectedUrls) {
            try {
                Resource compressedImage = compressImage(url);
                if (compressedImage != null) {
                    mediaList.add(new Media(MimeTypeUtils.IMAGE_JPEG, compressedImage));
                }
            } catch (Exception e) {
                log.error("이미지 압축 및 로드 실패: {}", url, e);
            }
        }
        
        return mediaList;
    }

    // 이미지를 다운로드하여 256px로 리사이징하고 JPEG 품질 50%로 강력 압축
    private Resource compressImage(String imageUrl) {
        try (InputStream is = new URL(imageUrl).openStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            // 압축 전 원본 크기 가늠을 위한 스트림 복사 (측정용)
            byte[] originalBytes = is.readAllBytes();
            int originalSize = originalBytes.length;

            BufferedImage originalImage = ImageIO.read(new java.io.ByteArrayInputStream(originalBytes));
            if (originalImage == null) return null;

            // 1. 해상도 조절 (가로 256px 기준 비율 유지) - AI 인식엔 충분
            int targetHeight = (int) (originalImage.getHeight() * ((double) TARGET_IMAGE_WIDTH / originalImage.getWidth()));
            Image resultingImage = originalImage.getScaledInstance(TARGET_IMAGE_WIDTH, targetHeight, Image.SCALE_SMOOTH);
            
            BufferedImage outputImage = new BufferedImage(TARGET_IMAGE_WIDTH, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = outputImage.createGraphics();
            graphics2D.drawImage(resultingImage, 0, 0, null);
            graphics2D.dispose();

            // 2. JPEG 압축 품질 명시적 설정 (0.5 = 50% 품질 -> 용량 대폭 감소)
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            if (!writers.hasNext()) throw new IllegalStateException("No JPG writer found");
            
            ImageWriter writer = writers.next();
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
                writer.setOutput(ios);
                ImageWriteParam param = writer.getDefaultWriteParam();
                
                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.5f); // 품질 50% 설정 (시각적으론 조금 깨져도 AI 분석엔 무방)
                }
                
                writer.write(null, new IIOImage(outputImage, null, null), param);
            } finally {
                writer.dispose();
            }
            
            byte[] compressedBytes = baos.toByteArray();
            int compressedSize = compressedBytes.length;
            
            log.info(">> [이미지 최적화 결과] 원본: {} bytes -> 압축: {} bytes (절감률: {}%)", 
                    originalSize, compressedSize, String.format("%.2f", (1 - (double)compressedSize / originalSize) * 100));
            
            return new ByteArrayResource(compressedBytes);
        } catch (Exception e) {
            log.warn("이미지 압축 중 오류 발생: {}", imageUrl, e);
            return null;
        }
    }

    // URL 확장자를 기반으로 MimeType 판별
    private MimeType resolveMimeType(String url) {
        String lowerUrl = url.toLowerCase();
        if (lowerUrl.endsWith(".png")) return MimeTypeUtils.IMAGE_PNG;
        if (lowerUrl.endsWith(".gif")) return MimeTypeUtils.IMAGE_GIF;
        if (lowerUrl.endsWith(".webp")) return MimeType.valueOf("image/webp");
        return MimeTypeUtils.IMAGE_JPEG; // 기본값
    }
    
    private int saveStory(AiStoryRequest request, String content, String memberId) {
    	 if (memberId == null) {
             throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
         }
    	// int는 값이 없으면 0이므로, 0 이하라면 유효하지 않은 ID로 간주합니다.
         if (request.getPlanId() <= 0) {
        	 // 값이 이상한 경우 -> 400 Bad Request
        	 throw new CustomException(ErrorCode.PLAN_ID_INVALID); 
         }
        // execute 메서드 내부의 로직만 하나의 트랜잭션으로 묶입니다.
        return transactionTemplate.execute(status -> {
            try {
                StorySaveRequest saveRequest = new StorySaveRequest();
                saveRequest.setMemberId(memberId);
                saveRequest.setPlanId(request.getPlanId());
                saveRequest.setTitle(request.getStoryTitle());
                saveRequest.setContent(content);
                
                // 썸네일 추출 (첫 번째 이미지)
                String thumbnail = extractFirstImage(request);
                saveRequest.setThumbnailPath(thumbnail);
                saveRequest.setPublic(false);

                // DB 저장
                storyDao.insertStory(saveRequest);
                int storyId = saveRequest.getStoryId();
                if (storyId == 0) {
                    throw new CustomException(ErrorCode.SERVER_ERROR);
                }
                return storyId;
                
            } catch (CustomException e) {
                // 이미 우리가 정의한 비즈니스 예외라면 그대로 던짐 (롤백은 자동)
                status.setRollbackOnly();
                throw e;
            } catch (Exception e) {
                // 예상치 못한 시스템 에러는 로그를 남기고 포장해서 던짐
                log.error("스토리 저장 트랜잭션 실패: ", e);
                status.setRollbackOnly();
                throw new CustomException(ErrorCode.SERVER_ERROR);
            }
        });
    }
    
 // 썸네일 추출 헬퍼 메서드
    private String extractFirstImage(AiStoryRequest request) {
        if (request.getStoryDays() != null) {
            for (var day : request.getStoryDays()) {
                if (day.getSections() != null) {
                    for (var section : day.getSections()) {
                        if (section.getImageUrls() != null && !section.getImageUrls().isEmpty()) {
                            return section.getImageUrls().get(0);
                        }
                    }
                }
            }
        }
        return null; // 이미지 없음
    }
    
 // AI가 이해하기 쉽게 DTO를 텍스트로 변환하는 유틸 메서드
    private String buildUserContext(AiStoryRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("여행 제목: ").append(request.getStoryTitle()).append("\n");
        sb.append("기간: ").append(request.getStartDate()).append(" ~ ").append(request.getEndDate());

        if (request.getDuration() != null && !request.getDuration().isEmpty()) {
             sb.append(" (").append(request.getDuration()).append(")");
        }
        sb.append("\n");

        if (request.getSeason() != null && !request.getSeason().isEmpty()) {
            sb.append("계절: ").append(request.getSeason()).append("\n");
        }
        sb.append("\n");
        
        if (request.getStoryDays() != null) {
            for (AiStoryRequest.DayDto day : request.getStoryDays()) {
                sb.append("## Day ").append(day.getDayNum()).append("\n");
                
                String weatherStr = (day.getWeather() != null) ? String.join(", ", day.getWeather()) : "정보 없음";
                sb.append("- 날씨: ").append(weatherStr).append("\n\n");
                
                if (day.getSections() != null) {
                    for (AiStoryRequest.SectionDto section : day.getSections()) {
                        sb.append("### 장소: ").append(section.getPlaceName()).append("\n");
                        
                        if (section.getCategory() != null && !section.getCategory().isEmpty()) {
                            sb.append("  - 카테고리: ").append(section.getCategory()).append("\n");
                        }
                        if (section.getLocation() != null && !section.getLocation().isEmpty()) {
                            sb.append("  - 위치: ").append(section.getLocation()).append("\n");
                        }
                        
                        sb.append("  - 메모: ").append(section.getContent()).append("\n");
                        
                        if (section.getSelectedTags() != null && !section.getSelectedTags().isEmpty()) {
                            sb.append("  - 태그(감정/분위기): ").append(String.join(", ", section.getSelectedTags())).append("\n");
                        }
                        
                        if (section.getImageUrls() != null && !section.getImageUrls().isEmpty()) {
                            // AI가 이미지를 인용할 수 있도록 URL을 명시적으로 제공
                            sb.append("  - [이미지 소스]: ").append(String.join(", ", section.getImageUrls())).append("\n");
                        } else {
                            sb.append("  - [이미지 소스]: 없음\n");
                        }
                        sb.append("\n");
                    }
                }
                sb.append("---\n");
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<StoryListResponse> getStoryList(String memberId, Integer planId) {
    	if (memberId == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    	return storyDao.selectStoryList(memberId, planId);
    }
    
    @Override
    public StoryDetailResponse getStoryDetail(int storyId, String memberId) {
    	if (memberId == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    	StoryDetailResponse story = storyDao.selectStoryDetail(storyId);
        
        // 1. 글이 없는 경우
        if (story == null) {
            throw new CustomException(ErrorCode.STORY_NOT_FOUND);
        }
        
        // 2. 비공개 글인데, 작성자가 아닌 경우 (권한 체크)
        if (!story.isPublic() && !story.getMemberId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
        }
        
        return story;
    }
    
    
    @Override
    @Transactional
    public void modifyStory(int storyId, StoryUpdateRequest request, String memberId) {
    	if (memberId == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    	// 1. 작성자 확인 (권한 체크)
    	String authorId = storyDao.selectMemberIdByStoryId(storyId);
    	if (authorId == null) {
    		throw new CustomException(ErrorCode.STORY_NOT_FOUND);
    	}
    	if (!authorId.equals(memberId)) {
    		throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
    	}
    	
    	// 2. 스토리북 메인 정보 수정
    	storyDao.updateStory(storyId, request);

    }
    
    @Override
    @Transactional
    public void deleteStory(int storyId, String memberId) {
    	if (memberId == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    	// 1. 작성자 확인
    	String authorId = storyDao.selectMemberIdByStoryId(storyId);
    	if (authorId == null) {
    		throw new CustomException(ErrorCode.STORY_NOT_FOUND);
    	}
    	if (!authorId.equals(memberId)) {
    		throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
    	}
    	
    	// 2. 삭제
    	storyDao.deleteStory(storyId);

    }
    
    @Override
    @Transactional
    public void changeVisibility(int storyId, boolean isPublic, String memberId) {
    	if (memberId == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    	// 1. 작성자 확인 (내 글인지)
        String authorId = storyDao.selectMemberIdByStoryId(storyId);
        
        if (authorId == null) {
            throw new CustomException(ErrorCode.STORY_NOT_FOUND);
        }
        if (!authorId.equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
        }

        // 2. 상태 변경
        storyDao.updateStoryVisibility(storyId, isPublic);
    }
    
    @Override
    public StoryPlanDetailResponse getPlanDetailForStory(int planId) {
        return storyDao.selectPlanDetailForStory(planId);
    }

}