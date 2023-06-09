package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDto;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM employee",
            nativeQuery = true)
    List<EmployeeDTO> findAllEmployees();


    List<EmployeeDTO> findByIdGreaterThan(int number);

    Page<EmployeeDTO> findAll(Pageable employeeOfConcretePage);

    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary.dto." +
            "EmployeeFullInfo(e.id, e.name , e.salary , p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p and e.id= :id")
    List<EmployeeFullInfo> findAllEmployeeFullInfo(@Param("id") int id);


    @Query("SELECT new ru.skypro.lessons.springboot.weblibrary.dto." +
            "EmployeeFullInfo(e.name , e.salary , p.name) " +
            "FROM Employee e join fetch Position p " +
            "WHERE e.position = p and p.name = :position")
    List<EmployeeFullInfo> getEmployeesFullPosition(@Param("position") String position);

    @Query( "SELECT new ru.skypro.lessons.springboot.weblibrary.dto." +
            "EmployeeFullInfo(e.name , e.salary , p.name) " +
            "FROM Employee e  left join Position p on p.id= e.position.id " +
            "WHERE e.salary = (select  max (e.salary) from  Employee e) ")
    List<EmployeeFullInfo> withHighestSalary();
    @Query( "SELECT new ru.skypro.lessons.springboot.weblibrary.dto." +
            "EmployeeFullInfo(e.name , e.salary , p.name) " +
            "FROM Employee e  left join Position p on p.id= e.position.id " +
            "WHERE e.salary = (select  min (e.salary) from  Employee e) ")
    List<EmployeeFullInfo> withLowSalary();

    @Query( "SELECT new ru.skypro.lessons.springboot.weblibrary.dto." +
            "ReportDto(e.position.name, count (e.id), max(e.salary), min (e.salary), avg(e.salary)) " +
            "FROM Employee e  group by e.position " )

    List<ReportDto> buildReport();

    Optional<Employee> findAllById(int id);
}
