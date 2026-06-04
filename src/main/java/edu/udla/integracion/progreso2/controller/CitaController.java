package edu.udla.integracion.progreso2.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.udla.integracion.progreso2.model.CitaRequest;
import edu.udla.integracion.progreso2.service.AuditService;
import edu.udla.integracion.progreso2.service.CitaValidationService;
import edu.udla.integracion.progreso2.service.ErrorLogService;
import edu.udla.integracion.progreso2.service.EventPublisherService;
import edu.udla.integracion.progreso2.service.RabbitMQService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaValidationService validationService;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private EventPublisherService eventPublisherService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private ErrorLogService errorLogService;

    @PostMapping
    public ResponseEntity<?> registrarCita(
            @RequestBody CitaRequest cita) {

        try {

            validationService.validar(cita);

            // Apache Camel
            producerTemplate.sendBody(
                    "direct:citas",
                    cita);

            // RabbitMQ Point-to-Point
            rabbitMQService.enviarAFacturacion(cita);

            // RabbitMQ Publish/Subscribe
            eventPublisherService.publicarEventoCita(cita);

            // Auditoría CSV
            auditService.registrarCita(cita);

            return ResponseEntity.ok(
                    "Cita recibida y enviada al flujo de integración");

        } catch (Exception e) {

            errorLogService.registrarError(
                    e.getMessage());

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}