package org.delcom.app.repositories;

import org.delcom.app.entities.MataKuliah;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MataKuliahRepository extends JpaRepository<MataKuliah, UUID> {
    List<MataKuliah> findAllByDosenId(UUID dosenId);
    Optional<MataKuliah> findByKode(String kode);
    boolean existsByKode(String kode);
}
