package ru.skypro.lessons.springboot.weblibrary.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class RepostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    private void cleanData() {
        employeeRepository.deleteAll();
    }


    @Test
    void report_Test() throws Exception {
        mockMvc.perform(post("/report")).
                andExpect(status().isOk());
    }

    @Test
    void find_Test() throws Exception {
        int id=0;
        mockMvc.perform(get("/report/{id}", id))
                .andExpect(status().isOk());

    }
}