package com.s14ittalents.insta.post;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Data
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @Min(value = 1)
    private long user_id;
    @Column
    private String caption;
    @Column
    private long location_id;
    @Column
    private boolean is_deleted;
}
