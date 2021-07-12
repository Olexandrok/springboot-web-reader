package com.example.test.controllers;

import com.example.test.entities.Book;
import com.example.test.entities.Fandom;
import com.example.test.entities.Tag;
import com.example.test.repos.BookRepository;
import com.example.test.repos.FandomRepository;
import com.example.test.repos.TagRepository;
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
    private TagRepository tagRepository;

    @Autowired
    private FandomRepository fandomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/create/{id}")
    public String create(@PathVariable("id") Long id, Model model){
        List<Tag> tags = tagRepository.findAll();
        model.addAttribute("tags", tags);
        List<Fandom> fandoms = fandomRepository.findAll();
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
        for (String tmp : fandoms) {
            if (!fandomRepository.findAll().toString().contains(tmp)) {
                fandomRepository.save(new Fandom(tmp));
            }
        }
        book.setFandom(fandoms);
        for (String tmp: tags) {
            if (!tagRepository.findAll().toString().contains(tmp)) {
                tagRepository.save(new Tag(tmp));
            }
        }
        book.setTags(tags);
        book.setPreview(preview);
        book.setUserId(id);
        book.setAuthor(userService.getById(id).getName());
        bookRepository.save(book);
        return "redirect:/book/" + book.getId();
    }
}
