package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    Logger logger = Logger.getLogger(BookRepository.class);
    private ApplicationContext context;

    private void defaultInit(){
        logger.info("provider INIT in book repo");
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void defaultDestroy(){
        logger.info("provider DESTROY in book repo");
    }


    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("select * from books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        //book.setId(context.getBean(IdProvider.class).provideId(book));
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValues(Map.of("author", book.getAuthor(),
                        "title", book.getTitle(),
                        "size", book.getSize()));
        jdbcTemplate.update("INSERT INTO books(author, title, size) values(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
        // repo.add(book);
    }

    @Override
    public void removeItemById(Integer id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);

    }

    @Override
    public void removeByRegexp(List<Book> books){

        for (Book book: books
             ) {
           removeItemById(book.getId());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
