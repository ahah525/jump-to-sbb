package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {
    private int n = 0;
    private int last_id = 0;
    private List<Article> articles = new ArrayList<>();

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
        // 스트림 적용
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(dan, i, dan * i))
                .collect(Collectors.joining("<br>\n"));
//        StringBuilder sb = new StringBuilder();
//        for (int i = 1; i <= limit; i++) {
//            sb.append("%d * %d = %d".formatted(dan, i, dan * i));
//            sb.append("<br>");
//        }
//        return sb.toString();
    }

    @GetMapping("/mbti")
    @ResponseBody
    public String showMbti(@RequestParam("name") String name) {
        return switch (name) {
            case "홍길동" -> "INFP";
            case "홍길순" -> "ENFP";
            case "임꺽정" -> "INFJ";
            default -> "???";
        };
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

    // 게시글 등록
    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(@RequestParam("title") String title, @RequestParam("body") String body) {
        Article article = new Article(title, body);
        articles.add(article);

        return "%d번 글이 등록되었습니다.".formatted(article.getId());
    }

    // 게시글 조회
    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable("id") int id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findAny()
                .orElse(null);
//        for (Article article : articles) {
//            if (article.getId() == id) {
//                return article;
//            }
//        }
//        return null;
    }

    // 게시글 수정
    @GetMapping("/modifyArticle")
    @ResponseBody
    public String modifyArticle(@RequestParam("id") int id, @RequestParam("title") String title, @RequestParam("body") String body) {
        Article findArticle = articles.stream()
                .filter(article -> article.getId() == id)
                .findAny()
                .orElse(null);
        if (findArticle != null) {
            findArticle.setId(id);
            findArticle.setTitle(title);
            findArticle.setBody(body);
            return "%d번 게시물이 수정되었습니다.".formatted(id);
        }
        return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }

    // 게시글 삭제
    @GetMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(@RequestParam("id") int id) {
        Article findArticle = articles.stream()
                .filter(article -> article.getId() == id)
                .findAny()
                .orElse(null);
        if (findArticle != null) {
            articles.remove(findArticle);
            return "%d번 게시물이 삭제되었습니다.".formatted(id);
        }
        return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }
}
