package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
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
        return employeeService.getEmployees();
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

    @PostMapping("/")
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @PutMapping("{id}")
    public void editEmployee(@RequestBody int id) {
        employeeService.editEmployee(id);
    }



    @GetMapping("fullInfo")
    public List<EmployeeFullInfo> getEmployeesFull() {
        return employeeService.getEmployeesFull();
    }

    @GetMapping("/paging/page")
    public List<Employee> getEmployeesWithPaging(@RequestParam("page") int page) {
        return employeeService.getEmployeesWithPaging(page, 10);
    }

    @GetMapping("/withHighestSalary")
    public List<Employee> withHighestSalary() {
        return employeeService.withHighestSalary();
    }

    @GetMapping(value = {"/","{position}"})
    public List<Employee> getEmployeesFullPosition(@PathVariable(required = false)String position) {
        return employeeService.getEmployeesFullPosition(position);
    }
}
