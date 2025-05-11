package app.biblioteca.sistemagestionbiblioteca.services;

import app.biblioteca.sistemagestionbiblioteca.models.Prestamo;

import java.util.List;

public interface PrestamoService {
    Prestamo buscarPorId(Long id);
    List<Prestamo> obtenerTodos();
    List<Prestamo> obtenerPorUsuario(Long usuarioId);
    List<Prestamo> obtenerPorLibro(Long libroId);
    Prestamo guardar(Prestamo prestamo);
    void eliminar(Long id);
    Prestamo actualizar(Long id, Prestamo prestamo);
}
