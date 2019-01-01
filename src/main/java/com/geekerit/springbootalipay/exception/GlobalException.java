package com.geekerit.springbootalipay.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView testException2(Exception ex){
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",ex.toString());
        mv.setViewName("error");
        return mv;
    }
}
