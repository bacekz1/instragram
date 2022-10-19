package com.s14ittalents.insta.user;

import com.s14ittalents.insta.post.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    private boolean activity_status;
    
    @Column
    @NonNull
    private String email;
    
    @Column
    @NonNull
    @Size(min = 3, max = 20)
    private String username;
    
    @Column
    @Size(min = 3, max = 20)
    private String password;
    
    @Column
    private String first_name;
    
    @Column
    private String last_name;
    
    @Column
    @Max(value = 200)
    private String bio;
    
    @Column
    private LocalDateTime date_of_birth;
    
    @Column
    private String phone_num;
    
    @Column
    private String gender;
    
    @Column
    private String profile_picture;
    
    
    @Column
    private boolean is_verified;
    //further extension - is_private
    
    @Column
    private boolean is_banned;
    
    @Column
    private boolean is_deleted;
    
    @OneToMany(mappedBy = "owner")
    List<Post> posts;
    
    @ManyToMany
    @JoinTable(name = "like_post",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    List<Post> likedPosts;
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
