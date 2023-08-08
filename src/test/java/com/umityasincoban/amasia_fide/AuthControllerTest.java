package com.umityasincoban.amasia_fide;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.umityasincoban.amasia_fide.dto.RegisterDTO;
import com.umityasincoban.amasia_fide.dto.TokenDTO;
import com.umityasincoban.amasia_fide.service.UserService;
import com.umityasincoban.amasia_fide.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(JwtUtil.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUserTest() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("Ümit", "Yasin", "Çoban", "test123@test.com", "1111111111", "11111111111", "5373591579");

        TokenDTO token = new TokenDTO("", System.currentTimeMillis(), 401);
        Mockito.when(userService.register(registerDTO)).thenReturn(token);

        ObjectMapper objectMapper = new ObjectMapper();
        String registerDTOJson = objectMapper.writeValueAsString(registerDTO);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/register").content(registerDTOJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
            ).andExpect(status().isOk());
    }
}
