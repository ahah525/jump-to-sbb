package com.ll.exam.sbb.question;

import com.ll.exam.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question getQuestion(int id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10); // 한 페이지에 10까지 가능
        return this.questionRepository.findAll(pageable);
    }

    public void save(QuestionForm questionForm) {
        Question question = Question.builder()
                .subject(questionForm.getSubject())
                .content(questionForm.getContent())
                .createDate(LocalDateTime.now())
                .build();
        questionRepository.save(question);
    }
}