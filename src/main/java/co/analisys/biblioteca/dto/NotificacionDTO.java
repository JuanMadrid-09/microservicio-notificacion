package co.analisys.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para enviar notificaciones sobre operaciones de préstamo")
public class NotificacionDTO {
    @Schema(description = "ID del usuario que recibirá la notificación", example = "USR001", format = "string")
    private String usuarioId;
    
    @Schema(description = "Mensaje de la notificación", example = "Libro prestado: LIB001", format = "string")
    private String mensaje;
}
