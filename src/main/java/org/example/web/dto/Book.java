package org.example.web.dto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Book {
    private Integer id;
    @NotEmpty
    @NotNull
    private String author;
    @NotEmpty
    @NotNull
    private String title;
    @Digits(integer = 4, fraction = 0)
    private Integer size;

    public Book(){}
    public Book(Integer id, String author, String title, Integer size) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
