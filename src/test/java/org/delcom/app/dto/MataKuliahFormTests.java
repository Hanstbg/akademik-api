package org.delcom.app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MataKuliahFormTests {
    @Test
    void testSettersAndGetters() {
        MataKuliahForm f = new MataKuliahForm();
        f.setNama("Pemrograman Web");
        f.setKode("IF301");
        f.setDeskripsi("Belajar web");
        f.setSks(3);
        assertEquals("Pemrograman Web", f.getNama());
        assertEquals("IF301", f.getKode());
        assertEquals("Belajar web", f.getDeskripsi());
        assertEquals(3, f.getSks());
    }
}
