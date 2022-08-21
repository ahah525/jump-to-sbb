package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionService;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    // Principal : 로그인한 사용자 정보
    @PostMapping("/create/{id}")
    public String save(Model model, @PathVariable("id") Long questionId, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = questionService.getQuestion(questionId);
        SiteUser siteUser = userService.getUser(principal.getName());

        // 필수 입력 폼 유효성 검증
        if ( bindingResult.hasErrors() ) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        answerService.save(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", questionId);
    }
}
