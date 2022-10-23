package com.s14ittalents.insta.post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class PostCreateDTO {

    private String caption;

    private Long locationId;

    private boolean deleted;

    List<MultipartFile> contents;
    private long owner;
    private LocalDateTime expirationTime;
    private LocalDateTime createdTime;
}
