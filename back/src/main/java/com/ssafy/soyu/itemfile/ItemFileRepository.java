package com.ssafy.soyu.itemfile;

import com.ssafy.soyu.file.ProfileImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemFileRepository extends JpaRepository<ItemFile, Long> {

//  @Query("select i.file from ItemFile i where i.item.id = :itemId order by i.file.id ASC limit 1")
//  Optional<ProfileImage> findByItemId(Long itemId);
}