package app.biblioteca.sistemagestionbiblioteca.repositories.impl;

import app.biblioteca.sistemagestionbiblioteca.models.EstadoLibro;
import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibroRepositoryImplTest {
    private LibroRepositoryImpl repo;
    private Libro libro1, libro2;

    @BeforeEach
    void setUp() {
        repo = new LibroRepositoryImpl();
        libro1 = new Libro(null, "111", "Title1", "Author1", EstadoLibro.DISPONIBLE);
        libro2 = new Libro(null, "222", "Title2", "Author2", EstadoLibro.PRESTADO);
    }

    @Test
    void save_asignaIdYGuarda() {
        Libro saved = repo.save(libro1);
        assertNotNull(saved.getId());
        assertEquals("111", saved.getIsbn());
    }

    @Test
    void findById_existente() {
        Libro saved = repo.save(libro1);
        Optional<Libro> found = repo.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }

    @Test
    void findById_noExistente() {
        assertTrue(repo.findById(999L).isEmpty());
    }

    @Test
    void findByIsbn_existente() {
        repo.save(libro2);
        Optional<Libro> found = repo.findByIsbn("222");
        assertTrue(found.isPresent());
        assertEquals(libro2.getTitulo(), found.get().getTitulo());
    }

    @Test
    void findByIsbn_noExistente() {
        assertTrue(repo.findByIsbn("xxx").isEmpty());
    }

    @Test
    void findAllRetornaTodos() {
        repo.save(libro1);
        repo.save(libro2);
        List<Libro> list = repo.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void deleteById_elimina() {
        Libro saved = repo.save(libro1);
        repo.deleteById(saved.getId());
        assertFalse(repo.findById(saved.getId()).isPresent());
    }

    @Test
    void existsById_funciona() {
        Libro saved = repo.save(libro1);
        assertTrue(repo.existsById(saved.getId()));
        assertFalse(repo.existsById(123L));
    }
}
