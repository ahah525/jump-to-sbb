package com.ll.exam.sbb.question;

import com.ll.exam.sbb.base.RepositoryUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

// <어떤 Entity의 Repository인지, Key의 type>
public interface QuestionRepository extends JpaRepository<Question, Integer>, RepositoryUtil {

    List<Question> findBySubject(String subject);

    List<Question> findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE question", nativeQuery = true)
    void truncateQuestion();
}
