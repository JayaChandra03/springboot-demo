package com.examples.cmdlinerunner.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Hi";
    }
}
