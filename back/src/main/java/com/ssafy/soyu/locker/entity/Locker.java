package com.ssafy.soyu.locker.entity;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.station.entity.Station;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "locker")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String code;
    Boolean isLight;
    Boolean isVisible;

    @Enumerated(EnumType.STRING)
    private LockerStatus status;
    private Integer lockerNum;
    private LocalDateTime time;

    public Locker(Station station, Integer lockerNum){
        this.station = station;
        this.status = LockerStatus.AVAILABLE;
        this.lockerNum = lockerNum;
        this.isLight = false;
        this.isVisible = false;
    }

    public Locker(Station station, Item item, String code, LockerStatus status, LocalDateTime reserveTime, Integer lockerNum){
        this.station = station;
        this.item = item;
        this.code = code;
        this.status = status;
        this.time = reserveTime;
        this.lockerNum = lockerNum;
    }
}
