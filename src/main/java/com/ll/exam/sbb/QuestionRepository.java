package com.ll.exam.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// <어떤 Entity의 Repository인지, Key의 type>
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findBySubject(String subject);

    List<Question> findBySubjectAndContent(String subject, String content);
}
