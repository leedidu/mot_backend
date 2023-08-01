package com.umc.mot.message.entity;


import com.umc.mot.auditable.Auditable;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.hotel.entity.Hotel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message extends Auditable { //답글 엔티티

    @Id
    private int messageId; //답글아이디

    @Column
    private String content; //내용

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;
}
