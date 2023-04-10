package com.mycompany.app.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeRestController {

    @GetMapping("/id")
    public String example(){
        return "response";
    }

}
