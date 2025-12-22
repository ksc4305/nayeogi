package com.ssafy.nayeogi.common.security;

import com.ssafy.nayeogi.member.model.dao.MemberDao;
import com.ssafy.nayeogi.member.model.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto member = memberDao.memberInfo(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
//        return User.builder()
//                .username(member.getUserId())
//                // TODO: 암호화된 조회, 저장 코드 구현하기, 현재는 평문으로 저장하고 조회함 
//                .password( member.getUserPassword())
//                .roles(member.getRole())
//                .build();
        return new CustomUserDetails(member);
    }
}