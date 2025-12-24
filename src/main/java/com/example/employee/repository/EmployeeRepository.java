package com.example.employee.repository;

import com.example.employee.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //(By creating Query)
    //List<Employee> findByDepartment(String department);
    //List<Employee> findByDepartment(String name);   // here "name" means department name

    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.profileImageUrl = :image WHERE e.id = :id")
    void updateImage(@Param("id") long id, @Param("image") String image);

}
