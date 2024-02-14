package com.ssafy.soyu.image.entity;

import com.ssafy.soyu.item.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude = {"item"})
public class Image {

  @Id
  @GeneratedValue
  @Column(name = "image_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  Item item;

  private String savePath;

  private String originalName;

  private String saveName;

  public void makeImage(Item item ,String today, String originalName, String saveName) {
    this.item = item;
    this.savePath = today;
    this.originalName = originalName;
    this.saveName = saveName;
  }

  public void addItem(Item item) {
    this.item = item;
    item.getImage().add(this);
  }
}
