package org.delcom.app.controllers;

import org.delcom.app.configs.ApiResponse;
import org.delcom.app.entities.Enrollment;
import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.User;
import org.delcom.app.services.EnrollmentService;
import org.delcom.app.services.MataKuliahService;
import org.delcom.app.services.UserService;
import org.delcom.app.utils.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private MataKuliahService mataKuliahService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getMyEnrollments(Authentication auth) {
        UUID mahasiswaId = UUID.fromString(auth.getName());
        List<Enrollment> list = enrollmentService.getAllByMahasiswa(mahasiswaId);
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, list));
    }

    @PostMapping("/{mataKuliahId}")
    public ResponseEntity<ApiResponse<?>> enroll(
            Authentication auth,
            @PathVariable UUID mataKuliahId) {
        UUID mahasiswaId = UUID.fromString(auth.getName());
        User mahasiswa = userService.findById(mahasiswaId).orElse(null);
        Optional<MataKuliah> mkOpt = mataKuliahService.getById(mataKuliahId);

        if (mahasiswa == null) return ResponseEntity.status(401).body(ApiResponse.error(ConstUtil.MSG_UNAUTHORIZED));
        if (mkOpt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error("Mata kuliah tidak ditemukan"));

        try {
            Enrollment e = enrollmentService.enroll(mahasiswa, mkOpt.get());
            return ResponseEntity.ok(ApiResponse.ok("Berhasil mendaftar mata kuliah", e));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @DeleteMapping("/{mataKuliahId}")
    public ResponseEntity<ApiResponse<?>> unenroll(
            Authentication auth,
            @PathVariable UUID mataKuliahId) {
        UUID mahasiswaId = UUID.fromString(auth.getName());
        enrollmentService.unenroll(mahasiswaId, mataKuliahId);
        return ResponseEntity.ok(ApiResponse.ok("Berhasil keluar dari mata kuliah"));
    }
}
