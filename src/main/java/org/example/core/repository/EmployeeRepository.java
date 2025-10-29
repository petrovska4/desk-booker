package org.example.core.repository;

import org.example.core.model.Employee;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface EmployeeRepository extends GenericRepository<Employee> {
    Boolean existsByUuid(UUID uuid);
    Employee findByEmail(String email);
    boolean existsByEmail(String email);
}
