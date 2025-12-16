package com.ssafy.nayeogi.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.nayeogi.common.dto.ApiResponse;
import com.ssafy.nayeogi.member.model.dto.MemberDto;
import com.ssafy.nayeogi.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 관련 API 요청을 처리하는 Controller
 * * [수정 사항 및 주석]
 * 1. 기존의 try-catch 블록을 모두 제거했습니다. (GlobalExceptionHandler가 처리)
 * 2. 리턴 타입을 'ApiResponse'로 통일하여 팀원 간 응답 규격을 맞췄습니다.
 * * @author SSAFY
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final AuthenticationManager authenticationManager;
		/**
		 * 서버 연결 테스트용 API
		 * [GET] /api/v1/members/test
		 * 브라우저 주소창에 이 주소를 치면 성공 메시지가 나와야 합니다.
		 */
		@GetMapping("/test")
		public ResponseEntity<ApiResponse<String>> test() {
			return ResponseEntity.ok(ApiResponse.success("성환이형님! 서버 연결 대성공입니다! 🎉"));
		}
	
	/**
	 * 회원가입 API
	 * [POST] /api/v1/members/join
	 * @param memberDto 회원가입할 사용자 정보 (JSON -> DTO)
	 * @return 성공 시 201 Created와 함께 성공 메시지 반환
	 */
	@PostMapping("/join")
	public ResponseEntity<ApiResponse<Void>> join(@RequestBody MemberDto memberDto) {
		// 1. 서비스 비즈니스 로직 호출
		// (중복 아이디 등 에러 발생 시, 여기서 즉시 중단되고 GlobalExceptionHandler로 넘어갑니다.)
		memberService.join(memberDto);
		// 2. 예외 없이 여기까지 왔다면 '성공'입니다.
		// 팀원이 만든 ApiResponse.success() 메서드 사용
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("회원가입 성공"));
	}
	
	@GetMapping("/id-check/{userId}")
	public ResponseEntity<Void> checkId(@PathVariable String userId) {
	    // 방금 서비스에 만든 메서드를 호출합니다.
	    memberService.idCheck(userId);
	    
	    // 예외가 안 터지고 여기까지 왔다면 사용 가능한 아이디입니다.
	    return ResponseEntity.ok().build();
	}
