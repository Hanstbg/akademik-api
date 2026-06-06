package org.delcom.app.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {
    @Test
    void testSettersAndGetters() {
        Enrollment e = new Enrollment();
        UUID id = UUID.randomUUID();
        e.setId(id);
        e.setEnrolledAt(LocalDateTime.now());
        assertEquals(id, e.getId());
        assertNotNull(e.getEnrolledAt());
    }
}
