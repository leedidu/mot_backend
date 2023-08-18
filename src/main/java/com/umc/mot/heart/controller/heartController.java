package com.umc.mot.heart.controller;

import com.umc.mot.heart.dto.HeartRequestDto;
import com.umc.mot.heart.mapper.HeartMapper;
import com.umc.mot.heart.service.HeartService;
import com.umc.mot.heart.dto.HeartRequestDto;
import com.umc.mot.heart.dto.HeartResponseDto;
import com.umc.mot.heart.entity.Heart;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/heart")
@Validated
@AllArgsConstructor
public class heartController {
    
    private final HeartService heartService;
    private final HeartMapper heartMapper;
    private final HotelService hotelService;
    
    // Create
    @PostMapping
    public ResponseEntity postHeart(@Positive @RequestParam int hotelId){
        Heart heart = heartService.createHeart(hotelId);
        int  memberId = heart.getPurchaseMember().getPurchaseMemberId();
        HeartResponseDto.Response response=heartMapper.HeartToHeartResponseDto(heart, hotelId, memberId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Read
    @GetMapping
    public ResponseEntity getHeart(){
        List<Heart> heart = heartService.findHeart();
        List<Hotel> hotel = new ArrayList<>();

        for(int i=0;i<heart.size();i++){
            Hotel hotel1 = new Hotel();
            int hotelId = heart.get(i).getHotel().getId();
            hotel1 = hotelService.findHotel(hotelId);
            hotel.add(hotel1);

        }
        List<HeartResponseDto.ListResponse> response = heartMapper.HeartToResponse(heart,hotel);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{heart-id}")
    public ResponseEntity deleteHeart(@Positive @PathVariable("heart-id") int heartId) {
        heartService.deleteHeart(heartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


