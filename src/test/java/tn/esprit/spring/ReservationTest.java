package tn.esprit.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ReservationTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    EtudiantRepository etudiantRepository;

    @Test
    public void testCreateReservationWithEtudiants() {
        // Créer deux étudiants
        Etudiant e1 = Etudiant.builder()
                .nomEt("Doe")
                .prenomEt("John")
                .cin(12345678)
                .ecole("ESPRIT")
                .build();

        Etudiant e2 = Etudiant.builder()
                .nomEt("Smith")
                .prenomEt("Anna")
                .cin(87654321)
                .ecole("ISI")
                .build();

        e1 = etudiantRepository.save(e1);
        e2 = etudiantRepository.save(e2);

        // Créer une réservation liée aux deux étudiants
        Reservation reservation = Reservation.builder()
                .idReservation("RES1")
                .anneeUniversitaire(LocalDate.of(2025, 9, 1))
                .estValide(true)
                .etudiants(List.of(e1, e2))
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        // Mise à jour inverse côté étudiant (utile si la relation est bidirectionnelle)
        e1.getReservations().add(savedReservation);
        e2.getReservations().add(savedReservation);
        etudiantRepository.saveAll(List.of(e1, e2));

        // Assertions
        Assertions.assertNotNull(savedReservation);
        Assertions.assertEquals("RES1", savedReservation.getIdReservation());
        Assertions.assertEquals(2, savedReservation.getEtudiants().size());
        Assertions.assertTrue(savedReservation.isEstValide());
    }

    @Test
    public void testFindReservationById() {
        // D'abord, on crée une réservation si elle n'existe pas
        Reservation reservation = Reservation.builder()
                .idReservation("RES2")
                .anneeUniversitaire(LocalDate.of(2025, 9, 1))
                .estValide(true)
                .build();

        reservationRepository.save(reservation);

        // Ensuite, on la récupère
        Reservation foundReservation = reservationRepository.findById("RES2").orElse(null);

        // Vérification
        Assertions.assertNotNull(foundReservation);
        Assertions.assertEquals("RES2", foundReservation.getIdReservation());
    }
}
