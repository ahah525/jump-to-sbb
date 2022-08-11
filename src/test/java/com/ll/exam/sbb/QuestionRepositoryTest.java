package com.ll.exam.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    private static int lastSampleDataId;

    @BeforeEach
    void beforeEach() {
        clearData(questionRepository);
        createSampleData(questionRepository);
    }

    public static void clearData(QuestionRepository questionRepository) {
        questionRepository.disableForeignKeyCheck();
        questionRepository.truncateQuestion();
        questionRepository.enableForeignKeyCheck();
    }

    public static void createSampleData(QuestionRepository questionRepository) {
        Question q1 = new Question();
        q1.setSubject("제목1");
        q1.setContent("내용1");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("제목2");
        q2.setContent("내용2");
        q2.setCreateDate(LocalDateTime.now());

        questionRepository.save(q2);

        lastSampleDataId = q2.getId();
    }

    @Test
    void saveQuestion() {
        Question q = new Question();
        q.setSubject("제목3");
        q.setContent("내용3");
        q.setCreateDate(LocalDateTime.now());
        // when
        questionRepository.save(q);
        // then
        Question saveQ = questionRepository.findById(3).orElse(null);
        assertThat(saveQ.getSubject()).isEqualTo("제목3");
        assertThat(saveQ.getContent()).isEqualTo("내용3");
    }

    @Test
    void deleteQuestion() {
        Question q = questionRepository.findById(1).orElse(null);
        // when
        questionRepository.delete(q);
        // then
        assertThat(questionRepository.count()).isEqualTo(1);
    }

    @Test
    void modifyQuestion() {
        Question q = questionRepository.findById(2).orElse(null);
        // when
        q.setSubject("new 제목");
        questionRepository.save(q);
        // then
        Question modifiedQ = questionRepository.findById(2).orElse(null);
        assertThat(modifiedQ.getSubject()).isEqualTo("new 제목");
    }

    @Test
    void findBySubject() {
        // when
        List<Question> questions = questionRepository.findBySubject("제목1");
        // then
        Question q = questions.get(0);
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void findBySubjectAndContent() {
        // when
        List<Question> questions = questionRepository.findBySubjectAndContent("제목2", "내용2");
        // then
        Question q = questions.get(0);
        assertThat(q.getId()).isEqualTo(2);
    }

    @Test
    void findBySubjectLike() {
        // when
        List<Question> questions = questionRepository.findBySubjectLike("%제목%");
        // then
        assertThat(questions.size()).isEqualTo(2);
    }

//	@Test
//	void findByIdJpa() {
//		Optional<Question> oq = questionRepository.findById(1);
//		Question question = oq.orElse(null);
//		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
//	}
//
//	@Test
//	void saveJpa() {
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		questionRepository.save(q1);  // 첫번째 질문 저장
//
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		questionRepository.save(q2);  // 두번째 질문 저장
//
//		assertThat(q1.getId()).isGreaterThan(0);
//		assertThat(q2.getId()).isGreaterThan(q1.getId());
//	}
//
//	@Test
//	void findAllJpa() {
//		List<Question> all = questionRepository.findAll();
//		assertThat(all.size()).isEqualTo(2);
//
//		Question q = all.get(0);
//		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
//	}
//
//	@Test
//	void findBySubjectJpa() {
//		List<Question> questions = questionRepository.findBySubject("sbb가 무엇인가요?");
//		assertThat(questions.get(0).getId()).isEqualTo(1);
//	}
//
//	@Test
//	void findBySubjectAndContentJpa() {
//		List<Question> questions = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
//		assertThat(questions.get(0).getId()).isEqualTo(1);
//	}
//
//	@Test
//	void findBySubjectLikeJpa() {
//		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
//		Question q = qList.get(0);
//		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
//	}
//
//	@Test
//	void modifyJpa() {
//		Optional<Question> oq = questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question question = oq.orElse(null);
//		question.setSubject("수정된 제목");
//		questionRepository.save(question);
//	}
//
//	@Test
//	void deleteJpa() {
//		Optional<Question> oq = questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question question = oq.orElse(null);
//		questionRepository.delete(question);
//		assertThat(questionRepository.count()).isEqualTo(3);
//	}
//
//	@Test
//	void truncateQuestion() {
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		questionRepository.save(q1);  // 첫번째 질문 저장
//
//		questionRepository.disableForeignKeyCheck();
//		questionRepository.truncateQuestion();
//		questionRepository.enableForeignKeyCheck();
//	}
}