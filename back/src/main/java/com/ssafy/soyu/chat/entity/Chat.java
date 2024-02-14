package com.ssafy.soyu.chat.entity;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.message.entity.Message;
import jakarta.persistence.*;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "chat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"item", "buyer", "seller"})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "chat")
    List<Message> message;

    private String lastMessage;

    private LocalDateTime lastDate;

    private Boolean isChecked;
    private LocalDateTime lastChecked;

    public Chat(Item item, Member seller, Member buyer) {
        this.item = item;
        this.seller = seller;
        this.buyer = buyer;
    }

    public void changeLast(String lastMessage) {
        this.lastMessage = lastMessage;
        this.lastDate = LocalDateTime.now();
    }
}
