package org.delcom.app.configs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTests {

    @Test
    void testOkWithData() {
        ApiResponse<String> r = ApiResponse.ok("Berhasil", "data");
        assertTrue(r.isSuccess());
        assertEquals("Berhasil", r.getMessage());
        assertEquals("data", r.getData());
    }

    @Test
    void testOkWithoutData() {
        ApiResponse<Void> r = ApiResponse.ok("Berhasil");
        assertTrue(r.isSuccess());
        assertNull(r.getData());
    }

    @Test
    void testError() {
        ApiResponse<Void> r = ApiResponse.error("Gagal");
        assertFalse(r.isSuccess());
        assertEquals("Gagal", r.getMessage());
    }

    @Test
    void testSetters() {
        ApiResponse<String> r = new ApiResponse<>(true, "msg", "val");
        r.setSuccess(false);
        r.setMessage("new");
        r.setData("x");
        assertFalse(r.isSuccess());
        assertEquals("new", r.getMessage());
        assertEquals("x", r.getData());
    }
}
