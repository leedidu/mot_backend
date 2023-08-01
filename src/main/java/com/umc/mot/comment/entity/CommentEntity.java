package com.umc.mot.comment.entity;

import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class CommentEntity extends Auditable {

    @Id
    private int id;

    @Column
    private String context;

    @Column
    private String imageUrl;

    @Column
    private int star;

    @Column
    private int memberId;

    @Column
    private boolean visible; // true : 보임, false : 안보임
}

