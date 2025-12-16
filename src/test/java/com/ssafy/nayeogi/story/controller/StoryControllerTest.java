package com.ssafy.nayeogi.story.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.nayeogi.member.model.dto.MemberDto;
import com.ssafy.nayeogi.story.model.dto.*;
import com.ssafy.nayeogi.story.service.StoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(StoryController.class)
class StoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoryService storyService;

    @Autowired
    private ObjectMapper objectMapper;

    
    @Retention(RetentionPolicy.RUNTIME)
    @WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
    public @interface WithMockCustomUser {
        String username() default "ssafy01";
        String name() default "김싸피";
    }

    // --- [커스텀 SecurityContextFactory] ---
    public static class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
        @Override
        public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            // MemberDto가 UserDetails를 구현했다고 가정 (또는 CustomUserDetails 사용 시 해당 객체 생성)
            MemberDto member = new MemberDto();
            member.setUserId(annotation.username());
            member.setUserName(annotation.name());
            // 필요한 권한 설정 (MemberDto 내부에 getAuthorities 구현이 있다면 사용)
            
            Authentication auth = new UsernamePasswordAuthenticationToken(member, "password", null);
            context.setAuthentication(auth);
            return context;
        }
    }
    @Test
    @DisplayName("스토리북 저장 성공 테스트 (로그인 사용자)")
    @WithMockCustomUser(username = "ssafy01")
    void saveStory_Success() throws Exception {
        // given
        StorySaveRequest request = new StorySaveRequest();
        request.setPlanId(101);
        request.setTitle("테스트 제목");
        
        // Service가 호출되면 ID 501을 리턴한다고 가정
        given(storyService.saveStory(any(StorySaveRequest.class), eq("ssafy01")))
                .willReturn(501);

        // when & then
        mockMvc.perform(post("/api/v1/stories")
                        .with(csrf()) // CSRF 보호가 켜져있다면 필수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data.storyId").value(501));
    }
    
    @Test
    @DisplayName("내 스토리북 목록 조회 성공 (전체 조회)")
    @WithMockCustomUser(username = "ssafy01")
    void getStoryList_All_Success() throws Exception {
        // given
        List<StoryListResponse> mockList = new ArrayList<>();
        mockList.add(new StoryListResponse(501, 101, "부산 여행", "2025-11-27", "http://img.jpg", true));
        mockList.add(new StoryListResponse(502, 102, "강릉 여행", "2025-12-01", "http://img2.jpg", false));

        // planId가 null일 때 전체 목록 반환 가정
        given(storyService.getStoryList(eq("ssafy01"), eq(null)))
                .willReturn(mockList);

        // when & then
        mockMvc.perform(get("/api/v1/stories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2)) // 2개 왔는지 확인
                .andExpect(jsonPath("$.data[0].title").value("부산 여행")); // [수정] 첫 번째 요소 확인
    }

    @Test
    @DisplayName("내 스토리북 목록 조회 성공 (특정 계획 planId 필터링)")
    @WithMockCustomUser(username = "ssafy01")
    void getStoryList_WithPlanId_Success() throws Exception {
        // given
        int targetPlanId = 101;
        List<StoryListResponse> filteredList = new ArrayList<>();
        // 101번 계획에 해당하는 스토리만 리턴된다고 가정
        filteredList.add(new StoryListResponse(501, targetPlanId, "부산 여행", "2025-11-27", "http://img.jpg", true));

        // Service가 (memberId="ssafy01", planId=101)로 호출되면 filteredList를 리턴하도록 설정
        given(storyService.getStoryList(eq("ssafy01"), eq(targetPlanId)))
                .willReturn(filteredList);

        // when & then
        mockMvc.perform(get("/api/v1/stories")
                        .param("planId", String.valueOf(targetPlanId)) // ?planId=101 파라미터 추가
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                // 반환된 리스트의 첫 번째 요소가 planId=101인지 검증
                .andExpect(jsonPath("$.data[0].planId").value(targetPlanId))
                .andExpect(jsonPath("$.data[0].title").value("부산 여행"));
    }
    
    @Test
    @DisplayName("스토리북 상세 조회 성공")
    @WithMockCustomUser(username = "ssafy01")
    void getStoryDetail_Success() throws Exception {
        // given
        int storyId = 501;
        StoryDetailResponse mockResponse = new StoryDetailResponse();
        mockResponse.setStoryId(storyId);
        mockResponse.setTitle("상세 조회 테스트");
        mockResponse.setPublic(true);
        mockResponse.setMemberId("ssafy01");
        
        // 페이지 데이터 추가
        List<StoryPageDetail> pages = new ArrayList<>();
        StoryPageDetail page = new StoryPageDetail();
        page.setPageId(1);
        page.setAttractionTitle("해운대");
        pages.add(page);
        mockResponse.setPages(pages);

        given(storyService.getStoryDetail(eq(storyId), any())) // memberId는 null일 수도 있음
                .willReturn(mockResponse);

        // when & then
        mockMvc.perform(get("/api/v1/stories/{storyId}", storyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("상세 조회 테스트"))
                .andExpect(jsonPath("$.data.pages[0].attractionTitle").value("해운대"));
    }
    
    @Test
    @DisplayName("스토리북 수정 성공")
    @WithMockCustomUser(username = "ssafy01")
    void modifyStory_Success() throws Exception {
        int storyId = 501;
        StoryUpdateRequest request = new StoryUpdateRequest();
        request.setTitle("수정된 제목");
        
        // Service는 void 반환이므로 willDoNothing() 사용 (기본값이라 생략 가능하지만 명시적 표현)
        // 에러가 안 나면 성공으로 간주

        mockMvc.perform(put("/api/v1/stories/{storyId}", storyId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"));
    }

    @Test
    @DisplayName("스토리북 삭제 성공")
    @WithMockCustomUser(username = "ssafy01")
    void deleteStory_Success() throws Exception {
        int storyId = 501;

        mockMvc.perform(delete("/api/v1/stories/{storyId}", storyId)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"));
    }
    
    @Test
    @DisplayName("스토리북 공개 여부 변경 성공")
    @WithMockCustomUser(username = "ssafy01")
    void changeVisibility_Success() throws Exception {
        int storyId = 501;
        StoryVisibilityRequest request = new StoryVisibilityRequest();
        request.setPublic(true); // 공개로 전환

        mockMvc.perform(patch("/api/v1/stories/{storyId}/visibility", storyId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"));
    }
    
    @Test
    @DisplayName("AI 스토리 초안 생성 테스트 (Mock)")
    @WithMockCustomUser(username = "ssafy01")
    void generateStoryPreview_Success() throws Exception {
        // given
        StoryPreviewRequest request = new StoryPreviewRequest();
        request.setPlanId(101);
        request.setStyle("EMOTIONAL");
        
        List<StoryPreviewRequest.PreviewItem> items = new ArrayList<>();
        StoryPreviewRequest.PreviewItem item = new StoryPreviewRequest.PreviewItem();
        item.setContentId(12540);
        item.setUserMemo("바다가 예뻤다.");
        items.add(item);
        request.setItems(items);

        // 가짜 응답 데이터 준비
        StoryPreviewResponse mockResponse = new StoryPreviewResponse();
        List<StoryPreviewResponse.GeneratedPage> pages = new ArrayList<>();
        pages.add(new StoryPreviewResponse.GeneratedPage(12540, "해운대", "AI가 써준 감성 글..."));
        mockResponse.setPages(pages);

        // Service가 호출되면 위 가짜 응답을 리턴하도록 설정 (Stubbing)
        given(storyService.generateStoryPreview(any(StoryPreviewRequest.class)))
                .willReturn(mockResponse);

        // when & then
        mockMvc.perform(post("/api/v1/stories/previews")
                        .with(csrf()) // 보안 설정 때문에 필요
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data.pages[0].title").value("해운대"))
                .andExpect(jsonPath("$.data.pages[0].aiText").value("AI가 써준 감성 글..."));
    }

}       