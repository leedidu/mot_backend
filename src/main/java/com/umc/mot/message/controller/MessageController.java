package com.umc.mot.message.controller;

import com.umc.mot.message.dto.MessageRequestDto;
import com.umc.mot.message.dto.MessageResponseDto;
import com.umc.mot.message.entity.Message;
import com.umc.mot.message.mapper.MessageMapper;
import com.umc.mot.message.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/message")
@Validated
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    // Create
    @PostMapping
    public ResponseEntity postMessage(@Valid @RequestBody MessageRequestDto.Post post) {
        Message message = messageService.createMessage(messageMapper.MessageRequestDtoPostToMessage(post));
        MessageResponseDto.Response response = messageMapper.MessageToMessageResponseDto(message);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity getMessage(@Positive @RequestParam int messageId) {

        Message message=messageService.findMessage(messageId);
        MessageResponseDto.Response response = messageMapper.MessageToMessageResponseDto((message));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{message-id}")
    public ResponseEntity patchMessage(@Positive @PathVariable("message-id") int messageId,
                                       @RequestBody MessageRequestDto.Patch patch) {
        patch.setId(messageId);
        Message message = messageService.patchMessage(messageMapper.MessageRequestDtoPatchToMessage(patch));
        MessageResponseDto.Response response = messageMapper.MessageToMessageResponseDto(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{message-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("message-id") int messageId) {
        messageService.deleteMessage(messageId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
