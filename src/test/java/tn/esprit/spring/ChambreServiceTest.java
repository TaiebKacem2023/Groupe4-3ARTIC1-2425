package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.dao.entities.Chambre;
import tn.esprit.spring.dao.entities.TypeChambre;
import tn.esprit.spring.dao.entities.Bloc;
import tn.esprit.spring.dao.entities.Reservation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ChambreTest {

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .typeC(TypeChambre.SIMPLE)
                .bloc(new Bloc())
                .reservations(List.of(new Reservation(), new Reservation()))
                .build();
    }

    @Test
    void testChambreBuilder() {
        assertNotNull(chambre);
        assertEquals(1L, chambre.getIdChambre());
        assertEquals(101L, chambre.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, chambre.getTypeC());
        assertNotNull(chambre.getBloc());
        assertEquals(2, chambre.getReservations().size());
    }

    @Test
    void testSettersAndGetters() {
        Chambre c = new Chambre();
        c.setIdChambre(2L);
        c.setNumeroChambre(202L);
        c.setTypeC(TypeChambre.DOUBLE);
        Bloc b = new Bloc();
        c.setBloc(b);

        assertEquals(2L, c.getIdChambre());
        assertEquals(202L, c.getNumeroChambre());
        assertEquals(TypeChambre.DOUBLE, c.getTypeC());
        assertEquals(b, c.getBloc());
    }

    @Test
    void testEmptyReservationsListByDefault() {
        Chambre c = new Chambre();
        assertNotNull(c.getReservations());
    }
}
