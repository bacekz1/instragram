package com.s14ittalents.insta.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class CreateCommentDTO {
    private String comment;
    @Positive
    private long ownerId;
}
