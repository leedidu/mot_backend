package com.umc.mot.comment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="comment")
public class CommentEntity {

    @Id
    private int commentId;

    @Column
    private String context;

    @Column
    private String imageUrl;

    @Column
    private int star;

    @Column
    private int memberId;

    @Column
    private boolean visible; //이거 enum으로 빼는게 좋지 않나?
}

