# Case Study 1

The first case study showcasing a web app built (movie ticket reservation system) to demonstrate microservice design patterns.

- Starting the services with Docker Compose:

  ```bash
  ./run-services.sh --clean
  ```

- For local testing of the backend API, run the requests in `.http` files under `./api-gateway-service/src/main/resources/http/*`.

- The following ports on the host machine need to be free:

  - 8099 (API gateway)
  - 8761 (Eureka server)

- Cleaning up after shutting services down:

  ```bash
  docker-compose down -v
  ```

- Load testing:
  - Plans and results: `./jmeter/`
  - Analysis: `./notebooks/`
