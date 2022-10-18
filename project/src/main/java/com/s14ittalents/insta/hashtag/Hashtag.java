package com.s14ittalents.insta.hashtag;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "hashtags")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotEmpty
    private String tagName;
}
