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

import java.time.LocalDate;

@Entity
@Table(name = "autor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAutor")
    private Integer idAutor;

    @Column(name = "nombreAutor")
    private String nombreAutor;

    @Column(name = "apellidoAutor")
    private String apellidoAutor;

    @Column(name = "nacionalidadAutor")
    private String nacionalidadAutor;

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;
}