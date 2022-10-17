package com.s14ittalents.insta.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
//
//    @GetMapping({"/{id:[0-9]+}"})
//    Comment getComment(@RequestBody @PathVariable long id) {
//        return commentService.getComment(id);
//    }

    @GetMapping({"/{id:[0-9]+}"})
    List<CommentWithRepliesDTO> getCommentWithReplies(@RequestBody @PathVariable long id) {
        return commentService.getCommentWithReplies(id);
    }
//
//    @PostMapping
//    Comment createComment(@RequestBody Comment comment) {
//        return commentRepository.save(comment);
//    }

}
