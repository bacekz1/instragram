package com.s14ittalents.insta.post;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.*;

@Service
public class PostService extends AbstractService {


    public Post createPost(Post post, long userId) {
        post.setOwner(getUserById(userId));
        post.setCreatedTime(LocalDateTime.now());
        post.setExpirationTime(LocalDateTime.now().plusDays(1));
        addHashtags(post);
        addPersonTags(post);
        return postRepository.save(post);
    }

    public PostWithoutOwnerDTO getPost(long id) {
        Post post = findPost(id);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    public PostWithoutOwnerDTO updatePost(long posId, PostUpdateDTO postUpdate, int userId) {
        Post post = findPost(posId);
        checkPermission(userId, post);
        post.setCaption(postUpdate.getCaption());
        post.getHashtags().clear();
        post.getPersonTags().clear();
        addHashtags(post);
        addPersonTags(post);
        postRepository.save(post);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }


    @Transactional
    public boolean deletePost(long postId, int userId) {
        Post post = findPost(postId);
        checkPermission(userId, post);
        List<Comment> comments = commentRepository.findByPostId(postId).stream().toList();
        comments.forEach(comment -> comment.setDeleted(true));
        commentRepository.saveAll(comments);
        post.setDeleted(true);
        post.setCaption("deleted at" + LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    public int likePost(long id, long userId) {
        Optional<Post> post = postRepository.findByIdAndDeletedIsFalse(id);
        if (post.isEmpty()) {
            throw new DataNotFoundException(POST_NOT_FOUND);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }

        if (post.get().getLikes().contains(user.get())) {
            post.get().getLikes().remove(getUserById(userId));
        } else {
            post.get().getLikes().add(getUserById(userId));
        }
        post.get().getLikes().forEach(a ->
                System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
        userRepository.save(getUserById(userId));
        post.get().getLikes().forEach(a ->
                System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
        return post.get().getLikes().size();
    }
    
    public String uploadPostContent(long uid, MultipartFile file) {
        //todo connect multiple upload post content to post
        try {
            User user = userRepository.findById(uid).orElseThrow(() -> new DataNotFoundException("User not found"));
            String ext = Objects.requireNonNull(file.getOriginalFilename()).
                    substring(file.getOriginalFilename().lastIndexOf("."));
            if(ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg") || ext.equals(".mp4")) {
                throw new BadRequestException("Invalid file type");
            }
            String name = "uploads" + File.separator + "post_contents"
                    + File.separator + user.getUsername() + File.separator + System.nanoTime() + "." + ext;
            File f = new File(name);
            return name;
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong");
        }
    }
}
