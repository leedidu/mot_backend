package com.umc.mot.packagee.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.repository.HotelRepository;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.repository.PackageRepository;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final HotelService hotelService;
    private final TokenService tokenService;

    //Create
    public Package createPackage(Package pa,int hotelId) {
        SellMember sellM = tokenService.getLoginSellMember();
        Hotel hotel = hotelService.verifiedHotel(hotelId);
        pa.setHotel(hotel);
        return packageRepository.save(pa);
    }

    // Read
    public Package findPackage(int packageId) {
        Package pa = verifiedPackage(packageId);
        return pa;
    }


    // Update
    public Package patchPackage(Package pa) {
        SellMember sellM = tokenService.getLoginSellMember();
        Package findPackage = verifiedPackage(pa.getId());
        Optional.ofNullable(pa.getName()).ifPresent(findPackage::setName);
        Optional.ofNullable(pa.getMinPeople()).ifPresent(findPackage::setMinPeople);
        Optional.ofNullable(pa.getMaxPeople()).ifPresent(findPackage::setMaxPeople);
        Optional.ofNullable(pa.getPrice()).ifPresent(findPackage::setPrice);
        Optional.ofNullable(pa.getRoomType()).ifPresent(findPackage::setRoomType);
        Optional.ofNullable(pa.getPhotos()).ifPresent(findPackage::setPhotos);


        return packageRepository.save(findPackage);
    }

    // Delete
    public void deletePackage(int packageId) {
        SellMember sellM = tokenService.getLoginSellMember();
        Package pa = verifiedPackage(packageId);
        packageRepository.delete(pa);
    }

    // 멤버 검증
    public Package verifiedPackage(int packageId) {
        Optional<Package> pa = packageRepository.findById(packageId);
        return pa.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PACKAGE_NOT_FOUND));

    }
}
