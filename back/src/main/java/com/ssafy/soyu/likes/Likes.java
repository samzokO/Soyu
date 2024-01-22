package com.ssafy.soyu.likes;

import com.ssafy.soyu.item.Item;
import com.ssafy.soyu.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "likes")
@Getter
public class Likes {
    @Id
    @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
