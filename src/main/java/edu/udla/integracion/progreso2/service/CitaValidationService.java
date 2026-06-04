package edu.udla.integracion.progreso2.service;

import org.springframework.stereotype.Service;

import edu.udla.integracion.progreso2.model.CitaRequest;

@Service
public class CitaValidationService {

    public void validar(CitaRequest cita) {

        if (cita.getIdCita() == null || cita.getIdCita().isBlank()) {
            throw new IllegalArgumentException("idCita es obligatorio");
        }

        if (cita.getPaciente() == null || cita.getPaciente().isBlank()) {
            throw new IllegalArgumentException("paciente es obligatorio");
        }

        if (cita.getCorreo() == null || cita.getCorreo().isBlank()) {
            throw new IllegalArgumentException("correo es obligatorio");
        }

        if (cita.getEspecialidad() == null || cita.getEspecialidad().isBlank()) {
            throw new IllegalArgumentException("especialidad es obligatoria");
        }

        if (cita.getFechaCita() == null || cita.getFechaCita().isBlank()) {
            throw new IllegalArgumentException("fechaCita es obligatoria");
        }

        if (cita.getSede() == null || cita.getSede().isBlank()) {
            throw new IllegalArgumentException("sede es obligatoria");
        }

        if (cita.getValor() == null || cita.getValor() <= 0) {
            throw new IllegalArgumentException("valor debe ser mayor a 0");
        }
    }
}