package com.ssafy.soyu.itemfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemFileRepository extends JpaRepository<ItemFile, Long> {

//  @Query("select i.file from ItemFile i where i.item.id = :itemId order by i.file.id ASC limit 1")
//  Optional<ProfileImage> findByItemId(Long itemId);
}