package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    public Integer showSalary();

    public List<Employee> showSalaryMin();

    public List<Employee> showSalaryMax();

    public List<Employee> showSalaryHigh();

    public List<Employee> getEmployeesWithSalaryHigherThan(Integer salary);

    public List<Employee> getEmployeesByIdWithRequired(Integer id);

    void deleteEmployeesWithId(Integer id);

    void addEmployee(@RequestBody Employee employee);

    void editEmployee(@RequestBody int id);


}
