package com.copperbrass.practice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class errorController {

    // 일반적인 예외 처리
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", "An unexpected error occurred.");
        model.addAttribute("error", ex.getMessage());
        return "error"; // error.html로 이동
    }

    // 404 에러 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(Exception ex, Model model) {
        model.addAttribute("message", "Page not found.");
        return "error/404"; // error/404.html로 이동
    }
}
