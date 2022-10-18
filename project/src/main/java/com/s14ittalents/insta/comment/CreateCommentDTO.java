package com.s14ittalents.insta.comment;

import lombok.Data;

@Data
public class CreateCommentDTO {
    private int id;
    private Integer replyId;
    private int ownerId;
    private boolean isDelete = false;
}
