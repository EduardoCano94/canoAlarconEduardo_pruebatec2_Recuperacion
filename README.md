# Sistema de Gestión de Turnos - Turnero

Este proyecto implementa un sistema de gestión de turnos/citas para una entidad gubernamental, permitiendo la administración de turnos y ciudadanos.

## Descripción

El sistema permite gestionar turnos para diferentes trámites gubernamentales, donde cada turno está asociado a un ciudadano específico.
Los turnos pueden estar en dos estados: "En espera" o "Ya atendido".

### Funcionalidades Principales

- Creación de nuevos turnos
- Asignación de ciudadanos a turnos
- Listado de turnos por fecha
- Filtrado de turnos por estado
- Sistema de autenticación (Login)

### Estructura del Menú

El sistema cuenta con un menú principal que incluye las siguientes opciones:
- **Inicio de sesión**: Gestión de usuario y contraseña
  ![image](https://github.com/user-attachments/assets/0c0c8c07-536f-4194-85c9-d735a2146db4)
- **Ciudadano**: Gestión de ciudadanos y registro de nuevos usuarios
  ![screencapture-localhost-8080-Prueba-Turnero-index-jsp-2025-02-21-00_20_12](https://github.com/user-attachments/assets/9b0faff0-a8b7-44f3-9e28-dace165ca1b2)
- **Trámite**: Administración de los diferentes tipos de trámites disponibles
![screencapture-localhost-8080-Prueba-Turnero-tramite-jsp-2025-02-21-00_21_35](https://github.com/user-attachments/assets/4871c050-2c5d-45b6-a944-0d1e15b68947)
- **Turno**: Gestión y seguimiento de turnos
![screencapture-localhost-8080-Prueba-Turnero-turno-jsp-2025-02-21-00_22_32 (1)](https://github.com/user-attachments/assets/d2410d19-a998-400c-854b-630e9a0ec3ee)


- **Cerrar Sesión**: Cada ventana lleva un boton para cerrar sesión desde la misma
## Diagrama de Clases
![Diagrama](https://github.com/user-attachments/assets/bef8f0e6-f49c-4b35-9d82-3d8e80b08add)
## Requisitos Técnicos

- Java EE 7 Web
- Apache Tomcat 9.0
- MySQL 8.0
- Maven

## Instalación y Ejecución

1. Clonar el repositorio e importar a NetBeans
2. Crear base de datos 'turnero' en MySQL
3. Modificar credenciales en `persistence.xml` si es necesario
4. Ejecutar el proyecto en Apache Tomcat
5. Acceder a `http://localhost:8080/turnero`
6. Iniciar sesión con:
   - **Usuario**: admin
   - **Contraseña**: 1234
 
## Tecnologías Utilizadas

- **Backend**: Java EE 7 Web + Servlets
- **Frontend**: JSP + JavaScript Vanilla + Bootstrap
- **Persistencia**: JPA (Hibernate)
- **Base de Datos**: MySQL
- **Servidor de Aplicaciones**: Apache Tomcat 9.0

## Estructura del Proyecto

Prueba_Turnero2/
├── Web Pages/
│   ├── META-INF/
│   ├── WEB-INF/
│   ├── ciudadano.jsp
│   ├── error.jsp
│   ├── index.jsp
│   ├── login.jsp
│   ├── styles.css
│   ├── tramite.jsp
│   └── turno.jsp
├── Source Packages/
│   ├── com.hackaboss.logica/
│   │   ├── Ciudadano.java
│   │   ├── ControladoraLogica.java
│   │   ├── Tramite.java
│   │   ├── Turno.java
│   │   └── Usuario.java
│   ├── com.hackaboss.persistencia/
│   ├── com.hackaboss.persistencia.excep/
│   └── com.hackaboss.servlets/
│       ├── CiudadanoSv.java
│       ├── LoginSv.java
│       ├── TestServlet.java
│       ├── TramiteSv.java
│       └── TurnoServlet.java
├── Test Packages/
├── Other Sources/
├── Generated Sources (annotations)/
├── Dependencies/
└── Java Dependencies/

## Supuestos

1. Un ciudadano puede tener múltiples turnos en un mismo día para diferentes trámites.
2. Los turnos no pueden ser eliminados, solo cambiados de estado.
3. La fecha de turno no puede ser anterior a la fecha actual.
4. El sistema no permite la modificación de datos del ciudadano una vez creado.


