package com.umc.mot.category.entity;

import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
public class CategoryEntity extends Auditable {

    @Id
    private int Id;

    @Column
    private String name;
}
