package com.ll.exam.sbb.question;

import com.ll.exam.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question getQuestion(int id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    public List<Question> getList() {
        return questionRepository.findAll();
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