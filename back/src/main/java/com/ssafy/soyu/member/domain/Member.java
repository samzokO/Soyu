package com.ssafy.soyu.member.domain;

import com.ssafy.soyu.file.File;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;
    private Long snsId;
    private String email;
    private String nickName;
    private String name;
    private String credit_name;
    private String credit_info;
    private Boolean isWithdraw;

    @Builder
    public Member(String email, String name ,String nickname) {
        this.email = email;
        this.name = name;
        this.nickName = nickname;
    }

}
