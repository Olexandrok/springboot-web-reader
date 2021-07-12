package com.example.test.controllers;

import com.example.test.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/fandom")
    public String listFandoms(@RequestParam("fandom")String fandom, Model model){
        model.addAttribute("books", bookService.findByFandom(fandom));
        return "starter";
    }

    @GetMapping("/tag")
    public String listTags(@RequestParam("tag")String tag, Model model){
        model.addAttribute("books", bookService.findByTag(tag));
        return "starter";
    }
}
