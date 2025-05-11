package app.biblioteca.sistemagestionbiblioteca.controllers;

import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.services.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con libros.
 * Se encarga de manejar solicitudes HTTP y delegar la lógica al servicio de libros.
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    /**
     * Constructor con inyección de dependencia de LibroService.
     * @param libroService servicio para gestionar libros
     */
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    /**
     * Obtiene la lista de todos los libros.
     * @return lista de objetos Libro
     */
    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }

    /**
     * Busca un libro por su ISBN.
     * @param isbn cadena que representa el ISBN del libro
     * @return objeto Libro encontrado
     */
    @GetMapping("/{isbn}")
    public Libro buscarPorIsbn(@PathVariable String isbn) {
        return libroService.buscarPorIsbn(isbn);
    }

    /**
     * Crea un nuevo libro.
     * @param libro objeto Libro a crear
     * @return el libro creado con ID asignado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Libro crear(@RequestBody Libro libro) {
        return libroService.guardar(libro);
    }

    /**
     * Actualiza un libro existente.
     * @param id identificador del libro a actualizar
     * @param libro objeto Libro con datos actualizados
     * @return el libro actualizado
     */
    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.actualizar(id, libro);
    }

    /**
     * Elimina un libro por su ID.
     * @param id identificador del libro a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}
