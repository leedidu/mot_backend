package com.umc.mot.message.entity;


import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class MessageEntity extends Auditable { //답글 엔티티

    @Id
    private int messageId; //답글아이디

    @Column
    private String content; //내용


}
