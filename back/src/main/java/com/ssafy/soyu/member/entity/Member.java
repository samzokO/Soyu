package com.ssafy.soyu.member.entity;

import com.ssafy.soyu.profileImage.ProfileImage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;
    private Long snsId;
    private String email;
    private String nickName;
    private String name;
    private String mobile;
    private String bank_name;
    private String account_number;
    private Boolean isWithdraw;

    @Builder
    public Member(String email, String name , String nickname, String mobile) {
        this.email = email;
        this.name = name;
        this.nickName = nickname;
        this.mobile = mobile;
    }

    public void updateMember(Long snsId, String bank_name, String account_number, Boolean isWithdraw, ProfileImage ProfileImage) {
        this.snsId = snsId;
        this.bank_name = bank_name;
        this.account_number = account_number;
        this.isWithdraw = isWithdraw;
        this.profileImage = ProfileImage;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

}
