package org.delcom.app.repositories;

import org.delcom.app.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> findAllByTugasId(UUID tugasId);
    List<Submission> findAllByMahasiswaId(UUID mahasiswaId);
    Optional<Submission> findByMahasiswaIdAndTugasId(UUID mahasiswaId, UUID tugasId);
    boolean existsByMahasiswaIdAndTugasId(UUID mahasiswaId, UUID tugasId);
}
