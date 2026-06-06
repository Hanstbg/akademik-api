package org.delcom.app.services;

import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.User;
import org.delcom.app.repositories.MataKuliahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MataKuliahService {

    @Autowired
    private MataKuliahRepository mataKuliahRepository;

    public MataKuliah create(String nama, String kode, String deskripsi, int sks, User dosen) {
        if (mataKuliahRepository.existsByKode(kode)) {
            throw new IllegalArgumentException("Kode mata kuliah sudah digunakan");
        }
        MataKuliah mk = new MataKuliah();
        mk.setNama(nama);
        mk.setKode(kode.toUpperCase());
        mk.setDeskripsi(deskripsi);
        mk.setSks(sks);
        mk.setDosen(dosen);
        return mataKuliahRepository.save(mk);
    }

    public List<MataKuliah> getAll() {
        return mataKuliahRepository.findAll();
    }

    public List<MataKuliah> getAllByDosen(UUID dosenId) {
        return mataKuliahRepository.findAllByDosenId(dosenId);
    }

    public Optional<MataKuliah> getById(UUID id) {
        return mataKuliahRepository.findById(id);
    }

    public MataKuliah update(MataKuliah mk, String nama, String deskripsi, int sks) {
        mk.setNama(nama);
        mk.setDeskripsi(deskripsi);
        mk.setSks(sks);
        return mataKuliahRepository.save(mk);
    }

    public void delete(UUID id) {
        mataKuliahRepository.deleteById(id);
    }
}
