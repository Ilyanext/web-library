package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;

import java.util.List;

public interface ReportService {
    Report getStatisticEmployee();
    Integer getCountEmployees();
    List<EmployeeDTO> showSalaryMin();
    List<EmployeeDTO> showSalaryMax();
    List<EmployeeDTO> showSalaryAvg();

}
