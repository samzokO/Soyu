package com.ssafy.soyu.locker;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.station.domain.Station;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "locker")
@Getter
public class Locker {
    @Id
    @GeneratedValue
    @Column(name = "locker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String code;
    Boolean isLight;
    Boolean isVisible;

    private LockerStatus isFill;
    private String location;
    private LocalDateTime time;
}
