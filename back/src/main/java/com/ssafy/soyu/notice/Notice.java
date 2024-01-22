package com.ssafy.soyu.notice;

import com.ssafy.soyu.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "notice")
@Getter
public class Notice {
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member member;

    private String content;

    private NoticeStatus status;

    private Boolean isCheck;
    private Boolean isDelete;

}
