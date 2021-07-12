package com.example.test.services;

import com.example.test.entities.Book;
import com.example.test.entities.Chapter;
import com.example.test.entities.Comment;
import com.example.test.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FandomRepository fandomRepository;

    public List<Book> findAllByUserId(Long userId){
        return bookRepository.findByUserId(userId);
    }
    public List<Book> findByFandom(String fandom){return bookRepository.findByFandom(fandom);}
    public List<Book> findByTag(String tag){return bookRepository.findByTags(tag);}

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
