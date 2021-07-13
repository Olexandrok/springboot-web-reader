package com.example.test.controllers;

import com.example.test.entities.*;
import com.example.test.services.BookService;
import com.example.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/book/{id}")
    public String book(@PathVariable("id")Long id, Model model, Principal principal) throws UnsupportedEncodingException {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("id", id);

        List<Chapter> chapterList = new ArrayList<>();

        model.addAttribute("chapters", book.getChapters());

        try {
            if (userService.loadUserByUsername(principal.getName()).getId().equals(book.getUserId())
                    || userService.loadUserByUsername(principal.getName()).getRole() == Role.ADMIN)
                return "bookPageEditable";
            else
                return "bookPage";
        } catch (NullPointerException n){
            return "bookPage";
        }

    }

    @GetMapping("/book/{id}/delete")
    public String book(@PathVariable("id")Long id){
        bookService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/comments")
    public String comments(@PathVariable("id") Long bookId, Model model) {
        model.addAttribute("id", bookId);
        model.addAttribute("comments", bookService.getById(bookId).getComments());

        return "comments";
    }

    @PostMapping("/{id}/addComment")
    public String addComment(@PathVariable("id") Long bookId, Principal principal, String text) throws UnsupportedEncodingException {

        User user = userService.loadByUserId(userService.loadUserByUsername(principal.getName()).getId());

        Comment comment = new Comment(user.getName(), text, bookId, user.getId(), user.getImage());
        bookService.addComment(comment, bookId);

        return "redirect:/" + bookId + "/comments";
    }


}
