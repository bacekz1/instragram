package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.util.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService extends AbstractService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CommentRepository commentRepository;

    List<CommentWithRepliesDTO> getCommentWithReplies(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        comment.orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
        return comment.stream()
                .map(comment1 -> modelMapper.map(comment1, CommentWithRepliesDTO.class))
                .collect(Collectors.toList());
    }
//
//    public Comment getComment(long id) {
//        return commentRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
//    }

    public CreateCommentDTO createComment(CreateCommentDTO dto) {
        Comment comment = modelMapper.map(dto, Comment.class);
        Comment createdComment = commentRepository.save(comment);
        return modelMapper.map(createdComment, CreateCommentDTO.class);
    }
    
    public Comment likeComment(long id, HttpSession session) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
        comment.getLikes().add(getUserById((Long) session.getAttribute("id")));
        getUserById((Long) session.getAttribute("id")).getLikedComments().add(comment);
        commentRepository.save(comment);
        userRepository.save(getUserById((Long) session.getAttribute("id")));
        return comment.get();
    }
}
