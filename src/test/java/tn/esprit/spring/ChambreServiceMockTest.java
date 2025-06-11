package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.Services.Chambre.ChambreService;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChambreServiceTest {

    @Mock
    ChambreRepository chambreRepository;

    @InjectMocks
    ChambreService chambreService;

    Chambre testChambre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testChambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .typeC(TypeChambre.SIMPLE)
                .build();
    }

    @Test
    void testGetChambreById() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(testChambre));

        Chambre result = chambreService.findById(1L);

        assertNotNull(result);
        assertEquals(101L, result.getNumeroChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testAddChambre() {
        when(chambreRepository.save(any(Chambre.class))).thenReturn(testChambre);

        Chambre saved = chambreService.addOrUpdate(testChambre);

        assertNotNull(saved);
        assertEquals(101L, saved.getNumeroChambre());
        verify(chambreRepository).save(testChambre);
    }
}
