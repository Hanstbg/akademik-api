package org.delcom.app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginFormTests {
    @Test
    void testSettersAndGetters() {
        LoginForm f = new LoginForm();
        f.setEmail("hans@example.com");
        f.setPassword("pass123");
        assertEquals("hans@example.com", f.getEmail());
        assertEquals("pass123", f.getPassword());
    }
}
