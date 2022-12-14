package com.ll.exam.sbb.question;

import com.ll.exam.sbb.base.RepositoryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, RepositoryUtil {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String s);

    // 제목 검색
    Page<Question> findBySubjectContains(String kw, Pageable pageable);

    // 제목 + 내용 검색
    Page<Question> findBySubjectContainsOrContentContains(String subject, String content, Pageable pageable);

    // 제목 + 내용 + 작성자 이름 검색
    Page<Question> findBySubjectContainsOrContentContainsOrAuthor_UsernameContains(String subject, String content, String username, Pageable pageable);

    // 질문 제목 + 질문 내용 + 질문작성자명 + 답변내용
    Page<Question> findDistinctBySubjectContainsOrContentContainsOrAuthor_UsernameContainsOrAnswerList_ContentContains(String qSubject, String qContent, String qUsername, String aContent, Pageable pageable);

    // 질문 제목 + 질문 내용 + 질문작성자명 + 답변내용 + 답변 작성자명
    Page<Question> findDistinctBySubjectContainsOrContentContainsOrAuthor_UsernameContainsOrAnswerList_ContentContainsOrAnswerList_Author_UsernameContains(String qSubject, String qContent, String qUsername, String aContent, String aUsername, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이게 실행됨
}