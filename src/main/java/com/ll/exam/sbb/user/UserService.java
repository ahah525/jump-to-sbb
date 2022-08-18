package com.ll.exam.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        // 비밀번호 bcrypt 암호화
        user.setPassword(passwordEncoder.encode(password));

        SiteUser user1 = userRepository.findByUsername(username).orElse(null);
        if (user1 != null) {
            throw new SignupUsernameDuplicatedException("이미 사용중인 username 입니다.");
        }
        SiteUser user2 = userRepository.findByEmail(email).orElse(null);
        if (user2 != null) {
            throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
        }
        userRepository.save(user);

        return user;
    }
}
