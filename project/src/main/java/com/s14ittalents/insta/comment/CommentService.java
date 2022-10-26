package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.comment.dto.CommentWithRepliesDTO;
import com.s14ittalents.insta.comment.dto.CreateCommentDTO;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.dto.UserWithoutPostsDTO;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.s14ittalents.insta.exception.Constant.*;

@Service
public class CommentService extends AbstractService {

    List<CommentWithRepliesDTO> getCommentWithReplies(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        comment.orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
        return comment.stream()
                .map(comment1 -> modelMapper.map(comment1, CommentWithRepliesDTO.class))
                .collect(Collectors.toList());
    }

    public CreateCommentDTO createComment(CreateCommentDTO dto, long postId) {
        Comment comment = modelMapper.map(dto, Comment.class);
        comment.setOwnerId(dto.getOwnerId());
        System.out.println(postId);
        Post post = findPost(postId);
        comment.setCreatedAt(LocalDateTime.now());
        addHashtags(post, comment);
        addPersonTags(post, comment);
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        return modelMapper.map(createdComment, CreateCommentDTO.class);
    }

    public CreateCommentDTO replyComment(CreateCommentDTO dto, long postId, long commentId) {
        Comment comment = modelMapper.map(dto, Comment.class);
        Comment replyComment = findComment(commentId);
        if (replyComment.ownerId() == dto.getOwnerId()) {
            throw new BadRequestException(CAN_NOT_REPLY_YOURSELF);
        }
        if (replyComment.replies.stream().anyMatch(c -> c.getOwnerId() == dto.getOwnerId())) {
            throw new BadRequestException(YOU_ALREADY_REPLY_THIS_COMMENT);
        }
        comment.setOwnerId(dto.getOwnerId());
        Post post = findPost(postId);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setReplyId(replyComment);
        addHashtags(post, comment);
        addPersonTags(post, comment);
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        return modelMapper.map(createdComment, CreateCommentDTO.class);
    }

    public CreateCommentDTO editComment(CreateCommentDTO dto, long commentId) {
        Comment comment = findComment(commentId);
        checkPermission(dto.getOwnerId(), comment);
        Post post = comment.getPost();
        post.getComments().remove(comment);
        comment.setComment(dto.getComment());
        post.getComments().add(comment);
        post.getHashtags().clear();
        post.getPersonTags().clear();
        addHashtags(post);
        addPersonTags(post);
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        return modelMapper.map(createdComment, CreateCommentDTO.class);
    }

    public void deleteComment(long userId, long commentId) {
        Comment comment = findComment(commentId);
        checkPermission(userId, comment);
        deleteComment(comment);
        if (comment.getReplies() != null) {
            comment.getReplies().forEach(this::deleteComment);
        }
        commentRepository.save(comment);
    }

    public int likeComment(long id, long userId) {
        Comment comment = findComment(id);
        User user = getUserById(userId);

        if (user.getLikedComments().contains(comment)) {
            user.getLikedComments().remove(comment);
            comment.getLikes().remove(user);
        } else {
            user.getLikedComments().add(comment);
            comment.getLikes().add(user);
        }
        userRepository.save(user);

        return comment.get().getLikes().size();
    }

    private void deleteComment(Comment comment) {
        comment.setDeleted(true);
        comment.setComment("deleted at" + LocalDateTime.now());
    }

}