// // 기존 성환이 로그인 코드
//	@PostMapping("/login") 
//	public ResponseEntity<ApiResponse<MemberDto>> login(@RequestBody MemberDto memberDto, HttpSession session) {
//		log.info("로그인 요청: {}", memberDto.getUserId());
//		
//		// 1. 서비스 호출
//		// 아이디가 없거나 비번이 틀리면 서비스에서 CustomException을 던집니다.
//		// 즉, 이 변수에 값이 담겼다는 건 '로그인 성공'이라는 뜻입니다.
//		MemberDto loginInfo = memberService.login(memberDto.getUserId(), memberDto.getUserPassword());
//		
//		// 2. 성공 시 회원 정보 리턴
//		// 프론트에서는 response.data 안에 있는 회원 정보를 꺼내 쓰면 됩니다.
//		session.setAttribute("userInfo", loginInfo);
//		log.info("세션 저장 완료 - Session ID: {}", session.getId());
//		
//		return ResponseEntity.ok(ApiResponse.success(loginInfo));
//	}
	@PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody MemberDto memberDto, HttpServletRequest request) {
		/*
		 * 흐름
		 * 1. authRequest 토큰 생성
		 * 2. authenticationManger가 loadUserByName호출하여 DB의 데이터와 authRequest 인증 후 authentication 반환
		 * 3. 세션에 "SPRING_SECURITY_CONTEXT"란 이름으로 인증User 저장
		 */
		UsernamePasswordAuthenticationToken authRequest = 
				new UsernamePasswordAuthenticationToken(memberDto.getUserId(), memberDto.getUserPassword());

		Authentication authentication = authenticationManager.authenticate(authRequest);

		SecurityContext context = SecurityContextHolder.createEmptyContext();
	    context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
      
  		HttpSession session = request.getSession();
      	session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

      	return ResponseEntity.ok(ApiResponse.success("로그인 성공"));
    }
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
	    log.info("로그아웃 요청 - Session ID: {}", session.getId());
	    
	    // 1. Spring Security Context 내부의 인증 정보 제거
	    SecurityContextHolder.clearContext();
	    
	    // 2. HTTP 세션 무효화 (JSESSIONID 제거 및 서버 메모리 정리)
	    session.invalidate();
	    
	    return ResponseEntity.ok(ApiResponse.success("로그아웃 성공"));
	}
	
	/**
     * 회원 정보 조회 API
     * [GET] /api/v1/members/me
     * 명세서: 로그인된 사용자(세션)의 정보를 조회합니다.
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberDto>> userInfo(Authentication authentication) {
        log.info("회원 정보 조회 요청 (Me)");

        // 1. 인증 정보 확인 (비로그인 상태 체크)
        // Spring Security가 세션(JSESSIONID)을 확인해서 authentication 객체를 채워줍니다.
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("로그인이 필요한 서비스입니다.", null)); 
                    // ApiResponse에 error(msg) 메서드가 없으면 .success(null) 등으로 대체 가능
        }

        // 2. 로그인 아이디(PK) 추출
        // CustomUserDetailsService에서 넣어둔 username(userId)을 꺼냅니다.
        String userId = authentication.getName();
        log.info("조회 대상 ID: {}", userId);

        // 3. 서비스 호출 (기존 메서드 재사용)
        MemberDto memberInfo = memberService.memberInfo(userId);

        if (memberInfo != null) {
            // [중요] 보안상 비밀번호는 클라이언트로 보내지 않습니다.
            memberInfo.setUserPassword(""); 
        }

        // 4. 응답 반환
        return ResponseEntity.ok(ApiResponse.success(memberInfo));
    }
    
    /**
     * 회원 정보 수정 API
     * [PUT] /api/v1/members
     * @param memberDto 수정할 정보 (userName, userPassword 등)
     * @param authentication 현재 로그인된 사용자 정보 (Spring Security Context)
     * @return 성공 시 200 OK와 성공 메시지 반환
     */
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateMember(
            @RequestBody MemberDto memberDto, Authentication authentication) {
        
        // 1. 로그인 여부 및 사용자 ID 추출 (인증 필수)
        if (authentication == null || !authentication.isAuthenticated()) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("로그인이 필요합니다.", null)); 
        }
        String userId = authentication.getName();
        
        // 2. DTO에 수정 대상 ID(PK) 설정
        // 클라이언트가 ID를 보내지 않아도, 서버에서 인증된 ID를 강제로 넣어줍니다.
        memberDto.setUserId(userId);
        
        // 3. 서비스 호출 (비밀번호 암호화 및 DB 업데이트 수행)
        memberService.updateMember(memberDto);
        
        // 4. 성공 응답
        return ResponseEntity.ok(ApiResponse.success("회원 정보 수정 성공"));
    }
    
    /**
     * 회원 탈퇴 API
     * [DELETE] /api/v1/members
     * 명세서: 회원 정보를 삭제하고 로그아웃 처리합니다.
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            Authentication authentication, 
            HttpServletRequest request) {
        
        // 1. 인증 확인
        if (authentication == null || !authentication.isAuthenticated()) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("로그인이 필요합니다.", null));
        }
        
        // 2. 사용자 ID 추출
        String userId = authentication.getName();
        log.info("회원 탈퇴 요청 - ID: {}", userId);
        
        // 3. 서비스 호출 (DB 데이터 삭제)
        memberService.deleteMember(userId);
        
        // 4. 로그아웃 처리 (세션 및 컨텍스트 삭제)
        // DB는 지웠는데 로그인 상태가 유지되면 안 되니까요!
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        return ResponseEntity.ok(ApiResponse.success("회원 탈퇴 성공 (로그아웃 처리됨)"));
    }
	
}