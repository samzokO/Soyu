package com.ssafy.soyu.station.repository;

import com.ssafy.soyu.station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {

}
