package com.s14ittalents.insta.util;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.comment.CommentRepository;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.content.ContentRepository;
import com.s14ittalents.insta.exception.*;
import com.s14ittalents.insta.hashtag.Hashtag;
import com.s14ittalents.insta.location.LocationRepository;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostRepository;
import com.s14ittalents.insta.hashtag.HashtagRepository;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.s14ittalents.insta.exception.Constant.*;

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
    protected ContentRepository contentRepository;
    @Autowired
    protected LocationRepository locationRepository;
    @Autowired
    protected ModelMapper modelMapper;

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND));
    }
    
    protected User getUserByUsernameLogin(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoAuthException(Constant.WRONG_CREDENTIALS));
    }

    protected long getUserId(String username) {
        return userRepository.
                findByUsername(username).orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND)).getId();
    }

    protected Post findPost(long id) {
        return postRepository.findByIdAndDeletedIsFalseAndExpirationTimeIsNull(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.POST_NOT_FOUND));
    }

    protected Post findStoryById(long userId) {
        return postRepository.findStoryById(userId)
                .orElseThrow(() -> new DataNotFoundException(STORY_NOT_FOUND));
    }
    protected Post findStoryByUserId(long userId) {
        return postRepository.findStoryById(userId)
                .orElseThrow(() -> new DataNotFoundException(STORY_NOT_FOUND));
    }

    protected Comment findComment(long id) {
        return commentRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new DataNotFoundException(COMMENT_NOT_FOUND));
    }

    public void addPersonTags(Post post) {
        if (post.getComments() != null) {
            Set<User> userTags = post.getComments().stream().map(Comment::getComment)
                    .map(tag -> userRepository.findByUsername(tag.replaceAll("@", "")))
                    .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
            post.getPersonTags().addAll(userTags);
        }
        Set<User> users = Helper.findPersonTags(post).stream()
                .map(tag -> userRepository.findByUsername(tag.replaceAll("@", "")))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
        post.getPersonTags().addAll(users);
    }

    public void addPersonTags(Post post, Comment comment) {
        Set<User> users = Helper.findPersonTags(comment).stream()
                .map(tag -> userRepository.findByUsername(tag.replaceAll("@", "")))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
        post.getPersonTags().addAll(users);
    }

    public void addHashtags(Post post) {
        Set<String> hashtagStr = Helper.findHashTags(post);
        if (post.getComments() != null) {
            Set<String> commentHashtags = post.getComments().stream().map(Comment::getComment).collect(Collectors.toSet());
            hashtagStr.addAll(commentHashtags);
        }
        for (String s : hashtagStr) {
            Optional<Hashtag> optionalHashtag = hashtagRepository.findByTagName(s);
            if (optionalHashtag.isPresent()) {
                post.getHashtags().add(optionalHashtag.get());
            } else {
                post.getHashtags().add(new Hashtag(s));
            }
        }
    }

    public void addHashtags(Post post, Comment comment) {
        Set<String> hashtagStr = Helper.findHashTags(comment);
        for (String s : hashtagStr) {
            Optional<Hashtag> optionalHashtag = hashtagRepository.findByTagName(s);
            if (optionalHashtag.isPresent()) {
                post.getHashtags().add(optionalHashtag.get());
            } else {
                post.getHashtags().add(new Hashtag(s));
            }
        }
    }


    protected List<PostWithoutOwnerDTO> getAllPostWithHashtag(String tagName) {
        if (hashtagRepository.findByTagName(tagName).isPresent()) {
            return hashtagRepository.findByTagName(tagName).get().getPostList().stream()
                    .map(post -> modelMapper.map(post, PostWithoutOwnerDTO.class))
                    .filter(post -> !post.is_deleted()).collect(Collectors.toList());
        } else {
            throw new DataNotFoundException("This hashtag has not been used yet");
        }
    }


    protected static void checkPermission(long userId, Ownerable owner) {
        if (owner.ownerId() != userId) {
            throw new NoAuthException(PERMISSION_DENIED);
        }
    }

    protected static void validateFileType(String fileType) {

        if (!(fileType.equals(".jpg") || fileType.equals(".png") ||
                fileType.equals(".jpeg") || fileType.equals(".mp4"))) {
            throw new BadRequestException("Invalid file type");
        }
    }

    protected static List<Content> uploadFiles(List<MultipartFile> files, long userId, Post createdPost, int maxSize) {
        List<Content> contents = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()){
                throw new BadRequestException(FILE_SHOULD_NOT_BE_EMPTY);
            }
            if (file.getSize() > maxSize * mb) {
                throw new BadRequestException(String.format(MAX_SIZE_PER_FILE_IS_MB, maxSize));
            }
            try {
                String ext = Objects.requireNonNull(file.getOriginalFilename()).
                        substring(file.getOriginalFilename().lastIndexOf("."));
                validateFileType(ext);
                String dirName;
                if (createdPost.getExpirationTime() != null) {
                    dirName = PATH_TO_STATIC + "story";
                } else {
                    dirName = PATH_TO_STATIC + "post";
                }
                String fileName = dirName + File.separator + System.nanoTime() + userId + ext;
                File dir = new File(dirName);
                File f = new File(fileName);
                dir.mkdirs();
                if (!f.exists()) {
                    Files.copy(file.getInputStream(), f.toPath());
                    contents.add(new Content(f.getPath(), createdPost));
                } else {
                    throw new FileException(THE_FILE_ALREADY_EXISTS);
                }
            } catch (IOException e) {
                throw new FileException(e.getMessage());
            }
        }
        return contents;
    }

    protected String validatePassword(String password) {
        if (password==null){
            throw new BadRequestException("Something went wrong!");
        }
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }
        if (password.length() > 20) {
            throw new BadRequestException("Password must be at most 20 characters long");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new BadRequestException("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new BadRequestException("Password must contain at least one digit");
        }
        if (!password.matches(".*[!@#$%^&*()_+].*")) {
            throw new BadRequestException("Password must contain at least one special character");
        }

        return password.trim();
    }

    protected String validateUsername(String username) {
        if (username.length() < 2) {
            throw new BadRequestException("Username must be at least 2 characters long");
        }
        if (username.length() > 30) {
            throw new BadRequestException("Username must be at most 30 characters long");
        }
        if (!username.matches("^[a-z0-9_]+$")) {
            throw new BadRequestException("Username must contain only lowercase letters, digits and underscore");
        }
        return username.trim();
    }

    protected String validateEmail(String email) {
        if (email.length() < 5) {
            throw new BadRequestException("Email must be at least 5 characters long");
        }
        if (email.length() > 50) {
            throw new BadRequestException("Email must be at most 50 characters long");
        }
        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2" +
                "(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])" +
                "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b" +
                "\\x0c\\x0e-\\x7f])+)\\])")) {

            throw new BadRequestException("Email is not valid");
        }
        return email.trim();
    }

    protected String validateBio(String bio) {
        if (bio.length() > 200) {
            throw new BadRequestException("Bio must be at most 200 characters long");
        }
        return bio.trim();
    }

    protected String validateName(String name, String type) {
        if (name.length() < 2) {
            throw new BadRequestException(type + "name must be at least 2 characters long");
        }
        if (name.length() > 30) {
            throw new BadRequestException(type + " name must be at most 30 characters long");
        }
        if (name.matches(".*[<>].*") || name.matches(".*http.*")) {
            throw new BadRequestException("Name must not contain <> symbols or web addresses");
        }
        return name.trim();
    }

    protected String validatePhoneNum(String phone) {
        if (phone.length() < 4) {
            throw new BadRequestException("Phone must be at least 5 characters long");
        }
        if (phone.length() > 20) {
            throw new BadRequestException("Phone must be at most 20 characters long");
        }
        if (!phone.matches("^[+0-9]+$")) {
            throw new BadRequestException("Phone must contain only digits, + and - symbols");
        }
        return phone.trim();
    }

    protected String validateGender(String gender) {
        if (gender.length() < 1) {
            throw new BadRequestException("Gender must be at least 1 characters long");
        }
        if (gender.length() > 15) {
            throw new BadRequestException("Gender name must be less than 15 characters long");
        }
        if (!gender.matches("[a-zA-Z]+")) {
            throw new BadRequestException("Gender name must contain only letters");
        }
        return gender.trim();
    }

    protected LocalDate validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new BadRequestException("Date of birth cannot be in the future");
        }
        if (dateOfBirth.isBefore(LocalDate.of(1850, 1, 1))) {
            throw new BadRequestException("Date of birth cannot be before 1850");
        }
        if (dateOfBirth.plusYears(13).isAfter(LocalDate.now())) {
            throw new BadRequestException("You must be at least 13 years old to register or use this application");
        }
        System.out.println(dateOfBirth);
        return dateOfBirth;
    }


    protected User validateIfUserIsAdminByEmail(long loggedUserId) {
        User user = getUserById(loggedUserId);
        if (!user.getEmail().split("@")[1].equals("admin.instagram.com")) {
            throw new NoAuthException("You do not have admin rights");
        }
        return user;
    }
}
