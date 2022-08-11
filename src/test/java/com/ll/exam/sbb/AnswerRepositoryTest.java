package com.ll.exam.sbb;

import org.junit.jupiter.api.BeforeEach;
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
    private int lastSampleDataId;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    private void clearData() {
        // question truncate
        QuestionRepositoryTest.clearData(questionRepository);
        // answer truncate
        answerRepository.truncateAnswer();
    }

    private void createSampleData() {
        QuestionRepositoryTest.createSampleData(questionRepository);
    }

    @Test
    void saveAnswer() {
        Question q = questionRepository.findById(1).orElse(null);
        Answer a = new Answer();
        a.setContent("답변");
        a.setCreateDate(LocalDateTime.now());
        a.setQuestion(q);
        // when
        answerRepository.save(a);
    }
}