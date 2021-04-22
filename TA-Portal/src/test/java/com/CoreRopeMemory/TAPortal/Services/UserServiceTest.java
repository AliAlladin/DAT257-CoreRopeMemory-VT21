package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        User user = new User("123456",
                "test.person@mail.com",
                "Person",
                "Test",
                "Kungsgatan 1",
                12345,
                "Göteborg",
                false);
        userService.save(user);
        assertFalse(userService.isEmpty());
    }

    @Test
    void get() {
        User user = new User("123456",
                "test.person@mail.com",
                "Person",
                "Test",
                "Kungsgatan 1",
                12345,
                "Göteborg",
                false);
        userService.save(user);
        User userDb = userService.get("123456");
        assertEquals(userDb, user);
    }
}