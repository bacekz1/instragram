package com.s14ittalents.insta.messages;


import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.util.Ownerable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "private_message")
public class Message implements Ownerable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NonNull
    private long senderId;
    @Column
    @NonNull
    private long receiverId;
    @Column
    @NonNull
    @Size(max = 500, message = "Message should be less than 500 symbols")
    private String text;
    @Column
    @NonNull
    private LocalDateTime sendDate;
    @Column
    private boolean isSeen;
//    @Column
//    private boolean isDeleted;
    @Override
    public long ownerId() {
        return id;
    }
}
