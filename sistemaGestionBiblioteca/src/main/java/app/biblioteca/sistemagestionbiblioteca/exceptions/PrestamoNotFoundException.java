package app.biblioteca.sistemagestionbiblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrestamoNotFoundException extends RuntimeException {
    public PrestamoNotFoundException(Long id) {
        super("Préstamo no encontrado con ID: " + id);
    }
}

