package app.biblioteca.sistemagestionbiblioteca.controllers;

import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.services.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService libroService;
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }
    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }
    @GetMapping("/{isbn}")
    public Libro buscarPorIsbn(@PathVariable String isbn) {
        return libroService.buscarPorIsbn(isbn);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Libro crear(@RequestBody Libro libro) {
        return libroService.guardar(libro);
    }
    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.actualizar(id, libro);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}
