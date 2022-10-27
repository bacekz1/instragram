package com.s14ittalents.insta.feed;

import com.s14ittalents.insta.content.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedPostWIthContentDTO {
    FeedPostDTO feedPostDTO;
    List<Content> contents;
}
