package app.biblioteca.sistemagestionbiblioteca.controllers;

import app.biblioteca.sistemagestionbiblioteca.models.Prestamo;
import app.biblioteca.sistemagestionbiblioteca.services.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;
    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }
    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }
    @GetMapping("/{id}")
    public Prestamo obtenerPorId(@PathVariable Long id) {
        return prestamoService.buscarPorId(id);
    }
    @GetMapping("/usuario/{usuarioId}")
    public List<Prestamo> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return prestamoService.obtenerPorUsuario(usuarioId);
    }
    @GetMapping("/libro/{libroId}")
    public List<Prestamo> obtenerPorLibro(@PathVariable Long libroId) {
        return prestamoService.obtenerPorLibro(libroId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prestamo crear(@RequestBody Prestamo prestamo) {
        return prestamoService.guardar(prestamo);
    }
    @PutMapping("/{id}")
    public Prestamo actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        return prestamoService.actualizar(id, prestamo);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        prestamoService.eliminar(id);
    }
}
