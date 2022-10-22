package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.AbstractCollection;
import java.util.List;


@RestController
public class CommentController extends AbstractController {
    @Autowired
    private CommentService commentService;
//
//    @GetMapping({"/{id:[0-9]+}"})
//    Comment getComment(@RequestBody @PathVariable long id) {
//        return commentService.getComment(id);
//    }

    @GetMapping({"/comment/{id:[0-9]+}"})
    List<CommentWithRepliesDTO> getCommentWithReplies(@RequestBody @PathVariable long id) {
        return commentService.getCommentWithReplies(id);
    }
    
    @PostMapping("/comment/{id:[0-9]+}")
    int likePost(@PathVariable long id) {
        long userId = getLoggedUserId();
        if(userId<=0){
            throw new NoAuthException("You are not logged in");
        }else {
            return commentService.likeComment(id, userId);
        }
    }
    @PostMapping("/post/{id}")
    CreateCommentDTO createComment(@PathVariable long id, @RequestBody CreateCommentDTO dto, HttpSession session) {
        int userId = 2;
        dto.setOwnerId(userId);
        dto.setPostId(id);
        return commentService.createComment(dto);
    }
    
    
}
