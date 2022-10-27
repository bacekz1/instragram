package com.s14ittalents.insta.feed;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class FeedStoryDTO {
    private String profile_picture;
    private String username;
    private String first_name;
    private String last_name;
    private long post_id;
    private String caption;
    private String location;
    private LocalDateTime created_time;
    private long timePassed;
    
    public FeedStoryDTO(String profile_picture, String username, String first_name, String last_name,
                        long post_id, String caption, String location, LocalDateTime created_time) {
        this.profile_picture = profile_picture;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.post_id = post_id;
        this.caption = caption;
        this.location = location;
        this.created_time = created_time;
    }
    
    void setTimePassed(long timePassed) {
        this.timePassed = timePassed;
    }
}
