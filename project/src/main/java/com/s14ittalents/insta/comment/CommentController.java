package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.comment.dto.CommentWithRepliesDTO;
import com.s14ittalents.insta.comment.dto.CreateCommentDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CommentController extends AbstractController {
    @Autowired
    private CommentService commentService;

    @GetMapping({"/comments/{id:[0-9]+}"})
    List<CommentWithRepliesDTO> getCommentWithReplies(@RequestBody @PathVariable long id) {
        return commentService.getCommentWithReplies(id);
    }

    @PostMapping("/posts/{postId}")
    CreateCommentDTO createComment(@PathVariable long postId, @RequestBody CreateCommentDTO dto) {
        long userId = getLoggedUserId();
        dto.setOwnerId(userId);
        return commentService.createComment(dto, postId);
    }

    @PostMapping("/comments/{commentId}")
    CreateCommentDTO createReply(@PathVariable long commentId, @RequestBody CreateCommentDTO dto) {
        long userId = getLoggedUserId();
        dto.setOwnerId(userId);
        return commentService.replyComment(dto, commentId);
    }

    @PutMapping("/comments/{commentId}")
    CreateCommentDTO editComment(@PathVariable long commentId, @RequestBody CreateCommentDTO dto) {
        long userId = getLoggedUserId();
        dto.setOwnerId(userId);
        return commentService.editComment(dto, commentId);
    }

    @DeleteMapping("/comments/{commentId}")
    void deleteComment(@PathVariable long commentId) {
        long userId = getLoggedUserId();
        commentService.deleteComment(userId, commentId);
    }

    @PostMapping("/comments/{id:[0-9]+}")
    int likePost(@PathVariable long id) {
        long userId = getLoggedUserId();
        return commentService.likeComment(id, userId);
    }

}
