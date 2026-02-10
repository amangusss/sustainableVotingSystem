package com.github.amangusss.sustainable_voting_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin-login";
    }
}

