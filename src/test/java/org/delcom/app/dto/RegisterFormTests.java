package org.delcom.app.dto;

import org.delcom.app.entities.User.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterFormTests {
    @Test
    void testSettersAndGetters() {
        RegisterForm f = new RegisterForm();
        f.setName("Hans");
        f.setEmail("hans@example.com");
        f.setPassword("pass123");
        f.setRole(Role.MAHASISWA);
        assertEquals("Hans", f.getName());
        assertEquals("hans@example.com", f.getEmail());
        assertEquals("pass123", f.getPassword());
        assertEquals(Role.MAHASISWA, f.getRole());
    }
}
