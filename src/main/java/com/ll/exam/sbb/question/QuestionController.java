package com.ll.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping("/list")
    // 이 자리에 @ResponseBody가 없으면 resources/question_list/question_list.html 파일을 뷰로 삼는다.
    public String list(Model model) {
        List<Question> questionList = questionService.getList();

        // 미래에 실행된 question_list.html 에서
        // questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 리다이렉트
    @RequestMapping("/")
    public String root() {
        System.out.println("dd");
        return "redirect:/question/list";
    }
}