# Progreso 2 - Integración de Sistemas

## 1. Nombre del estudiante

Priscila Zúñiga

---

## 2. Descripción breve de la solución

La solución implementa un sistema de integración para el registro de citas médicas utilizando Spring Boot, Apache Camel y RabbitMQ.

Cuando una cita es registrada mediante una API REST, la información es validada y distribuida a diferentes sistemas utilizando patrones de integración. Las solicitudes de facturación son enviadas mediante Point-to-Point, mientras que las notificaciones y analítica reciben eventos mediante Publish/Subscribe. Además, se generan archivos de auditoría y registros de errores para garantizar la trazabilidad de la información.

---

## 3. Tecnologías utilizadas

* Java 17
* Spring Boot
* Apache Camel
* RabbitMQ
* Maven
* Docker
* Postman
* CSV
* VS Code

---

## 4. Instrucciones para levantar RabbitMQ

Ubicarse en la raíz del proyecto y ejecutar:

```bash
docker compose up -d
```

Verificar que RabbitMQ esté disponible en:

Panel de administración:

http://localhost:15672

Credenciales:

Usuario:

admin

Contraseña:

admin

---

## 5. Instrucciones para ejecutar la aplicación

Compilar el proyecto:

```bash
mvn clean install
```

Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

La API quedará disponible en:

```text
http://localhost:8087
```

---

## 6. Endpoint disponible

### Registrar cita

```http
POST /api/citas
```

URL completa:

```text
http://localhost:8087/api/citas
```

---

## 7. Ejemplo de request válido

```json
{
  "idCita": "CITA-1001",
  "paciente": "Ana Torres",
  "correo": "ana.torres@email.com",
  "especialidad": "Cardiologia",
  "fechaCita": "2026-06-15",
  "sede": "Centro Norte",
  "valor": 45.50
}
```

Respuesta esperada:

```text
Cita recibida y enviada al flujo de integración
```

---

## 8. Ejemplo de request inválido

```json
{
  "idCita": "",
  "paciente": "Ana Torres",
  "correo": "",
  "especialidad": "Cardiologia",
  "fechaCita": "2026-06-15",
  "sede": "Centro Norte",
  "valor": -10
}
```

Respuesta esperada:

```text
Mensaje de validación indicando el error encontrado.
```

---

## 9. Explicación de los patrones utilizados

### Point-to-Point

Se aplica mediante la cola:

```text
billing.queue
```

Cada solicitud de facturación debe ser procesada una única vez por un único consumidor, evitando duplicidades en la generación de facturas.

### Publish/Subscribe

Se aplica mediante el exchange:

```text
appointments.events
```

Los eventos son distribuidos a:

```text
notifications.queue
analytics.queue
```

permitiendo que varios sistemas reciban simultáneamente la misma información.

### Transferencia de archivos

Se aplica mediante la generación del archivo:

```text
data/outbox/auditoria-citas.csv
```

Este archivo almacena las citas procesadas correctamente y permite mantener una auditoría del sistema.

### Manejo de errores

Las solicitudes inválidas son registradas en:

```text
data/errors/citas-rechazadas.log
```

permitiendo mantener trazabilidad de los errores detectados durante la validación.

---

## 10. Evidencia esperada para verificar el funcionamiento

Para verificar el correcto funcionamiento de la solución se debe comprobar:

1. La API inicia correctamente.
2. Se puede registrar una cita válida mediante Postman.
3. La API devuelve una respuesta exitosa.
4. Se generan mensajes en la cola `billing.queue`.
5. Los eventos son distribuidos a `notifications.queue` y `analytics.queue`.
6. Se genera el archivo `data/outbox/auditoria-citas.csv`.
7. Las solicitudes inválidas generan registros en `data/errors/citas-rechazadas.log`.
