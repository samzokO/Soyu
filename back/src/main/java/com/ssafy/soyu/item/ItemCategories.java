package com.ssafy.soyu.item;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ItemCategories {
  // 차례로 가전 , 옷, 가구, 책, 스포츠
  Electronics, Clothing, Furniture, Books, Sports;
  @JsonCreator
  public static ItemCategories from(String s) {
    return ItemCategories.valueOf(s.toUpperCase());
  }

}
