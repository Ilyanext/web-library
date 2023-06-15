package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDto;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM employee",
            nativeQuery = true)
    List<EmployeeDto> findAllEmployees();

    List<EmployeeDto> findByIdGreaterThan(int number);

    Page<EmployeeDto> findAll(Pageable employeeOfConcretePage);

    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary.dto." +
            "EmployeeFullInfo(e.id, e.name , e.salary , p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p and e.id= :id")
    List<EmployeeFullInfo> findAllEmployeeFullInfo(@Param("id") int id);

    @Query(value = "select emploee.name, employee.salary, position.name " +
            " from employee join position on employee.position_id = position.id where position.name = (:positionEmployee)", nativeQuery = true)
    List<EmployeeDto> getEmployeesFullPosition(@Param("positionEmployee") String positionEmployee);

    @Query(value = "select* from employee where salary = (select max(salary) from employee); ", nativeQuery = true)
    List<EmployeeDto> withHighestSalary();
}
