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
}
enum  TypeChambre {
    SINGLE, DOUBLE, TRIPLE, QUAD
}