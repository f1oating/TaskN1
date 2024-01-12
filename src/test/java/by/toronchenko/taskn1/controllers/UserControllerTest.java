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
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testUpdateUserPage() throws Exception {
        this.mockMvc.perform(get("/user/updateUser/1")).andExpect(status().isOk());
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
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testDeleteUser() throws Exception {
        this.mockMvc.perform(get("/user/deleteUser/1")).andExpect(redirectedUrl("/user/users"));
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

}