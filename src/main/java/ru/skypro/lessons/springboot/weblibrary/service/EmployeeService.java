package ru.skypro.lessons.springboot.weblibrary.service;

import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();

    List<EmployeeDTO> getEmployees();

    public Integer showSalary();

    public List<EmployeeDTO> showSalaryMin();

    public List<EmployeeDTO> showSalaryMax();

    public List<EmployeeDTO> getEmployeesWithSalaryHigherThan(Integer salary);

    public List<EmployeeDTO> getEmployeesByIdWithRequired(Integer id);

    void deleteEmployeesWithId(int id);



    void addEmployee(@RequestBody Employee employee);



    void editEmployee(@RequestBody int id);
    List<EmployeeDTO> findByIdGreaterThan(int number);


    List<EmployeeFullInfo> getEmployeesFull(int id);
    List<EmployeeFullInfo> getEmployeesFullPosition(@Nullable String position);
    List<EmployeeFullInfo> withHighestSalary();
    List<EmployeeDTO> getEmployeesWithPaging(int page, int size);
    void uploadFile(@RequestParam("file") MultipartFile file) throws IOException;
}

