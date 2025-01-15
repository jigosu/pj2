package com.copperbrass.practice.user;
import jakarta.validation.Valid;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult,Model model) {
    	if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (msg1, msg2) -> msg1 + "\n" + msg2); // 앞단에 보낼 메시지
            
            // 내부에서 볼 에러내용 
            bindingResult.getAllErrors().forEach(error -> {
    	    System.out.println("Error: " + error.getDefaultMessage());
    	    
    	    
            });
            
            model.addAttribute("errorMessage", errorMessage);
            return "signup_form";
    	}

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "signup_form";
        }

        userService.create(userCreateForm.getUsername(), 
                userCreateForm.getEmail(), userCreateForm.getPassword1());
        return "redirect:/copperbrass";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }    
    


}
