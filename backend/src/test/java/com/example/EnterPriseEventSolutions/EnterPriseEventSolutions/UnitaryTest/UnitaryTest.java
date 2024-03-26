package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.UnitaryTest;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.DataBaseInitializer;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.UserRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController REST tests - MockedMVC")
public class UnitaryTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DataBaseInitializer dataBaseInitializer;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp(){
        user2 = new User("URJC", "patxi@example.com", passwordEncoder.encode("pass"));
        user3 = new User("Michel", "michel@example.com", passwordEncoder.encode("pass"));
    }

    @Test
    @DisplayName("Check that all users can be fetched as Admin")
    @WithMockUser(username = "Admin", password = "pass", roles = "ADMIN")
    public void getUsers() throws Exception{

        List<User> fakeUsers = Arrays.asList(user2, user3);

        when(userRepository.findAll()).thenReturn(fakeUsers);

        mvc.perform(
                get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userRepository).findAll();

    }



}
