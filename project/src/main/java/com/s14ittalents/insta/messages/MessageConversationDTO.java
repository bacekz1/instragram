package com.s14ittalents.insta.messages;

import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class MessageConversationDTO {
    List<MessageInfoDTO> messages;
    public MessageConversationDTO(List<MessageInfoDTO> messagesFromSenderToReceiver
            , List<MessageInfoDTO> messagesFromReceiverToSender) {
        messagesFromSenderToReceiver.addAll(messagesFromReceiverToSender);
        messages = messagesFromSenderToReceiver.stream()
                .sorted(Comparator.comparing(MessageInfoDTO::getSendDate).reversed()).toList();
    }
}
