package com.logex.demo.repository;

import com.logex.demo.enums.EmployeeType;
import com.logex.demo.model.PayRoll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;

public interface PayrollRepository extends JpaRepository<PayRoll,Long> {

    List<PayRoll> findAllByIsDeletedFalse();

    PayRoll findByIdAndIsDeletedFalse(Long id);

    List<PayRoll> findByEmployee_EmployeeTypeAndIsDeletedFalse(EmployeeType empType);

    List<PayRoll> findAllByEmployee_IdAndIsDeletedFalse(Long id);

    PayRoll findByMonthYearAndEmployee_IdAndIsDeletedFalse(String month,Long id);

    List<PayRoll> findByMonthYearAndIsDeletedFalse(String monthyear);
}
