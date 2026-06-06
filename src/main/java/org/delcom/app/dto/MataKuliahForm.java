package org.delcom.app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MataKuliahForm {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;

    @NotBlank(message = "Kode tidak boleh kosong")
    private String kode;

    private String deskripsi;

    @NotNull(message = "SKS tidak boleh kosong")
    @Min(value = 1, message = "SKS minimal 1")
    private Integer sks;

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public Integer getSks() { return sks; }
    public void setSks(Integer sks) { this.sks = sks; }
}
