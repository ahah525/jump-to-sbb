package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    private int n = 0;

    @RequestMapping("/sbb")
    @ResponseBody// URL 요청에 대한 응답의 Body에 리턴값을 문자열로 리턴
    public String index() {
        System.out.println("Hello World");
        return "HelloWorld";
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
}
