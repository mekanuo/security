package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CsrfController {

    @GetMapping("/toupdate")
    public String test(Model model){
        return "csrf/csrfTest";
    }

    @PostMapping
    public String getToken(){
        return "csrf/csrf_token";
    }
}
