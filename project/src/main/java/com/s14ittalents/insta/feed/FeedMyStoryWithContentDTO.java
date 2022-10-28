package com.s14ittalents.insta.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedMyStoryWithContentDTO {
    private FeedMyStoryDTO story;
    private List<FeedContentDTO> content;
    private int countLikes;
    private List<FeedUserLikerDTO> usersWhoLiked;
}
