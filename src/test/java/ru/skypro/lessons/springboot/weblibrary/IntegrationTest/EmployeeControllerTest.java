package ru.skypro.lessons.springboot.weblibrary.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary.WebLibraryApplication;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

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
    @SneakyThrows
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
    @SneakyThrows
    void addEmployee_test() throws Exception {
//        Employee employee = new Employee(1, "Alex", 1200, new Position(0, "developer"));
//        mockMvc.perform(post("/employee"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .contentType(objectMapper.writeValueAsString(employee))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.name").value("Alex"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "test_name");

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("test_name"));

    }

    @Test
    void editEmployee_changName() throws Exception {
        int id = createTestEmployee("Nick").getId();
//    Employee employee = new Employee(1, "Alex", 1200, new Position(0, "developer"));
        mockMvc.perform(put("employee/{id}", id))
                .content(objectMapper.writeValueAsString(new Employee("Michail")))
                .contentType(MediaType.APPLICATION_JSON)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Michail"));
    }


    private Employee createTestEmployee(String name) {
        Employee employee = new Employee(name);
        return employeeRepository.save(employee);
    }

}

