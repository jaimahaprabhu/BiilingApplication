package jmp.billingapplication.repository;

import jmp.billingapplication.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jmp.billingapplication.utils.BillingConstants.*;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    @Query(findByCompositeKey)
    List<Item> findByCompositeKey(@Param("CATEGORYNAME") String catName, @Param("ITEMNAME") String itemName,
                                  @Param("PRICE") float price, @Param("TAX") float tax);

    @Query(findByItemId)
    List<Item> findByItemId(@Param("itemId") int itemId);

    @Query(findItemId)
    List<Item> findItemId(@Param("itemName") String itemName);

    @Modifying(clearAutomatically = true)
    @Query(updateItemPrice)
    int updateItemPrice(@Param("itemId") int id, @Param("price") float price);

    @Modifying(clearAutomatically = true)
    @Query(updateItemTax)
    void updateItemTax(@Param("itemId") int id, @Param("tax") float tax);

    @Modifying(clearAutomatically = true)
    @Query(updateItemQuantity)
    void updateItemQuantity(@Param("itemId") int id, @Param("quantity") int quantity);

    @Modifying
    @Query(deleteByItemId)
    void deleteByItemId(@Param("itemId") int id);

}