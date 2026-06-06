package org.delcom.app.services;

import org.delcom.app.entities.Enrollment;
import org.delcom.app.entities.MataKuliah;
import org.delcom.app.entities.User;
import org.delcom.app.repositories.EnrollmentRepository;
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
class EnrollmentServiceTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    @InjectMocks private EnrollmentService enrollmentService;

    private User mahasiswa;
    private MataKuliah mk;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        mahasiswa = new User();
        mahasiswa.setId(UUID.randomUUID());

        mk = new MataKuliah();
        mk.setId(UUID.randomUUID());

        enrollment = new Enrollment();
        enrollment.setId(UUID.randomUUID());
        enrollment.setMahasiswa(mahasiswa);
        enrollment.setMataKuliah(mk);
    }

    @Test
    void testEnrollSuccess() {
        when(enrollmentRepository.existsByMahasiswaIdAndMataKuliahId(any(), any())).thenReturn(false);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        Enrollment result = enrollmentService.enroll(mahasiswa, mk);
        assertNotNull(result);
    }

    @Test
    void testEnrollDuplicate() {
        when(enrollmentRepository.existsByMahasiswaIdAndMataKuliahId(any(), any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
            () -> enrollmentService.enroll(mahasiswa, mk));
    }

    @Test
    void testGetAllByMahasiswa() {
        when(enrollmentRepository.findAllByMahasiswaId(mahasiswa.getId())).thenReturn(List.of(enrollment));
        assertEquals(1, enrollmentService.getAllByMahasiswa(mahasiswa.getId()).size());
    }

    @Test
    void testIsEnrolled() {
        when(enrollmentRepository.existsByMahasiswaIdAndMataKuliahId(any(), any())).thenReturn(true);
        assertTrue(enrollmentService.isEnrolled(mahasiswa.getId(), mk.getId()));
    }

    @Test
    void testUnenroll() {
        when(enrollmentRepository.findByMahasiswaIdAndMataKuliahId(any(), any()))
            .thenReturn(Optional.of(enrollment));
        doNothing().when(enrollmentRepository).deleteById(any());
        enrollmentService.unenroll(mahasiswa.getId(), mk.getId());
        verify(enrollmentRepository).deleteById(enrollment.getId());
    }
}
