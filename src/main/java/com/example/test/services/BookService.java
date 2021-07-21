package com.example.test.services;

import com.example.test.entities.*;
import com.example.test.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllByUserId(Long userId){
        return bookRepository.findByUserId(userId);
    }

    public List<Book> findByFandom(String fandom){
        List<Book> books = new ArrayList<>();
        for (Book book: bookRepository.findAll()) {
            for (Fandom f : book.getFandom()) {
                if (f.getName().equals(fandom)) {
                    books.add(book);
                }
            }
        }
        return books;
    }

    public List<Book> findByTag(String tag){
        List<Book> books = new ArrayList<>();
        for (Book book: bookRepository.findAll()) {
            for (Tag t : book.getTag()) {
                if (t.getName().equals(tag)) {
                    books.add(book);
                }
            }
        }
        return books;
    }

    public void save(Book book){
        bookRepository.save(book);
    }

    public void addChapter(Chapter chapter, Long id){
        Book book = bookRepository.getById(id);
        book.getChapters().add(chapter);
        bookRepository.save(book);
    }

    public void addComment(Comment comment, Long id){
        Book book = bookRepository.getById(id);
        book.getComments().add(comment);
        bookRepository.save(book);
    }

    public void addTag(Tag tag, Long id){
        Book book = bookRepository.getById(id);
        book.getTag().add(tag);
        bookRepository.save(book);
    }

    public void addFandom(Fandom fandom, Long id){
        Book book = bookRepository.getById(id);
        book.getFandom().add(fandom);
        bookRepository.save(book);
    }

    public Book getById(Long id){
        return bookRepository.getById(id);
    }

    public List<Book> findAll(){
        return  bookRepository.findAll();
    }

    public void delete(Long id){
        bookRepository.delete(bookRepository.getById(id));
    }

    /*public Page<Book> getPage(@PageableDefault(sort = {"dateOfCreation"}, direction = Sort.Direction.DESC)Pageable pageable){
        return bookRepository.findAll(pageable);
    }*/

}
