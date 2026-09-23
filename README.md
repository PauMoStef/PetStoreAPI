# PetStore API Demo

Proyecto Spring Boot que consume la API pública de [Swagger Petstore](https://petstore.swagger.io/) y expone endpoints propios con validación y manejo de errores.

## Requisitos

- Java 17+
- Gradle 9.x

## Ejecución

```bash
./gradlew bootRun
```

La aplicación levanta en `http://localhost:8080`.

## Endpoints

| Método | Ruta             | Descripción                         |
|--------|------------------|-------------------------------------|
| GET    | `/api/pet/{id}`  | Obtiene una mascota por su ID       |
| POST   | `/api/pet`       | Registra una nueva mascota          |

### Ejemplo POST `/api/pet`

```json
{
  "name": "Firulais",
  "status": "available",
  "category": { "id": 1, "name": "Dogs" },
  "photoUrls": [],
  "tags": []
}
```

### Respuesta POST

```json
{
  "name": "Firulais",
  "status": "available",
  "transactionId": "a1b2c3d4-...",
  "createdAt": "2026-09-23T09:45:12.345678"
}
```

## Tecnologías

- Spring Boot 4.1.1
- RestClient (HTTP)
- Jakarta Validation
- JUnit 5 + Mockito (testing)
