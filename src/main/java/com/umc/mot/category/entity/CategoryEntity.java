package com.umc.mot.category.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
public class CategoryEntity {

    @Id
    private int categoryId;

    @Column
    private String categoryName;
}
