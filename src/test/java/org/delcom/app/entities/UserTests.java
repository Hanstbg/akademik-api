package org.delcom.app.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {
    @Test
    void testSettersAndGetters() {
        User u = new User();
        UUID id = UUID.randomUUID();
        u.setId(id);
        u.setName("Hans");
        u.setEmail("hans@example.com");
        u.setPassword("pass");
        u.setRole(User.Role.DOSEN);
        u.setCreatedAt(LocalDateTime.now());
        assertEquals(id, u.getId());
        assertEquals("Hans", u.getName());
        assertEquals(User.Role.DOSEN, u.getRole());
    }

    @Test
    void testRoleEnum() {
        assertEquals(2, User.Role.values().length);
        assertNotNull(User.Role.valueOf("DOSEN"));
        assertNotNull(User.Role.valueOf("MAHASISWA"));
    }
}
