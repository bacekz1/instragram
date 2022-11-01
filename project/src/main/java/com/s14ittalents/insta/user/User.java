package com.s14ittalents.insta.user;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.util.Ownerable;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User implements Ownerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean activityStatus;

    @Column
    @NonNull
    private String email;

    @Column
    @NonNull
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @Size(max = 200)
    private String bio;

    @Column(name = "date_of_birth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @Column
    private String phoneNum;

    @Column
    private String gender;

    @Column
    private String profilePicture;

    @Column(name = "is_verified")
    private boolean verified;
    
    @Column(name = "is_banned")
    private boolean banned;

    @Column(name = "is_deleted")
    private boolean deleted;
    @Column
    private boolean isDeactivated;
    @Column
    private String verificationCode;

    @Column
    @NotNull
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner")
    List<Post> posts;


    @ManyToMany(mappedBy = "likes")
    List<Post> likedPosts;
    
    @ManyToMany(mappedBy = "likes")
    List<Comment> likedComments;


    @ManyToMany(mappedBy = "personTags")
    private List<Post> postList;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "following",
            joinColumns = {@JoinColumn(name = "follower_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> following;


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "following",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})

    private List<User> followers;

    @Override
    public long ownerId() {
        return id;
    }
}
