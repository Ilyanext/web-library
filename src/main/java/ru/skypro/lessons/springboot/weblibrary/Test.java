package ru.skypro.lessons.springboot.weblibrary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("C:\\Users\\Дом\\IdeaProjects\\web-library\\File.json");

        List<EmployeeDTO> employeeDTO = objectMapper.readValue(file, new TypeReference<List<EmployeeDTO>>(){});
        List<Employee> newEmployee = employeeDTO.stream()
                .map(EmployeeDTO::toEmployee)
                .collect(Collectors.toList());

        System.out.println(newEmployee);

    }
}

