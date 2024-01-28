package com.ssafy.soyu.station.service;

import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.station.domain.Station;
import com.ssafy.soyu.station.dto.response.DetailResponseDto;
import com.ssafy.soyu.station.dto.response.ListResponseDto;
import com.ssafy.soyu.station.repository.StationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService {

  private final StationRepository stationRepository;

  public List<ListResponseDto> findAllStation(Long memberId) {
    return stationRepository.findAllWithMemberId(memberId)
        .stream()
        .map(object -> {
          Station s = (Station) object[0];
          boolean isFavorite = (Boolean) object[1];
          return new ListResponseDto(s, isFavorite);
        })
        .collect(Collectors.toList());
  }

  public List<DetailResponseDto> findOneStation(Long memberId, Long stationId) {
    return stationRepository.findOneWithMemberId(memberId, stationId)
        .stream()
        .map(object -> {
          Station s = (Station) object[0];
          Locker l = (Locker) object[1];
          boolean isFavorite = (Boolean) object[2];
          return new DetailResponseDto(s, l, isFavorite);
        })
        .collect(Collectors.toList());
  }
}
