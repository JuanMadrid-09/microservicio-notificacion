package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.dto.NotificacionDTO;
import co.analisys.biblioteca.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notificar")
@Tag(name = "Notificaciones", description = "API para gestión de notificaciones del sistema")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @Operation(
        summary = "Enviar notificación",
        description = "Envía una notificación a un usuario específico. " +
                    "Requiere autenticación y rol de LIBRARIAN o USER."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación enviada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado - Rol insuficiente")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_USER')")
    public ResponseEntity<String> enviarNotificacion(
            @Parameter(description = "Datos de la notificación a enviar", required = true)
            @RequestBody NotificacionDTO notificacion) {
        notificacionService.enviarNotificacion(notificacion);
        return ResponseEntity.ok("Notificación enviada exitosamente");
    }

    @Operation(
        summary = "Estado del servicio",
        description = "Endpoint público que permite verificar el estado del servicio de notificaciones. " +
                    "No requiere autenticación."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Servicio funcionando correctamente",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(type = "string")
            )
        )
    })
    @GetMapping("/public/status")
    public ResponseEntity<String> getPublicStatus() {
        return ResponseEntity.ok("El servicio de notificaciones está funcionando correctamente");
    }

    @Operation(
        summary = "Información del servicio",
        description = "Endpoint público que proporciona información básica del servicio. " +
                    "No requiere autenticación."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Información del servicio obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "object")
            )
        )
    })
    @GetMapping("/public/info")
    public ResponseEntity<Object> getServiceInfo() {
        return ResponseEntity.ok(Map.of(
            "service", "Notificación Service",
            "version", "1.0.0",
            "description", "Microservicio para gestión de notificaciones del sistema",
            "status", "ACTIVE",
            "endpoints", List.of(
                "POST /notificar - Enviar notificación (ROLE_LIBRARIAN/ROLE_USER)",
                "GET /notificar/public/status - Estado del servicio (PÚBLICO)",
                "GET /notificar/public/info - Información del servicio (PÚBLICO)"
            ),
            "roles", List.of(
                "ROLE_LIBRARIAN - Acceso completo a envío de notificaciones",
                "ROLE_USER - Acceso a envío de notificaciones"
            )
        ));
    }
}