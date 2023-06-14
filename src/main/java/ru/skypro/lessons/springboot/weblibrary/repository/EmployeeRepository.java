package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM employee",
            nativeQuery = true)
    List<Employee> findAllEmployees();

    List<Employee> findByIdGreaterThan(int number);

    Page<Employee> findAll(Pageable employeeOfConcretePage);

    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary.dto." +
            "EmployeeFullInfo(e.name , e.salary , p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p ")
    List<EmployeeFullInfo> findAllEmployeeFullInfo();

    @Query(value = "select emploee.name, employee.salary, position.name " +
            " from employee join position on employee.position_id = position.id where position.name = 'tester'", nativeQuery = true)
    List<Employee> getEmployeesFullPosition(@PathVariable(required = false) String position);
}
