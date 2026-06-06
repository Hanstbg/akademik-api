package org.delcom.app.services;

import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.Tugas;
import org.delcom.app.repositories.TugasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TugasServiceTest {

    @Mock private TugasRepository tugasRepository;
    @InjectMocks private TugasService tugasService;

    private MataKuliah mk;
    private Tugas tugas;

    @BeforeEach
    void setUp() {
        mk = new MataKuliah();
        mk.setId(UUID.randomUUID());

        tugas = new Tugas();
        tugas.setId(UUID.randomUUID());
        tugas.setJudul("Tugas 1");
        tugas.setDeadline(LocalDateTime.now().plusDays(7));
        tugas.setMataKuliah(mk);
    }

    @Test
    void testCreate() {
        when(tugasRepository.save(any(Tugas.class))).thenReturn(tugas);
        Tugas result = tugasService.create("Tugas 1", "Deskripsi", LocalDateTime.now().plusDays(7), mk);
        assertNotNull(result);
        verify(tugasRepository).save(any(Tugas.class));
    }

    @Test
    void testGetAllByMataKuliah() {
        when(tugasRepository.findAllByMataKuliahId(mk.getId())).thenReturn(List.of(tugas));
        assertEquals(1, tugasService.getAllByMataKuliah(mk.getId()).size());
    }

    @Test
    void testGetById() {
        when(tugasRepository.findById(tugas.getId())).thenReturn(Optional.of(tugas));
        assertTrue(tugasService.getById(tugas.getId()).isPresent());
    }

    @Test
    void testUpdate() {
        when(tugasRepository.save(any(Tugas.class))).thenReturn(tugas);
        Tugas updated = tugasService.update(tugas, "Tugas Baru", "Deskripsi baru", LocalDateTime.now().plusDays(10));
        assertEquals("Tugas Baru", updated.getJudul());
    }

    @Test
    void testDelete() {
        doNothing().when(tugasRepository).deleteById(tugas.getId());
        tugasService.delete(tugas.getId());
        verify(tugasRepository).deleteById(tugas.getId());
    }
}
