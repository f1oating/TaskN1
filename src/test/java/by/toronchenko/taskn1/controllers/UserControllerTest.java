package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.validators.Error;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController controller;

    @Test
    void testShowUsers() throws Exception {
        this.mockMvc.perform(get("/user/users"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", new ArrayList<>()));
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testShowUser() throws Exception {
        this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user_name", "Test"));
    }

    @Test
    void testShowNoFoundUser() throws Exception {
        this.mockMvc.perform(get("/user/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateUserPage() throws Exception {
        this.mockMvc.perform(get("/user/updateUser/1")).andExpect(status().isOk());
    }

    @Test
    void testUpdateUserPageNotFound() throws Exception {
        this.mockMvc.perform(get("/user/updateUser/10")).andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateUser() throws Exception {
        this.mockMvc.perform(post("/user/updateUser/1")
                        .param("name", "Test1")
                        .param("password", "test1")
                        .param("company", "1"))
                .andExpect(redirectedUrl("/user/users"));
    }

    @Test
    void testUpdateNoFoundUser() throws Exception {
        this.mockMvc.perform(get("/user/updateUser/10")).andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateNoValidUser() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/user/updateUser/1")
                .param("name", "")
                .param("password", "")
                .param("company", "1"))
                .andReturn();
        List<Error> errors = new ArrayList<>();
        errors.add(Error.of("invalid.name", "Name is empty !"));
        errors.add(Error.of("invalid.password", "Password is empty!"));
        assertEquals(errors.size(), ((List<Error>) mvcResult.getModelAndView().getModel().get("errors")).size());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteUser() throws Exception {
        this.mockMvc.perform(get("/user/deleteUser/1")).andExpect(redirectedUrl("/user/users"));
    }

    @Test
    void testDeleteNoFoundUser() throws Exception {
        this.mockMvc.perform(get("/user/updateUser/10")).andExpect(status().isNotFound());
    }

    @Test
    void testCreateUserPage() throws Exception {
        this.mockMvc.perform(get("/user/createUser"))
                .andExpect(model().attribute("companies", new ArrayList<>()));
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateUser() throws Exception {
        this.mockMvc.perform(post("/user/createUser")
                        .param("name", "Test")
                        .param("password", "test")
                        .param("company", "1"))
                .andExpect(redirectedUrl("/user/users"));
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateNoValidUser() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/user/createUser")
                .param("name", "")
                .param("password", "")
                .param("company", "1"))
                .andReturn();
        List<Error> errors = new ArrayList<>();
        errors.add(Error.of("invalid.name", "Name is empty !"));
        errors.add(Error.of("invalid.password", "Password is empty!"));
        assertEquals(errors.size(), ((List<Error>) mvcResult.getModelAndView().getModel().get("errors")).size());
    }

}
