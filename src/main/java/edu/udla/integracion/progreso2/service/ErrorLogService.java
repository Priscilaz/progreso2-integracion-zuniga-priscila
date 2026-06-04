package edu.udla.integracion.progreso2.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class ErrorLogService {

    private static final String LOG_FILE =
            "data/errors/citas-rechazadas.log";

    public void registrarError(String mensaje) {

        try {

            File file = new File(LOG_FILE);

            file.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(file, true)) {

                writer.write(
                        LocalDateTime.now()
                        + " - "
                        + mensaje
                        + System.lineSeparator());
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}