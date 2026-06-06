package org.delcom.app.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class SubmissionTest {
    @Test
    void testSettersAndGetters() {
        Submission s = new Submission();
        UUID id = UUID.randomUUID();
        s.setId(id);
        s.setJawaban("Jawaban saya");
        s.setNilai(90);
        s.setCatatan("Bagus");
        s.setSubmittedAt(LocalDateTime.now());
        s.setDinilaiAt(LocalDateTime.now());
        assertEquals(id, s.getId());
        assertEquals("Jawaban saya", s.getJawaban());
        assertEquals(90, s.getNilai());
        assertEquals("Bagus", s.getCatatan());
        assertNotNull(s.getSubmittedAt());
        assertNotNull(s.getDinilaiAt());
    }
}
