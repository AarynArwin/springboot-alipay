package com.geekerit.springbootalipay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

    @RequestMapping(value = "/show")
    public String testException(){
        String str = null;
        str.toCharArray();
        return "upload";
    }


}
