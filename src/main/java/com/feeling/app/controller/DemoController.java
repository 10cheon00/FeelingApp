package com.feeling.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller는 View를 반환한다. 그런데 Data만 반환해야할 때가 있다.
// 그럴 때 함수에 @ResponseBody를 사용한다.
// 귀찮으니까, Data만 반환할 컨트롤러는 @RestController만 써도 된다.
@RestController
@RequestMapping("/api/user")
public class DemoController {
    @GetMapping("/")
    public Data getData() {
        Data d = new Data();
        d.setName("asdf");
        return d;
    }

    private class Data {
        private String name;
        private Integer number = 1;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getNumber() { return number; }
    }
}
