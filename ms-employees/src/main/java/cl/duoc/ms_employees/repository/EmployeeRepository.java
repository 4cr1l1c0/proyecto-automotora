package cl.duoc.ms_employees.repository;

import cl.duoc.ms_employees.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
