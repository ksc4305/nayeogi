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
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.core.io.UrlResource;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import java.net.MalformedURLException;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryDao storyDao;
    private final ImageService imageService;
    private final ChatClient chatClient;
    private final TransactionTemplate transactionTemplate;
    @Value("classpath:/prompts/default_system_prompt.txt")
    private Resource systemPrompt;
    
    @Override
    public int generateAndSaveStory(AiStoryRequest request, String memberId) {
        
        // 1. AI 컨텐츠 생성 (시간이 오래 걸림, DB 트랜잭션 없이 실행)
        String generatedContent = generateAiContent(request);

        // 2. DB 저장 (순식간에 끝남, 여기서만 트랜잭션 실행)
        return saveStory(request, generatedContent, memberId);
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

        // 3. 이미지 리스트 추출 (Vision 기능 활용)
        List<Media> mediaList = extractAllImagesAsMedia(request);
        log.info(">> AI 분석 요청 컨텐츠 구성 완료 (텍스트 길이: {}, 분석 이미지: {}개)", userContext.length(), mediaList.size());

        // AI 호출
        log.info("AI 스토리 생성 시작 (Gemini Multimodal)...");
        return chatClient.prompt()
                .system(sp -> sp.text(systemPrompt)
                        .params(Map.of("tones", finalTones, "companions", finalCompanions)))
                .user(u -> u.text(userContext).media(mediaList.toArray(new Media[0])))
                .call()
                .content();
    }

    // 모든 이미지 URL을 Media 객체로 변환
    private List<Media> extractAllImagesAsMedia(AiStoryRequest request) {
        List<Media> mediaList = new ArrayList<>();
        if (request.getStoryDays() != null) {
            for (var day : request.getStoryDays()) {
                if (day.getSections() != null) {
                    for (var section : day.getSections()) {
                        if (section.getImageUrls() != null) {
                            for (String url : section.getImageUrls()) {
                                try {
                                    MimeType mimeType = resolveMimeType(url);
                                    mediaList.add(new Media(mimeType, new UrlResource(url)));
                                } catch (MalformedURLException e) {
                                    log.warn("잘못된 이미지 URL 건너뜀: {}", url);
                                } catch (Exception e) {
                                    log.error("이미지 리소스 로드 실패: {}", url, e);
                                }
                            }
                        }
                    }
                }
            }
        }
        return mediaList;
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
        sb.append("기간: ").append(request.getStartDate()).append(" ~ ").append(request.getEndDate()).append("\n\n");
        
        if (request.getStoryDays() != null) {
            for (AiStoryRequest.DayDto day : request.getStoryDays()) {
                sb.append("## Day ").append(day.getDayNum()).append(" (").append(day.getDate()).append(")\n");
                
                String weatherStr = (day.getWeather() != null) ? String.join(", ", day.getWeather()) : "정보 없음";
                sb.append("- 날씨: ").append(weatherStr).append("\n\n");
                
                if (day.getSections() != null) {
                    for (AiStoryRequest.SectionDto section : day.getSections()) {
                        sb.append("### 장소: ").append(section.getPlaceName()).append("\n");
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