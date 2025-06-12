package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.dao.entities.Etudiant;
import tn.esprit.spring.dao.repositories.EtudiantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Transactional
public class EtudiantTest {

    @Autowired
    private EtudiantRepository etudiantRepository;

    private Etudiant etudiantTest;
    private static Long savedEtudiantId;

    @BeforeEach
    void setUp() {
        etudiantTest = Etudiant.builder()
                .nomEt("Dupont")
                .prenomEt("Jean")
                .cin(12345678L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2000, 5, 15))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Etudiant")
    void testCreateEtudiant() {
        // Given
        assertNotNull(etudiantTest);

        // When
        Etudiant savedEtudiant = etudiantRepository.save(etudiantTest);
        savedEtudiantId = savedEtudiant.getIdEtudiant();

        // Then
        assertNotNull(savedEtudiant);
        assertNotNull(savedEtudiant.getIdEtudiant());

        assertEquals("Dupont", savedEtudiant.getNomEt()); // String
        assertEquals("Jean", savedEtudiant.getPrenomEt()); // String

        Long expectedCin = 12345678L;
        assertEquals(expectedCin, savedEtudiant.getCin()); // Long

        assertEquals("ESPRIT", savedEtudiant.getEcole()); // String

        LocalDate expectedDate = LocalDate.of(2000, 5, 15);
        assertEquals(expectedDate, savedEtudiant.getDateNaissance()); // LocalDate

        System.out.println("✅ Etudiant created successfully with ID: " + savedEtudiant.getIdEtudiant());
    }




    @Test
    @Order(2)
    @DisplayName("Test Read Etudiant by ID")
    void testReadEtudiantById() {
        // Given
        Etudiant savedEtudiant = etudiantRepository.save(etudiantTest);
        Long etudiantId = savedEtudiant.getIdEtudiant();

        // When
        Optional<Etudiant> foundEtudiant = etudiantRepository.findById(etudiantId);

        // Then
        assertTrue(foundEtudiant.isPresent());
        assertEquals("Dupont", foundEtudiant.get().getNomEt());
        assertEquals("Jean", foundEtudiant.get().getPrenomEt());
        assertEquals(12345678L, foundEtudiant.get().getCin());

        System.out.println("✅ Etudiant found successfully: " + foundEtudiant.get().getNomEt() + " " + foundEtudiant.get().getPrenomEt());
    }

    @Test
    @Order(3)
    @DisplayName("Test Read All Etudiants")
    void testReadAllEtudiants() {
        // Given
        Etudiant etudiant1 = Etudiant.builder()
                .nomEt("Martin")
                .prenomEt("Pierre")
                .cin(87654321L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(1999, 8, 20))
                .build();

        Etudiant etudiant2 = Etudiant.builder()
                .nomEt("Bernard")
                .prenomEt("Marie")
                .cin(11223344L)
                .ecole("ESPRIT")
                .dateNaissance(LocalDate.of(2001, 3, 10))
                .build();

        etudiantRepository.save(etudiant1);
        etudiantRepository.save(etudiant2);

        // When
        List<Etudiant> etudiants = etudiantRepository.findAll();

        // Then
        assertNotNull(etudiants);
        assertTrue(etudiants.size() >= 2);

        System.out.println("✅ Found " + etudiants.size() + " etudiants in database");
    }

    @Test
    @Order(4)
    @DisplayName("Test Update Etudiant")
    void testUpdateEtudiant() {
        // Given
        Etudiant savedEtudiant = etudiantRepository.save(etudiantTest);
        Long etudiantId = savedEtudiant.getIdEtudiant();

        // When
        savedEtudiant.setNomEt("Dupont-Updated");
        savedEtudiant.setPrenomEt("Jean-Claude");
        savedEtudiant.setEcole("ESPRIT-Updated");

        Etudiant updatedEtudiant = etudiantRepository.save(savedEtudiant);

        // Then
        assertNotNull(updatedEtudiant);
        assertEquals(etudiantId, updatedEtudiant.getIdEtudiant());
        assertEquals("Dupont-Updated", updatedEtudiant.getNomEt());
        assertEquals("Jean-Claude", updatedEtudiant.getPrenomEt());
        assertEquals("ESPRIT-Updated", updatedEtudiant.getEcole());
        assertEquals(12345678L, updatedEtudiant.getCin()); // Should remain unchanged

        System.out.println("✅ Etudiant updated successfully: " + updatedEtudiant.getNomEt() + " " + updatedEtudiant.getPrenomEt());
    }

    @Test
    @Order(5)
    @DisplayName("Test Delete Etudiant")
    void testDeleteEtudiant() {
        // Given
        Etudiant savedEtudiant = etudiantRepository.save(etudiantTest);
        Long etudiantId = savedEtudiant.getIdEtudiant();

        // Verify etudiant exists
        assertTrue(etudiantRepository.existsById(etudiantId));

        // When
        etudiantRepository.deleteById(etudiantId);

        // Then
        assertFalse(etudiantRepository.existsById(etudiantId));
        Optional<Etudiant> deletedEtudiant = etudiantRepository.findById(etudiantId);
        assertFalse(deletedEtudiant.isPresent());

        System.out.println("✅ Etudiant deleted successfully with ID: " + etudiantId);
    }



    @AfterEach
    void tearDown() {
        // Clean up if needed
        etudiantTest = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("🏁 All Etudiant CRUD tests completed successfully!");
    }
}