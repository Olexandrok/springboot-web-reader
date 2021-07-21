package com.example.test.services;

import com.example.test.entities.Fandom;
import com.example.test.repos.FandomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FandomService {
    @Autowired
    private FandomRepository fandomRepository;

    public List<Fandom> findAll(){
        return fandomRepository.findAll();
    }
}
