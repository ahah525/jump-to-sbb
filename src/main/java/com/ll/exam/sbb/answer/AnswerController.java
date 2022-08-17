package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String save(Model model, @PathVariable("id") int questionId, @Valid AnswerForm answerForm, BindingResult bindingResult) {
        Question question = questionService.getQuestion(questionId);

        // 필수 입력 폼 유효성 검증
        if ( bindingResult.hasErrors() ) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        answerService.save(question, answerForm.getContent());
       return String.format("redirect:/question/detail/%s", questionId);
    }
}
