package com.s14ittalents.insta.comment;

import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import com.s14ittalents.insta.util.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.s14ittalents.insta.exception.Constant.POST_NOT_FOUND;
import static com.s14ittalents.insta.exception.Constant.USER_NOT_FOUND;

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
    
    /*
    public Comment likeComment(long id, HttpSession session) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
        comment.getLikes().add(getUserById((Long) session.getAttribute("id")));
        getUserById((Long) session.getAttribute("id")).getLikedComments().add(comment);
        commentRepository.save(comment);
        userRepository.save(getUserById((Long) session.getAttribute("id")));
        return comment.get();
    }
     */
    public int likeComment(long id, long userId) {
        Optional<Comment> comment = commentRepository.findByIdAndDeletedIsFalse(id);
        if (comment.isEmpty()){
            throw new DataNotFoundException(POST_NOT_FOUND);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new DataNotFoundException(USER_NOT_FOUND);
        }
        
        if(comment.get().getLikes().contains(user.get())) {
            comment.get().getLikes().remove(getUserById(userId));
        } else {
            comment.get().getLikes().add(getUserById(userId));
        }
        comment.get().getLikes().forEach(a ->
                System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
        userRepository.save(getUserById(userId));
        comment.get().getLikes().forEach(a ->
                System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
        return comment.get().getLikes().size();
    }
}
