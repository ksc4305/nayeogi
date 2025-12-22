package com.ssafy.nayeogi.common.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.nayeogi.member.model.dto.MemberDto;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

    private final MemberDto memberDto; // 실제 회원 정보

    public CustomUserDetails(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    // 권한 설정 (ROLE_ 접두사 처리 등 필요 시 로직 추가)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + memberDto.getRole()));
    }

    @Override
    public String getPassword() {
        return memberDto.getUserPassword();
    }

    @Override
    public String getUsername() {
        return memberDto.getUserId();
    }

    // 계정 상태 관련 (모두 true로 설정)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
