package org.delcom.app.controllers;

import jakarta.validation.Valid;
import org.delcom.app.configs.ApiResponse;
import org.delcom.app.dto.TugasForm;
import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.Tugas;
import org.delcom.app.services.MataKuliahService;
import org.delcom.app.services.TugasService;
import org.delcom.app.utils.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tugas")
public class TugasController {

    @Autowired
    private TugasService tugasService;

    @Autowired
    private MataKuliahService mataKuliahService;

    @GetMapping("/matakuliah/{mataKuliahId}")
    public ResponseEntity<ApiResponse<?>> getByMataKuliah(@PathVariable UUID mataKuliahId) {
        List<Tugas> list = tugasService.getAllByMataKuliah(mataKuliahId);
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable UUID id) {
        Optional<Tugas> opt = tugasService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, opt.get()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            Authentication auth,
            @Valid @RequestBody TugasForm form) {
        UUID dosenId = UUID.fromString(auth.getName());
        Optional<MataKuliah> mkOpt = mataKuliahService.getById(form.getMataKuliahId());
        if (mkOpt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error("Mata kuliah tidak ditemukan"));

        // Pastikan hanya dosen pemilik yang bisa buat tugas
        if (!mkOpt.get().getDosen().getId().equals(dosenId)) {
            return ResponseEntity.status(403).body(ApiResponse.error(ConstUtil.MSG_FORBIDDEN));
        }

        Tugas t = tugasService.create(form.getJudul(), form.getDeskripsi(), form.getDeadline(), mkOpt.get());
        return ResponseEntity.ok(ApiResponse.ok("Tugas berhasil dibuat", t));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(
            Authentication auth,
            @PathVariable UUID id,
            @Valid @RequestBody TugasForm form) {
        UUID dosenId = UUID.fromString(auth.getName());
        Optional<Tugas> opt = tugasService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));

        if (!opt.get().getMataKuliah().getDosen().getId().equals(dosenId)) {
            return ResponseEntity.status(403).body(ApiResponse.error(ConstUtil.MSG_FORBIDDEN));
        }

        Tugas updated = tugasService.update(opt.get(), form.getJudul(), form.getDeskripsi(), form.getDeadline());
        return ResponseEntity.ok(ApiResponse.ok("Tugas berhasil diperbarui", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(Authentication auth, @PathVariable UUID id) {
        UUID dosenId = UUID.fromString(auth.getName());
        Optional<Tugas> opt = tugasService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));

        if (!opt.get().getMataKuliah().getDosen().getId().equals(dosenId)) {
            return ResponseEntity.status(403).body(ApiResponse.error(ConstUtil.MSG_FORBIDDEN));
        }

        tugasService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Tugas berhasil dihapus"));
    }
}
