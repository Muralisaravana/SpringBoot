package com.example.SpringSecurityDemo.Security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/hello")
    public String publicHello() { return "Hello - public end"; }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userHello() { return "Hello user"; }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHello() { return "Hello admin"; }
}

