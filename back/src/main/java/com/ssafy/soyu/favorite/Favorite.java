package com.ssafy.soyu.favorite;

import com.ssafy.soyu.station.Station;
import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "favorite")
@Getter
public class Favorite {

    @Id
    @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;
}
