package com.umc.mot.heart.controller;

import com.umc.mot.heart.dto.HeartRequestDto;
import com.umc.mot.heart.mapper.HeartMapper;
import com.umc.mot.heart.service.HeartService;
import com.umc.mot.heart.dto.HeartRequestDto;
import com.umc.mot.heart.dto.HeartResponseDto;
import com.umc.mot.heart.entity.Heart;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/Heart")
@Validated
@AllArgsConstructor
public class heartController {
    
    private final HeartService heartService;
    private final HeartMapper heartMapper;
    
    // Create
    @PostMapping
    public ResponseEntity postHeart(@Valid @RequestBody HeartRequestDto.Post post){
        Heart heart = heartService.createHeart(heartMapper.HeartRequestDtoPostToHeart(post));
        HeartResponseDto.Response response=heartMapper.HeartToHeartResponseDto(heart);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Read
    @GetMapping
    public ResponseEntity getHeart(@Positive @RequestParam int heartId){
        Heart heart = heartService.findHeart(heartId);
        HeartResponseDto.Response response = heartMapper.HeartToHeartResponseDto(heart);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // Update
    @PatchMapping("/{heart-id}")
    public ResponseEntity patchHeart(@Positive @PathVariable("heart-id") int heartId,
                                     @RequestBody HeartRequestDto.Patch patch) {
        patch.setId(heartId);
        Heart heart = heartService.patchHeart(heartMapper.HeartRequestDtoPatchToHeart(patch));
        HeartResponseDto.Response response =heartMapper.HeartToHeartResponseDto(heart);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{heart-id}")
    public ResponseEntity deleteHeart(@Positive @PathVariable("heart-id") int heartId) {
        heartService.deleteHeart(heartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


