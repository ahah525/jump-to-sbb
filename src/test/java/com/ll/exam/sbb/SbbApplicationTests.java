package com.ll.exam.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void saveJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		assertThat(q1.getId()).isGreaterThan(0);
		assertThat(q2.getId()).isGreaterThan(q1.getId());
	}

	@Test
	void findAllJpa() {
		List<Question> all = questionRepository.findAll();
		assertThat(all.size()).isEqualTo(2);

		Question q = all.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	void findByIdJpa() {
		Optional<Question> oq = questionRepository.findById(1);
		Question question = oq.orElse(null);
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	void findBySubjectJpa() {
		List<Question> questions = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertThat(questions.get(0).getId()).isEqualTo(1);
	}

	@Test
	void findBySubjectAndContentJpa() {
		List<Question> questions = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertThat(questions.get(0).getId()).isEqualTo(1);
	}

	@Test
	void findBySubjectLikeJpa() {
		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	void modifyJpa() {
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question question = oq.orElse(null);
		question.setSubject("수정된 제목");
		questionRepository.save(question);
	}

	@Test
	void deleteJpa() {
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question question = oq.orElse(null);
		questionRepository.delete(question);
		assertThat(questionRepository.count()).isEqualTo(3);
	}

	@Test
	void truncateQuestion() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		questionRepository.disableForeignKeyCheck();
		questionRepository.truncateTable();
		questionRepository.enableForeignKeyCheck();
	}
}
