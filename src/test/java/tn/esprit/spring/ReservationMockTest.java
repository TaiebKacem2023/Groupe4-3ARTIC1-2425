package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;
import tn.esprit.spring.Services.Reservation.ReservationService;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationMockTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ReservationService reservationService;

    public ReservationMockTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant() {
        Long numChambre = 101L;
        long cin = 12345678L;

        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(numChambre);
        chambre.setTypeC(tn.esprit.spring.DAO.Entities.TypeChambre.SIMPLE);
        chambre.setReservations(new ArrayList<>());

        Etudiant etudiant = new Etudiant();
        etudiant.setCin(cin);

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                anyLong(), any(), any())).thenReturn(0);

        Reservation savedReservation = new Reservation();
        savedReservation.setIdReservation("2024/2025-BLOC-A-101-12345678");

        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNotNull(result);
        assertEquals("2024/2025-BLOC-A-101-12345678", result.getIdReservation());
    }
}
