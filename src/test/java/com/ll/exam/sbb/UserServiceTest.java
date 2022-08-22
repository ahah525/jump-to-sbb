package com.ll.exam.sbb;

import com.ll.exam.sbb.answer.AnswerRepository;
import com.ll.exam.sbb.question.QuestionRepository;
import com.ll.exam.sbb.user.UserRepository;
import com.ll.exam.sbb.user.UserService;
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

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    // 외부용
    public static void clearData(UserRepository userRepository, AnswerRepository answerRepository, QuestionRepository questionRepository) {
//        AnswerRepositoryTest.clearData(answerRepository, questionRepository);
//        QuestionRepositoryTest.clearData(questionRepository);
//
//        userRepository.deleteAll();
        // Answer -> Question -> SiteUser 순으로 삭제
        answerRepository.deleteAll();
        answerRepository.truncateTable();

        questionRepository.deleteAll();
        questionRepository.truncateTable();

        userRepository.deleteAll();
        userRepository.truncateTable();
    }

    private void clearData() {
        clearData(userRepository, answerRepository, questionRepository);
    }

    // 외부용
    public static void createSampleData(UserService userService) {
        userService.create("admin", "admin@test.com", "1234");
        userService.create("user1", "user1@test.com", "1234");
    }

    private void createSampleData() {
        createSampleData(userService);
    }

    @Test
    @DisplayName("회원가입이 가능하다.")
    void create() {
        userService.create("user2", "user2@email.com", "1234");
    }
}