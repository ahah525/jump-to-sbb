package com.ll.exam.sbb.user;

import com.ll.exam.sbb.answer.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    // 외부용
    public static void clearData() {
        
    }

    @Test
    @DisplayName("회원가입이 가능하다.")
    void create() {
        userService.create("user1", "user1@email.com", "1234");
    }


}