package com.ll.exam.sbb.question;

import com.ll.exam.sbb.DataNotFoundException;
import com.ll.exam.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    public Page<Question> getList(int page, String kw, String order) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (order.equals("new")) {
            sorts.add(Sort.Order.desc("createDate"));
        } else if (order.equals("older")) {
            sorts.add(Sort.Order.asc("createDate"));
        }
//        sorts.add(Sort.Order.desc("id"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

//        return questionRepository.findAll(pageable);
//        return questionRepository.findBySubjectContains(kw, pageable);
//        return questionRepository.findBySubjectContainsOrContentContains(kw, kw, pageable);
//        return questionRepository.findBySubjectContainsOrContentContainsOrAuthor_UsernameContains(kw, kw, kw, pageable);
//        return questionRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_UsernameContainsOrAnswerList_ContentContains(kw, kw, kw, kw, pageable);
        return questionRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_UsernameContainsOrAnswerList_ContentContainsOrAnswerList_Author_UsernameContains(kw, kw, kw, kw, kw, pageable);
    }

    public void save(QuestionForm questionForm, SiteUser siteUser) {
        Question question = Question.builder()
                .subject(questionForm.getSubject())
                .content(questionForm.getContent())
                .createDate(LocalDateTime.now())
                .author(siteUser)
                .build();

        questionRepository.save(question);
    }

    public void modify(Question question, QuestionForm questionForm) {
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setModifyDate(LocalDateTime.now());

        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        questionRepository.save(question);
    }
}