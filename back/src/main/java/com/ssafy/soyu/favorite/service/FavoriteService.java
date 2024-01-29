package com.ssafy.soyu.favorite.service;

import com.ssafy.soyu.favorite.dto.response.FavoriteListResponseDto;
import com.ssafy.soyu.favorite.domain.Favorite;
import com.ssafy.soyu.favorite.repository.FavoriteRepository;
import com.ssafy.soyu.locker.Locker;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.station.domain.Station;
import com.ssafy.soyu.station.repository.StationRepository;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final MemberRepository memberRepository;
  private final StationRepository stationRepository;

  @Transactional
  public void registFavorite(Long memberId, Long stationId) {
    Member member = memberRepository.getOne(memberId);
    Station station = stationRepository.getOne(stationId);

    if (station == null) {
      throw new CustomException(ErrorCode.STATION_NOT_FOUND);
    }

    Favorite favorite = new Favorite(member, station);
    favoriteRepository.save(favorite);
  }

  @Transactional
  public void deleteFavorite(Long favoriteId) {
    Favorite favorite = favoriteRepository.getOne(favoriteId);
    if (favorite == null) {
      throw new CustomException(ErrorCode.FAVORITE_NOT_FOUND);
    }
    favoriteRepository.deleteById(favoriteId);
  }

  /**
   * 즐겨찾기 목록 검색
   * @param memberId 사용자 식별자
   * @return FavoriteListResponseDto 리스트
   */
  public List<FavoriteListResponseDto> findByMemberId(Long memberId) {
    return favoriteRepository.findByMemberId(memberId)
        .stream()
        .map(object -> {
          Favorite f = (Favorite) object[0];
          Station s = (Station) object[1];
          List<Locker> ls = (List<Locker>) object[2];
          return new FavoriteListResponseDto(f, s, ls);
        })
        .collect(Collectors.toList());
  }
}
