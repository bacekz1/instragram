package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.DataNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
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

    public Comment getComment(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
    }
}
