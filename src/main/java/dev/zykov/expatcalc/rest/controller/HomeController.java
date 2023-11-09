package dev.zykov.expatcalc.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String hello() {
        return "Welcome!";
    }

    @GetMapping("/api")
    public Object home() {
        return "This works";
    }

}
