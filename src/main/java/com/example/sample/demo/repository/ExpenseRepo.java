package com.example.sample.demo.repository;
import com.example.sample.demo.Category;
import com.example.sample.demo.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long> {

    List<Expense> findByCategoryId(Long categoryId);

    Optional<Category> findByIdAndCategoryId(Long Expenseid,Long Categoryid);

    @Query(value = "SELECT SUM(amount) FROM expense",nativeQuery = true)
    Double getTotalAmount();

    @Query(value = "select category.name,SUM(expense.amount) as amount from expense JOIN category ON expense.category_id=category.id GROUP BY expense.category_id",nativeQuery = true)
    List<Map<Object,Object>> getAmountByCategory();

}
