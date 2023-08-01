package com.umc.mot.packagee.mapper;

import com.umc.mot.packagee.dto.PackageRequestDto;
import com.umc.mot.packagee.dto.PackageResponseDto;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    PackageResponseDto.Response PackageToPackageResponseDto(Package pa);

    Package PackageRequestDtoPostToPackage(PackageRequestDto.Post post);

    Package PackageRequestDtoPatchToPackage(PackageRequestDto.Patch patch);

}
