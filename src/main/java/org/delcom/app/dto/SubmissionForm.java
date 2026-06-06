package org.delcom.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class SubmissionForm {

    @NotNull(message = "Tugas tidak boleh kosong")
    private UUID tugasId;

    @NotBlank(message = "Jawaban tidak boleh kosong")
    private String jawaban;

    public UUID getTugasId() { return tugasId; }
    public void setTugasId(UUID tugasId) { this.tugasId = tugasId; }

    public String getJawaban() { return jawaban; }
    public void setJawaban(String jawaban) { this.jawaban = jawaban; }
}
