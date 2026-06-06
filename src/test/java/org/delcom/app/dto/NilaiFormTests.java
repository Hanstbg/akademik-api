package org.delcom.app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NilaiFormTests {
    @Test
    void testSettersAndGetters() {
        NilaiForm f = new NilaiForm();
        f.setNilai(85);
        f.setCatatan("Kerja bagus");
        assertEquals(85, f.getNilai());
        assertEquals("Kerja bagus", f.getCatatan());
    }
}
