package tn.esprit.spring.Services.Etudiant;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EtudiantService implements IEtudiantService {
    EtudiantRepository repo;
    ReservationRepository reservationRepository;

    @Override
    public Etudiant addOrUpdate(Etudiant e) {
        return repo.save(e);
    }

    @Override
    public List<Etudiant> findAll() {
        return repo.findAll();
    }

    @Override
    public Etudiant findById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Etudiant not found with id: " + id));
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(Etudiant e) {
        repo.delete(e);
    }

    @Override
    public List<Etudiant> selectJPQL(String nom) {
        return repo.selectJPQL(nom);
    }

    @Override
    public void affecterReservationAEtudiant(String idR, String nomE, String prenomE) {
        // 1- Récupérer les objets avec contrôle de présence
        Reservation res = reservationRepository.findById(idR)
                .orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée avec ID: " + idR));

        Etudiant et = repo.getByNomEtAndPrenomEt(nomE, prenomE);
        if (et == null) {
            throw new EntityNotFoundException("Étudiant non trouvé: " + nomE + " " + prenomE);
        }

        // 2- Affectation: On affecte le child au parent
        et.getReservations().add(res);

        // 3- Save du parent
        repo.save(et);
    }
    @Override
    public void desaffecterReservationAEtudiant(String idR, String nomE, String prenomE) {
        // 1- Récupérer les objets avec vérification
        Reservation res = reservationRepository.findById(idR)
                .orElseThrow(() -> new EntityNotFoundException("Réservation non trouvée avec ID: " + idR));

        Etudiant et = repo.getByNomEtAndPrenomEt(nomE, prenomE);
        if (et == null) {
            throw new EntityNotFoundException("Étudiant non trouvé: " + nomE + " " + prenomE);
        }

        // 2- Désaffectation: retirer le child du parent
        et.getReservations().remove(res);

        // 3- Save du parent
        repo.save(et);
    }

}
