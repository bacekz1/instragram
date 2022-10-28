package com.s14ittalents.insta.feed.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class FeedMyStoryDTO {
    private String profile_picture;
    private String username;
    private String first_name;
    private String last_name;
    private long post_id;
    private String caption;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiration_time;
    private String timePassed;
    
    public FeedMyStoryDTO(String profile_picture, String username, String first_name, String last_name,
                        long post_id, String caption, String location, LocalDateTime expiration_time) {
        this.profile_picture = profile_picture;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.post_id = post_id;
        this.caption = caption;
        this.location = location;
        this.expiration_time = expiration_time;
    }
    
    public void setTimePassed(long timePassed) {
        this.timePassed = "~"+(24+timePassed)+"h";
    }
}
