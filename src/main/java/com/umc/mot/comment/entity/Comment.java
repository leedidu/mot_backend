package com.umc.mot.comment.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.message.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends Auditable {

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

    @OneToMany(mappedBy = "comment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> messages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private
}

