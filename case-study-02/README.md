## Case Study 2

The second case study showcasing a web app (movie ticket reservation system) built to demonstrate microservice design patterns.

- Starting services with Docker Compose:

  ```bash
  ./run-services.sh --clean
  ```

- For local testing of the backend API, run the requests in `.http` files under `./broker-service/src/main/resources/http/*`.

- The following ports on the host machine need to be free:
  - 3000 (Grafana)
  - 5672 (RabbitMQ)
  - 8099 (Broker service)
  - 9090 (Prometheus)
  - 15672 (RabbitMQ Management)
  - 15692 (RabbitMQ Prometheus)

### RabbitMQ Management

Management UI: http://localhost:15672
Credentials: `guest:guest`

### Prometheus

UI: http://localhost:9090

### Grafana

UI: http://localhost:3000
Credentials: `admin:admin` (skip password reset when prompted)
