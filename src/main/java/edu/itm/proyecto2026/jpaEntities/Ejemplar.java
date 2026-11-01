package edu.itm.proyecto2026.jpaEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ejemplar")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEjemplar")
    private Integer idEjemplar;

    @ManyToOne
    @JoinColumn(name = "idLibro")
    private Libro libro;

    @Column(name = "codigoInventario")
    private String codigoInventario;

    @Column(name = "fechaAdquisicion")
    private LocalDate fechaAdquisicion;

    @Column(name = "estadoEjemplar")
    private String estadoEjemplar;

    @Column(name = "ubicacionEjemplar")
    private String ubicacionEjemplar;

    @Column(name = "disponibilidadEjemplar")
    private String disponibilidadEjemplar;
}
