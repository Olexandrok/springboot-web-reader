package com.example.test.controllers;

import com.example.test.entities.Chapter;
import com.example.test.repos.ChapterRepository;
import com.example.test.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChapterController {
    @Autowired
    private BookService bookService;

    @Autowired
    private ChapterRepository chapterRepository;

    @GetMapping("/chapter/{id}")
    public String chapter(@PathVariable("id")Long id, Model model){
        Chapter chapter = chapterRepository.getById(id);
        model.addAttribute("chapter", chapter);

        return "chapterPage";
    }

    @GetMapping("/addChapter/{id}")
    public String addChapter(@PathVariable("id") Long id, Model model){
        model.addAttribute("bookId", id);
        return "addChapter";
    }

    @PostMapping("/addChapter/{id}")
    public String addChapter(@PathVariable("id") Long id,
                             @RequestParam("number") int number,
                             @RequestParam("name") String name,
                             @RequestParam("text") String text){
        Chapter chapter = new Chapter(number, name, text);
        bookService.addChapter(chapter, id);
        return "redirect:/book/" + id;
    }

    @GetMapping("/chapter/delete")
    public String deleteChapter(@RequestParam("delete")Long id){
        chapterRepository.delete(chapterRepository.getById(id));
        return "/";
    }

    @GetMapping("/chapter/edit")
    public String editChapter(@RequestParam("edit")Long id, Model model){
        model.addAttribute("chapter", chapterRepository.findById(id));
        return "editChapter";
    }

    @PostMapping("/chapter/edit")
    public String editChapter(@RequestParam("chapter")Long id,
                              @RequestParam("number")int number,
                              @RequestParam("name")String name,
                              @RequestParam("text")String text){
        Chapter chapter = chapterRepository.getById(id);
        chapter.setNumber(number);
        chapter.setName(name);
        chapter.setText(text);
        chapterRepository.save(chapter);
        return "/chapter/" + id;
    }
}
