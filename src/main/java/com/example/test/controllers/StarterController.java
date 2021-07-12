package com.example.test.controllers;

import com.example.test.services.BookService;
import com.sun.javafx.geom.Quat4f;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StarterController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String list(Model model){
        /*PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "dateOfCreation"));*/

        model.addAttribute("books", bookService.findAll());
        return "starter";
    }
}
