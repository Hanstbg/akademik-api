package org.delcom.app.services;

import org.delcom.app.entities.Submission;
import org.delcom.app.entities.Tugas;
import org.delcom.app.entities.User;
import org.delcom.app.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission submit(User mahasiswa, Tugas tugas, String jawaban) {
        if (submissionRepository.existsByMahasiswaIdAndTugasId(mahasiswa.getId(), tugas.getId())) {
            throw new IllegalArgumentException("Kamu sudah mengumpulkan tugas ini");
        }
        Submission s = new Submission();
        s.setMahasiswa(mahasiswa);
        s.setTugas(tugas);
        s.setJawaban(jawaban);
        return submissionRepository.save(s);
    }

    public List<Submission> getAllByTugas(UUID tugasId) {
        return submissionRepository.findAllByTugasId(tugasId);
    }

    public List<Submission> getAllByMahasiswa(UUID mahasiswaId) {
        return submissionRepository.findAllByMahasiswaId(mahasiswaId);
    }

    public Optional<Submission> getById(UUID id) {
        return submissionRepository.findById(id);
    }

    public Submission beriNilai(Submission submission, int nilai, String catatan) {
        submission.setNilai(nilai);
        submission.setCatatan(catatan);
        submission.setDinilaiAt(LocalDateTime.now());
        return submissionRepository.save(submission);
    }
}
