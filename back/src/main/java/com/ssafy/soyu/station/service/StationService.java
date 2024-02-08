package com.ssafy.soyu.station.service;

import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.locker.dto.response.LockerResponseDto;
import com.ssafy.soyu.locker.service.LockerService;
import com.ssafy.soyu.station.entity.Station;
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
  private final LockerService lockerService;

  public List<ListResponseDto> findAllStation(Long memberId) {
    return stationRepository.findAllWithMemberId(memberId)
        .stream()
        .map(o -> {
          Station s = (Station) o[0];
          boolean isFavorite = o[1] == null ? false : (Boolean) o[1];
          Integer total = s.getLockers().size();
          Integer count = lockerRepository.countNotEmptyLocker(s.getId());
          return new ListResponseDto(s, total, count, isFavorite);
        })
        .collect(Collectors.toList());
  }

  public List<DetailResponseDto> findOneStation(Long memberId, Long stationId) {
    return stationRepository.findOneWithMemberId(memberId, stationId)
        .stream()
        .map(o -> {
          Station s = (Station) o[0];
          List<LockerResponseDto> ls = lockerService.getLockerResponse(s.getLockers(), memberId);
          boolean isFavorite = o[1] == null ? false : (Boolean) o[1];
          return new DetailResponseDto(s, ls, isFavorite);
        })
        .collect(Collectors.toList());
  }

}
