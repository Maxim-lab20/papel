package com.example.papel.it;

import com.example.papel.entity.UserEntity;
import com.example.papel.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papel.openapi.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-it")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE papel.user_sequence RESTART WITH 1");
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String expectedJson = "{\"userId\":1,\"username\":\"testUser\",\"email\":\"test@example.com\"}";

        assertThat(expectedJson)
                .isEqualTo(response);

        UserDto createdUser = objectMapper.readValue(response, UserDto.class);
        UserEntity savedUser = userRepository.findById(createdUser.getUserId()).orElse(null);
        assertThat(savedUser.getUsername())
                .isEqualTo(userDto.getUsername());
        assertThat(savedUser.getEmail())
                .isEqualTo(userDto.getEmail());
    }
}
