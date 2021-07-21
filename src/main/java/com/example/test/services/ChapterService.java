package com.example.test.services;

import com.example.test.entities.Chapter;
import com.example.test.repos.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    public List<Chapter> findAll(){
        return chapterRepository.findAll();
    }

    public Chapter getById(Long id){
        return chapterRepository.getById(id);
    }

    public void delete(Chapter chapter){
        chapterRepository.delete(chapter);
    }

    public void save(Chapter chapter){
        chapterRepository.save(chapter);
    }
}
