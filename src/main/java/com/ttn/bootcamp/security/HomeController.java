package com.ttn.bootcamp.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    public String home(){
        return "Home Page.";
    }
}
