package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.exception.DataNotFoundException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
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

//    @PostMapping("/post{id}")
//    CreateCommentDTO createComment(@PathVariable long id, @RequestBody CreateCommentDTO comment) {
//        return commentRepository.save(comment);
//    }

}
