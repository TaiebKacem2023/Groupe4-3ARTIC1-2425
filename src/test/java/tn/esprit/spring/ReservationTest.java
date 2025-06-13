package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationTest {

    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation reservationTest;
    private static String savedReservationId;

    @BeforeEach
    void setUp() {
        reservationTest = Reservation.builder()
                .idReservation("2024/2025-B1-101-12345678")
                .anneeUniversitaire(LocalDate.of(2024, 9, 15))
                .estValide(true)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Reservation")
    void testCreateReservation() {
        Reservation savedReservation = reservationRepository.save(reservationTest);
        savedReservationId = savedReservation.getIdReservation();

        assertNotNull(savedReservation);
        assertEquals("2024/2025-B1-101-12345678", savedReservation.getIdReservation());
        assertTrue(savedReservation.isEstValide());
        assertEquals(LocalDate.of(2024, 9, 15), savedReservation.getAnneeUniversitaire());

        System.out.println("✅ Reservation created with ID: " + savedReservationId);
    }

    @Test
    @Order(2)
    @DisplayName("Test Read Reservation by ID")
    void testReadReservationById() {
        reservationRepository.save(reservationTest);
        Optional<Reservation> found = reservationRepository.findById(reservationTest.getIdReservation());

        assertTrue(found.isPresent());
        assertEquals("2024/2025-B1-101-12345678", found.get().getIdReservation());
        assertTrue(found.get().isEstValide());

        System.out.println("✅ Reservation found: " + found.get().getIdReservation());
    }

    @Test
    @Order(3)
    @DisplayName("Test Read All Reservations")
    void testReadAllReservations() {
        reservationRepository.save(reservationTest);
        List<Reservation> reservations = reservationRepository.findAll();

        assertNotNull(reservations);
        assertFalse(reservations.isEmpty());

        System.out.println("✅ Found " + reservations.size() + " reservations");
    }

    @Test
    @Order(4)
    @DisplayName("Test Update Reservation")
    void testUpdateReservation() {
        Reservation saved = reservationRepository.save(reservationTest);

        saved.setEstValide(false);
        saved.setAnneeUniversitaire(LocalDate.of(2025, 9, 15));
        Reservation updated = reservationRepository.save(saved);

        assertEquals(saved.getIdReservation(), updated.getIdReservation());
        assertFalse(updated.isEstValide());
        assertEquals(LocalDate.of(2025, 9, 15), updated.getAnneeUniversitaire());

        System.out.println("✅ Reservation updated: " + updated.getIdReservation());
    }

    @Test
    @Order(5)
    @DisplayName("Test Delete Reservation")
    void testDeleteReservation() {
        Reservation saved = reservationRepository.save(reservationTest);
        String id = saved.getIdReservation();

        reservationRepository.deleteById(id);
        Optional<Reservation> deleted = reservationRepository.findById(id);

        assertFalse(deleted.isPresent());
        System.out.println("✅ Reservation deleted: " + id);
    }

    @AfterEach
    void tearDown() {
        reservationTest = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("🏁 All Reservation CRUD tests completed successfully!");
    }
}
