# Spring API Consumer — ViaCEP

A simple Spring Boot REST API that looks up Brazilian postal codes (CEP) using the [ViaCEP](https://viacep.com.br/) public API.

## Tech Stack

- **Java 17**
- **Spring Boot 2.6.6**
- **RestTemplate** — for consuming the external ViaCEP API
- **Lombok** — to reduce boilerplate in model classes
- **JUnit 5 + MockMvc** — for integration tests

## Running the Application

```bash
./mvnw spring-boot:run
```

The server starts on port **8889**.

## Endpoint

### GET `/api/cep/{zipCode}`

Looks up address information for the given CEP.

**Example request:**
```
GET http://localhost:8889/api/cep/88020231
```

**Success response — 200 OK:**
```json
{
  "cep": "88020-231",
  "street": "Rua Marechal Guilherme",
  "complement": "",
  "neighborhood": "Centro",
  "city": "Florianópolis",
  "state": "SC"
}
```

## Error Handling

| Scenario | HTTP Status | Description |
|---|---|---|
| CEP not found | `404 Not Found` | Valid format but no address registered |
| Invalid CEP format | `400 Bad Request` | CEP with wrong length or non-numeric characters |

**Error response body:**
```json
{
  "error": "CEP not found: 88140000"
}
```

## Running the Tests

```bash
./mvnw test
```

The integration tests cover 3 valid CEPs (200) and 3 invalid ones (404 / 400) using `MockMvc` with a mocked `CepService`, so no real HTTP calls are made during testing.
