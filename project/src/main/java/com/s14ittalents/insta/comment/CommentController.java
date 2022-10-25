package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.comment.dto.CommentWithRepliesDTO;
import com.s14ittalents.insta.comment.dto.CreateCommentDTO;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CommentController extends AbstractController {
    @Autowired
    private CommentService commentService;

    @GetMapping({"/comment/{id:[0-9]+}"})
    List<CommentWithRepliesDTO> getCommentWithReplies(@RequestBody @PathVariable long id) {
        return commentService.getCommentWithReplies(id);
    }

    @PostMapping("/post/{postId}")
    CreateCommentDTO createComment(@PathVariable long postId, @RequestBody CreateCommentDTO dto) {
        long userId = getLoggedUserId();
        dto.setOwnerId(userId);
        return commentService.createComment(dto, postId);
    }

    @PostMapping("/post/{postId}/comment/{commentId}")
    CreateCommentDTO createReply(@PathVariable long postId, @PathVariable long commentId,
                                 @RequestBody CreateCommentDTO dto) {
        long userId = getLoggedUserId();
        dto.setOwnerId(userId);
        return commentService.replyComment(dto, postId, commentId);
    }

    @PutMapping("/comment/{commentId}")
    CreateCommentDTO editComment(@PathVariable long commentId, @RequestBody CreateCommentDTO dto) {
        long userId = getLoggedUserId();
        dto.setOwnerId(userId);
        return commentService.editComment(dto, commentId);
    }

    @DeleteMapping("/comment/{commentId}")
    void deleteComment(@PathVariable long commentId) {
        long userId = getLoggedUserId();
        commentService.deleteComment(userId, commentId);
    }

    @PostMapping("/comment/{id:[0-9]+}")
    int likePost(@PathVariable long id) {
        long userId = getLoggedUserId();
        if (userId <= 0) {
            throw new NoAuthException("You are not logged in");
        } else {
            return commentService.likeComment(id, userId);
        }
    }


}
