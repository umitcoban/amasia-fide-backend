package com.umityasincoban.amasia_fide;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.entity.User;
import com.umityasincoban.amasia_fide.repository.UserRepository;
import com.umityasincoban.amasia_fide.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import(JwtUtil.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUserTest() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Ümit", "Yasin", "Çoban", "test123@test.com", "1111111111", "11111111111", "05373591576");
        String registerDTOJson = objectMapper.writeValueAsString(registerDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/register").content(registerDTOJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
            ).andExpect(status().isOk());

        Optional<User> foundUser = userRepository.findUserByEmail("test123@test.com");
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(registerDTO.email(), foundUser.get().getEmail());
    }

}
