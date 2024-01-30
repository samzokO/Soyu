package com.ssafy.soyu.locker;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.station.domain.Station;
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
    @GeneratedValue
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
    private String location;
    private LocalDateTime time;

    public Locker(Item item, String code, LockerStatus status, LocalDateTime reserveTime){
        this.item = item;
        this.code = code;
        this.status = status;
        this.time = reserveTime;
    }
}
