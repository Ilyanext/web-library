package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> showEmployee() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/salary/sum")
    public Integer showSalary() {
        return employeeService.showSalary();
    }

    @GetMapping("/salary/min")
    public List<Employee> showSalaryMin() {
      return employeeService.showSalaryMin();

    }

    @GetMapping("/salary/max")
    public List<Employee> showSalaryMax() {
        return employeeService.showSalaryMax();
    }

    @GetMapping("/high-salary")
    public List<Employee> showSalaryHigh() {
      return employeeService.showSalaryHigh();
    }
}