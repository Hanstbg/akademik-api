package org.delcom.app.entities;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class MataKuliahTest {
    @Test
    void testSettersAndGetters() {
        MataKuliah mk = new MataKuliah();
        UUID id = UUID.randomUUID();
        mk.setId(id);
        mk.setNama("Pemrograman Web");
        mk.setKode("IF301");
        mk.setDeskripsi("Belajar web");
        mk.setSks(3);
        assertEquals(id, mk.getId());
        assertEquals("Pemrograman Web", mk.getNama());
        assertEquals("IF301", mk.getKode());
        assertEquals(3, mk.getSks());
    }
}
