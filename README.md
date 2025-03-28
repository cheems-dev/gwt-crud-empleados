# Sistema de Gestión de Empleados

Una aplicación web para la gestión de empleados construida con GWT, Java 8 y PostgreSQL.

## Requisitos Previos

- Java 8 JDK
- Maven
- Docker y Docker Compose

## Compilación y Ejecución de la Aplicación

1. Clona este repositorio:

   ```
   git clone https://github.com/cheems-dev/gwt-crud-empleados
   cd gwt-crud-empleados
   ```

2. Ejecuta el script de construcción:

   ```
   ./build.sh
   ```

   Esto realizará:

   - Compilación de la aplicación GWT
   - Construcción de una imagen Docker para la aplicación web
   - Inicio de los contenedores de PostgreSQL y la aplicación web

3. Accede a la aplicación en http://localhost:8080

## Pasos de Construcción Manual

Si prefieres construir y ejecutar la aplicación manualmente:

1. Compila la aplicación:

   ```
   mvn clean package
   ```

2. Construye e inicia los contenedores Docker:
   ```
   docker-compose up --build -d
   ```

## Detener la Aplicación

Para detener la aplicación y eliminar los contenedores y volúmenes:

```
docker-compose down -v
```

## Estructura del Proyecto

### 📁 Cliente (GWT)

```
src/main/java/com/example/client/
├── EmployeeManagement.java     # Punto de entrada principal de la aplicación
├── EmployeeService.java        # Interfaz del servicio RPC
└── EmployeeServiceAsync.java   # Versión asíncrona de la interfaz
```

### 📁 Servidor

```
src/main/java/com/example/server/
├── EmployeeDAO.java            # Acceso a datos para operaciones de BD
└── EmployeeServiceImpl.java    # Implementación del servicio RPC
```

### 📁 Compartido

```
src/main/java/com/example/shared/
└── Employee.java               # Modelo de datos para empleados
```

### 📁 Recursos

```
src/main/resources/
└── db/
    └── init.sql                # Script SQL para inicializar la BD
```

### 📁 Aplicación Web

```
src/main/webapp/
├── EmployeeManagement.html     # Página HTML principal
├── EmployeeManagement.css      # Estilos CSS
└── WEB-INF/
    └── web.xml                 # Configuración de la aplicación web
```

### 📁 Docker

```
├── docker-compose.yml          # Configuración de Docker Compose
└── Dockerfile                  # Definición de la imagen Docker
```
