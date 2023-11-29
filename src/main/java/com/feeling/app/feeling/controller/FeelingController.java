package com.feeling.app.feeling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feelings")
public class FeelingController {
    @GetMapping("")
    public String get() {
        return "";
    }
}
