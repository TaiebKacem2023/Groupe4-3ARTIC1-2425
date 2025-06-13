package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.Services.Bloc.BlocService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlocServiceTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    private Bloc bloc;
    private Chambre chambre1, chambre2;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        // Initialize Bloc using constructor and setters
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);
        bloc.setChambres(new ArrayList<>()); // Initialize empty list

        // Initialize Chambres using constructor and setters
        chambre1 = new Chambre();
        chambre1.setIdChambre(1L);

        chambre2 = new Chambre();
        chambre2.setIdChambre(2L);

        // Initialize Foyer using constructor and setters
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer X");

        // Set the relationship
        bloc.setChambres(Arrays.asList(chambre1, chambre2));
    }

    @Test
    void testAddOrUpdate() {
        // Arrange
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre1);

        // Act
        Bloc result = blocService.addOrUpdate(bloc);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
        verify(blocRepository, times(1)).save(bloc);
        verify(chambreRepository, times(2)).save(any(Chambre.class));
    }

    @Test
    void testAddOrUpdate2() {
        // Arrange
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre1);

        // Act
        Bloc result = blocService.addOrUpdate2(bloc);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
        verify(chambreRepository, times(2)).save(any(Chambre.class));
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Bloc> blocs = Arrays.asList(bloc);
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals(bloc, result.get(0));
    }

    @Test
    void testFindById() {
        // Arrange
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
    }

    @Test
    void testDeleteById() {
        // Arrange
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        doNothing().when(chambreRepository).deleteAll(anyList());
        doNothing().when(blocRepository).delete(any(Bloc.class));

        // Act
        blocService.deleteById(1L);

        // Assert
        verify(chambreRepository, times(1)).deleteAll(bloc.getChambres());
        verify(blocRepository, times(1)).delete(bloc);
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(chambreRepository).deleteAll(anyList());
        doNothing().when(blocRepository).delete(any(Bloc.class));

        // Act
        blocService.delete(bloc);

        // Assert
        verify(chambreRepository, times(1)).deleteAll(bloc.getChambres());
        verify(blocRepository, times(1)).delete(bloc);
    }

    @Test
    void testAffecterChambresABloc() {
        // Arrange
        List<Long> numChambres = Arrays.asList(1L, 2L);
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(chambreRepository.findByNumeroChambre(1L)).thenReturn(chambre1);
        when(chambreRepository.findByNumeroChambre(2L)).thenReturn(chambre2);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre1);

        // Act
        Bloc result = blocService.affecterChambresABloc(numChambres, "Bloc A");

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
        verify(chambreRepository, times(2)).save(any(Chambre.class));
    }

    @Test
    void testAffecterBlocAFoyer() {
        // Arrange
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(foyerRepository.findByNomFoyer("Foyer X")).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc result = blocService.affecterBlocAFoyer("Bloc A", "Foyer X");

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testAjouterBlocEtSesChambres() {
        // Arrange
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre1);

        // Act
        Bloc result = blocService.ajouterBlocEtSesChambres(bloc);

        // Assert
        assertNotNull(result);
        assertEquals(bloc, result);
        verify(chambreRepository, times(2)).save(any(Chambre.class));
    }

    @Test
    void testAjouterBlocEtAffecterAFoyer() {
        // Arrange
        when(foyerRepository.findByNomFoyer("Foyer X")).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc result = blocService.ajouterBlocEtAffecterAFoyer(bloc, "Foyer X");

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());
        verify(blocRepository, times(1)).save(bloc);
    }
}