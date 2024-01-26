package com.ssafy.soyu.item.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ItemCategories {
  // 차례로 가전 , 옷, 가구, 책, 스포츠
  ELECTRONICS, CLOTHING, FURNITURE, BOOKS, SPORTS;
  @JsonCreator
  public static ItemCategories from(String s) {
    return ItemCategories.valueOf(s.toUpperCase());
  }

}
