package edu.udla.integracion.progreso2.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarAFacturacion(Object mensaje) {

        rabbitTemplate.convertAndSend(
                "billing.queue",
                mensaje);
    }
}