package org.delcom.app.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TugasFormTests {
    @Test
    void testSettersAndGetters() {
        TugasForm f = new TugasForm();
        UUID mkId = UUID.randomUUID();
        f.setJudul("Tugas 1");
        f.setDeskripsi("Buat program REST API");
        f.setDeadline(LocalDateTime.now().plusDays(7));
        f.setMataKuliahId(mkId);
        assertEquals("Tugas 1", f.getJudul());
        assertEquals("Buat program REST API", f.getDeskripsi());
        assertNotNull(f.getDeadline());
        assertEquals(mkId, f.getMataKuliahId());
    }
}
