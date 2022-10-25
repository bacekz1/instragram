package com.s14ittalents.insta.post.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class PostCreateDTO {
    private String caption;
    private String location;
    List<MultipartFile> contents;
}
