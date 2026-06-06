package org.delcom.app.services;

import org.delcom.app.entities.*;
import org.delcom.app.repositories.SubmissionRepository;
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
class SubmissionServiceTest {

    @Mock private SubmissionRepository submissionRepository;
    @InjectMocks private SubmissionService submissionService;

    private User mahasiswa;
    private Tugas tugas;
    private Submission submission;

    @BeforeEach
    void setUp() {
        mahasiswa = new User();
        mahasiswa.setId(UUID.randomUUID());

        tugas = new Tugas();
        tugas.setId(UUID.randomUUID());

        submission = new Submission();
        submission.setId(UUID.randomUUID());
        submission.setMahasiswa(mahasiswa);
        submission.setTugas(tugas);
        submission.setJawaban("Jawaban saya");
    }

    @Test
    void testSubmitSuccess() {
        when(submissionRepository.existsByMahasiswaIdAndTugasId(any(), any())).thenReturn(false);
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        Submission result = submissionService.submit(mahasiswa, tugas, "Jawaban saya");
        assertNotNull(result);
    }

    @Test
    void testSubmitDuplicate() {
        when(submissionRepository.existsByMahasiswaIdAndTugasId(any(), any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
            () -> submissionService.submit(mahasiswa, tugas, "Jawaban"));
    }

    @Test
    void testGetAllByTugas() {
        when(submissionRepository.findAllByTugasId(tugas.getId())).thenReturn(List.of(submission));
        assertEquals(1, submissionService.getAllByTugas(tugas.getId()).size());
    }

    @Test
    void testBeriNilai() {
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        Submission result = submissionService.beriNilai(submission, 90, "Bagus");
        assertEquals(90, result.getNilai());
        assertEquals("Bagus", result.getCatatan());
        assertNotNull(result.getDinilaiAt());
    }
}
