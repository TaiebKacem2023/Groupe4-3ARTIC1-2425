package tn.esprit.spring.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "T_FOYER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Foyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idFoyer;

    String NomFoyer;

    @OneToMany(mappedBy = "foyer")
    @JsonIgnore
    List<Bloc> blocs;

    // Add other foyer attributes and relationships as needed
}