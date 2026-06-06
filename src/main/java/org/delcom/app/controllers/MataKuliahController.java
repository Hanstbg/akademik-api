package org.delcom.app.controllers;

import jakarta.validation.Valid;
import org.delcom.app.configs.ApiResponse;
import org.delcom.app.dto.MataKuliahForm;
import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.User;
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
@RequestMapping("/api/matakuliah")
public class MataKuliahController {

    @Autowired
    private MataKuliahService mataKuliahService;

    @Autowired
    private UserService userService;

    private UUID getAuthId(Authentication auth) {
        return UUID.fromString(auth.getName());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        List<MataKuliah> list = mataKuliahService.getAll();
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, list));
    }

    @GetMapping("/saya")
    public ResponseEntity<ApiResponse<?>> getMilikSaya(Authentication auth) {
        List<MataKuliah> list = mataKuliahService.getAllByDosen(getAuthId(auth));
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable UUID id) {
        Optional<MataKuliah> opt = mataKuliahService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));
        return ResponseEntity.ok(ApiResponse.ok(ConstUtil.MSG_SUCCESS, opt.get()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            Authentication auth,
            @Valid @RequestBody MataKuliahForm form) {
        User dosen = userService.findById(getAuthId(auth)).orElse(null);
        if (dosen == null) return ResponseEntity.status(401).body(ApiResponse.error(ConstUtil.MSG_UNAUTHORIZED));
        try {
            MataKuliah mk = mataKuliahService.create(
                form.getNama(), form.getKode(), form.getDeskripsi(), form.getSks(), dosen);
            return ResponseEntity.ok(ApiResponse.ok("Mata kuliah berhasil dibuat", mk));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(
            Authentication auth,
            @PathVariable UUID id,
            @Valid @RequestBody MataKuliahForm form) {
        Optional<MataKuliah> opt = mataKuliahService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));

        MataKuliah mk = opt.get();
        if (!mk.getDosen().getId().equals(getAuthId(auth))) {
            return ResponseEntity.status(403).body(ApiResponse.error(ConstUtil.MSG_FORBIDDEN));
        }
        MataKuliah updated = mataKuliahService.update(mk, form.getNama(), form.getDeskripsi(), form.getSks());
        return ResponseEntity.ok(ApiResponse.ok("Mata kuliah berhasil diperbarui", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(Authentication auth, @PathVariable UUID id) {
        Optional<MataKuliah> opt = mataKuliahService.getById(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).body(ApiResponse.error(ConstUtil.MSG_NOT_FOUND));

        if (!opt.get().getDosen().getId().equals(getAuthId(auth))) {
            return ResponseEntity.status(403).body(ApiResponse.error(ConstUtil.MSG_FORBIDDEN));
        }
        mataKuliahService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Mata kuliah berhasil dihapus"));
    }
}
