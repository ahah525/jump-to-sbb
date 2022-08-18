package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.AnswerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/create")
    public String createForm(QuestionForm questionForm) {
        // 질문 등록 폼의 th:object 때문에 QuestionForm 객체를 매개변수로 받아야 함
        return "question_form";
    }

    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        // 유효성 검증에서 에러가 발견되면 폼으로 돌아가기
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        // 질문 저장 후 질문 리스트로 리다이렉트
        questionService.save(questionForm);
        return "redirect:/question/list";
    }

    // 해당 페이지 번호의 게시글 리스트 조회
    @GetMapping("/list")
    // 이 자리에 @ResponseBody가 없으면 resources/question_list/question_list.html 파일을 뷰로 삼는다.
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Question> paging = questionService.getList(page);

        // 미래에 실행된 question_list.html 에서
        // questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
        model.addAttribute("paging", paging);

        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 리다이렉트
    @RequestMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
}