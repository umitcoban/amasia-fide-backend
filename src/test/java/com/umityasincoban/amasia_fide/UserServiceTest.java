package com.umityasincoban.amasia_fide;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.umityasincoban.amasia_fide.entity.User;
import com.umityasincoban.amasia_fide.repository.UserRepository;
import com.umityasincoban.amasia_fide.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByUsername() {
        final String email = "umityasincoban@gmail.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user)); // userRepository'nin findByUsername metodunun "testuser" argümanı ile çağrıldığında "user" döndürmesini sağlar.

        User result = userService.getUserByEmail(email);

        assertEquals(user.getUsername(), result.getUsername()); // Kontrol edilen değer ile beklenen değerin aynı olup olmadığını kontrol eder.
        verify(userRepository, times(1)).findByEmail(email); // userRepository'nin findByUsername metodunun bir kere çağrılıp çağrılmadığını kontrol eder.
    }

   @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        user.setPhone("05373591570");
        user.setCitizenNumber("11111111111");
        user.setFirstName("test");
        user.setLastName("test");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createNewUser(user);

        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setEmail("testuser@test.com");
        User user2 = new User();
        user2.setEmail("testuser1@test.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2)); // userRepository'nin findAll metodunun çağrıldığında "user1" ve "user2" döndürmesini sağlar.

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size()); // Kontrol edilen listesinin boyutunun beklenen değer ile aynı olup olmadığını kontrol eder.
        verify(userRepository, times(1)).findAll(); // userRepository'nin findAll metodunun bir kere çağrılıp çağrılmadığını kontrol eder.
    }
}
