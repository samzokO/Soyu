package com.ssafy.soyu.item;

import com.ssafy.soyu.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  private String title;

  private String content;

  private LocalDateTime regDate;

  private Integer price;

  @Enumerated(EnumType.STRING)
  private ItemStatus itemStatus;

  @Enumerated(EnumType.STRING)
  private ItemCategories itemCategories;

  public Item(Member member, String title, String content, Integer price, ItemCategories itemCategories ) {
    this.member = member;
    this.title = title;
    this.content = content;
    this.price = price;
    this.itemCategories = itemCategories;
  }

  public void updateItem(String title, String content, Integer price, ItemCategories itemCategories ) {
    this.title = title;
    this.content = content;
    this.price = price;
    this.itemCategories = itemCategories;
  }

  public void updateItemStatus(ItemStatus itemStatus) {
    this.itemStatus = itemStatus;
  }

}
