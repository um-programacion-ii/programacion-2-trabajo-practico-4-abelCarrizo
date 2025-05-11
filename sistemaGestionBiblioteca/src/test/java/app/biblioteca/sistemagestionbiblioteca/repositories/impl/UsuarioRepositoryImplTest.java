package app.biblioteca.sistemagestionbiblioteca.repositories.impl;

import app.biblioteca.sistemagestionbiblioteca.models.EstadoUsuario;
import app.biblioteca.sistemagestionbiblioteca.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositoryImplTest {
    private UsuarioRepositoryImpl repo;
    private Usuario u1, u2;

    @BeforeEach
    void setUp() {
        repo = new UsuarioRepositoryImpl();
        u1 = new Usuario(null, "User1", "u1@dom.com", EstadoUsuario.ACTIVO);
        u2 = new Usuario(null, "User2", "u2@dom.com", EstadoUsuario.INACTIVO);
    }

    @Test
    void save_asignaIdYGuarda() {
        Usuario saved = repo.save(u1);
        assertNotNull(saved.getId());
        assertEquals("User1", saved.getNombre());
    }

    @Test
    void findById_existente() {
        Usuario saved = repo.save(u1);
        Optional<Usuario> found = repo.findById(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void findByEmail_existente() {
        repo.save(u2);
        Optional<Usuario> found = repo.findByEmail("u2@dom.com");
        assertTrue(found.isPresent());
        assertEquals(u2.getNombre(), found.get().getNombre());
    }

    @Test
    void findAllRetornaTodos() {
        repo.save(u1);
        repo.save(u2);
        List<Usuario> list = repo.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void deleteById_elimina() {
        Usuario saved = repo.save(u1);
        repo.deleteById(saved.getId());
        assertFalse(repo.existsById(saved.getId()));
    }

    @Test
    void existsById_funciona() {
        Usuario saved = repo.save(u1);
        assertTrue(repo.existsById(saved.getId()));
    }
}
