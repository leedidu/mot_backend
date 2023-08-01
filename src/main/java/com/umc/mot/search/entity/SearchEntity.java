package com.umc.mot.search.entity;


import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class SearchEntity extends Auditable {

    @Id
    private int id;

    @Column
    private String context;

}
