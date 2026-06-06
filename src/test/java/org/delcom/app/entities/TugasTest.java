package org.delcom.app.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TugasTest {
    @Test
    void testSettersAndGetters() {
        Tugas t = new Tugas();
        UUID id = UUID.randomUUID();
        t.setId(id);
        t.setJudul("Tugas 1");
        t.setDeskripsi("Buat program");
        t.setDeadline(LocalDateTime.now().plusDays(7));
        t.setCreatedAt(LocalDateTime.now());
        assertEquals(id, t.getId());
        assertEquals("Tugas 1", t.getJudul());
        assertNotNull(t.getDeadline());
    }
}
