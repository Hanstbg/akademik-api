package org.delcom.app.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class SubmissionFormTests {
    @Test
    void testSettersAndGetters() {
        SubmissionForm f = new SubmissionForm();
        UUID tugasId = UUID.randomUUID();
        f.setTugasId(tugasId);
        f.setJawaban("Ini jawaban saya");
        assertEquals(tugasId, f.getTugasId());
        assertEquals("Ini jawaban saya", f.getJawaban());
    }
}
