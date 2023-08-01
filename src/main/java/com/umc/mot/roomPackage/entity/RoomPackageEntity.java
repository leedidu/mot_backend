package com.umc.mot.roomPackage.entity;

import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class RoomPackageEntity extends Auditable {

    @Id
    private int id; //객실 패키지 식별자

}
