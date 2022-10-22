package com.s14ittalents.insta.util;

import com.s14ittalents.insta.comment.CommentRepository;
import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.hashtag.Hashtag;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostRepository;
import com.s14ittalents.insta.hashtag.HashtagRepository;
import com.s14ittalents.insta.post.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.s14ittalents.insta.exception.Constant.REMOTE_IP;

@Service
public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected HashtagRepository hashtagRepository;

    @Autowired
    protected ModelMapper modelMapper;
    
    protected User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND));
    }

    protected Post findPost(long id) {
        return postRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.POST_NOT_FOUND));
    }

    public void addPersonTags(Post post) {
        List<User> users = Helper.findPersonTags(post.getCaption()).stream()
                .map(tag -> userRepository.findByUsername(tag.replaceAll("@", "")))
                .filter(Optional::isPresent).map(Optional::get).toList();
        post.getPersonTags().addAll(users);
    }

    public void addHashtags(Post post) {
        List<String> hashtagStr = Helper.findHashTags(post.getCaption());
        for (String s : hashtagStr) {
            Optional<Hashtag> optionalHashtag = hashtagRepository.findByTagName(s);
            if (optionalHashtag.isPresent()) {
                post.getHashtags().add(optionalHashtag.get());
            } else {
                post.getHashtags().add(new Hashtag(s));
            }
        }
    }


    //attempting to create a method that will return a list of posts containing certain hashtag
    protected List<PostWithoutOwnerDTO> getAllPostWithHashtag(String tagName) {
        if (hashtagRepository.findByTagName(tagName).isPresent()) {
            return hashtagRepository.findByTagName(tagName).get().getPostList().stream()
                    .map(post -> modelMapper.map(post, PostWithoutOwnerDTO.class))
                    .filter(post -> !post.is_deleted()).collect(Collectors.toList());
        } else {
            throw new DataNotFoundException("This hashtag has not been used yet");
        }
    }

    /*
    If a person is logged in this should return a positive non-null id value which can be used to both get
    the user and to check if the user is logged in
     */
    public static int getLoggedUserId(HttpSession session, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (session.isNew()) {
            throw new NoAuthException("You have to login");
            //in login set logged true and write the id of user
        }
        if (!(session.getAttribute("logged").equals(true))
                || session.getAttribute("logged") == null
                || !(session.getAttribute(REMOTE_IP).equals(ip))
                || session.getAttribute("id") == null
                || session.getAttribute("id").equals("")
                || session.getAttribute("id").equals(0)) {
            throw new NoAuthException("You have to login");
        }
        return (int) session.getAttribute("id");
    }
}
