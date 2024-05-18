package com.alurachallenge.literalura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    private Long idLibro;
    private String nombre;
    private String anioNacimiento;
    private String anioFallecimiento;

    @OneToOne
    @JoinColumn(name = "idLibro", nullable = false)
    private Libro libro;

    public Autor(Long idLibro, DatosAutor datosAutor){
        this.idLibro = idLibro;
        this.nombre = datosAutor.nombre();
        this.anioNacimiento = datosAutor.anioNacimiento();
        this.anioFallecimiento = datosAutor.anioFallecimiento();
    }

    public Autor() {
    }

    public Long getidLibro() {
        return idLibro;
    }

    public void setidLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(String anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(String anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    
}
