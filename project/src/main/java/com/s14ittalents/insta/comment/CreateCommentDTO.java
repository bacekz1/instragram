package com.s14ittalents.insta.comment;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class CreateCommentDTO {
    private long id;
    private String comment;
    @Positive
    private Integer replyId;
    @Positive
    private long ownerId;
    private boolean isDelete = false;
    @Positive
    private long postId;
}
