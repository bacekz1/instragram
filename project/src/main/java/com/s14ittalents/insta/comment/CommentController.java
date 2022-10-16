package com.s14ittalents.insta.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping({"/{id:[0-9]+}"})
    Comment getComment(@PathVariable long id) {
        return commentRepository.findById(id);
    }

    @PostMapping
    Comment createComment(@RequestBody Comment comment) {
        System.out.println(comment.getReply_id());
        return commentRepository.save(comment);
    }

}
