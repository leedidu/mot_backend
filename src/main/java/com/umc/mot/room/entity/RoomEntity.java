package com.umc.mot.room.entity;

import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class RoomEntity extends Auditable {
    @Id
    private int Id; //객실 식별자

    @Column
    private String Name; //객실 이름

    @Column
    private int minPeople;//최소인원

    @Column
    private int maxPeople; //최대인원

    @Column
    private int roomPrice; //비용

}
