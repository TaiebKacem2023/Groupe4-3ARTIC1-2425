package tn.esprit.spring.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    // Define any custom query methods if needed
    Chambre findByNumeroChambre(long numeroChambre);

    // You can add more methods as per your requirements
}
