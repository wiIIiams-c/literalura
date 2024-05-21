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
import com.alurachallenge.literalura.repository.AutorRepository;
import com.alurachallenge.literalura.repository.LibroRepository;
import com.alurachallenge.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.service.ConvierteDatos;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository repositoryLibro;
    private AutorRepository repositoryAutor;
    private Libro libro;
    private List<Libro> libros;
    private Optional<Autor> autor;

    public Principal(AutorRepository repositoryAutor, LibroRepository repositoryLibro) {
        this.repositoryAutor = repositoryAutor;
        this.repositoryLibro = repositoryLibro;
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
        libros = repositoryLibro.findAll();

        var headerListaLibros = """
                -------------------------------------------
                    Libros almacenados en literalura
                -------------------------------------------
                """;
        System.out.println("\n" + headerListaLibros + "\n");

        if(libros.isEmpty()){
            System.out.println("No hay libros registrados en literalura...\n");
        }else{
            var cuentaLibros = repositoryLibro.count();
            
            libros.forEach(l -> System.out.println(
                datosLibro().formatted(
                    l.getTitulo(),
                    l.getAutor().getNombre(),
                    l.getIdioma(),
                    l.getNumeroDescargas()
                )
            ));
    
            System.out.println("Total de libros: %s\n".formatted(cuentaLibros));
        }
    }

    private void consultarAutoresVivosPorAnio() {
        try {
            System.out.println("\nIngrese año a consultar:\n");
            int anio = teclado.nextInt();
    
            List<Autor> autores = repositoryAutor.obtenerAutorVivoAnio(anio);
    
            var headerAnioAutor = """
                    -------------------------------------------
                        Autores vivos para el año ingresado
                    -------------------------------------------
                    """;
            System.out.println("\n" + headerAnioAutor);
    
            if(autores.isEmpty()){
                System.out.println("No hay autores vivos para el año ingresado...\n");
            }else{
                var muestraAutor = """
                    -------------------------
                        Datos del Autor
                    -------------------------
                    Nombre            : %s
                    Año Nacimiento    : %s
                    Año Fallecimiento : %s
                    Libros            :
                    """;
                
                autores.forEach(a -> System.out.println(
                    muestraAutor.formatted(
                        a.getNombre(),
                        a.getAnioNacimiento(),
                        a.getAnioFallecimiento()
                    )
                ));
    
                System.out.println("Total de autores: %s\n".formatted(autores.size()));
            }
        } catch (InputMismatchException e) {
            System.out.println("\nEl año ingresado no es un valor valido...\n");
            teclado.nextLine();
        }
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
            .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
            .findFirst();
            
        if (libroBuscado.isPresent()) {
            DatosAutor datosAutor = libroBuscado.get().autor().get(0);
            
            //Valida existencia libro en la DB
            if(repositoryLibro.findByIdLibro(libroBuscado.get().idLibro()).isPresent()){
                System.out.println("\nLibro ya se encuentra guardado en la base de datos...\n");
            }else{
                System.out.println("\nLibro encontrado:\n");
    
                
                System.out.println(datosLibro().formatted(
                    libroBuscado.get().titulo(), 
                    libroBuscado.get().autor().get(0).nombre(), 
                    libroBuscado.get().idioma().get(0), 
                    libroBuscado.get().numeroDescargas()
                ));
                
                //Esta parte valida previamente si el autor existe
                //para guardar solo el libro en caso de que sea necesario
                autor = repositoryAutor.findByNombre(datosAutor.nombre());
                
                if(autor.isPresent()){
                    libro = new Libro(libroBuscado.get());
                    libro.setAutor(autor.get());
                    repositoryLibro.save(libro);
                }else{
                    libros = libroBuscado.stream()
                        .map(l -> new Libro(l))
                        .collect(Collectors.toList());

                    Autor autorClass = new Autor(datosAutor);
                    autorClass.setLibros(libros);
                    repositoryAutor.save(autorClass);
                }
    
                System.out.println("\n Se ha guardado el libro en la base de datos...\n");
            }
        } else {
            System.out.println("\nNo se ha encontrado el libro o no es una busqueda valida...\n");
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
