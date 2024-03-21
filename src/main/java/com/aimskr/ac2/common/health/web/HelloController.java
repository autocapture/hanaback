package com.aimskr.ac2.common.health.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Hello, carrot insurance! We're ready to serve";
    }
}
