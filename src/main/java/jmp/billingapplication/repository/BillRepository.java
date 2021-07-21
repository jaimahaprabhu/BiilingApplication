package jmp.billingapplication.repository;

import jmp.billingapplication.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jmp.billingapplication.utils.BillingConstants.findBillByOrderId;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query(findBillByOrderId)
    List<Bill> findBillByOrderId(@Param("orderId") int orderId);

}