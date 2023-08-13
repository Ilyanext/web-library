package ru.skypro.lessons.springboot.weblibrary.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.skypro.lessons.springboot.weblibrary.WebLibraryApplication;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.PositionDto;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WebLibraryApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public ReportRepository reportRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    private void cleanData() {
        employeeRepository.deleteAll();
    }

    @Test
    void getEmployeeInDatabase_thenEmptyJsonArray() throws Exception {
        mockMvc.perform(get("/employee")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").isArray()).
                andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deleteEmployeeById_thenCheckNotContainEmployee() throws Exception {
        Employee employee = new Employee(1, "Alex", 1200, new Position(0, "developer"));
        mockMvc.perform(delete("/employee/{id}", employee.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void addEmployee_test() throws Exception {
        List<Employee> employee = new ArrayList<>();
        employee.add(new Employee(1, "Alex", 12000, new Position(0, "test")));
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alex"));

    }

    @Test
    void editEmployee_changName() throws Exception {
//        List<Employee> employee = new ArrayList<>();
//        employee.add(new Employee(1, "Alex", 12000, new Position(0,"test")));

        int id = createTestEmployee("Nick").getId();
        mockMvc.perform(put("/employee/{id}", id)
                        .content(objectMapper.writeValueAsString(new Employee(id, "Michail")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/employee")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Michail"));
    }


    private Employee createTestEmployee(String name) {
        Employee employee = new Employee(name);
        return employeeRepository.save(employee);
    }

}

