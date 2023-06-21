package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.PaginEmployeeRepository;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PaginEmployeeRepository paginEmployeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PaginEmployeeRepository paginEmployeeRepository) {
        this.employeeRepository = employeeRepository;
        this.paginEmployeeRepository = paginEmployeeRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAllEmployees().stream()
                .map((EmployeeDTO employee) -> EmployeeDTO.fromEmployee(employee.toEmployee(employee)))
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployees() {
        return employeeRepository.findAllEmployees();
    }

    @Override
    public Integer showSalary() {
        Integer sum = getEmployees().stream().map(EmployeeDTO::getSalary).reduce(0, Integer::sum);
        return sum;
    }

    @Override
    public List<EmployeeDTO> showSalaryMin() {
        Comparator<EmployeeDTO> comparator = Comparator.comparing(EmployeeDTO::getSalary);
        List<EmployeeDTO> minSalary = getEmployees().stream()
                .min(comparator)
                .stream()
                .collect(Collectors.toList());
        return minSalary;
    }

    @Override
    public List<EmployeeDTO> showSalaryMax() {
        Comparator<EmployeeDTO> comparator = Comparator.comparing(EmployeeDTO::getSalary);
        List<EmployeeDTO> maxSalary = getEmployees().stream()
                .max(comparator)
                .stream()
                .collect(Collectors.toList());
        return maxSalary;
    }


    @Override
    public List<EmployeeDTO> getEmployeesWithSalaryHigherThan(Integer salary) {
        List<EmployeeDTO> salaryEmployeeBigerThenSalary = getEmployees().stream().filter(i -> i.getSalary() >= salary).toList();
        return salaryEmployeeBigerThenSalary;
    }

    @Override
    public List<EmployeeDTO> getEmployeesByIdWithRequired(Integer id) {
        List<EmployeeDTO> getIdEmplyee = getEmployees().stream().filter(i -> i.equals(getEmployees().get(id))).toList();
        return getIdEmplyee;
    }

    @Override
    public void deleteEmployeesWithId(int id) {
        employeeRepository.deleteById(id);
    }


    // Метод для добавления нового сотрудника
    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void editEmployee(int id) {
        getEmployees().get(id);
    }

    @Override
    public List<EmployeeDTO> findByIdGreaterThan(int number) {
        return employeeRepository.findByIdGreaterThan(10000);
    }


    @Override
    public List<EmployeeFullInfo> getEmployeesFull(int id) {
        return employeeRepository.findAllEmployeeFullInfo(id);
    }

    @Override
    public List<EmployeeFullInfo> getEmployeesFullPosition(String position) {
        return employeeRepository.getEmployeesFullPosition(position);

    }

    @Override
    public List<EmployeeFullInfo> withHighestSalary() {
        return employeeRepository.withHighestSalary();
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithPaging(int page, int size) {
        Pageable employeeOfConcretePage = PageRequest.of(page, size);
        Page<EmployeeDTO> allPage = employeeRepository.findAll(employeeOfConcretePage);

        return allPage.stream()
                .toList();
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException { // ?????

        ObjectMapper objectMapper = new ObjectMapper();
        List<EmployeeDTO> employeeDTO = objectMapper.readValue((JsonParser) file, new TypeReference<List<EmployeeDTO>>() {
        });
        List<Employee> newEmployee = employeeDTO.stream()
                .map(EmployeeDTO::toEmployee)
                .collect(Collectors.toList());
        employeeRepository.saveAll(newEmployee);

    }


}


