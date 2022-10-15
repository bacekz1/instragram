package com.s14ittalents.insta.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

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
    @Min(value = 5)
    private String username;
    
    @Column
    @Min(value = 8)
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
    
    
}
