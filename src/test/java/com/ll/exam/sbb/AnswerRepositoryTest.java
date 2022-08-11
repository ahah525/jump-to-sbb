package com.ll.exam.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class AnswerRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void saveAnswer() {
        Answer a = new Answer();
        a.setContent("답변");
        a.setCreateDate(LocalDateTime.now());
        // when
        answerRepository.save(a);
    }
}