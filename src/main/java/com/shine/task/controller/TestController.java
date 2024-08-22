package com.shine.task.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TestController {

    @GetMapping("/connect-test")
    public String Test() {
        return "스프링 부트 & 뷰 연동 테스트";
    }
}
