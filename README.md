# Carbon Relay Integration

## Getting Started

This project uses Docker Compose for local development and Gradle for building and running the application.

---

## Docker Setup

### 1. Start Docker Containers
- **Linux/macOS:**
  ```sh
  docker compose -p carbonrelay up -d
  ```
- **Windows (PowerShell):**
  ```powershell
  docker compose -p carbonrelay up -d
  ```

### 2. MySQL Initialization
- **Linux/macOS:**
  ```sh
  mkdir -p ./docker/mysql
  touch ./docker/mysql/init-db.sql
  chmod 400 ./docker/mysql/init-db.sql
  ```
- **Windows (PowerShell):**
  ```powershell
  New-Item -ItemType Directory -Force -Path ./docker/mysql
  New-Item -ItemType File -Force -Path ./docker/mysql/init-db.sql
  icacls ./docker/mysql/init-db.sql /inheritance:r /grant:r "$($env:USERNAME):(R)"
  ```

---

## Podman Users
If you are using Podman instead of Docker, you can use the following command:

- **Linux/macOS:**
  ```sh
  podman-compose -p carbonrelay up -d
  ```
- **Windows (PowerShell):**
  ```powershell
  podman-compose -p carbonrelay up -d
  ```

The rest of the setup steps (MySQL initialization) remain the same.

---

## Gradle Commands

- **Build the project:**
  - Linux/macOS:
    ```sh
    ./gradlew build
    ```
  - Windows (PowerShell):
    ```powershell
    .\gradlew.bat build
    ```

- **Compile the project:**
  - Linux/macOS:
    ```sh
    ./gradlew compileKotlin
    ```
  - Windows (PowerShell):
    ```powershell
    .\gradlew.bat compileKotlin
    ```

- **Run the application:**
  - Linux/macOS:
    ```sh
    ./gradlew run
    ```
  - Windows (PowerShell):
    ```powershell
    .\gradlew.bat run
    ```

---

## Notes
- Ensure Docker or Podman is installed and running on your system.
- The MySQL init script permissions are required for proper container operation.
- For any issues, check the logs of the respective containers or Gradle output.
