package org.delcom.app.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConstUtilTest {

    @Test
    void testConstants() {
        assertNotNull(ConstUtil.MSG_SUCCESS);
        assertNotNull(ConstUtil.MSG_NOT_FOUND);
        assertNotNull(ConstUtil.MSG_UNAUTHORIZED);
        assertNotNull(ConstUtil.MSG_FORBIDDEN);
        assertNotNull(ConstUtil.MSG_INVALID);
        assertEquals("DOSEN", ConstUtil.ROLE_DOSEN);
        assertEquals("MAHASISWA", ConstUtil.ROLE_MAHASISWA);
    }
}
