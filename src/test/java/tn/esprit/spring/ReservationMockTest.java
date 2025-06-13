package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.*;
import tn.esprit.spring.DAO.Repositories.*;
import tn.esprit.spring.Services.Reservation.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationMockTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    ChambreRepository chambreRepository;

    @Mock
    EtudiantRepository etudiantRepository;

    @InjectMocks
    ReservationService reservationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterReservation_ChambreDisponible() {
        Long numChambre = 101L;
        long cin = 12345678L;

        Bloc bloc = Bloc.builder().nomBloc("A").build();

        Chambre chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(numChambre)
                .typeC(TypeChambre.DOUBLE)
                .bloc(bloc)
                .reservations(new ArrayList<>())
                .build();

        Etudiant etudiant = Etudiant.builder()
                .cin(cin)
                .nomEt("NomEt")
                .prenomEt("PrenomEt")
                .build();

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(0);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(chambreRepository.save(any(Chambre.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNotNull(result);
        assertEquals(1, result.getEtudiants().size());
        assertTrue(result.isEstValide());
        assertEquals("A", chambre.getBloc().getNomBloc());
        verify(reservationRepository, times(1)).save(captor.capture());
        verify(chambreRepository, times(1)).save(any(Chambre.class));

        // Vérifier format idReservation (ex: "2024/2025-A-101-12345678")
        String idRes = captor.getValue().getIdReservation();
        assertTrue(idRes.contains("-A-") && idRes.contains("-101-") && idRes.endsWith(String.valueOf(cin)));
    }

    @Test
    void testAjouterReservation_ChambrePleine() {
        Long numChambre = 101L;
        long cin = 12345678L;

        Bloc bloc = Bloc.builder().nomBloc("B").build();

        Chambre chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(numChambre)
                .typeC(TypeChambre.SIMPLE)
                .bloc(bloc)
                .reservations(new ArrayList<>())
                .build();

        Etudiant etudiant = Etudiant.builder()
                .cin(cin)
                .nomEt("Dupont")
                .prenomEt("Alice")
                .build();

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(
                eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(1); // chambre pleine (SIMPLE = 1 max)

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNull(result);
        verify(reservationRepository, never()).save(any());
        verify(chambreRepository, never()).save(any());
    }

    @Test
    void testAjouterReservation_ChambreIntrouvable() {
        Long numChambre = 999L;
        long cin = 12345678L;

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(null);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNull(result);
        verify(reservationRepository, never()).save(any());
        verify(chambreRepository, never()).save(any());
    }

    @Test
    void testAjouterReservation_EtudiantIntrouvable() {
        Long numChambre = 101L;
        long cin = 99999999L;

        Bloc bloc = Bloc.builder().nomBloc("A").build();

        Chambre chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(numChambre)
                .typeC(TypeChambre.DOUBLE)
                .bloc(bloc)
                .reservations(new ArrayList<>())
                .build();

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(null);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        assertNull(result);
        verify(reservationRepository, never()).save(any());
        verify(chambreRepository, never()).save(any());
    }
}
