package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    public Integer showSalary();
    public List<Employee> showSalaryMin();
    public List<Employee> showSalaryMax();
    public List<Employee> showSalaryHigh();

}
