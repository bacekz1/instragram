package com.s14ittalents.insta.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/post/{id}")
    CreateCommentDTO createComment(@PathVariable long id, @RequestBody CreateCommentDTO dto, HttpSession session) {
        int userId = 2;
        dto.setOwnerId(userId);
        dto.setPostId(id);
        return commentService.createComment(dto);
    }

}
