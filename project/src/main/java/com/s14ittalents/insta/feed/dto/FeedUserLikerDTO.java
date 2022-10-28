package com.s14ittalents.insta.feed.dto;

import lombok.Data;

@Data
public class FeedUserLikerDTO {
    private int id;
    private String username;
    private String profilePicture;
    private String firstName;
    private String lastName;
    
    public FeedUserLikerDTO(long id, String profile_picture, String username, String first_name, String last_name) {
        this.id = (int) id;
        this.username = username;
        this.profilePicture = profile_picture;
        this.firstName = first_name;
        this.lastName = last_name;
    }
}
