package org.ydc.system.system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class textController {

    @GetMapping("/newUserShow")
    public String newUserShow(){
        return "/user/newUserShow";
    }

    @GetMapping("/text")
    public String text(){
        return "/text";
    }
}
