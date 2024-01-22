package com.ssafy.soyu.member;

import com.ssafy.soyu.file.File;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "member")
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;
    private Long snsId;
    private String nickName;
    private String name;
    private String credit_name;
    private String credit_info;
    private Boolean isWithdraw;

}
