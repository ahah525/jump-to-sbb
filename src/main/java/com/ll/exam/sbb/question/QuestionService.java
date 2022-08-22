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

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
//        sorts.add(Sort.Order.desc("id"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        return questionRepository.findAll(pageable);
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
}