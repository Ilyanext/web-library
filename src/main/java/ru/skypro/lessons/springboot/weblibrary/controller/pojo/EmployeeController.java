package ru.skypro.lessons.springboot.weblibrary.controller.pojo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        Integer sum = showEmployee().stream().map(Employee::getSalary).reduce(0, Integer::sum);
        return sum;
    }

    @GetMapping("/salary/min")
    public List<Employee> showSalaryMin() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        List<Employee> minSalary = showEmployee().stream()
                .min(comparator)
                .stream()
                .collect(Collectors.toList());
        return minSalary;

    }

    @GetMapping("/salary/max")
    public List<Employee> showSalaryMax() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        List<Employee> maxSalary = showEmployee().stream()
                .max(comparator)
                .stream()
                .collect(Collectors.toList());
        return maxSalary;
    }

    @GetMapping("/high-salary")
    public List<Employee> showSalaryHigh() {
        int chetcik = showEmployee().size();
        int midlSalary = showSalary() / chetcik;
        List<Employee> salaryBigerMidlSalary = showEmployee().stream().filter(i -> i.getSalary() >= midlSalary).toList();
        return salaryBigerMidlSalary;
    }
}