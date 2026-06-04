package edu.udla.integracion.progreso2.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import edu.udla.integracion.progreso2.config.RabbitMQConfig;
import edu.udla.integracion.progreso2.model.CitaRequest;

@Service
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarEventoCita(CitaRequest cita) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.APPOINTMENTS_EXCHANGE,
                "",
                cita);
    }
}