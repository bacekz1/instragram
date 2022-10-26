package com.s14ittalents.insta.search;

import com.s14ittalents.insta.hashtag.dto.HashtagWithoutPost;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.dto.UserWithPostsDTO;
import com.s14ittalents.insta.user.dto.UserWithoutPostsDTO;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.s14ittalents.insta.util.Helper.hashtag;
import static com.s14ittalents.insta.util.Helper.personTag;

@Service
public class SearchService extends AbstractService {
    private final static Pageable pages = PageRequest.of(0, 5);

    public SearchResultDTO search(String query) {
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        if (query.isEmpty()) {
            return searchResultDTO;
        } else if (query.charAt(0) == hashtag) {
            searchResultDTO.setPosts(postRepository.searchHashtagPosts(query)
                    .stream()
                    .map(s -> modelMapper.map(s, PostWithoutOwnerDTO.class)).toList());
            searchResultDTO.setStories(postRepository.searchHashtagStories(query)
                    .stream()
                    .map(s -> modelMapper.map(s, PostWithoutOwnerDTO.class)).toList());

        } else if (query.charAt(0) == personTag) {
            Optional<User> user = userRepository
                    .findByUsernameAndVerifiedIsTrueAndBannedIsFalseAndDeletedIsFalse(query.substring(1));
            if (user.isPresent()) {
                searchResultDTO.setUser(modelMapper.map(user, UserWithoutPostsDTO.class));
            } else {
                return searchResultDTO;
            }
        } else {
            searchResultDTO.setPosts(postRepository.searchHashtagPosts(query)
                    .stream()
                    .map(s -> modelMapper.map(s, PostWithoutOwnerDTO.class)).toList());
            searchResultDTO.setStories(postRepository.searchHashtagStories(query)
                    .stream()
                    .map(s -> modelMapper.map(s, PostWithoutOwnerDTO.class)).toList());
            searchResultDTO.setUsers(userRepository
                    .findByUsernameContainingAndVerifiedIsTrueAndBannedIsFalseAndDeletedIsFalse( query, pages)
                    .stream()
                    .map(u -> modelMapper.map(u, UserWithoutPostsDTO.class)).toList());
            searchResultDTO.setHashtags(hashtagRepository.findByTagNameContaining(query,pages)
                    .stream()
                    .map(h -> modelMapper.map(h, HashtagWithoutPost.class)).toList());

        }
        return searchResultDTO;
    }
}
