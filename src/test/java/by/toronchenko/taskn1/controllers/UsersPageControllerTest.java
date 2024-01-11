package by.toronchenko.taskn1.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class UsersPageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersPageController controller;

    @Test
    void testShowUsers() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", new ArrayList<>()));
    }

}
