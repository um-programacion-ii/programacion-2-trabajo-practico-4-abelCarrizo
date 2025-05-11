package app.biblioteca.sistemagestionbiblioteca.controllers;

import app.biblioteca.sistemagestionbiblioteca.exceptions.GlobalExceptionHandler;
import app.biblioteca.sistemagestionbiblioteca.exceptions.UsuarioNotFoundException;
import app.biblioteca.sistemagestionbiblioteca.models.Usuario;
import app.biblioteca.sistemagestionbiblioteca.models.EstadoUsuario;
import app.biblioteca.sistemagestionbiblioteca.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void obtenerTodos_statusOk_yJsonArray() throws Exception {
        Usuario u1 = new Usuario(1L, "User1", "u1@mail.com", EstadoUsuario.ACTIVO);
        when(usuarioService.obtenerTodos()).thenReturn(Collections.singletonList(u1));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("User1"));
    }

    @Test
    public void crear_usuarioCreado_statusCreated() throws Exception {
        Usuario toCreate = new Usuario(null, "NewUser", "new@mail.com", EstadoUsuario.INACTIVO);
        Usuario created = new Usuario(2L, "NewUser", "new@mail.com", EstadoUsuario.INACTIVO);
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(created);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("new@mail.com"));
    }

    @Test
    public void obtenerPorId_noExiste_notFound() throws Exception {
        when(usuarioService.buscarPorId(eq(10L))).thenThrow(new UsuarioNotFoundException(10L));

        mockMvc.perform(get("/api/usuarios/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void actualizar_actualizaYRetornaJson() throws Exception {
        Usuario updated = new Usuario(1L, "User1-Updated", "u1@mail.com", EstadoUsuario.ACTIVO);
        when(usuarioService.actualizar(eq(1L), any(Usuario.class))).thenReturn(updated);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("User1-Updated"));
    }

    @Test
    public void eliminar_eliminaYRetornaNoContent() throws Exception {
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
