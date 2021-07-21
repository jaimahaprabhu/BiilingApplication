package jmp.billingapplication.repository;

import jmp.billingapplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jmp.billingapplication.utils.BillingConstants.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(findCategoryName)
    List<Category> findCategoryName(@Param("categoryName") String categoryName);

    @Modifying(clearAutomatically = true)
    @Query(updateCategory)
    int updateCategory(@Param("categoryName") String categoryName, @Param("id") int id);

    @Query(findCategoryId)
    List<Category> findCategoryId(@Param("id") int categoryId);

}