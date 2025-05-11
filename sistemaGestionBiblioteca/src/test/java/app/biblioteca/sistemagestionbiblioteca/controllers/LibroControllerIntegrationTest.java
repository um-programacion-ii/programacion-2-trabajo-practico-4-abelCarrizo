package app.biblioteca.sistemagestionbiblioteca.controllers;

import app.biblioteca.sistemagestionbiblioteca.exceptions.GlobalExceptionHandler;
import app.biblioteca.sistemagestionbiblioteca.exceptions.LibroNotFoundException;
import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.models.EstadoLibro;
import app.biblioteca.sistemagestionbiblioteca.services.LibroService;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LibroControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private LibroService libroService;

    @InjectMocks
    private LibroController libroController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(libroController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void obtenerTodos_statusOk_yJsonArray() throws Exception {
        Libro l1 = new Libro(1L, "111", "T1", "A1", EstadoLibro.DISPONIBLE);
        Libro l2 = new Libro(2L, "222", "T2", "A2", EstadoLibro.PRESTADO);
        when(libroService.obtenerTodos()).thenReturn(Arrays.asList(l1, l2));

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].isbn").value("111"))
                .andExpect(jsonPath("$[1].estado").value("PRESTADO"));
    }

    @Test
    public void crear_createsAndReturnsJson() throws Exception {
        Libro toCreate = new Libro(null, "333", "T3", "A3", EstadoLibro.EN_REPARACION);
        Libro created = new Libro(3L, "333", "T3", "A3", EstadoLibro.EN_REPARACION);
        when(libroService.guardar(any(Libro.class))).thenReturn(created);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.titulo").value("T3"));
    }

    @Test
    public void obtenerPorIsbn_noExiste_notFound() throws Exception {
        // Simular excepcion específica para que GlobalExceptionHandler devuelva 404
        when(libroService.buscarPorIsbn("5")).thenThrow(new LibroNotFoundException(5L));

        mockMvc.perform(get("/api/libros/5"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void actualizar_actualizaYRetornaJson() throws Exception {
        Libro updated = new Libro(1L, "111", "T1-Updated", "A1", EstadoLibro.DISPONIBLE);
        when(libroService.actualizar(eq(1L), any(Libro.class))).thenReturn(updated);

        mockMvc.perform(put("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("T1-Updated"));
    }

    @Test
    public void eliminar_eliminaYRetornaNoContent() throws Exception {
        doNothing().when(libroService).eliminar(1L);

        mockMvc.perform(delete("/api/libros/1"))
                .andExpect(status().isNoContent());
    }
}