package com.logex.demo.repository;

import com.logex.demo.enums.EmployeeType;
import com.logex.demo.enums.JobStatus;
import com.logex.demo.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findAllByIsDeletedFalseOrderById();

    Employee findByIdAndIsDeletedFalse(Long id);

    Employee findByEmpIdAndIsDeletedFalse(String id);

    @Query(value = "SELECT MAX(seq.next_val) FROM hibernate_sequence seq",nativeQuery = true)
    Long getHighestId();

    @Query(value = "SELECT * from employee e where is_deleted=0 and job_status IN (?1,?2) and employee_type = ?3",nativeQuery = true)
    List<Employee> getAllActiveReserveDrivers(Integer value1, Integer value2,Integer value3);

    List<Employee> findAllByEmployeeTypeAndIsDeletedFalse(EmployeeType employeeType);

    @Query(value = "Select * from employee p where is_deleted=0 and p.employee_type IN ?1",nativeQuery = true)
    List<Employee> getAllByEmployeeTypeList(List<Integer> list);

    @Query("select employee from Employee employee join fetch employee.terminationDetails where employee.terminationDetails is not null")
    List<Employee> getAllTerminatedEmployees();
}
