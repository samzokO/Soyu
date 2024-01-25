package com.ssafy.soyu.history.domain;

import com.ssafy.soyu.item.Item;
import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Getter
public class History {
    @Id
    @GeneratedValue
    @Column(name = "history_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    LocalDateTime regDate;

    private boolean is_Delete;
}
