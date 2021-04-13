package com.CoreRopeMemory.TAPortal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ApplicationController {

    @GetMapping({"/", "/index"})
    public String hello(Model model){
        model.addAttribute("helloWorld", "Hello World!");
        return "index";
    }
}
