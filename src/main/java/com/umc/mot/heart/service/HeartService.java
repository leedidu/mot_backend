package com.umc.mot.heart.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.heart.entity.Heart;
import com.umc.mot.heart.repository.HeartRepository;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final TokenService tokenService;
    private final HotelService hotelService;

    //create
    public Heart createHeart(int hotelId){
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Heart heart = new Heart();
        Hotel hotel = hotelService.findHotel(hotelId);
        heart.setHotel(hotel);
        heart.setPurchaseMember(purchaseMember);

        return heartRepository.save(heart);

    }


    //read
    public List<Heart> findHeart(){
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        int MemberId = purchaseMember.getPurchaseMemberId();
        List<Heart> heartList = heartRepository.findHeartByPurchaseMember(MemberId);

        return heartList;

    }


    //Update
    public Heart patchHeart(Heart heart){
        Heart findHeart = verifiedHeart(heart.getId());
        Optional.ofNullable(heart.getId()).ifPresent(findHeart::setId);
        return heartRepository.save(findHeart);
    }



    //Delete
    public void deleteHeart(int heartId){
        Heart heart = verifiedHeart(heartId);
        heartRepository.delete(heart);
    }


    // 멤버 검증
    public Heart verifiedHeart(int heartId) {

        Optional<Heart> heart = heartRepository.findById(heartId);
        return heart.orElseThrow(() -> new BusinessLogicException(ExceptionCode.HEART_NOT_FOUND));

    }


}
