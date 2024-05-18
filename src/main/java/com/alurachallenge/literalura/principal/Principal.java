package com.alurachallenge.literalura.principal;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.model.Datos;
import com.alurachallenge.literalura.model.DatosAutor;
import com.alurachallenge.literalura.model.DatosLibro;
import com.alurachallenge.literalura.model.Libro;
import com.alurachallenge.literalura.repository.LibroRespository;
import com.alurachallenge.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.service.ConvierteDatos;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRespository respository;
    private List<Libro> libros;

    public Principal(LibroRespository respository) {
        this.respository = respository;
    }

    public void iniciar(){
        var opcionMenu = -1;

        while(opcionMenu != 0){
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
            
            try {
                System.out.println(menu);
                opcionMenu = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e) {
                teclado.nextLine();
                opcionMenu = -1;
            }
    
            switch (opcionMenu) {
                case 0:
                    System.out.println("\nCerrando aplicacion...\n");
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
                    System.out.println("\nOpcion Invalida\n");
            }
        }
    }
    
    private void listarLibros() {
        libros = respository.findAll();
        libros.forEach(l -> System.out.println(
            datosLibro().formatted(
                l.getTitulo(),
                l.getAutor().getNombre(),
                l.getIdioma(),
                l.getNumeroDescargas()
            )
        ));
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

        List<DatosAutor> datosAutor = libroBuscado.get().autor();
        List<Autor> autores = datosAutor.stream()
            .map(a -> new Autor(libroBuscado.get().idLibro(), a))
            .collect(Collectors.toList());
    
        if (libroBuscado.isPresent()) {
            System.out.println("\nLibro encontrado:\n");
            
            System.out.println(datosLibro().formatted(
                libroBuscado.get().titulo(), 
                libroBuscado.get().autor().get(0).nombre(), 
                libroBuscado.get().idioma().get(0), 
                libroBuscado.get().numeroDescargas()
            ));

            Libro libro = new Libro(libroBuscado.get());
            libro.setAutor(autores.get(0));
            respository.save(libro);
        } else {
            System.out.println("No se ha encontrado el libro\n");
        }        
    }

    private String datosLibro(){
        var muestraLibro = """
            -------------------------
                Datos del Libro
            -------------------------
            Titulo   : %s
            Autor    : %s
            Idiomas  : %s
            Descargas: %s
            """;

        return muestraLibro;
    }
}
