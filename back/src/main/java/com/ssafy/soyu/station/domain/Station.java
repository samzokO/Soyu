package com.ssafy.soyu.station.domain;

import com.ssafy.soyu.locker.Locker;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "station")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station {
    @Id
    @GeneratedValue
    @Column(name = "station_id")
    private Long id;

    private String name;
    private Float latitude;
    private Float longitude;
    private String address;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Locker> lockers = new ArrayList<>();

    public Station(String name, Float latitude, Float longitude, String address) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
