package edu.itm.proyecto2026.jpaEntities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.itm.proyecto2026.jpaEntities.Sancion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "prestamo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestamo")
    private Integer idPrestamo;

    @ManyToOne
    @JoinColumn(name = "idEjemplar")
    private Ejemplar ejemplar;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idBibliotecario")
    private Bibliotecario bibliotecario;

    @Column(name = "estadoPrestamo")
    private String estadoPrestamo;

    @Column(name = "fechaPrestamo")
    private LocalDate fechaPrestamo;

    @Column(name = "fechaDevolucionPrevista")
    private LocalDate fechaDevolucionPrevista;

    @Column(name = "fechaDevolucionReal")
    private LocalDate fechaDevolucionReal;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Sancion> sanciones;
}