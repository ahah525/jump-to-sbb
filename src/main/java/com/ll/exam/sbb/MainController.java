package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/sbb")
    @ResponseBody// URL 요청에 대한 응답의 Body에 리턴값을 문자열로 리턴
    public String index() {
        System.out.println("Hello World");
        return "HelloWorld";
    }
}
