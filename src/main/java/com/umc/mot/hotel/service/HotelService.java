package com.umc.mot.hotel.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.repository.HotelRepository;
import com.umc.mot.message.entity.Message;
import com.umc.mot.message.repository.MessageRepository;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.repository.SellMemberRepository;
import com.umc.mot.sellMember.service.SellMemberService;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final SellMemberRepository sellMemberRepository;
    private final SellMemberService sellMemberService;
    private final TokenService tokenService;

    //Create
    public Hotel createHotel(Hotel hotel) {
        SellMember sellM = tokenService.getLoginSellMember();
        return hotelRepository.save(hotel);

    }

    // Read
    public Hotel findHotel(int hotelId) {
        Hotel hotel = verifiedHotel(hotelId);
        return hotel;
    }


    // Update
    public Hotel patchHotel(Hotel hotel) {

        Hotel findHotel = verifiedHotel(hotel.getId());
        Optional.ofNullable(hotel.getId()).ifPresent(findHotel::setId);
        Optional.ofNullable(hotel.getMinPeople()).ifPresent(findHotel::setMinPeople);
        Optional.ofNullable(hotel.getMaxPeople()).ifPresent(findHotel::setMaxPeople);
        Optional.ofNullable(hotel.getPrice()).ifPresent(findHotel::setPrice);
        Optional.ofNullable(hotel.getName()).ifPresent(findHotel::setName);
        Optional.ofNullable(hotel.getStar()).ifPresent(findHotel::setStar);
        Optional.ofNullable(hotel.getMap()).ifPresent(findHotel::setMap);
        Optional.ofNullable(hotel.getTransfer()).ifPresent(findHotel::setTransfer);
        Optional.ofNullable(hotel.getAddress()).ifPresent(findHotel::setAddress);
        Optional.ofNullable(hotel.getInfo()).ifPresent(findHotel::setInfo);
        Optional.ofNullable(hotel.getSellMember()).ifPresent(findHotel::setSellMember);

        return hotelRepository.save(findHotel);



    }

    // Delete
    public void deleteHotel(int hotelId) {
        Hotel hotel=verifiedHotel(hotelId);
        hotelRepository.delete(hotel);


    }

    // 멤버 검증
    public Hotel verifiedHotel(int hotelId) {

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        return hotel.orElseThrow(() -> new BusinessLogicException(ExceptionCode.HOTEL_NOT_FOUND));

    }

}
