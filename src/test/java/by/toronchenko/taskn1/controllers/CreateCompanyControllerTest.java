package by.toronchenko.taskn1.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CreateCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersPageController usersPageController;

    @Test
    void testCreateCompanyPage() throws Exception {
        this.mockMvc.perform(get("/createcompany")).andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateCompany() throws Exception {
        this.mockMvc.perform(post("/createcompany")
                .param("name", "TEST"))
                .andExpect(redirectedUrl("/companies"));
    }

}
