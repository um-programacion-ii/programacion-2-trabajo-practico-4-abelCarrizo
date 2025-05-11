package app.biblioteca.sistemagestionbiblioteca.controllers;

import app.biblioteca.sistemagestionbiblioteca.models.Prestamo;
import app.biblioteca.sistemagestionbiblioteca.services.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con préstamos.
 * Proporciona endpoints para CRUD de préstamos y consultas por libro o usuario.
 */
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    /**
     * Constructor con inyección de dependencia de PrestamoService.
     * @param prestamoService servicio de lógica de préstamos
     */
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    /**
     * Obtiene todos los préstamos registrados.
     * @return lista de objetos Prestamo
     */
    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }

    /**
     * Busca un préstamo por su ID.
     * @param id identificador del préstamo
     * @return objeto Prestamo encontrado
     */
    @GetMapping("/{id}")
    public Prestamo obtenerPorId(@PathVariable Long id) {
        return prestamoService.buscarPorId(id);
    }

    /**
     * Obtiene los préstamos asociados a un usuario.
     * @param usuarioId ID del usuario
     * @return lista de préstamos del usuario
     */
    @GetMapping("/usuario/{usuarioId}")
    public List<Prestamo> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return prestamoService.obtenerPorUsuario(usuarioId);
    }

    /**
     * Obtiene los préstamos asociados a un libro.
     * @param libroId ID del libro
     * @return lista de préstamos del libro
     */
    @GetMapping("/libro/{libroId}")
    public List<Prestamo> obtenerPorLibro(@PathVariable Long libroId) {
        return prestamoService.obtenerPorLibro(libroId);
    }

    /**
     * Registra un nuevo préstamo.
     * @param prestamo objeto Prestamo a crear
     * @return el préstamo creado con ID asignado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prestamo crear(@RequestBody Prestamo prestamo) {
        return prestamoService.guardar(prestamo);
    }

    /**
     * Actualiza un préstamo existente.
     * @param id identificador del préstamo
     * @param prestamo datos actualizados del préstamo
     * @return el préstamo actualizado
     */
    @PutMapping("/{id}")
    public Prestamo actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        return prestamoService.actualizar(id, prestamo);
    }

    /**
     * Elimina un préstamo por su ID.
     * @param id identificador del préstamo a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        prestamoService.eliminar(id);
    }
}
