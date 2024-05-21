package com.alurachallenge.literalura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alurachallenge.literalura.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    Optional<Libro> findByIdLibro(Long idLibro);
}
