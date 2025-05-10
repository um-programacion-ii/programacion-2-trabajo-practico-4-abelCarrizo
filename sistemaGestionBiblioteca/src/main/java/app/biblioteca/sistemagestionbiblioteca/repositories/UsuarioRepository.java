package app.biblioteca.sistemagestionbiblioteca.repositories;

import app.biblioteca.sistemagestionbiblioteca.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}

