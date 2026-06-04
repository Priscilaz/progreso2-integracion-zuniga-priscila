package edu.udla.integracion.progreso2.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CitaIntegrationRoute extends RouteBuilder {

    @Override
    public void configure() {

        from("direct:citas")
            .log("Cita recibida por Camel: ${body}");
    }
}