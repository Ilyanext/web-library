package ru.skypro.lessons.springboot.weblibrary.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository mockRepository;
    @InjectMocks
    private  EmployeeServiceImpl employeeService;

    @Test
    public void getAllEmployees_Test_OK() {

        List<EmployeeDTO> employeeDTOS = employeesTest().stream().map(EmployeeDTO::fromEmployee).collect(Collectors.toList());
        when(mockRepository.findAllEmployees()).thenReturn(employeeDTOS);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(employeeDTOS.size(), employees.size());

    }
    @Test
    public  void getEmployees_OK(){
       when(mockRepository.findAll()).thenReturn(employeesTest());
       assertTrue(employeesTest().isEmpty());
    }

    public  List<Employee> employeesTest() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Ilya", 100000, new Position(1, "developer")));
        employeeList.add(new Employee(2, "Anna", 90000, new Position(2, "tester")));
        employeeList.add(new Employee(3, "Ilyas", 1100000, new Position(1, "developer")));

        return employeeList;
    }

}
