package org.example.app.services;

import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class LoginService {

    private Logger logger = Logger.getLogger("LoginService");
    public boolean authenicate(LoginForm loginForm){
            logger.info("try auth with user-form: " + loginForm);
            return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("root");
    }
}
