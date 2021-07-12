package com.example.test.controllers;

import com.example.test.entities.User;
import com.example.test.services.BookService;
import com.example.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookService bookService;

    @GetMapping("/user")
    public String user(Principal principal, Model model){
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("books", bookService.findAllByUserId(user.getId()));
        return "user";
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable("id") Long id, Model model){
        User user = (User) userService.loadByUserId(id);
        model.addAttribute("user", user);
        model.addAttribute("books", bookService.findAllByUserId(id));
        return "user";
    }

    @GetMapping("/user/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        User user = userService.loadByUserId(id);
        model.addAttribute("user", user);
        return "/editUser";
    }

    @PostMapping("/user/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       @RequestParam("name") String name,
                       @RequestParam("username") String username,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password,
                       @RequestParam("image") MultipartFile image) throws IOException {
        User user = userService.loadByUserId(id);
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setImage(image.getBytes());
        userService.save(user);

        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/login";
    }
}
