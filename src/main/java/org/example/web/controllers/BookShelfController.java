package org.example.web.controllers;

import org.apache.commons.fileupload.FileUploadBase;
import  org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.BookRegexToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.*;
import java.util.regex.PatternSyntaxException;

@Controller
@RequestMapping(value = "books")
@Scope("session")
public class BookShelfController {
    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model){
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookRegexToRemove", new BookRegexToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookRegexToRemove", new BookRegexToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            bookService.saveBook(book);
            logger.info("current repository contents: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookRegexToRemove", new BookRegexToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());

            return "redirect:/books/shelf";
        }

    }

    @PostMapping("/removeByRegex")
    public String removeByRegex(@Valid BookRegexToRemove bookRegexToRemove, BindingResult bindingResult, Model model) throws PatternSyntaxException {
        if(bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeByRegexp(bookRegexToRemove.getRegex());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
            String name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            //create dir
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "externalUploads");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("now file saved at: " + serverFile.getAbsolutePath());

            return "redirect:/books/shelf";

    }

    @ExceptionHandler(IOException.class)
    public String handleException(IOException e) {
        return "redirect:/books/shelf";
    }

    @ExceptionHandler(PatternSyntaxException.class)
    public String handleRegex(PatternSyntaxException p, BindingResult bindingResult, Model model){
            bindingResult.addError(new ObjectError(p.getClass().getName(), "Invalid regex"));
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
    }




}
