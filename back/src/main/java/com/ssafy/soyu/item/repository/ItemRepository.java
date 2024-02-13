package com.ssafy.soyu.item.repository;


import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import java.util.List;
import com.ssafy.soyu.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

  @EntityGraph(attributePaths = {"member" , "image"})
  Item findItemById(Long id);

  @EntityGraph(attributePaths = {"member" , "image"})
  @Query("select i from Item i where i.itemStatus != 'SOLD' AND i.itemStatus != 'DELETED'")
  List<Item> findItemAll();

  @EntityGraph(attributePaths = {"member" , "image"})
  @Query("select i from Item i "
      + "where i.title like concat('%', :keyword, '%') AND "
      + "i.itemStatus != 'SOLD' AND i.itemStatus != 'DELETED'")
  List<Item> findItemByKeyWord(@Param("keyword") String keyword);

  @EntityGraph(attributePaths = {"member" , "image"})
  @Query("select i from Item i "
      + "where i.itemCategories = :itemCategories AND "
      + "i.itemStatus != 'SOLD' AND i.itemStatus != 'DELETED'")
  List<Item> findItemByItemCategories(ItemCategories itemCategories);

  @Query("select i from Item i join fetch i.member m where i.member.id = :memberId")
  List<Item> findByMemberId(@Param("memberId") Long memberId);

  @Modifying
  @Query("UPDATE Item SET itemStatus= :status where id=:itemId")
  int updateStatus(@Param("itemId") Long itemId, @Param("status") ItemStatus status);

  @Modifying
  @Query("UPDATE Item SET orderNumber = null WHERE id = :itemId")
  void deleteOrderNumber(@Param("itemId") Long itemId);

  @Modifying
  @Query("UPDATE Item SET orderNumber = :orderNumber WHERE id = :itemId")
  void updateOrderNumber(@Param("itemId") Long itemId, @Param("orderNumber") String orderNumber);

  @Modifying
  @Query("UPDATE Item SET discountPrice= :price where id=:itemId")
  void updateDiscountPrice(@Param("itemId") Long itemId,  @Param("price") Integer price);
}
