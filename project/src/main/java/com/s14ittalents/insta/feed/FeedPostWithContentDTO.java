package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.content.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedPostWithContentDTO {
    private FeedPostDTO post;
    private List<FeedContentDTO> content;
    private int countLikes;
    private int countComments;
}
