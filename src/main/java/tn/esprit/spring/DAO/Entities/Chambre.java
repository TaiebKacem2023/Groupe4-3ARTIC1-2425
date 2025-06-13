package tn.esprit.spring.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "T_CHAMBRE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idChambre;
    String nomChambre;
    int capaciteChambre;
    int numeroChambre;
    TypeChambre typeChambre;
    @ManyToOne
    @JsonIgnore
    Bloc bloc;

    // Add other chambre attributes and relationships as needed


    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public void setIdChambre(long idChambre) {
        this.idChambre = idChambre;
    }

    public long getIdChambre() {
        return idChambre;
    }

    public String getNomChambre() {
        return nomChambre;
    }

    public void setNomChambre(String nomChambre) {
        this.nomChambre = nomChambre;
    }

    public int getCapaciteChambre() {
        return capaciteChambre;
    }

    public void setCapaciteChambre(int capaciteChambre) {
        this.capaciteChambre = capaciteChambre;
    }

    public int getNumeroChambre() {
        return numeroChambre;
    }

    public void setNumeroChambre(int numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public TypeChambre getTypeChambre() {
        return typeChambre;
    }

    public void setTypeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
    }

    public Bloc getBloc() {
        return bloc;
    }
}
