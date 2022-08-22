package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.DataNotFoundException;
import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void save(Question question, String content, SiteUser siteUser) {
        Answer answer = Answer.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .question(question)
                .author(siteUser)
                .build();
        answerRepository.save(answer);
    }

    public Answer getAnswer(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("answer not found");
        });
        return answer;
    }

    public void modify(AnswerForm answerForm, Answer answer) {
        answer.setContent(answerForm.getContent());
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
