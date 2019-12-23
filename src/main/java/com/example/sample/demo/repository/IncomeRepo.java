package com.example.sample.demo.repository;

import com.example.sample.demo.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {

    @Query(value = "SELECT SUM(amount) FROM income",nativeQuery = true)
    Double getTotalAmount();

}
