package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    Logger logger = Logger.getLogger(BookService.class);

    private void defaultInit(){
        logger.info("provider INIT in  BookService");
    }

    private void defaultDestroy(){
        logger.info("provider DESTROY in  BookService");
    }

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks(){
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book){
        if(book.getAuthor() != null || book.getSize() != null || book.getTitle() != null) {
            bookRepo.store(book);
        }
    }

    public void removeBookById(Integer bookIdToRemove){
        if(getAllBooks().stream().anyMatch(e-> Objects.equals(e.getId(), bookIdToRemove)))
            bookRepo.removeItemById(bookIdToRemove);

    }

    public void removeByRegexp(String regex){
        bookRepo.removeByRegexp(regex);
    }
}
