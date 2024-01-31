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
import java.util.Optional;
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
    Optional<Member> member = memberRepository.findById(memberId);
    Optional<Station> station = stationRepository.findById(stationId);

    if (!station.isPresent()) {
      throw new CustomException(ErrorCode.STATION_NOT_FOUND);
    }

    Optional<Favorite> f = favoriteRepository.isExist(memberId, stationId);

    if (f.isPresent()) {
      throw new CustomException(ErrorCode.ALREADY_FAVORITE_STATION);
    }

    Favorite favorite = new Favorite(member.get(), station.get());
    favoriteRepository.save(favorite);
  }

  @Transactional
  public void deleteFavorite(Long memberId, Long stationId) {
    Optional<Favorite> favorite = favoriteRepository.isExist(memberId, stationId);
    if (!favorite.isPresent()) {
      throw new CustomException(ErrorCode.FAVORITE_NOT_FOUND);
    }
    favoriteRepository.delete(favorite.get());
  }

  /**
   * 즐겨찾기 목록 검색
   *
   * @param memberId 사용자 식별자
   * @return FavoriteListResponseDto 리스트
   */
  public List<FavoriteListResponseDto> findByMemberId(Long memberId) {
    return favoriteRepository.findByMemberId(memberId)
        .stream()
        .map(object -> {
          Favorite f = (Favorite) object[0];
          Station s = (Station) object[1];
          List<Locker> ls = s.getLockers();
          return new FavoriteListResponseDto(f, s, ls);
        })
        .collect(Collectors.toList());
  }
}
