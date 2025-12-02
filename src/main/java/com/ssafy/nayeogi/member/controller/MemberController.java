package com.ssafy.nayeogi.member.controller;

import com.ssafy.nayeogi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 정보 관리 관련 API 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 정보 조회
     * @param memberId 조회할 회원 ID
     * @return 회원 정보
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMemberInfo(@PathVariable String memberId) {
        // MemberDto member = memberService.findById(memberId);
        // if (member != null) {
        //     return ResponseEntity.ok(member);
        // }
        // return ResponseEntity.notFound().build();
        return ResponseEntity.ok("회원 정보 조회 (구현 필요)");
    }

    /**
     * 회원 정보 수정
     * @param memberId 수정할 회원 ID
     * @param memberUpdateRequest 수정할 회원 정보
     * @return
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<?> updateMemberInfo(@PathVariable String memberId, @RequestBody Object memberUpdateRequest) {
        // memberService.update(memberUpdateRequest);
        return ResponseEntity.ok("회원 정보 수정 (구현 필요)");
    }

    /**
     * 회원 탈퇴
     * @param memberId 탈퇴할 회원 ID
     * @return
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable String memberId) {
        // memberService.delete(memberId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
