package com.s14ittalents.insta.comment.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentWithRepliesDTO {
    private String comment;
    private List<CommentWithRepliesDTO> replies;
}