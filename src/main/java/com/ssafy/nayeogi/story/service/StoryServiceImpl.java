package com.ssafy.nayeogi.story.service;

import com.ssafy.nayeogi.common.exception.CustomException;
import com.ssafy.nayeogi.common.exception.ErrorCode;
import com.ssafy.nayeogi.image.service.ImageService;
import com.ssafy.nayeogi.story.model.dao.StoryDao;
import com.ssafy.nayeogi.story.model.dto.AiStoryRequest;
import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPlanDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewRequest;
import com.ssafy.nayeogi.story.model.dto.StoryPreviewResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StoryUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
@Slf4j
@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryDao storyDao;
    private final ImageService imageService;
    @Override
    @Transactional
    public int generateAndSaveStory(AiStoryRequest request, String memberId) {
    	if (memberId == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    	
    	// 1. 시스템 프롬프트: "마크다운으로 써줘"
        String systemText = """
            당신은 여행 전문 에세이 작가입니다. 
            사용자의 여행 기록을 바탕으로 블로그 포스팅을 작성해주세요.
            
            [작성 규칙]
            1. **Markdown(마크다운) 형식**으로만 작성하세요. (HTML 태그 금지)
            2. 제목은 '# ', 소제목은 '## ', '### ' 문법을 사용하세요.
            3. 중요한 단어는 '**강조**' 처리하세요.
            4. 글의 흐름을 자연스럽게 이어주세요.
            """;
    	
//        StringBuilder userText = new StringBuilder();
//        userText.append(String.format("제목: %s, 동행: %s, 분위기: %s, 계절: %s\n", 
//                request.getStoryTitle(), request.getCompanions(), request.getTones(), request.getStoryDays()));
//
//        // 2. 멀티모달 데이터 준비 (이미지 리스트 만들기)
//        List<Media> mediaList = new ArrayList<>();
//
//        for (StoryAIRequest.StoryDayDto day : request.getDays()) {
//            userText.append(String.format("\n[Day %d] %s\n", day.getDayNum(), day.getDate()));
//            
//            for (StoryAIRequest.StorySectionDto section : day.getSections()) {
//                userText.append(String.format("- 장소: %s (날씨: %s, 분위기: %s)\n  메모: %s\n", 
//                        section.getPlaceName(), section.getWeather(), section.getAtmosphere(), section.getContent()));
//
//                // 이미지 URL이 있다면 Media 객체로 변환하여 리스트에 추가
//                if (section.getImageUrls() != null) {
//                    for (String url : section.getImageUrls()) {
//                        try {
//                            // S3 URL을 Resource로 변환
////                            mediaList.add(new Media(MimeTypeUtils.IMAGE_JPEG, new UrlResource(url)));
//                            // AI가 어떤 이미지가 어떤 장소 것인지 알 수 있게 텍스트 힌트 추가
//                            userText.append(String.format("  (참고 이미지 URL: %s)\n", url));
//                        } catch (Exception e) {
//                            log.error("이미지 로드 실패: {}", url);
//                        }
//                    }
//                }
//            }
//        }
        // 1. AI 호출 (Gemini 2.5 Pro)
        // UserMessage에 텍스트와 미디어 리스트를 함께 담음
//        UserMessage userMessage = new UserMessage(systemText + "\n\n" + userText.toString(), mediaList);
    	// 2. AI 호출
//        String aiMarkdownText = chatClient.prompt(new Prompt(userMessage)).call().content();
    	
    	// 3. [핵심] 이미지 병합 (Merge) - 마크다운 이미지 문법 활용
        // 문법: ![이미지설명](이미지URL)
        
        StringBuilder finalContent = new StringBuilder();
//        finalContent.append(aiMarkdownText).append("\n\n");
        
        finalContent.append("## 📸 여행 사진첩\n"); // 사진 섹션 헤더
        
//        List<String> finalImageUrls = new ArrayList<>();
//       
//            // 프론트에서 받은 이미지 URL 목록을 순회
//            for (String imageUrl : request.getDays().get(1).getSections().get(1).getImageUrls()) {
//                // [수정] imageService를 통해 파일을 영구 폴더로 이동시키고, 최종 URL을 받음
//                String permanentUrl = imageService.moveImageToPermanent(imageUrl);
//                finalImageUrls.add(permanentUrl);
//            }
        
        // 모든 날짜의 이미지를 하단에 갤러리처럼 추가하거나, 
        // 혹은 AI가 텍스트 중간에 넣을 수 있게 'PLACEHOLDER'를 쓰는 방법도 있음.
        // 여기서는 가장 쉬운 '하단 배치' 예시입니다.
//    	for (var day : request.getDays()) {
//            for (var section : day.getSections()) {
//                if (section.getImageUrls() != null) {
//                    for (String url : section.getImageUrls()) {
//                        // 마크다운 이미지 문법으로 변환하여 추가
//                        finalContent.append(String.format("![%s](%s)\n", section.getPlaceName(), url));
//                    }
//                }
//            }
//        }
    	
    	StorySaveRequest saveRequest = new StorySaveRequest();
    	// 1. 작성자 ID 설정
    	saveRequest.setMemberId(memberId);
//    	saveRequest.setTitle(request.getTitle());
//    	saveRequest.setContent(finalContent.toString()); // 마크다운 문자열 저장
//    	saveRequest.setThumbnailPath(request.getDays().get(0).getSections().get(0).getImageUrls().get(0)); // 첫 번째 사진을 썸네일로
        // 2. 스토리북 메인 저장 (DTO의 id 필드에 PK가 담김)
        storyDao.insertStory(saveRequest);
        
        // 3. PK 확인 (저장 실패 시 0)
        int storyId = saveRequest.getStoryId();
        if (storyId == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
        
        return saveRequest.getStoryId();
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
    public StoryPreviewResponse generateStoryPreview(StoryPreviewRequest request) {
        // TODO: 추후 실제 OpenAI API 연동 필요 (RestTemplate or WebClient 사용)
        
        List<StoryPreviewResponse.GeneratedPage> generatedPages = new ArrayList<>();

        // 가짜 AI 로직: 사용자가 보낸 메모 뒤에 "~~했습니다."를 붙여서 생성
        for (StoryPreviewRequest.PreviewItem item : request.getItems()) {
            
            // 1. 관광지 정보 조회 (장소 이름을 알기 위해)
            // (AttractionDao가 필요하지만, 일단 임시로 ID를 이름처럼 사용하거나 생략 가능)
            String placeName = "관광지_" + item.getContentId(); 

            // 2. AI 문체 적용 시뮬레이션
            String aiText;
            if ("EMOTIONAL".equalsIgnoreCase(request.getStyle())) {
                aiText = "감성적인 하루였습니다. " + item.getUserMemo() + " 그 순간의 공기가 기억납니다.";
            } else if ("FUNNY".equalsIgnoreCase(request.getStyle())) {
                aiText = "완전 대박! " + item.getUserMemo() + " 진짜 웃겼음 ㅋㅋ";
            } else {
                aiText = item.getUserMemo(); // 기본
            }

            generatedPages.add(new StoryPreviewResponse.GeneratedPage(
                    item.getContentId(),
                    placeName,
                    aiText
            ));
        }

        return new StoryPreviewResponse(generatedPages);
    }
    
    @Override
    public StoryPlanDetailResponse getPlanDetailForStory(int planId) {
        return storyDao.selectPlanDetailForStory(planId);
    }

}