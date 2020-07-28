package com.logex.demo.repository;

import com.logex.demo.model.FoodPaymentLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodPaymentRepository extends JpaRepository<FoodPaymentLog,Long> {

    FoodPaymentLog findByIdAndIsDeletedFalse(Long id);
    List<FoodPaymentLog> findAllByIsDeletedFalse();

}
