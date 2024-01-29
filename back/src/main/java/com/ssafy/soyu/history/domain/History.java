package com.ssafy.soyu.history.domain;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean is_Delete;

    private String order_number;

    public History(Item i, Member m){
        this.item = i;
        this.member = m;
        this.is_Delete = false;
    }

    public void updateDate(String order_number){
        this.order_number = order_number;
    }
}
