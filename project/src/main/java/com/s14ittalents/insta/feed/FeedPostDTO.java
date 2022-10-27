package com.s14ittalents.insta.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
//@NoArgsConstructor
public class FeedPostDTO {
    private String profile_picture;
    private String username;
    private String first_name;
    private String last_name;
    private long post_id;
    private String caption;
    private String location;
    private LocalDateTime created_time;
}
