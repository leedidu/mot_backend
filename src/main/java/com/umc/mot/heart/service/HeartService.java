package com.umc.mot.heart.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.heart.entity.Heart;
import com.umc.mot.heart.repository.HeartRepository;
import com.umc.mot.hotel.entity.Hotel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    //create
    public Heart createHeart(Heart heart){

        return heartRepository.save(heart);

    }
    //read
    public Heart findHeart(int heartId){
        Heart heart = verifiedHeart(heartId);
        return heart;

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
