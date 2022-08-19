package com.ll.exam.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    // 입력받은 비밀번호가 loadUserByUsername에 의해 리턴된 User 객체의 비밀번호와 일치하는지 검사
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SiteUser siteUser = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        });

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            // 사용자명이 admin이면 ADMIN 권한 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            // 일반 회원 USER 권한 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
