package com.ll.exam.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

//        answerRepository.deleteAll(); // DELETE FROM question;
        // answer truncate
        answerRepository.disableForeignKeyCheck();
        answerRepository.truncateAnswer();
        answerRepository.enableForeignKeyCheck();
    }

    private void createSampleData() {
        QuestionRepositoryTest.createSampleData(questionRepository);

        Question q = questionRepository.findById(1).orElse(null);

        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판 입니다.");
        a1.setQuestion(q);
        a1.setCreateDate(LocalDateTime.now());
        answerRepository.save(a1);
        //q.getAnswerList().add(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링부트관련 내용을 다룹니다.");
        a2.setQuestion(q);
        a2.setCreateDate(LocalDateTime.now());
        answerRepository.save(a2);
        //q.getAnswerList().add(a2);
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

    @Test
    void 조회() {
        Answer a = answerRepository.findById(1).get();
        assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");
    }

    @Test
    void 관련된_question_조회() {
        Answer a = answerRepository.findById(1).orElse(null);
        Question q = a.getQuestion();

        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback(false)
    void question으로부터_관련된_질문들_조회() {
        // SELECT * FROM question WHERE id = 1
        Question q = questionRepository.findById(1).get();
        // DB 연결이 끊김

        // SELECT * FROM answer WHERE question_id = 1
        List<Answer> answerList = q.getAnswerList();

        assertThat(answerList.size()).isEqualTo(2);
        assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");
    }
}