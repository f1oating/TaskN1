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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyController companyController;

    @Test
    void testShowUsers() throws Exception {
        this.mockMvc.perform(get("/company/companies"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("companies", new ArrayList<>()));
    }

    @Test
    void testCreateCompanyPage() throws Exception {
        this.mockMvc.perform(get("/company/createCompany")).andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateCompany() throws Exception {
        this.mockMvc.perform(post("/company/createCompany")
                        .param("name", "TEST"))
                .andExpect(redirectedUrl("/company/companies"));
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteUser() throws Exception {
        this.mockMvc.perform(get("/company/deleteCompany/1")).andExpect(redirectedUrl("/company/companies"));
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateCompaniePage() throws Exception {
        this.mockMvc.perform(get("/company/updateCompany/1")).andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateCompanie() throws Exception {
        this.mockMvc.perform(post("/company/updateCompany/1")
                .param("name", "TEST1")).andExpect(redirectedUrl("/company/companies"));
    }

}
