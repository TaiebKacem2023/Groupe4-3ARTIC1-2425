package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.Services.Chambre.ChambreService;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.class)
@SpringBootTest
public class ChambreServiceTest {

    @Autowired
    private ChambreService chambreService;

    public void testAjouterChambre () {
        Chambre numChambre = Chambre.builder().numeroChambre(15).typeC(TypeChambre.DOUBLE).build();
        Chambre savedChambre = chambreService.addOrUpdate(numChambre);
        Assertions.assertNotNull(savedChambre.getIdChambre());
        TypeChambre expectedTypeChambre = TypeChambre.DOUBLE;
        Assertions.assertTrue(savedChambre.getNumeroChambre() > 0);
    }

    @BeforeAll
    void before() {

    }

    @AfterAll
    void after() {

    }

    @BeforeEach
    void beforeEach() {

    }

    @AfterEach
    void afterEach() {

    }

    @Order(1)
    @RepeatedTest(4)
    void test() {

    }

    @Order(4)
    @Test
    void test2() {

    }

    @Order(2)
    @Test
    void test3() {

    }

    @Order(3)
    @Test
    void test4() {

    }
}