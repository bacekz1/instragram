package com.s14ittalents.insta.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfoDTO {
    private long id;
    private String senderName;
    private String receiverName;
    private String text;
    private String sendDate;
    private boolean isSeen = false;
    public MessageInfoDTO(long id, LocalDateTime date, String senderUsername, String receiverUsername, String text) {
        this.id = id;
        this.senderName = senderUsername;
        this.receiverName = receiverUsername;
        this.sendDate = date.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"));
        this.text = text;
    }
}
