package com.ssafy.soyu.favorite.service;

import com.ssafy.soyu.favorite.domain.Favorite;
import com.ssafy.soyu.favorite.repository.FavoriteRepository;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.station.domain.Station;
import com.ssafy.soyu.station.repository.StationRepository;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
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

    if(station == null)
      throw new CustomException(ErrorCode.STATION_NOT_FOUND);

    Favorite favorite = new Favorite(member, station);
    favoriteRepository.save(favorite);
  }

  @Transactional
  public void deleteFavorite(Long favoriteId){
    Favorite favorite = favoriteRepository.getOne(favoriteId);
    if(favorite == null)
      throw new CustomException(ErrorCode.FAVORITE_NOT_FOUND);
    favoriteRepository.deleteById(favoriteId);
  }
}
