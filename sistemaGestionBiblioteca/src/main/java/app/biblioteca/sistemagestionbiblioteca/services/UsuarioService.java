package app.biblioteca.sistemagestionbiblioteca.services;

import app.biblioteca.sistemagestionbiblioteca.models.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario buscarPorId(Long id);
    List<Usuario> obtenerTodos();
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuario);
}
