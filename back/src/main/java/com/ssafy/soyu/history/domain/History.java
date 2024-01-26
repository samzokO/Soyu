package com.ssafy.soyu.history.domain;

import com.ssafy.soyu.history.dto.request.HistoryRequestDto;
import com.ssafy.soyu.item.Item;
import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private boolean is_Delete;

    public History(Item i, Member m){
        this.item = i;
        this.member = m;
        this.is_Delete = false;
    }
}
