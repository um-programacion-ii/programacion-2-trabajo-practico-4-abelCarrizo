package app.biblioteca.sistemagestionbiblioteca.services.impl;

import app.biblioteca.sistemagestionbiblioteca.exceptions.LibroNotFoundException;
import app.biblioteca.sistemagestionbiblioteca.models.EstadoLibro;
import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.repositories.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {
    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro(1L, "123", "Título", "Autor", EstadoLibro.DISPONIBLE);
    }

    @Test
    void buscarPorIsbn_existente_retornaLibro() {
        when(libroRepository.findByIsbn("123")).thenReturn(Optional.of(libro));

        Libro result = libroService.buscarPorIsbn("123"); // assuming buscarPorIsbn tests
        assertNotNull(result);
        assertEquals("123", result.getIsbn());
    }

    @Test
    void buscarPorId_noExistente_lanzaExcepcion() {
        when(libroRepository.findByIsbn("999")).thenReturn(Optional.empty());

        assertThrows(LibroNotFoundException.class, () -> libroService.buscarPorIsbn("999"));
    }

    @Test
    void guardar_libro_original_guardado() {
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro saved = libroService.guardar(libro);
        assertEquals(libro, saved);
        verify(libroRepository).save(libro);
    }

    @Test
    void eliminar_existente_llamaDelete() {
        when(libroRepository.existsById(1L)).thenReturn(true);

        libroService.eliminar(1L);
        verify(libroRepository).deleteById(1L);
    }

    @Test
    void eliminar_noExistente_lanzaExcepcion() {
        when(libroRepository.existsById(2L)).thenReturn(false);

        assertThrows(LibroNotFoundException.class, () -> libroService.eliminar(2L));
    }
}
