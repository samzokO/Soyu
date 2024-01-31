package com.ssafy.soyu.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * ELECTRONICS - 가전<br/>
 * CLOTHING - 옷<br/>
 * FURNITURE - 가구<br/>
 * BOOKS - 책<br/>
 * SPORTS - 스포츠<br/>
 */
public enum ItemCategories {
  // 차례로 가전 , 옷, 가구, 책, 스포츠
  ELECTRONICS, CLOTHING, FURNITURE, BOOKS, SPORTS;
  @JsonCreator
  public static ItemCategories from(String s) {
    return ItemCategories.valueOf(s.toUpperCase());
  }

}
