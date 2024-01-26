package com.ssafy.soyu.item;

import com.ssafy.soyu.history.dto.response.SaleResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

  @Query("select i from Item i join fetch i.member m where i.member.id = :memberId")
  List<Item> findByMemberId(Long memberId);
}
