package com.umc.mot.hotel.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.dto.HotelRequestDto;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.repository.HotelRepository;
import com.umc.mot.message.entity.Message;
import com.umc.mot.message.repository.MessageRepository;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.repository.SellMemberRepository;
import com.umc.mot.sellMember.service.SellMemberService;
import com.umc.mot.token.service.TokenService;
import com.umc.mot.utils.S3Uploader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final SellMemberRepository sellMemberRepository;
    private final SellMemberService sellMemberService;
    private final TokenService tokenService;
    private final S3Uploader s3Uploader;

    //Create
    public Hotel createHotel(Hotel hotel) {
        SellMember sellM = tokenService.getLoginSellMember();
        hotel.setSellMember(sellM);
        return hotelRepository.save(hotel);

    }

    // Read
    public Hotel findHotel(int hotelId) {
        Hotel hotel = verifiedHotel(hotelId);
        return hotel;
    }

    public List<Hotel> findHotels(String post,int people) {
      //  System.out.println("#########"+post);
        List<Hotel> hotels=hotelRepository.findByName(post,people);
        return hotels;
    }


    // Update
    public Hotel patchHotel(Hotel hotel) {
        SellMember sellM = tokenService.getLoginSellMember();
        Hotel findHotel = verifiedHotel(hotel.getId());
        Optional.ofNullable(hotel.getName()).ifPresent(findHotel::setName);
        Optional.ofNullable(hotel.getMap()).ifPresent(findHotel::setMap);
        Optional.ofNullable(hotel.getTransfer()).ifPresent(findHotel::setTransfer);
        Optional.ofNullable(hotel.getRegion()).ifPresent(findHotel::setRegion);
        Optional.ofNullable(hotel.getAddress()).ifPresent(findHotel::setAddress);
        Optional.ofNullable(hotel.getAddressInfo()).ifPresent(findHotel::setAddressInfo);
        Optional.ofNullable(hotel.getInfo()).ifPresent(findHotel::setInfo);
        Optional.ofNullable(hotel.getDistance()).ifPresent(findHotel::setDistance);
        Optional.ofNullable(hotel.getSellMember()).ifPresent(findHotel::setSellMember);
        Optional.ofNullable(hotel.getPhoto()).ifPresent(findHotel::setPhoto);

        if(hotel.getMinPeople() != 0) findHotel.setMinPeople(hotel.getMinPeople());
        if(hotel.getMaxPeople() != 0) findHotel.setMaxPeople(hotel.getMaxPeople());
        if(hotel.getPrice() != 0) findHotel.setPrice(hotel.getPrice());
        if(hotel.getStar() != 0) findHotel.setStar(hotel.getStar());

        return hotelRepository.save(findHotel);



    }

    // Delete
    public void deleteHotel(int hotelId) {
        SellMember sellM = tokenService.getLoginSellMember();
        Hotel hotel=verifiedHotel(hotelId);
        hotelRepository.delete(hotel);


    }

    // 멤버 검증
    public Hotel verifiedHotel(int hotelId) {

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        return hotel.orElseThrow(() -> new BusinessLogicException(ExceptionCode.HOTEL_NOT_FOUND));

    }

    // 판매자와 숙소 매칭 확인
    public Hotel verifiedHotelAndSellMember(int hotelId) {
        Hotel hotel = verifiedHotel(hotelId);
        SellMember sellMember = tokenService.getLoginSellMember();

        if(hotel.getSellMember().getSellMemberId() != sellMember.getSellMemberId())
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZATION);
        return hotel;
    }

    // 사진 업로드
    public Hotel uploadHotelImage(int hotelId, MultipartFile multipartFile) throws IOException {
        Hotel hotel = verifiedHotelAndSellMember(hotelId);

        // 이미 저장되어 있는 이미지 삭제
        if(hotel.getPhoto() != null)
            s3Uploader.deleteFile(hotel.getPhoto());

        // 이미지 업로드
        String s3ImageUrl = s3Uploader.uploadFile(multipartFile);
        hotel.setPhoto(s3ImageUrl);

        return hotelRepository.save(hotel);
    }

}
