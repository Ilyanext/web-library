package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Integer showSalary() {
        Integer sum = getAllEmployees().stream().map(Employee::getSalary).reduce(0, Integer::sum);
        return sum;
    }

    @Override
    public List<Employee> showSalaryMin() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        List<Employee> minSalary = getAllEmployees().stream()
                .min(comparator)
                .stream()
                .collect(Collectors.toList());
        return minSalary;
    }

    @Override
    public List<Employee> showSalaryMax() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        List<Employee> maxSalary = getAllEmployees().stream()
                .max(comparator)
                .stream()
                .collect(Collectors.toList());
        return maxSalary;
    }

    @Override
    public List<Employee> showSalaryHigh() {
        int chetcik = getAllEmployees().size();
        int midlSalary = showSalary() / chetcik;
        List<Employee> salaryBigerMidlSalary = getAllEmployees().stream().filter(i -> i.getSalary() >= midlSalary).toList();
        return salaryBigerMidlSalary;
    }


    @Override
    public List<Employee> getEmployeesWithSalaryHigherThan(Integer salary) {
        List<Employee> salaryEmployeeBigerThenSalary = getAllEmployees().stream().filter(i -> i.getSalary() >= salary).toList();
        return salaryEmployeeBigerThenSalary;
    }

    @Override
    public List<Employee> getEmployeesByIdWithRequired(Integer id) {
        List<Employee> getIdEmplyee = getAllEmployees().stream().filter(i -> i.equals(getAllEmployees().get(id))).toList();
        return getIdEmplyee;
    }

    @Override
    public void deleteEmployeesWithId(Integer id) {
        getAllEmployees().remove(id);
    }

    @Override
    public void addEmployee(Employee employee) {
        getAllEmployees().add(employee);
    }

    @Override
    public void editEmployee(int id) {
        getAllEmployees().get(id);
    }
}
