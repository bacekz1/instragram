package com.s14ittalents.insta.util;

import com.s14ittalents.insta.comment.CommentRepository;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.content.ContentRepository;
import com.s14ittalents.insta.exception.*;
import com.s14ittalents.insta.hashtag.Hashtag;
import com.s14ittalents.insta.location.LocationRepository;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostRepository;
import com.s14ittalents.insta.hashtag.HashtagRepository;
import com.s14ittalents.insta.post.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    protected User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND));
    }
    protected long getUserId(String username) {
        return userRepository.
                findByUsername(username).orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND)).getId();
    }

    protected Post findPost(long id) {
        return postRepository.findByIdAndDeletedIsFalseAndExpirationTimeIsNull(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.POST_NOT_FOUND));
    }

    protected Post findStory(long id) {
        return postRepository.findByIdAndDeletedIsFalseAndExpirationTimeNotNullAndCreatedTimeIsNotNull(id)
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
            if (file.getSize() > maxSize * mb) {
                throw new BadRequestException(MAX_SIZE_PER_FILE_IS_10_MB);
            }
            try {
                String ext = Objects.requireNonNull(file.getOriginalFilename()).
                        substring(file.getOriginalFilename().lastIndexOf("."));
                validateFileType(ext);
                String name = "uploads" + File.separator + System.nanoTime() + userId + ext;
                File f = new File(name);
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

}
