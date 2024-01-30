package com.ssafy.soyu.station.service;

import com.ssafy.soyu.favorite.repository.FavoriteRepository;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.locker.LockerRepository;
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
  private final LockerRepository lockerRepository;
  private final FavoriteRepository favoriteRepository;

  public List<ListResponseDto> findAllStation(Long memberId) {
    return stationRepository.findAllWithMemberId(memberId)
        .stream()
        .map(o -> {
          Station s = (Station) o[0];
          boolean isFavorite = (Boolean) o[1];
          Integer count = lockerRepository.countNotEmptyLocker(s.getId());
          return new ListResponseDto(s, count, isFavorite);
        })
        .collect(Collectors.toList());
  }

  public List<DetailResponseDto> findOneStation(Long memberId, Long stationId) {
    return stationRepository.findOneWithMemberId(stationId)
        .stream()
        .map(s -> {
          List<Locker> ls = s.getLockers();
          boolean isFavorite = favoriteRepository.checkIsFavorite(memberId, stationId);
          return new DetailResponseDto(s, ls, isFavorite);
        })
        .collect(Collectors.toList());
  }
}
