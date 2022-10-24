package com.s14ittalents.insta.util;

import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.NoAuthException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.s14ittalents.insta.exception.Constant.REMOTE_IP;

public abstract class AbstractController {
    @Autowired
    protected HttpSession session;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    protected long getLoggedUserId() {
        String ip = request.getRemoteAddr();
        if (session.isNew()) {
            throw new NoAuthException("You have to login");
            //in login set logged true and write the id of user
        }
        if(session.getAttribute("logged") == null){
            throw new NoAuthException("You have to login");
        }
        if (!(session.getAttribute("logged").equals(true))
                || !(session.getAttribute(REMOTE_IP).equals(ip))
                || session.getAttribute("id") == null
                || session.getAttribute("id").equals("")
                || session.getAttribute("id").equals(0)) {
            throw new NoAuthException("You have to login");
        }
        return (long) session.getAttribute("id");
    }
    
    protected String validatePassword(String password){
        if(password.length() < 8){
            throw new BadRequestException("Password must be at least 8 characters long");
        }
        if(password.length() > 20){
            throw new BadRequestException("Password must be at most 20 characters long");
        }
        if(!password.matches(".*[a-z].*")){
            throw new BadRequestException("Password must contain at least one lowercase letter");
        }
        if(!password.matches(".*[A-Z].*")){
            throw new BadRequestException("Password must contain at least one uppercase letter");
        }
        if(!password.matches(".*[0-9].*")){
            throw new BadRequestException("Password must contain at least one digit");
        }
        if(!password.matches(".*[!@#$%^&*()_+].*")){
            throw new BadRequestException("Password must contain at least one special character");
        }
        
        return password.trim();
    }
    
    protected String validateUsername(String username){
        if(username.length() < 2){
            throw new BadRequestException("Username must be at least 2 characters long");
        }
        if(username.length() > 30){
            throw new BadRequestException("Username must be at most 30 characters long");
        }
        if(!username.matches("^[a-z0-9_]+$")){
            throw new BadRequestException("Username must contain only lowercase letters, digits and underscore");
        }
        return username.trim();
    }
    
    protected String validateEmail(String email){
        if(email.length() < 5){
            throw new BadRequestException("Email must be at least 5 characters long");
        }
        if(email.length() > 50) {
            throw new BadRequestException("Email must be at most 50 characters long");
        }
        if(!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2" +
                "(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])" +
                "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b" +
                "\\x0c\\x0e-\\x7f])+)\\])")){
            throw new BadRequestException("Email is not valid");
        }
        return email.trim();
    }
}
