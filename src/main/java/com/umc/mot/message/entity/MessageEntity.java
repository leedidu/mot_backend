package com.umc.mot.message.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class MessageEntity { //답글

    @Id
    private int messageId;

    @Column
    private String content;


}
