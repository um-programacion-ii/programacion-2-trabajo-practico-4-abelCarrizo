package app.biblioteca.sistemagestionbiblioteca.services.impl;

import app.biblioteca.sistemagestionbiblioteca.exceptions.PrestamoNotFoundException;
import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.models.Prestamo;
import app.biblioteca.sistemagestionbiblioteca.models.Usuario;
import app.biblioteca.sistemagestionbiblioteca.repositories.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {
    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        Libro libro = new Libro(1L, "x", "t", "a", null);
        Usuario usuario = new Usuario(1L, "u", "e", null);
        prestamo = new Prestamo(null, libro, usuario,
                LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    void buscarPorId_existente_retornaPrestamo() {
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        Prestamo result = prestamoService.buscarPorId(1L);
        assertEquals(prestamo, result);
    }

    @Test
    void buscarPorId_noExistente_lanzaExcepcion() {
        when(prestamoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(PrestamoNotFoundException.class, () -> prestamoService.buscarPorId(2L));
    }

    @Test
    void guardar_prestamo_llamaSave() {
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);

        Prestamo saved = prestamoService.guardar(prestamo);
        assertSame(prestamo, saved);
        verify(prestamoRepository).save(prestamo);
    }
}
