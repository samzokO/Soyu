package com.ssafy.soyu.likes.entity;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
@ToString(exclude = {"item"})
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
