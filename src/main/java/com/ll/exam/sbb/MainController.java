package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    private int n = 0;

    @RequestMapping("/sbb")
    @ResponseBody// URL 요청에 대한 응답의 Body에 리턴값을 문자열로 리턴
    public String index() {
        System.out.println("Hello World");
        return "HelloWorld";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/plus")
    @ResponseBody
    public int showPlus(@RequestParam(value = "a", defaultValue = "0") int a, @RequestParam(value = "b", defaultValue = "0") int b) {
        return a + b;
    }

    @GetMapping("/minus")
    @ResponseBody
    public int showMinus(@RequestParam(value = "a", defaultValue = "0") int a, @RequestParam(value = "b", defaultValue = "0") int b) {
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        return n++;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudan(@RequestParam(defaultValue = "2") int dan, @RequestParam(defaultValue = "9") int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= limit; i++) {
            sb.append("%d * %d = %d".formatted(dan, i, dan * i));
            sb.append("<br>");
        }
        return sb.toString();
    }

    @GetMapping("/mbti")
    @ResponseBody
    public String showMbti(@RequestParam("name") String name) {
        switch (name) {
            case "홍길동":
                return "INFP";
            case "홍길순":
                return "ENFP";
            case "임꺽정":
                return "INFJ";
            default:
                return "???";
        }
    }

    // 세션 저장
    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable("name") String name, @PathVariable("value") String value, HttpSession session) {
        session.setAttribute(name, value);
        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    // 세션 정보 얻기
    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable("name") String name, HttpSession session) {
        String value = (String) session.getAttribute(name);
        return "세션변수 %s의 값이 %s 입니다.".formatted(name, value);
    }
}
