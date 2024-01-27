package com.ssafy.soyu.station.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "station")
@Getter
public class Station {
    @Id
    @GeneratedValue
    @Column(name = "station_id")
    private Long id;

    private String name;
    private Float latitude;
    private Float longitude;
    private String address;
}
