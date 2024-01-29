package com.ssafy.soyu.likes.entity;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
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

    private Boolean status;
    
    public Likes(Member member, Item item, Boolean status) {
        this.member = member;
        this.item = item;
        this.status = status;
    }

    public void changeStatus() {
      this.status = !status;
    }
}