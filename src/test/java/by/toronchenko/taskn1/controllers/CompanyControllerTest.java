package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.validators.Error;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testShowCompanies() throws Exception {
        PageImpl<Company> page = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 5), 0);
        this.mockMvc.perform(get("/company/companies"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("companies", page));
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
    void testCreateNoValidCompany() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/company/createCompany")
                .param("name", "")).andReturn();
        List<Error> errors = new ArrayList<>();
        errors.add(Error.of("invalid.name", "Name is empty!"));
        assertEquals(errors.size(), ((List<Error>) mvcResult.getModelAndView().getModel().get("errors")).size());
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteCompany() throws Exception {
        this.mockMvc.perform(get("/company/deleteCompany/1")).andExpect(redirectedUrl("/company/companies"));
    }

    @Test
    void testDeleteNoFoundCompany() throws Exception {
        this.mockMvc.perform(get("/company/deleteCompany/10")).andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateCompanyPage() throws Exception {
        this.mockMvc.perform(get("/company/updateCompany/1")).andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateCompany() throws Exception {
        this.mockMvc.perform(post("/company/updateCompany/1")
                .param("name", "TEST1")).andExpect(redirectedUrl("/company/companies"));
    }

    @Test
    void testUpdateNoFoundCompany() throws Exception {
        this.mockMvc.perform(get("/company/updateCompany/10")).andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateNoValidCompany() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/company/updateCompany/1")
                .param("name", ""))
                .andReturn();
        List<Error> errors = new ArrayList<>();
        errors.add(Error.of("invalid.name", "Name is empty!"));
        assertEquals(errors.size(), ((List<Error>) mvcResult.getModelAndView().getModel().get("errors")).size());
    }

}
