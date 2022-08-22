package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionService;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String save(Model model, @PathVariable("id") Long questionId, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = questionService.getQuestion(questionId);
        SiteUser siteUser = userService.getUser(principal.getName());

        // 필수 입력 폼 유효성 검증
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        answerService.save(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", questionId);
    }

    // 답변 수정폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable("id") Long id, AnswerForm answerForm, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        // 글쓴이
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        // 기존값 담기
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    // 답변 수정 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerService.modify(answerForm, answer);

        return "redirect:/question/detail/%d".formatted(answer.getQuestion().getId());
    }

}
