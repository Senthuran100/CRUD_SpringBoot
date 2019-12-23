package com.example.sample.demo.repository;

import com.example.sample.demo.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long>{

    @Query(value = "SELECT SUM(amount) FROM category",nativeQuery = true)
    Double getTotalAmount();
}
