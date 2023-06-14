package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDto;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();

    List<Employee> getEmployees();

    public Integer showSalary();

    public List<Employee> showSalaryMin();

    public List<Employee> showSalaryMax();

    public List<Employee> withHighestSalary();

    public List<Employee> getEmployeesWithSalaryHigherThan(Integer salary);

    public List<Employee> getEmployeesByIdWithRequired(Integer id);

    void deleteEmployeesWithId(int id);

    List<Employee> getEmployeesWithPaging(int page, int size);

    void addEmployee(@RequestBody Employee employee);

    void editEmployee(@RequestBody int id);
    List<Employee> findByIdGreaterThan(int number);


    List<EmployeeFullInfo> getEmployeesFull();
    List<Employee> getEmployeesFullPosition(@PathVariable(required = false)String position);
}

