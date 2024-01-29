package com.crm.wm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.crm.wm.entities.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  
//find employees by role name
  @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r.name = ?1")
  List<Employee> findByRoleName(String roleName);

  // find employees by first name or last name containing a keyword
  List<Employee> findByFirstNameContainingOrLastNameContaining(String keyword1, String keyword2);
}
