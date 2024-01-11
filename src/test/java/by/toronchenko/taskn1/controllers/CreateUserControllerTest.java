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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CreateUserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersPageController usersPageController;

    @Test
    void testCreateUserPage() throws Exception {
        this.mockMvc.perform(get("/createuser"))
                .andExpect(model().attribute("companies", new ArrayList<>()));
    }

    @Test
    @Sql(value = {"/create-company-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-company-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCreateUser() throws Exception {
        this.mockMvc.perform(post("/createuser")
                .param("name", "Test")
                .param("password", "test")
                .param("company", "1"))
                .andExpect(redirectedUrl("/users"));
    }

}
