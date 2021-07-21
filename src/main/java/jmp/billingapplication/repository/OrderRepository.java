package jmp.billingapplication.repository;

import jmp.billingapplication.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jmp.billingapplication.utils.BillingConstants.*;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query(findRedundantOrder)
    List<Orders> findRedundantOrder(@Param("orderId") int orderId, @Param("itemId") int itemId);

    @Modifying(clearAutomatically = true)
    @Query(updateOrderId)
    int updateOrderId(@Param("orderId") int orderId);

    @Query(getOrderId)
    List<Orders> getOrderId(@Param("itemId") int itemId);

    @Modifying
    @Query(updateQuantity)
    int updateQuantity(@Param("orderId") int orderId, @Param("itemId") int itemId, @Param("quantity") int quantity);

    @Query(findByOrderId)
    List<Orders> findByOrderId(@Param("orderId") int orderId);

    @Modifying
    @Query(deleteOrder)
    int deleteOrder(@Param("orderId") int orderId, @Param("itemId") int itemId);

}