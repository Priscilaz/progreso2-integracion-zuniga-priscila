package edu.udla.integracion.progreso2.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import edu.udla.integracion.progreso2.model.CitaRequest;

@Service
public class AuditService {

    private static final String CSV_FILE = "data/outbox/auditoria-citas.csv";

    public void registrarCita(CitaRequest cita) throws IOException {

        File file = new File(CSV_FILE);

        file.getParentFile().mkdirs();

        boolean nuevoArchivo = !file.exists();

        try (FileWriter writer = new FileWriter(file, true)) {

            if (nuevoArchivo) {
                writer.append("idCita,paciente,correo,especialidad,fechaCita,sede,valor\n");
            }

            writer.append(cita.getIdCita()).append(",");
            writer.append(cita.getPaciente()).append(",");
            writer.append(cita.getCorreo()).append(",");
            writer.append(cita.getEspecialidad()).append(",");
            writer.append(cita.getFechaCita()).append(",");
            writer.append(cita.getSede()).append(",");
            writer.append(String.valueOf(cita.getValor())).append("\n");
        }
    }
}