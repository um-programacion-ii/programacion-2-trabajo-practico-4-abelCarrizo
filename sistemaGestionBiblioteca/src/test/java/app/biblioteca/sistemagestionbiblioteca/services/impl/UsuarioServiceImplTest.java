package app.biblioteca.sistemagestionbiblioteca.services.impl;

import app.biblioteca.sistemagestionbiblioteca.exceptions.UsuarioNotFoundException;
import app.biblioteca.sistemagestionbiblioteca.models.EstadoUsuario;
import app.biblioteca.sistemagestionbiblioteca.models.Usuario;
import app.biblioteca.sistemagestionbiblioteca.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Nombre", "email@dominio.com", EstadoUsuario.ACTIVO);
    }

    @Test
    void buscarPorId_existente_retornaUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.buscarPorId(1L);
        assertSame(usuario, result);
    }

    @Test
    void buscarPorId_noExistente_lanzaExcepcion() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> usuarioService.buscarPorId(2L));
    }

    @Test
    void guardar_usuario_llamaSave() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario saved = usuarioService.guardar(usuario);
        assertEquals(usuario, saved);
    }
}
