package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE ="https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private LibroRepository libroRepo;
    private AutorRepository autorRepo;

    public Principal(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - buscar libro por titulo
                    2 - listar libros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Bye Bye");
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }

    private DatosLibros getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datos.resultados().stream()
                .filter(libro -> libro.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        return libroBuscado.orElse(null);
    }

    private void buscarLibroPorTitulo() {

        DatosLibros datos = getDatosLibro();
        if (datos != null) {
            Libro libro;
            DatosAutor datosAutor = datos.autor().get(0);
            Autor autorExistente = autorRepo.findByNombre(datosAutor.nombre());
            if (autorExistente != null) {
                libro = new Libro(datos, autorExistente);
            } else {
                Autor autor = new Autor(datosAutor);
                libro = new Libro(datos, autor);
                autorRepo.save(autor);
            }
            try {
                libroRepo.save(libro);
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("No se puede registar un libro que ya existe.");
            }
        } else {
            System.out.println("No se encontro el libro en la base de datos.");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepo.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepo.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }


    private void buscarAutoresVivosPorAnio() {
        System.out.println("Ingrese el año en el que desea buscar");
        int fecha = teclado.nextInt();
        List<Autor> autores = autorRepo.buscarAutoresVivosEnAnio(fecha);
        if (autores.isEmpty()) {
            System.out.println("Sin resultados");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
        var idioma = teclado.nextLine();
        List<Libro> libroPorIdioma = libroRepo.findAll().stream()
                .filter(l -> l.getIdiomas().contains(idioma))
                .toList();
        if (libroPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros con el idioma seleccionado");
            return;
        }
        libroPorIdioma.forEach(System.out::println);
    }

}
