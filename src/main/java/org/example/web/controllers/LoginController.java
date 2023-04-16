package org.example.web.controllers;


import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
@RequestMapping(value = "login" )
public class LoginController {

    private Logger logger = Logger.getLogger("HomeController");

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(){
        logger.info("Get /home returns index.html");
        return new ModelAndView("index");
    }

    @GetMapping()
    public ModelAndView login(Model model){
        logger.info("Get /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return new ModelAndView("login_page");
    }

    @PostMapping("/auth")
    public String authenticate(LoginForm loginForm) throws BookShelfLoginException {
        if(loginService.authenicate(loginForm)){
            logger.info("login OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else{
            logger.info("login FAIL redirect to login");
            throw new BookShelfLoginException("invalid username or password");
        }
    }

}
