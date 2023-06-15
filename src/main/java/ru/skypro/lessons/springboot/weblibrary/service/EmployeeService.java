package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDto;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();

    List<EmployeeDto> getEmployees();

    public Integer showSalary();

    public List<EmployeeDto> showSalaryMin();

    public List<EmployeeDto> showSalaryMax();

    public List<EmployeeDto> getEmployeesWithSalaryHigherThan(Integer salary);

    public List<EmployeeDto> getEmployeesByIdWithRequired(Integer id);

    void deleteEmployeesWithId(int id);



    void addEmployee(@RequestBody Employee employee);



    void editEmployee(@RequestBody int id);
    List<EmployeeDto> findByIdGreaterThan(int number);


    List<EmployeeFullInfo> getEmployeesFull(int id);
    List<EmployeeDto> getEmployeesFullPosition(String positionEmployee);
    List<EmployeeDto> withHighestSalary();
    List<EmployeeDto> getEmployeesWithPaging(int page, int size);
}

