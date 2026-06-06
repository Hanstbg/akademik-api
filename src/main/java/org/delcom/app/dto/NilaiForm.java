package org.delcom.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class NilaiForm {

    @NotNull(message = "Nilai tidak boleh kosong")
    @Min(value = 0, message = "Nilai minimal 0")
    @Max(value = 100, message = "Nilai maksimal 100")
    private Integer nilai;

    private String catatan;

    public Integer getNilai() { return nilai; }
    public void setNilai(Integer nilai) { this.nilai = nilai; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}
