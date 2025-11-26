# Mutant Detector API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen)
![Coverage](https://img.shields.io/badge/Coverage-92%25-success)
![Status](https://img.shields.io/badge/Status-Completed-blue)

## Descripción del Proyecto

Magneto quiere reclutar la mayor cantidad de mutantes para luchar contra los X-Men. [cite_start]Este proyecto es una API REST desarrollada en **Spring Boot** que detecta si un humano es mutante basándose en su secuencia de ADN[cite: 2, 3].

El sistema verifica secuencias de ADN en una matriz de (NxN). [cite_start]Se considera mutante si se encuentra **más de una secuencia de cuatro letras iguales** de forma oblicua, horizontal o vertical[cite: 13].

## Despliegue en la Nube (Demo en Vivo)

La API se encuentra desplegada y disponible para pruebas en **Render**:

**Base URL:** `[TU_URL_DE_RENDER]`
**Swagger UI (Documentación Interactiva):** `[TU_URL_DE_RENDER]/swagger-ui.html`

---

## Tecnologías y Arquitectura

[cite_start]El proyecto sigue una arquitectura de **6 capas** para garantizar escalabilidad y mantenimiento[cite: 112]:

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3
* [cite_start]**Base de Datos:** H2 Database (En memoria) para persistencia de ADNs verificados[cite: 61].
* **Testing:** JUnit 5 + Mockito + MockMvc.
* **Herramientas:** Gradle, Lombok, Swagger/OpenAPI.

### [cite_start]Optimizaciones Implementadas [cite: 17, 92]
Para manejar tráfico agresivo y cumplir con la eficiencia solicitada:
1.  **Early Termination:** El algoritmo se detiene inmediatamente al encontrar más de 1 secuencia.
2.  [cite_start]**Hashing (SHA-256):** Se guarda el hash del ADN en la base de datos para evitar re-procesar ADNs ya analizados (Caché de BD)[cite: 195].
3.  **Matriz de Caracteres:** Conversión eficiente a `char[][]` para lectura rápida.
4.  **Validaciones Fail-Fast:** Se rechazan peticiones inválidas antes de procesar lógica de negocio.

---

## Guía de Uso de la API

### 1. Detectar Mutante (`POST /mutant`)

Envía una secuencia de ADN para su verificación.

* **URL:** `/mutant`
* **Método:** `POST`
* **Body (JSON):**

```json
{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
````

* [cite\_start]**Respuestas:** [cite: 53, 54, 55]
    * `200 OK`: Es un **Mutante**.
    * `403 Forbidden`: Es un **Humano**.
    * `400 Bad Request`: ADN inválido (caracteres erróneos, matriz no cuadrada o nula).

**Ejemplo cURL:**

```bash
curl -X POST [TU_URL_DE_RENDER]/mutant \
-H "Content-Type: application/json" \
-d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'
```

### 2\. Obtener Estadísticas (`GET /stats`)

Devuelve las estadísticas de las verificaciones realizadas.

* **URL:** `/stats`
* **Método:** `GET`
* [cite\_start]**Respuesta (JSON):** [cite: 30, 66]

<!-- end list -->

```json
{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
```

-----

## Ejecución Local

Si deseas correr el proyecto en tu entorno local:

1.  **Clonar el repositorio:**

    ```bash
    git clone [https://github.com/TU_USUARIO/mutantes-api.git](https://github.com/TU_USUARIO/mutantes-api.git)
    cd mutantes-api
    ```

2.  **Ejecutar la aplicación (con Gradle):**

    ```bash
    ./gradlew bootRun
    ```

    La API estará disponible en `http://localhost:8080`.

3.  **Ver Base de Datos (H2 Console):**

    * URL: `http://localhost:8080/h2-console`
    * JDBC URL: `jdbc:h2:mem:mutantesdb`
    * User: `sa`
    * Password: (vacío)

-----

## Ejecución de Tests y Cobertura

[cite\_start]El proyecto cuenta con una cobertura de código superior al **90%**, superando el requisito del 80%[cite: 31].

Para ejecutar los tests y generar el reporte:

```bash
./gradlew test jacocoTestReport
```

El reporte detallado se generará en: `build/reports/jacoco/test/html/index.html`

-----
