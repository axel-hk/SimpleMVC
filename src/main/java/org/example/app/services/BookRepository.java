package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        repo.add(book);
    }

    @Override
    public void removeItemById(Integer id) {
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
}
