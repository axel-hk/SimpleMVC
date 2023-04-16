package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    Logger logger = Logger.getLogger(BookRepository.class);
    private ApplicationContext context;
    private final List<Book> repo = new ArrayList<>();

    private void defaultInit(){
        logger.info("provider INIT in book repo");
    }

    private void defaultDestroy(){
        logger.info("provider DESTROY in book repo");
    }


    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        repo.add(book);
    }

    @Override
    public void removeItemById(String id) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(id)) {
                repo.remove(book);
            }

        }
    }

    @Override
    public void removeByRegexp(List<Book> books){
            repo.removeAll(books);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
