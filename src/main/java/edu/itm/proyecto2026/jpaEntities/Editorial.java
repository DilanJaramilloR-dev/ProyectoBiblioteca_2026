package edu.itm.proyecto2026.jpaEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "editorial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEditorial")
    private Integer idEditorial;

    @Column(name = "correoEditorial")
    private String correoEditorial;

    @Column(name = "nombreEditorial")
    private String nombreEditorial;

    @Column(name = "paisEditorial")
    private String paisEditorial;

    @Column(name = "telefonoEditorial")
    private String telefonoEditorial;

    @OneToMany(mappedBy = "editorial")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Libro> libros;
}