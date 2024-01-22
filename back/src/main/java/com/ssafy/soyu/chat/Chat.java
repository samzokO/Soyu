package com.ssafy.soyu.chat;

import com.ssafy.soyu.item.Item;
import com.ssafy.soyu.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;



@Entity
@Table(name = "chat")
@Getter
public class Chat {
    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    private String lastMessage;

    private LocalDateTime lastDate;

    private Boolean isChecked;
    private LocalDateTime lastChecked;
}
