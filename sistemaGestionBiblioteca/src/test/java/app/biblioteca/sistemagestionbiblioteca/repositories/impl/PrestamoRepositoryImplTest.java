package app.biblioteca.sistemagestionbiblioteca.repositories.impl;

import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.models.Prestamo;
import app.biblioteca.sistemagestionbiblioteca.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoRepositoryImplTest {
    private PrestamoRepositoryImpl repo;
    private Prestamo p1, p2;

    @BeforeEach
    void setUp() {
        repo = new PrestamoRepositoryImpl();
        Libro libro = new Libro(1L, "x", "t", "a", null);
        Usuario usuario = new Usuario(1L, "u", "e", null);
        p1 = new Prestamo(null, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(1));
        p2 = new Prestamo(null, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(2));
    }

    @Test
    void save_asignaIdYGuarda() {
        Prestamo saved = repo.save(p1);
        assertNotNull(saved.getId());
    }

    @Test
    void findById_existente() {
        Prestamo saved = repo.save(p1);
        Optional<Prestamo> found = repo.findById(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void findByUsuarioId_retornaLista() {
        repo.save(p1);
        repo.save(p2);
        List<Prestamo> list = repo.findByUsuarioId(1L);
        assertEquals(2, list.size());
    }

    @Test
    void findByLibroId_retornaLista() {
        repo.save(p1);
        repo.save(p2);
        List<Prestamo> list = repo.findByLibroId(1L);
        assertEquals(2, list.size());
    }

    @Test
    void findAllRetornaTodos() {
        repo.save(p1);
        repo.save(p2);
        assertEquals(2, repo.findAll().size());
    }

    @Test
    void deleteById_elimina() {
        Prestamo saved = repo.save(p1);
        repo.deleteById(saved.getId());
        assertFalse(repo.existsById(saved.getId()));
    }

    @Test
    void existsById_funciona() {
        Prestamo saved = repo.save(p1);
        assertTrue(repo.existsById(saved.getId()));
    }
}
