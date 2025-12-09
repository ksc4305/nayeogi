package com.ssafy.nayeogi.story.service;

import com.ssafy.nayeogi.common.exception.CustomException;
import com.ssafy.nayeogi.common.exception.ErrorCode;
import com.ssafy.nayeogi.story.model.dao.StoryDao;
import com.ssafy.nayeogi.story.model.dto.StoryDetailResponse;
import com.ssafy.nayeogi.story.model.dto.StoryListResponse;
import com.ssafy.nayeogi.story.model.dto.StorySaveRequest;
import com.ssafy.nayeogi.story.model.dto.StoryUpdateRequest;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryDao storyDao;

    @Override
    @Transactional
    public int saveStory(StorySaveRequest request, String memberId) {
        // 1. 작성자 ID 설정
        request.setMemberId(memberId);

        // 2. 스토리북 메인 저장 (DTO의 id 필드에 PK가 담김)
        storyDao.insertStorybook(request);
        
        // 3. PK 확인 (저장 실패 시 0)
        int storyId = request.getId();
        if (storyId == 0) {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }

        // 4. 페이지가 있다면 일괄 저장
        if (request.getPages() != null && !request.getPages().isEmpty()) {
            storyDao.insertStoryPages(storyId, request.getPages());
        }
        
        return storyId;
    }
    
    
    @Override
    public List<StoryListResponse> getStoryList(String memberId, Integer planId) {
        return storyDao.selectStoryList(memberId, planId);
    }
    
    @Override
    public StoryDetailResponse getStoryDetail(int storyId, String memberId) {
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
    	// 1. 작성자 확인 (권한 체크)
    	String authorId = storyDao.selectMemberIdByStoryId(storyId);
    	if (authorId == null) {
    		throw new CustomException(ErrorCode.STORY_NOT_FOUND);
    	}
    	if (!authorId.equals(memberId)) {
    		throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
    	}
    	
    	// 2. 스토리북 메인 정보 수정
    	storyDao.updateStorybook(storyId, request);
    	
    	// 3. 기존 페이지들 모두 삭제 (갈아엎기 전략)
    	storyDao.deleteStoryPages(storyId);
    	
    	// 4. 새 페이지들 입력
    	if (request.getPages() != null && !request.getPages().isEmpty()) {
    		// 기존 insertStoryPages 메서드 재사용!
    		storyDao.insertStoryPages(storyId, request.getPages());
    	}
    }
    
    @Override
    @Transactional
    public void deleteStory(int storyId, String memberId) {
    	// 1. 작성자 확인
    	String authorId = storyDao.selectMemberIdByStoryId(storyId);
    	if (authorId == null) {
    		throw new CustomException(ErrorCode.STORY_NOT_FOUND);
    	}
    	if (!authorId.equals(memberId)) {
    		throw new CustomException(ErrorCode.FORBIDDEN_ACCESS);
    	}
    	
    	// 2. 삭제 (DB의 ON DELETE CASCADE 덕분에 story_pages도 자동 삭제됨)
    	storyDao.deleteStorybook(storyId);
    	
    	// ※ 만약 S3에 올라간 이미지 파일도 같이 지워야 한다면?
    	// 여기서 story_pages를 조회해서 imagePath를 얻은 뒤 ImageService.delete()를 호출해야 합니다.
    	// 현재는 DB 데이터만 삭제합니다.
    }
    
    @Override
    @Transactional
    public void changeVisibility(int storyId, boolean isPublic, String memberId) {
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
}