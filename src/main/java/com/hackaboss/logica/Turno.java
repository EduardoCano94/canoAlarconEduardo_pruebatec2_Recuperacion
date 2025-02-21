package com.hackaboss.logica;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "turno")
public class Turno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTURNO")
    private Long idTurno;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(nullable = false, length = 20)
    private String estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ciudadano")
    private Ciudadano ciudadano;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tramite_id")
    private Tramite tramite;

    @Column(name = "NUMEROTURNO")
    private Integer numeroTurno;

    @Column
    private String descripcion;

    // Constructor vacío
    public Turno() {
    }

    // Constructor con parámetros
    public Turno(Date fecha, String estado, Ciudadano ciudadano, Tramite tramite, String descripcion, Integer numeroTurno) {
        this.fecha = fecha;
        this.estado = estado;
        this.ciudadano = ciudadano;
        this.tramite = tramite;
        this.descripcion = descripcion;
        this.numeroTurno = numeroTurno;
    }

    // Getters y Setters
    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(Integer numeroTurno) {
        this.numeroTurno = numeroTurno;
    }
}