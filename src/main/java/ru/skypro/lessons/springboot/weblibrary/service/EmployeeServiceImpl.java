package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDto;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.PaginEmployeeRepository;

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
    public List<EmployeeDto> getAllEmployees() {
        // Получаем список сотрудников из репозитория,
        // Преобразуем их в DTO и собираем в список
        return employeeRepository.findAllEmployees().stream()
                .map(EmployeeDto::fromEmployee)
                .collect(Collectors.toList());
    }
    public List<Employee> getEmployees() {
        return employeeRepository.findAllEmployees();
    }

    @Override
    public Integer showSalary() {
        Integer sum = getEmployees().stream().map(Employee::getSalary).reduce(0, Integer::sum);
        return sum;
    }

    @Override
    public List<Employee> showSalaryMin() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        List<Employee> minSalary = getEmployees().stream()
                .min(comparator)
                .stream()
                .collect(Collectors.toList());
        return minSalary;
    }

    @Override
    public List<Employee> showSalaryMax() {
        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        List<Employee> maxSalary = getEmployees().stream()
                .max(comparator)
                .stream()
                .collect(Collectors.toList());
        return maxSalary;
    }

    @Override
    public List<Employee> withHighestSalary() {
        int chetcik = getEmployees().size();
        int midlSalary = showSalary() / chetcik;
        List<Employee> salaryBigerMidlSalary = getEmployees().stream().filter(i -> i.getSalary() >= midlSalary).toList();
        return salaryBigerMidlSalary;
    }


    @Override
    public List<Employee> getEmployeesWithSalaryHigherThan(Integer salary) {
        List<Employee> salaryEmployeeBigerThenSalary = getEmployees().stream().filter(i -> i.getSalary() >= salary).toList();
        return salaryEmployeeBigerThenSalary;
    }

    @Override
    public List<Employee> getEmployeesByIdWithRequired(Integer id) {
        List<Employee> getIdEmplyee = getEmployees().stream().filter(i -> i.equals(getEmployees().get(id))).toList();
        return getIdEmplyee;
    }

    @Override
    public void deleteEmployeesWithId(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesWithPaging(int page,int size) {
        Pageable employeeOfConcretePage =  PageRequest.of(page,size);
        Page<Employee> allPage = employeeRepository.findAll(employeeOfConcretePage);

        return allPage.stream()
                .toList();
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
    public List<Employee> findByIdGreaterThan(int number) {
        return employeeRepository.findByIdGreaterThan(10000);
    }



    @Override
    public List<EmployeeFullInfo> getEmployeesFull() {
        return employeeRepository.findAllEmployeeFullInfo();
    }

    @Override
    public List<Employee> getEmployeesFullPosition(String position) {
        return employeeRepository.getEmployeesFullPosition(position);
    }


}
