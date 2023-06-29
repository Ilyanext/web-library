package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("appInfo")
@RequestMapping("/appInfo")
public class InfoController {
    @Value("${app.env}")
    public String appEnv;
    @Value("${dev}")
    public String dev;

    @GetMapping("/")
    public void GetString() {
        System.out.println(dev);
    }
}
