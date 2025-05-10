package app.biblioteca.sistemagestionbiblioteca.repositories;

import app.biblioteca.sistemagestionbiblioteca.models.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    List<Prestamo> findByUsuarioId(Long usuarioId);
    List<Prestamo> findByLibroId(Long libroId);
    List<Prestamo> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
