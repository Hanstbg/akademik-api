package org.delcom.app.repositories;

import org.delcom.app.entities.Tugas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TugasRepository extends JpaRepository<Tugas, UUID> {
    List<Tugas> findAllByMataKuliahId(UUID mataKuliahId);
    Optional<Tugas> findByIdAndMataKuliahId(UUID id, UUID mataKuliahId);
}
