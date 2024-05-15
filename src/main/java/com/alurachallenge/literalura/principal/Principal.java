package com.alurachallenge.literalura.principal;

import java.util.Optional;
import java.util.Scanner;

import com.alurachallenge.literalura.model.Datos;
import com.alurachallenge.literalura.model.DatosLibro;
import com.alurachallenge.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.service.ConvierteDatos;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void iniciar(){
        var opcionMenu = -1;
        var menu = """
                1 -> Consultar Libro
                2 -> Consultar Autor
                3 -> Libros por Idioma
                4 -> Autores Vivos por X Año

                0 -> Salir
                """;
        
        System.out.println(menu);
        opcionMenu = teclado.nextInt();
        teclado.nextLine();

        switch (opcionMenu) {
            case 1:
                consultarLibro();
                break;
            case 2:
                consultarAutor();
                break;
            case 3:
                consultarLibrosPorIdioma();
                break;
            case 4:
                consultarAutoresVivosPorAnio();
                break;
            default:
                System.out.println("Opcion Invalida");;
        }
    }
    
    private void consultarAutoresVivosPorAnio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarAutoresVivosPorAnio'");
    }

    private void consultarLibrosPorIdioma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarLibrosPorIdioma'");
    }

    private void consultarAutor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarAutor'");
    }

    private void consultarLibro() {
        System.out.println("\nIngrese algun nombre de libro: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.libros().stream()
            .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
            .findFirst();
    
        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado: ");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("No se ha encontrado el libro");
        }        
    }
}
