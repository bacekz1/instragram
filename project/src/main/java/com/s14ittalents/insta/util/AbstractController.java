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
    
}
