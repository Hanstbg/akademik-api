package org.delcom.app.services;

import org.delcom.app.entities.Enrollment;
import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.User;
import org.delcom.app.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enroll(User mahasiswa, MataKuliah mataKuliah) {
        if (enrollmentRepository.existsByMahasiswaIdAndMataKuliahId(
                mahasiswa.getId(), mataKuliah.getId())) {
            throw new IllegalArgumentException("Sudah terdaftar di mata kuliah ini");
        }
        Enrollment e = new Enrollment();
        e.setMahasiswa(mahasiswa);
        e.setMataKuliah(mataKuliah);
        return enrollmentRepository.save(e);
    }

    public List<Enrollment> getAllByMahasiswa(UUID mahasiswaId) {
        return enrollmentRepository.findAllByMahasiswaId(mahasiswaId);
    }

    public boolean isEnrolled(UUID mahasiswaId, UUID mataKuliahId) {
        return enrollmentRepository.existsByMahasiswaIdAndMataKuliahId(mahasiswaId, mataKuliahId);
    }

    public void unenroll(UUID mahasiswaId, UUID mataKuliahId) {
        Optional<Enrollment> opt = enrollmentRepository
            .findByMahasiswaIdAndMataKuliahId(mahasiswaId, mataKuliahId);
        opt.ifPresent(e -> enrollmentRepository.deleteById(e.getId()));
    }
}
