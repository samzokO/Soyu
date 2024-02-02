package com.ssafy.soyu.message.entity;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.file.ProfileImage;
import com.ssafy.soyu.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"chat", "member", "file"})
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private ProfileImage ProfileImage;

    private String content;

    LocalDateTime regDate;

    public Message(Chat chat, Member member, String content) {
        this.chat = chat;
        this.member = member;
        this.content = content;
    }
}
