package com.umc.mot.message.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.message.entity.Message;
import com.umc.mot.message.repository.MessageRepository;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.repository.PackageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class MessageService {


    private final MessageRepository messageRepository;

    //Create
    public Message createMessage(Message message) {

        return messageRepository.save(message);
    }

    // Read
    public Message findMessage(int messageId) {
        Message message = verifiedMessage(messageId);
        return message;
    }


    // Update
    public Message patchMessage(Message message) {
        Message findMessage = verifiedMessage(message.getId());
        Optional.ofNullable(message.getId()).ifPresent(findMessage::setId);
        Optional.ofNullable(message.getContent()).ifPresent(findMessage::setContent);


        return messageRepository.save(findMessage);
    }

    // Delete
    public void deleteMessage(int messageId) {
        Message pa = verifiedMessage(messageId);
        messageRepository.delete(pa);
    }

    // 멤버 검증
    public Message verifiedMessage(int messageId) {

        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PACKAGE_NOT_FOUND));

    }

}
