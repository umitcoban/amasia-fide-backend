package com.umityasincoban.amasia_fide;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.umityasincoban.amasia_fide.dto.LoginDTO;
import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.TokenDTO;
import com.umityasincoban.amasia_fide.dto.UserActivateDTO;
import com.umityasincoban.amasia_fide.entity.User;
import com.umityasincoban.amasia_fide.repository.UserRepository;
import com.umityasincoban.amasia_fide.util.JwtUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(JwtUtil.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    private final String email = "test123@test.com";
    private final String password = "12345678";
    @BeforeAll
    public  void clearUser(){
        var user = userRepository.findUserByEmail(email);
        user.ifPresent(value -> userRepository.delete(value));
    }
    @Test
    @Order(1)
    void createUserTest() throws Exception {
       RegisterDTO registerDTO = new RegisterDTO("Ümit", "Yasin", "Çoban", email, password, "11111111111", "11111111111");
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
    @Test
    @Order(2)
    void createUserTest_InvalidData() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("", "", "", "invalidemail", "123", "", "");
        String registerDTOJson = objectMapper.writeValueAsString(registerDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/register").content(registerDTOJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isBadRequest()); // Geçersiz veri için uygun durum kodu
    }
    @Test
    @Order(3)
    void createdUser_NotActivatedLoginTest() throws Exception{
        var loginDTO = new LoginDTO(email, password);
        String loginDTOJSON = objectMapper.writeValueAsString(loginDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .content(loginDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isBadRequest());
    }
    @Test
    @Order(4)
    void createdUser_BadActivateTest() throws Exception{
        var user = userRepository.findUserByEmail(email).orElseThrow(RuntimeException::new);
        Random rnd = new Random();
        var userActivateDTO = new UserActivateDTO(email, rnd.nextInt(99999));
        String userActivateDTOJSON = objectMapper.writeValueAsString(userActivateDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/activate")
                        .content(userActivateDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isBadRequest());
    }
    @Test
    @Order(5)
    void createdUser_ActivateTest() throws Exception{
        var user = userRepository.findUserByEmail(email).orElseThrow(RuntimeException::new);
        var userActivateDTO = new UserActivateDTO(email, user.getRegistrationCode());
        String userActivateDTOJSON = objectMapper.writeValueAsString(userActivateDTO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/activate")
                        .content(userActivateDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isOk());
    }
    @Test
    @Order(6)
    void createdUser_LoginTest() throws Exception{
        var loginDTO = new LoginDTO(email, password);
        String loginDTOJSON = objectMapper.writeValueAsString(loginDTO);
        var result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .content(loginDTOJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isOk()).andReturn();
        var tokenDTO = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDTO.class);
        Assertions.assertNotNull(tokenDTO.token());
    }
}
