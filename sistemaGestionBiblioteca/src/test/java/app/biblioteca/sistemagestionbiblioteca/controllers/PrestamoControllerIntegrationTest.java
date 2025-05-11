package app.biblioteca.sistemagestionbiblioteca.controllers;

import app.biblioteca.sistemagestionbiblioteca.exceptions.GlobalExceptionHandler;
import app.biblioteca.sistemagestionbiblioteca.exceptions.PrestamoNotFoundException;
import app.biblioteca.sistemagestionbiblioteca.models.Prestamo;
import app.biblioteca.sistemagestionbiblioteca.models.Libro;
import app.biblioteca.sistemagestionbiblioteca.models.Usuario;
import app.biblioteca.sistemagestionbiblioteca.services.PrestamoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PrestamoControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private PrestamoService prestamoService;

    @InjectMocks
    private PrestamoController prestamoController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void obtenerTodos_statusOk_yJsonArray() throws Exception {
        Libro libro = new Libro(1L, "x", "t", "a", null);
        Usuario usuario = new Usuario(1L, "u", "e", null);
        Prestamo p = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(1));
        when(prestamoService.obtenerTodos()).thenReturn(Collections.singletonList(p));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void crear_prestamoCreado_statusCreated() throws Exception {
        // Datos de prueba
        Libro libro = new Libro(1L, "y", "t2", "a2", null);
        Usuario usuario = new Usuario(2L, "u2", "e2", null);
        Prestamo created = new Prestamo(2L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(2));

        // Configurar el mock
        when(prestamoService.guardar(any(Prestamo.class))).thenReturn(created);

        // Ejecutar la prueba - usar una cadena JSON simplificada sin los campos de fecha
        String requestJson = "{\"id\":null,\"libro\":{\"id\":1,\"isbn\":\"y\",\"titulo\":\"t2\",\"autor\":\"a2\"},\"usuario\":{\"id\":2,\"nombre\":\"u2\",\"email\":\"e2\"}}";

        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));

        // Verificar que el servicio fue llamado
        verify(prestamoService).guardar(any(Prestamo.class));
    }

    @Test
    public void obtenerPorId_noExiste_notFound() throws Exception {
        when(prestamoService.buscarPorId(eq(9L))).thenThrow(new PrestamoNotFoundException(9L));

        mockMvc.perform(get("/api/prestamos/9"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void actualizar_actualizaYRetornaJson() throws Exception {
        // Datos de prueba
        Libro libro = new Libro(1L, "x", "t", "a", null);
        Usuario usuario = new Usuario(1L, "u", "e", null);
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = LocalDate.now().plusDays(3);
        Prestamo updated = new Prestamo(1L, libro, usuario, fechaPrestamo, fechaDevolucion);

        // Configurar el mock
        when(prestamoService.actualizar(eq(1L), any(Prestamo.class))).thenReturn(updated);

        // Crear capturador para verificar el objeto Prestamo pasado al servicio
        ArgumentCaptor<Prestamo> prestamoCaptor = ArgumentCaptor.forClass(Prestamo.class);

        // Ejecutar la prueba - usar una cadena JSON simplificada
        String requestJson = "{\"id\":1,\"libro\":{\"id\":1,\"isbn\":\"x\",\"titulo\":\"t\",\"autor\":\"a\"},\"usuario\":{\"id\":1,\"nombre\":\"u\",\"email\":\"e\"}}";

        mockMvc.perform(put("/api/prestamos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // Verificar que el servicio fue llamado
        verify(prestamoService).actualizar(eq(1L), prestamoCaptor.capture());

        // Verificar contenido básico del objeto capturado
        assertEquals(1L, prestamoCaptor.getValue().getLibro().getId());
        assertEquals("x", prestamoCaptor.getValue().getLibro().getIsbn());
    }

    @Test
    public void eliminar_eliminaYRetornaNoContent() throws Exception {
        doNothing().when(prestamoService).eliminar(1L);

        mockMvc.perform(delete("/api/prestamos/1"))
                .andExpect(status().isNoContent());
    }
}
