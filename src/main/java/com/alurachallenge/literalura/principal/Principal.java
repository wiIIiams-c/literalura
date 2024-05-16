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
                -------------------------------------------
                    Bienvenido al sistema de Literalura
                -------------------------------------------
                            Seleccione una opcion:
                -------------------------------------------

                1 -> Consultar libro por titulo
                2 -> Listar libros registrados
                3 -> Listar autores registrados
                4 -> Listar autores vivos por X año
                5 -> Listar libros por idioma

                0 -> Salir
                """;
        
        System.out.println(menu);
        opcionMenu = teclado.nextInt();
        teclado.nextLine();

        switch (opcionMenu) {
            case 0:
                System.out.println("\nCerrando aplicacion...");
                break;
            case 1:
                consultarLibro();
                break;
            case 2:
                listarLibros();
                break;
            case 3:
                listarAutores();
                break;
            case 4:
                consultarAutoresVivosPorAnio();
                break;
            case 5:
                consultarLibrosPorIdioma();
                break;
            default:
                System.out.println("Opcion Invalida");
        }
    }
    
    private void listarLibros() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarLibros'");
    }

    private void consultarAutoresVivosPorAnio() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarAutoresVivosPorAnio'");
    }

    private void consultarLibrosPorIdioma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarLibrosPorIdioma'");
    }

    private void listarAutores() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarAutores'");
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
            System.out.println("\nLibro encontrado:\n");
            //System.out.println(libroBuscado.get());
            var muestraLibro = """
                    -------------------------
                        Datos del Libro
                    -------------------------
                    Titulo   : %s
                    Autor    : %s
                    Idiomas  : %s
                    Descargas: %s
                    """;
            System.out.println(muestraLibro.formatted(
                libroBuscado.get().titulo(), 
                libroBuscado.get().autor().get(0).nombre(), 
                libroBuscado.get().idioma().get(0), 
                libroBuscado.get().numeroDescargas()
            ));
        } else {
            System.out.println("No se ha encontrado el libro\n");
        }        
    }
}
