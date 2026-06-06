package org.delcom.app.services;

import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.User;
import org.delcom.app.repositories.MataKuliahRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MataKuliahServiceTest {

    @Mock private MataKuliahRepository mataKuliahRepository;
    @InjectMocks private MataKuliahService mataKuliahService;

    private User dosen;
    private MataKuliah mk;

    @BeforeEach
    void setUp() {
        dosen = new User();
        dosen.setId(UUID.randomUUID());

        mk = new MataKuliah();
        mk.setId(UUID.randomUUID());
        mk.setNama("Pemrograman Web");
        mk.setKode("IF301");
        mk.setSks(3);
        mk.setDosen(dosen);
    }

    @Test
    void testCreateSuccess() {
        when(mataKuliahRepository.existsByKode("IF301")).thenReturn(false);
        when(mataKuliahRepository.save(any(MataKuliah.class))).thenReturn(mk);
        MataKuliah result = mataKuliahService.create("Pemrograman Web", "IF301", null, 3, dosen);
        assertNotNull(result);
        verify(mataKuliahRepository).save(any(MataKuliah.class));
    }

    @Test
    void testCreateDuplicateKode() {
        when(mataKuliahRepository.existsByKode(anyString())).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
            () -> mataKuliahService.create("Test", "IF301", null, 3, dosen));
    }

    @Test
    void testGetAll() {
        when(mataKuliahRepository.findAll()).thenReturn(List.of(mk));
        assertEquals(1, mataKuliahService.getAll().size());
    }

    @Test
    void testGetAllByDosen() {
        when(mataKuliahRepository.findAllByDosenId(dosen.getId())).thenReturn(List.of(mk));
        assertEquals(1, mataKuliahService.getAllByDosen(dosen.getId()).size());
    }

    @Test
    void testUpdate() {
        when(mataKuliahRepository.save(any(MataKuliah.class))).thenReturn(mk);
        MataKuliah updated = mataKuliahService.update(mk, "Baru", "deskripsi", 4);
        assertEquals("Baru", updated.getNama());
    }

    @Test
    void testDelete() {
        doNothing().when(mataKuliahRepository).deleteById(mk.getId());
        mataKuliahService.delete(mk.getId());
        verify(mataKuliahRepository).deleteById(mk.getId());
    }
}
