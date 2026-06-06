package org.delcom.app.controllers;

import jakarta.validation.Valid;
import org.delcom.app.configs.ApiResponse;
import org.delcom.app.dto.NilaiForm;
import org.delcom.app.dto.SubmissionForm;
import org.delcom.app.entities.Submission;
import org.delcom.app.entities.Tugas;
import org.delcom.app.entities.User;
import org.delcom.app.services.SubmissionService;
import org.delcom.app.services.TugasService;
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
@RequestMapping("/api/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private TugasService tugasService;

    @Autowired
    private UserService userService;

    // Mahasiswa: lihat submission milik sendiri
    @GetMapping("/saya")
    public ResponseEntity<ApiResponse<?>> getMySub(Authentication auth) {
        UUID mahasiswaId = UUID.fromString(auth.getName());
        List<Submission> list = submissionService.getAllByMahasiswa(mahasiswaId);
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, list));
    }

    // Dosen: lihat semua submission untuk sebuah tugas
    @GetMapping("/tugas/{tugasId}")
    public ResponseEntity<ApiResponse<?>> getByTugas(
            Authentication auth,
            @PathVariable UUID tugasId) {
        UUID dosenId = UUID.fromString(auth.getName());
        Optional<Tugas> tugasOpt = tugasService.getById(tugasId);
        if (tugasOpt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));

        // Pastikan dosen adalah pemilik mata kuliah tugas ini
        if (!tugasOpt.get().getMataKuliah().getDosen().getId().equals(dosenId)) {
            return ResponseEntity.status(403).body(ApiResponse.error(ConstUtil.MSG_FORBIDDEN));
        }

        List<Submission> list = submissionService.getAllByTugas(tugasId);
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, list));
    }

    // Mahasiswa: submit tugas
    @PostMapping
    public ResponseEntity<ApiResponse<?>> submit(
            Authentication auth,
            @Valid @RequestBody SubmissionForm form) {
        UUID mahasiswaId = UUID.fromString(auth.getName());
        User mahasiswa = userService.findById(mahasiswaId).orElse(null);
        Optional<Tugas> tugasOpt = tugasService.getById(form.getTugasId());

        if (mahasiswa == null) return ResponseEntity.status(401).body(ApiResponse.error(ConstUtil.MSG_UNAUTHORIZED));
        if (tugasOpt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error("Tugas tidak ditemukan"));

        try {
            Submission s = submissionService.submit(mahasiswa, tugasOpt.get(), form.getJawaban());
            return ResponseEntity.ok(ApiResponse.ok("Tugas berhasil dikumpulkan", s));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // Dosen: beri nilai
    @PutMapping("/{id}/nilai")
    public ResponseEntity<ApiResponse<?>> beriNilai(
            Authentication auth,
            @PathVariable UUID id,
            @Valid @RequestBody NilaiForm form) {
        UUID dosenId = UUID.fromString(auth.getName());
        Optional<Submission> opt = submissionService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));

        // Pastikan dosen adalah pemilik mata kuliah tugas ini
        if (!opt.get().getTugas().getMataKuliah().getDosen().getId().equals(dosenId)) {
            return ResponseEntity.status(403).body(ApiResponse.error(ConstUtil.MSG_FORBIDDEN));
        }

        Submission updated = submissionService.beriNilai(opt.get(), form.getNilai(), form.getCatatan());
        return ResponseEntity.ok(ApiResponse.ok("Nilai berhasil diberikan", updated));
    }
}
