package app.biblioteca.sistemagestionbiblioteca.services.impl;

import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.repositories.LibroRepository;
import app.biblioteca.sistemagestionbiblioteca.services.LibroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {
    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + isbn));
    }

    @Override
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }

    @Override
    public Libro actualizar(Long id, Libro libro) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
        libro.setId(id);
        return libroRepository.save(libro);
    }
}
