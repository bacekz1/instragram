package com.s14ittalents.insta.search;

import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.s14ittalents.insta.util.Helper.hashtag;

@Service
public class SearchService extends AbstractService {

    public SearchResultDTO search(String query) {
        SearchResultDTO searchResultDTO = new SearchResultDTO();
        if (query.isEmpty()){
        return searchResultDTO;
        }
        else if (query.charAt(0) == hashtag){
            Pageable pages = PageRequest.of(0, 12);
//            searchResultDTO.setPosts(postRepository
//                    .findByHashtagsLikedAndDeletedIsFalseAndExpirationTimeIsNullOrderByLikes(query, pages)
//                    .stream().map(p -> modelMapper.map(p, PostWithoutOwnerDTO.class)).toList());
        }
        return  searchResultDTO;
    }
}
