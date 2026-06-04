package edu.udla.integracion.progreso2.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
     // Point-to-Point
    public static final String BILLING_QUEUE = "billing.queue";

    // Publish/Subscribe
    public static final String APPOINTMENTS_EXCHANGE = "appointments.events";

    public static final String NOTIFICATIONS_QUEUE = "notifications.queue";
    public static final String ANALYTICS_QUEUE = "analytics.queue";

    @Bean
    public Queue billingQueue() {
        return new Queue(BILLING_QUEUE);
    }

    @Bean
    public Queue notificationsQueue() {
        return new Queue(NOTIFICATIONS_QUEUE);
    }

    @Bean
    public Queue analyticsQueue() {
        return new Queue(ANALYTICS_QUEUE);
    }

    @Bean
    public FanoutExchange appointmentsExchange() {
        return new FanoutExchange(APPOINTMENTS_EXCHANGE);
    }

    @Bean
    public Binding notificationsBinding(
            Queue notificationsQueue,
            FanoutExchange appointmentsExchange) {

        return BindingBuilder
                .bind(notificationsQueue)
                .to(appointmentsExchange);
    }

    @Bean
    public Binding analyticsBinding(
            Queue analyticsQueue,
            FanoutExchange appointmentsExchange) {

        return BindingBuilder
                .bind(analyticsQueue)
                .to(appointmentsExchange);
    }
}
