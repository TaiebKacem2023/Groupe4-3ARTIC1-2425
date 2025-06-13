package tn.esprit.spring.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.*;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationMockTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Chambre chambre;
    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Préparer des objets factices
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        chambre.setReservations(new ArrayList<>());

        Bloc bloc = new Bloc();
        bloc.setNomBloc("A");
        chambre.setBloc(bloc);

        etudiant = new Etudiant();
        etudiant.setCin(12345678L);

        // Mock des appels aux repositories
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre);
        when(etudiantRepository.findByCin(12345678L)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                eq(1L), any(LocalDate.class), any(LocalDate.class))).thenReturn(1);

        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant_Success() {
        Reservation reservation = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(101L, 12345678L);

        assertNotNull(reservation);
        assertTrue(reservation.getEstValide());
        assertEquals(1, reservation.getEtudiants().size());
        assertEquals(etudiant.getCin(), reservation.getEtudiants().get(0).getCin());
        verify(chambreRepository).save(chambre);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant_ChambrePleine() {
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                eq(1L), any(LocalDate.class), any(LocalDate.class))).thenReturn(2); // chambre DOUBLE déjà pleine

        Reservation reservation = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(101L, 12345678L);

        assertNull(reservation);
        verify(reservationRepository, never()).save(any());
        verify(chambreRepository, never()).save(any());
    }
}
