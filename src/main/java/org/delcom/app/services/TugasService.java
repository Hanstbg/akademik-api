package org.delcom.app.services;

import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.Tugas;
import org.delcom.app.repositories.TugasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TugasService {

    @Autowired
    private TugasRepository tugasRepository;

    public Tugas create(String judul, String deskripsi, LocalDateTime deadline, MataKuliah mk) {
        Tugas t = new Tugas();
        t.setJudul(judul);
        t.setDeskripsi(deskripsi);
        t.setDeadline(deadline);
        t.setMataKuliah(mk);
        return tugasRepository.save(t);
    }

    public List<Tugas> getAllByMataKuliah(UUID mataKuliahId) {
        return tugasRepository.findAllByMataKuliahId(mataKuliahId);
    }

    public Optional<Tugas> getById(UUID id) {
        return tugasRepository.findById(id);
    }

    public Tugas update(Tugas tugas, String judul, String deskripsi, LocalDateTime deadline) {
        tugas.setJudul(judul);
        tugas.setDeskripsi(deskripsi);
        tugas.setDeadline(deadline);
        return tugasRepository.save(tugas);
    }

    public void delete(UUID id) {
        tugasRepository.deleteById(id);
    }
}
