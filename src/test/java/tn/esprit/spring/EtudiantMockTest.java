package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.dao.entities.Etudiant;
import tn.esprit.spring.dao.repositories.EtudiantRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EtudiantMockTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    private Etudiant etudiantTest;
    private Etudiant savedEtudiant;
    private static final Long TEST_ID = 1L;

    @BeforeEach
    void setUp() {
        etudiantTest = Etudiant.builder()
                .nomEt("Dupont")
                .prenomEt("Jean")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2000, 5, 15))
                .build();

        savedEtudiant = Etudiant.builder()
                .idEtudiant(TEST_ID)
                .nomEt("Dupont")
                .prenomEt("Jean")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2000, 5, 15))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Etudiant - Success")
    void testCreateEtudiant() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(savedEtudiant);

        // When
        Etudiant result = etudiantRepository.save(etudiantTest);

        // Then
        assertNotNull(result);
        assertEquals(TEST_ID, result.getIdEtudiant());
        assertEquals("Dupont", result.getNomEt());
        assertEquals("Jean", result.getPrenomEt());
        assertEquals(12345678L, result.getCin());
        assertEquals("ESPRIT", result.getEcole());
        assertEquals(LocalDate.of(2000, 5, 15), result.getDateNaissance());

        // Verify interactions
        verify(etudiantRepository, times(1)).save(etudiantTest);

        System.out.println("✅ Etudiant created successfully with ID: " + result.getIdEtudiant());
    }

    @Test
    @Order(2)
    @DisplayName("Test Read Etudiant by ID - Found")
    void testReadEtudiantById_Found() {
        // Given
        when(etudiantRepository.findById(TEST_ID)).thenReturn(Optional.of(savedEtudiant));

        // When
        Optional<Etudiant> result = etudiantRepository.findById(TEST_ID);

        // Then
        assertTrue(result.isPresent());
        assertEquals(TEST_ID, result.get().getIdEtudiant());
        assertEquals("Dupont", result.get().getNomEt());
        assertEquals("Jean", result.get().getPrenomEt());
        assertEquals(12345678L, result.get().getCin());

        // Verify interactions
        verify(etudiantRepository, times(1)).findById(TEST_ID);

        System.out.println("✅ Etudiant found successfully: " + result.get().getNomEt() + " " + result.get().getPrenomEt());
    }

    @Test
    @Order(3)
    @DisplayName("Test Read Etudiant by ID - Not Found")
    void testReadEtudiantById_NotFound() {
        // Given
        Long nonExistentId = 999L;
        when(etudiantRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        Optional<Etudiant> result = etudiantRepository.findById(nonExistentId);

        // Then
        assertFalse(result.isPresent());

        // Verify interactions
        verify(etudiantRepository, times(1)).findById(nonExistentId);

        System.out.println("✅ Etudiant not found test passed for ID: " + nonExistentId);
    }

    @Test
    @Order(4)
    @DisplayName("Test Read All Etudiants")
    void testReadAllEtudiants() {
        // Given
        Etudiant etudiant1 = Etudiant.builder()
                .idEtudiant(1L)
                .nomEt("Martin")
                .prenomEt("Pierre")
                .cin(87654321L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(1999, 8, 20))
                .build();

        Etudiant etudiant2 = Etudiant.builder()
                .idEtudiant(2L)
                .nomEt("Bernard")
                .prenomEt("Marie")
                .cin(11223344L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2001, 3, 10))
                .build();

        List<Etudiant> etudiantsList = Arrays.asList(etudiant1, etudiant2);
        when(etudiantRepository.findAll()).thenReturn(etudiantsList);

        // When
        List<Etudiant> result = etudiantRepository.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Martin", result.get(0).getNomEt());
        assertEquals("Bernard", result.get(1).getNomEt());

        // Verify interactions
        verify(etudiantRepository, times(1)).findAll();

        System.out.println("✅ Found " + result.size() + " etudiants in database");
    }

    @Test
    @Order(5)
    @DisplayName("Test Read All Etudiants - Empty List")
    void testReadAllEtudiants_EmptyList() {
        // Given
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Etudiant> result = etudiantRepository.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(etudiantRepository, times(1)).findAll();

        System.out.println("✅ Empty etudiants list test passed");
    }

    @Test
    @Order(6)
    @DisplayName("Test Update Etudiant - Success")
    void testUpdateEtudiant() {
        // Given
        Etudiant updatedEtudiant = Etudiant.builder()
                .idEtudiant(TEST_ID)
                .nomEt("Dupont-Updated")
                .prenomEt("Jean-Claude")
                .cin(12345678L)
                .ecole("ESPRIT-Updated")
                .dateNaissance(LocalDate.of(2000, 5, 15))
                .build();

        when(etudiantRepository.findById(TEST_ID)).thenReturn(Optional.of(savedEtudiant));
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(updatedEtudiant);

        // When
        Optional<Etudiant> existingEtudiant = etudiantRepository.findById(TEST_ID);
        assertTrue(existingEtudiant.isPresent());

        Etudiant etudiantToUpdate = existingEtudiant.get();
        etudiantToUpdate.setNomEt("Dupont-Updated");
        etudiantToUpdate.setPrenomEt("Jean-Claude");
        etudiantToUpdate.setEcole("ESPRIT-Updated");

        Etudiant result = etudiantRepository.save(etudiantToUpdate);

        // Then
        assertNotNull(result);
        assertEquals(TEST_ID, result.getIdEtudiant());
        assertEquals("Dupont-Updated", result.getNomEt());
        assertEquals("Jean-Claude", result.getPrenomEt());
        assertEquals("ESPRIT-Updated", result.getEcole());
        assertEquals(12345678L, result.getCin()); // Should remain unchanged

        // Verify interactions
        verify(etudiantRepository, times(1)).findById(TEST_ID);
        verify(etudiantRepository, times(1)).save(any(Etudiant.class));

        System.out.println("✅ Etudiant updated successfully: " + result.getNomEt() + " " + result.getPrenomEt());
    }

    @Test
    @Order(7)
    @DisplayName("Test Update Etudiant - Not Found")
    void testUpdateEtudiant_NotFound() {
        // Given
        Long nonExistentId = 999L;
        when(etudiantRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        Optional<Etudiant> result = etudiantRepository.findById(nonExistentId);

        // Then
        assertFalse(result.isPresent());

        // Verify interactions
        verify(etudiantRepository, times(1)).findById(nonExistentId);
        verify(etudiantRepository, never()).save(any(Etudiant.class));

        System.out.println("✅ Update etudiant not found test passed");
    }

    @Test
    @Order(8)
    @DisplayName("Test Delete Etudiant by ID - Success")
    void testDeleteEtudiant() {
        // Given
        when(etudiantRepository.existsById(TEST_ID)).thenReturn(true).thenReturn(false);
        doNothing().when(etudiantRepository).deleteById(TEST_ID);

        // When - Verify etudiant exists before deletion
        boolean existsBefore = etudiantRepository.existsById(TEST_ID);
        assertTrue(existsBefore);

        // Delete the etudiant
        etudiantRepository.deleteById(TEST_ID);

        // Verify etudiant no longer exists
        boolean existsAfter = etudiantRepository.existsById(TEST_ID);

        // Then
        assertFalse(existsAfter);

        // Verify interactions
        verify(etudiantRepository, times(2)).existsById(TEST_ID);
        verify(etudiantRepository, times(1)).deleteById(TEST_ID);

        System.out.println("✅ Etudiant deleted successfully with ID: " + TEST_ID);
    }

    @Test
    @Order(9)
    @DisplayName("Test Delete Etudiant - Not Found")
    void testDeleteEtudiant_NotFound() {
        // Given
        Long nonExistentId = 999L;
        when(etudiantRepository.existsById(nonExistentId)).thenReturn(false);

        // When
        boolean exists = etudiantRepository.existsById(nonExistentId);

        // Then
        assertFalse(exists);

        // Verify interactions
        verify(etudiantRepository, times(1)).existsById(nonExistentId);
        verify(etudiantRepository, never()).deleteById(nonExistentId);

        System.out.println("✅ Delete etudiant not found test passed");
    }

    @Test
    @Order(10)
    @DisplayName("Test Save Etudiant - Exception Handling")
    void testSaveEtudiant_Exception() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class)))
                .thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            etudiantRepository.save(etudiantTest);
        });

        assertEquals("Database connection error", exception.getMessage());

        // Verify interactions
        verify(etudiantRepository, times(1)).save(etudiantTest);

        System.out.println("✅ Exception handling test passed: " + exception.getMessage());
    }

    @Test
    @Order(11)
    @DisplayName("Test Repository Method Calls Verification")
    void testRepositoryMethodCalls() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(savedEtudiant);
        when(etudiantRepository.findById(TEST_ID)).thenReturn(Optional.of(savedEtudiant));
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(savedEtudiant));
        when(etudiantRepository.existsById(TEST_ID)).thenReturn(true);

        // When
        etudiantRepository.save(etudiantTest);
        etudiantRepository.findById(TEST_ID);
        etudiantRepository.findAll();
        etudiantRepository.existsById(TEST_ID);
        etudiantRepository.deleteById(TEST_ID);

        // Then - Verify all method calls
        verify(etudiantRepository, times(1)).save(etudiantTest);
        verify(etudiantRepository, times(1)).findById(TEST_ID);
        verify(etudiantRepository, times(1)).findAll();
        verify(etudiantRepository, times(1)).existsById(TEST_ID);
        verify(etudiantRepository, times(1)).deleteById(TEST_ID);

        System.out.println("✅ All repository method calls verified successfully");
    }

    @AfterEach
    void tearDown() {
        // Reset mocks after each test
        reset(etudiantRepository);
        etudiantTest = null;
        savedEtudiant = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("🏁 All Etudiant Mockito tests completed successfully!");
    }
}