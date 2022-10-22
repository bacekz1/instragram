package com.s14ittalents.insta.user;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.util.Ownerable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    private String email;

    @Column
    @NonNull
    @Size(min = 3, max = 20)
    private String username;

    @Column
    @Size(min = 3, max = 80)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @Size(max = 200)
    private String bio;

    @Column
    private LocalDateTime dateOfBirth;

    @Column
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
