package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    // Коллекция для имитации данных
    private final List<Employee> employeeList = List.of(
            new Employee(0,"Катя", 90_000),
            new Employee(1,"Дима", 102_000),
            new Employee(2,"Олег", 80_000),
            new Employee(3,"Вика", 165_000));

    @Override
    public List<Employee> getAllEmployees() {
        return employeeList;
    }
}
