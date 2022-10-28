package com.s14ittalents.insta.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedStoryWithContentDTO {
    private FeedStoryDTO story;
    private List<FeedContentDTO> content;
}
