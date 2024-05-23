package com.alurachallenge.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alurachallenge.literalura.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    Optional<Libro> findByIdLibro(Long idLibro);
    List<Libro> findByIdioma(String idioma);

    @Query("SELECT DISTINCT l.idioma from Libro l ORDER BY l.idioma")
    List<String> obtenerListaUnicaIdioma();

    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE a.idAutor = :id")
    List<Libro> obtenerLibrosPorAutor(Long id);
}
