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
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
            "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2" +
            "(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])" +
            "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b" +
            "\\x0c\\x0e-\\x7f])+)\\])", message = "Invalid email")
    private String email;

    @Column
    @NonNull
    @Pattern(regexp = "^[a-z0-9_]+$", message = "Username should contain only letters, numbers and _")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters")
    private String username;

    @Column
    @Size(min = 8, max = 80, message = "Password should be longer than 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
            , message = "Password should contain at least one digit, one uppercase letter, one lowercase letter" +
            ", one special character and no whitespaces")
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @Size(max = 200)
    private String bio;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateOfBirth;

    @Column
    @Size(min = 10, message = "Phone number should be at least 10 digits")
    private String phoneNum;

    @Column
    private String gender;

    @Column
    private String profilePicture;

    @Column(name = "is_verified")
    private boolean verified;

    @Column(name = "is_private")
    private boolean privateAccount;
    @Column(name = "is_banned")
    private boolean banned;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Column
    @NotNull
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "owner")
    List<Post> posts;

    @ManyToMany
    @JoinTable(name = "like_post",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    List<Post> likedPosts;
    
    @ManyToMany
    @JoinTable(name = "like_comments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    List<Comment> likedComments;


    @ManyToMany(mappedBy = "personTags")
    private List<Post> postList;

    @Override
    public long ownerId() {
        return id;
    }
    /*
    From lecture:
    
    Way 1 - Basic Many to many - only one column in joined table with foreign keys
    user is responsible to connection many to many
    
    userRepository.save(user);
    
    user as owning has to do edit both tales
    posts.save()- would not work if user is owning
    
    

    depends which is the owning side
     */
}
