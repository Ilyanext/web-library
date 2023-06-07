package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.List;

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


    @GetMapping("/salaryHigherThan")
    public List<Employee> getEmployeesWithSalaryHigherThan(@RequestParam("salary") Integer salary) {
        return employeeService.getEmployeesWithSalaryHigherThan(salary);
    }

    @GetMapping("{id}")
    public List<Employee> getEmployeesByIdWithRequired(@PathVariable(required = false) Integer id) {
        return employeeService.getEmployeesByIdWithRequired(id);
    }

    @DeleteMapping("{id}")//?
    public void deleteEmployeesWithId(@PathVariable(required = false) Integer id) {
        employeeService.deleteEmployeesWithId(id);
    }

    @PostMapping("/") //?
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @PutMapping("{id}") //?
    public void editEmployee(@RequestBody int id) {
        employeeService.editEmployee(id);
    }
}
