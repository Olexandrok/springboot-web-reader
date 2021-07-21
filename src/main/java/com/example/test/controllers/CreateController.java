package com.example.test.controllers;

import com.example.test.entities.Book;
import com.example.test.entities.Fandom;
import com.example.test.entities.Tag;
import com.example.test.services.BookService;
import com.example.test.services.FandomService;
import com.example.test.services.TagService;
import com.example.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class CreateController {

    @Autowired
    private TagService tagService;

    @Autowired
    private FandomService fandomService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/create/{id}")
    public String create(@PathVariable("id") Long id, Model model){
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        List<Fandom> fandoms = fandomService.findAll();
        model.addAttribute("fandoms", fandoms);
        model.addAttribute("id", id);
        return "createBook";
    }

    @PostMapping("/create/{id}")
    public String createNewBook(@PathVariable("id") Long id,
                                @RequestParam("name")String name,
                                @RequestParam("image") MultipartFile image,
                                @RequestParam("fandom")String fandom,
                                @RequestParam("tag")String tag,
                                @RequestParam("preview")String preview) throws IOException {
        Set<String> fandoms = new HashSet<String>(Arrays.asList(fandom.split(" ")));
        Set<String> tags = new HashSet<String>(Arrays.asList(tag.split(" ")));
        Book book = new Book();
        book.setName(name);
        book.setImage(image.getBytes());
        book.setPreview(preview);
        book.setUserId(id);
        book.setAuthor(userService.getById(id).getName());
        bookService.save(book);
        for (String tmp : fandoms) {
            bookService.addFandom(new Fandom(tmp), book.getId());
        }
        for (String tmp: tags) {
            bookService.addTag(new Tag(tmp), book.getId());
        }
        return "redirect:/book/" + book.getId();
    }
}
