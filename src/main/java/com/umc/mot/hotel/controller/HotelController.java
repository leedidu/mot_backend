package com.umc.mot.hotel.controller;

import com.umc.mot.hotel.dto.HotelRequestDto;
import com.umc.mot.hotel.dto.HotelResponseDto;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.mapper.HotelMapper;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.hotelCategory.entity.HotelCategory;
import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.mapper.SellMemberMapper;
import com.umc.mot.sellMember.service.SellMemberService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/hotel")
@Validated
@AllArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    // Create
    @PostMapping
    public ResponseEntity postHotel(@Valid @RequestBody HotelRequestDto.Post post){
        Hotel hotel = hotelService.createHotel(hotelMapper.HotelRequestDtoPostToHotel(post));
        HotelResponseDto.Response response=hotelMapper.HotelToHotelResponseDto(hotel);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Read
    @GetMapping
    public ResponseEntity getHotel(@Positive @RequestParam int hotel_id){
        Hotel hotel = hotelService.findHotel(hotel_id);
        HotelResponseDto.Response response = hotelMapper.HotelToHotelResponseDto(hotel);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // Update
    @PatchMapping("/{hotel-id}")
    public ResponseEntity patchHotel(@Positive @PathVariable("hotel-id") int hotelId,
                                      @RequestBody HotelRequestDto.Patch patch) {
        patch.setId(hotelId);
        Hotel hotel = hotelService.patchHotel(hotelMapper.HotelRequestDtoPatchToHotel(patch));
        HotelResponseDto.Response response = hotelMapper.HotelToHotelResponseDto(hotel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 호텔 사진 업로드 API
    @PatchMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity patchImageHotel(@RequestBody HotelRequestDto.PatchImage patchImage) throws IOException {
    public ResponseEntity patchImageHotel(HotelRequestDto.PatchImage patchImage) throws IOException {
        Hotel hotel = hotelService.uploadHotelImage(patchImage.getHotelId(), patchImage.getImage());
        HotelResponseDto.Response response = hotelMapper.HotelToHotelResponseDto(hotel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity findHotel(@RequestParam("name") String name){
        //  this.name=name;
        List<Hotel> hotel = hotelService.findHotels(name);
        List<HotelResponseDto.Response> response = hotelMapper.HotelToListHotelResponseDto(hotel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/search/peopleandday")
    public ResponseEntity findHotelPeopleDay(@RequestParam("name") String name, @RequestParam("checkin") String checkin,@RequestParam("checkout") String checkout, @RequestParam(value = "people",defaultValue="0") int people){
        LocalDate checkindate = LocalDate.parse(checkin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate checkoutdate = LocalDate.parse(checkout, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Hotel> hotel = hotelService.findHotelPeopleDay(name,checkindate,checkoutdate,people);
        List<HotelResponseDto.Response> response = hotelMapper.HotelToListHotelResponseDto(hotel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/category/{hotel-id}")
    public ResponseEntity categoryHotel(@PathVariable("hotel-id") int hotelId,@RequestParam List<String> categorylist){
        List<HotelCategory> hotelCategory=hotelService.createCategory(hotelId,categorylist);
        //HotelResponseDto.Response response=hotelMapper.HotelToHotelResponseDto(hotelCategory);
        //수정이 필요함
        return new ResponseEntity<>(hotelCategory, HttpStatus.CREATED);
    }


    // Delete
    @DeleteMapping("/{hotel-id}")
    public ResponseEntity deleteHotel(@Positive @PathVariable("hotel-id") int hotelId) {
        hotelService.deleteHotel(hotelId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
