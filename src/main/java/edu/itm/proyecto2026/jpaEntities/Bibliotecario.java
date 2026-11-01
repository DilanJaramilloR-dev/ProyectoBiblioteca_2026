package edu.itm.proyecto2026.jpaEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "bibliotecario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bibliotecario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBibliotecario")
    private Integer idBibliotecario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPersona")
    private Persona persona;

    @Column(name = "usuarioLoginBibliotecario")
    private String usuarioLoginBibliotecario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @Column(name = "contrasenaBibliotecario")
    private String contrasenaBibliotecario;

    @Column(name = "documentoBibliotecario")
    private String documentoBibliotecario;
}
