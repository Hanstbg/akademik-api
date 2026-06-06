package org.delcom.app.utils;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void testGenerateAndValidate() {
        UUID id = UUID.randomUUID();
        String token = JwtUtil.generateToken(id, "DOSEN");
        assertNotNull(token);
        assertTrue(JwtUtil.validateToken(token, false));
    }

    @Test
    void testExtractUserId() {
        UUID id = UUID.randomUUID();
        String token = JwtUtil.generateToken(id, "MAHASISWA");
        assertEquals(id, JwtUtil.extractUserId(token));
    }

    @Test
    void testExtractRole() {
        UUID id = UUID.randomUUID();
        String token = JwtUtil.generateToken(id, "DOSEN");
        assertEquals("DOSEN", JwtUtil.extractRole(token));
    }

    @Test
    void testGetUserIdFromToken() {
        UUID id = UUID.randomUUID();
        String token = JwtUtil.generateToken(id, "MAHASISWA");
        assertEquals(id, JwtUtil.getUserIdFromToken(token));
    }

    @Test
    void testInvalidToken() {
        assertFalse(JwtUtil.validateToken("invalid.token", false));
        assertNull(JwtUtil.extractUserId("invalid.token"));
        assertNull(JwtUtil.extractRole("invalid.token"));
    }

    @Test
    void testGetKey() {
        assertNotNull(JwtUtil.getKey());
    }
}
