package com.s14ittalents.insta.messages;


import com.s14ittalents.insta.util.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends AbstractService {
    @Autowired
    private MessageRepository messageRepository;
    

    public List<MessageInfoDTO> getSentMessages(long userId) {
        return messageRepository.findAllBySenderId(userId)
                .stream()
                .map(m -> {
                    MessageInfoDTO mTransfer = new MessageInfoDTO(m.getId(), m.getSendDate()
                            , userRepository.findById(m.getSenderId()).get().getUsername()
                        , userRepository.findById(m.getReceiverId()).get().getUsername(),m.getText());
                    mTransfer.setSeen(m.isSeen());
                    return mTransfer;}
                ).collect(Collectors.toList());
    }
    @Transactional
    public List<MessageInfoDTO> getReceivedMessages(long userId) {
        return messageRepository.findAllByReceiverId(userId)
                .stream()
                .map(m -> {
                    MessageInfoDTO mTransfer = new MessageInfoDTO(m.getId(), m.getSendDate()
                            , userRepository.findById(m.getSenderId()).get().getUsername()
                            , userRepository.findById(m.getReceiverId()).get().getUsername(),m.getText());
                    mTransfer.setSeen(true);
                    m.setSeen(true);
                    messageRepository.save(m);
                    return mTransfer;}
                ).collect(Collectors.toList());
    }
    
    public MessageInfoDTO sendMessage(long loggedUserId, String username, String text) {
        Message message = new Message();
        message.setReceiverId(getUserByUsername(username).getId());
        message.setSenderId(getUserById(loggedUserId).getId());
        message.setText(text);
        message.setSeen(false);
        message.setSendDate(LocalDateTime.now());
        messageRepository.save(message);
        return new MessageInfoDTO(message.getId(), message.getSendDate()
                , getUserById(message.getSenderId()).getUsername()
                , getUserById(message.getReceiverId()).getUsername(), message.getText());
    }
    
    @Transactional
    public MessageConversationDTO getConversation(long loggedUserId, String username) {
        long receiverId = getUserByUsername(username).getId();
        return loggedUserId==receiverId?getConversationWithYourself(loggedUserId,receiverId):getConversationBetweenTwoUsers(loggedUserId, receiverId);
    }
    
    private MessageConversationDTO getConversationBetweenTwoUsers(long loggedUserId, long receiverId) {
        return new MessageConversationDTO(
                messageRepository.findAllBySenderIdAndReceiverId(loggedUserId, receiverId)
                        .stream()
                        .map(m -> {
                            MessageInfoDTO mTransfer = new MessageInfoDTO(m.getId(), m.getSendDate()
                                    , userRepository.findById(m.getSenderId()).get().getUsername()
                                    , userRepository.findById(m.getReceiverId()).get().getUsername(),m.getText());
                            mTransfer.setSeen(m.isSeen());
                            return mTransfer;}
                        ).collect(Collectors.toList()),
                messageRepository.findAllBySenderIdAndReceiverId(receiverId,loggedUserId)
                        .stream()
                        .map(m -> {
                            MessageInfoDTO mTransfer = new MessageInfoDTO(m.getId(), m.getSendDate()
                                    , userRepository.findById(m.getSenderId()).get().getUsername()
                                    , userRepository.findById(m.getReceiverId()).get().getUsername(),m.getText());
                            m.setSeen(true);
                            mTransfer.setSeen(m.isSeen());
                            messageRepository.save(m);
                            return mTransfer;}
                        ).collect(Collectors.toList())
        );
    }
    
    private MessageConversationDTO getConversationWithYourself(long loggedUserId, long receiverId) {
        List<MessageInfoDTO> a = new ArrayList<>();
        return new MessageConversationDTO(a,
                messageRepository.findAllBySenderIdAndReceiverId(receiverId,loggedUserId)
                        .stream()
                        .map(m -> {
                            MessageInfoDTO mTransfer = new MessageInfoDTO(m.getId(), m.getSendDate()
                                    , userRepository.findById(m.getSenderId()).get().getUsername()
                                    , userRepository.findById(m.getReceiverId()).get().getUsername(),m.getText());
                            m.setSeen(true);
                            mTransfer.setSeen(m.isSeen());
                            messageRepository.save(m);
                            return mTransfer;}
                        ).collect(Collectors.toList())
        );
    }
}
