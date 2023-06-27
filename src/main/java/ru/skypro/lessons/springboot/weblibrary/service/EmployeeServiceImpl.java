package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.PaginEmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;
    private final PaginEmployeeRepository paginEmployeeRepository;

    private final ObjectMapper objectMapper;
    private final Optional optional;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ReportRepository reportRepository, PaginEmployeeRepository paginEmployeeRepository, ObjectMapper objectMapper, Optional optional) {
        this.employeeRepository = employeeRepository;
        this.reportRepository = reportRepository;
        this.paginEmployeeRepository = paginEmployeeRepository;
        this.objectMapper = objectMapper;
        this.optional = optional;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees().stream()
                .map(EmployeeDTO::toEmployee)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        return employees;
    }

    @Override
    public Integer showSalary() {
        Integer sum = getEmployees().stream().map(Employee::getSalary).reduce(0, Integer::sum);
        return sum;
    }

    @Override
    public Integer showAvgSalary() {

        Integer count = getEmployees().size();
        Integer avg = showSalary() / count;
        return avg;
    }


    @Override
    public List<EmployeeDTO> showSalaryMin() {
        Comparator<EmployeeDTO> comparator = Comparator.comparing(EmployeeDTO::getSalary);
        List<EmployeeDTO> minSalary = getEmployees().stream().
                map(EmployeeDTO::fromEmployee)
                .min(comparator)
                .stream()
                .collect(Collectors.toList());
        return minSalary;
    }

    @Override
    public List<EmployeeDTO> showSalaryMax() {
        Comparator<EmployeeDTO> comparator = Comparator.comparing(EmployeeDTO::getSalary);
        List<EmployeeDTO> maxSalary = getEmployees().stream().map(EmployeeDTO::fromEmployee)
                .max(comparator)
                .stream()
                .collect(Collectors.toList());
        return maxSalary;
    }


    @Override
    public List<EmployeeDTO> getEmployeesWithSalaryHigherThan(Integer salary) {
        List<EmployeeDTO> salaryEmployeeBigerThenSalary = getEmployees().stream().map(EmployeeDTO::fromEmployee).filter(i -> i.getSalary() >= salary).toList();
        return salaryEmployeeBigerThenSalary;
    }

    @Override
    public List<EmployeeDTO> getEmployeesByIdWithRequired(Integer id) {
        List<EmployeeDTO> getIdEmplyee = getEmployees().stream().map(EmployeeDTO::fromEmployee).filter(i -> i.equals(getEmployees().get(id))).toList();
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
    public List<EmployeeFullInfo> withLowSalary() {
        return employeeRepository.withLowSalary();
    }


    @Override
    public List<EmployeeDTO> getEmployeesWithPaging(int page, int size) {
        Pageable employeeOfConcretePage = PageRequest.of(page, size);
        Page<EmployeeDTO> allPage = employeeRepository.findAll(employeeOfConcretePage);

        return allPage.stream()
                .toList();
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        List<EmployeeDTO> employeeDTO = objectMapper.readValue(file.getBytes(), new TypeReference<List<EmployeeDTO>>() {
        });
        List<Employee> newEmployee = employeeDTO.stream()
                .map(EmployeeDTO::toEmployee)
                .collect(Collectors.toList());
        employeeRepository.saveAll(newEmployee);

    }

    @Override
    public int generateReport() {
        var report = employeeRepository.buildReport();
        try {
            var content = objectMapper.writeValueAsString(report);

            var path = generateReportFile(content);
            var reportEntity = new Report();
            reportEntity.setReport(content);
            reportEntity.setPath(path);
            return reportRepository.save(reportEntity).getId();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot generate report", e);
        }

    }
    @Transactional
    @Override
    public Resource findReport(int id) {
        return new ByteArrayResource(reportRepository.findAllById(id)
                .orElseThrow(()->new IllegalStateException("Report with id " + id + " not found"))
                .getReport()
                .getBytes(StandardCharsets.UTF_8));
    }
    @Transactional
    @Override
    public File findReportFile(int id) {
        return reportRepository.findById(id)
                .map(Report::getPath)
                .map(File::new)
                .orElse(null);

    }

    @Override
    public String generateReportFile(String content) {
        var f = new File("report_" + System.currentTimeMillis() + ".json");
        try (var writer = new FileWriter(f)) {
            writer.write(content);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot generate report file", e);
        }
        return f.getName();
    }


}


