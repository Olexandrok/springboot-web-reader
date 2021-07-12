package com.example.test.controllers;

import com.example.test.entities.Role;
import com.example.test.entities.User;
import com.example.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("username") String username,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password,
                          @RequestParam("image") MultipartFile image,
                          HttpServletRequest request) throws IOException, MessagingException {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.USER);
        user.setImage(image.getBytes());

        userService.register(user, request);

        return "verification";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code){
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}
