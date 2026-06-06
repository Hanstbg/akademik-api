package org.delcom.app.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class TugasForm {

    @NotBlank(message = "Judul tidak boleh kosong")
    private String judul;

    private String deskripsi;

    @NotNull(message = "Deadline tidak boleh kosong")
    @Future(message = "Deadline harus di masa depan")
    private LocalDateTime deadline;

    @NotNull(message = "Mata kuliah tidak boleh kosong")
    private UUID mataKuliahId;

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public UUID getMataKuliahId() { return mataKuliahId; }
    public void setMataKuliahId(UUID mataKuliahId) { this.mataKuliahId = mataKuliahId; }
}
