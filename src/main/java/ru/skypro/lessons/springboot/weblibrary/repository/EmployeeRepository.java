package ru.skypro.lessons.springboot.weblibrary.repository;

import ru.skypro.lessons.springboot.weblibrary.controller.pojo.Employee;

import java.util.List;

public interface EmployeeRepository {
     List<Employee> getAllEmployees();
}
