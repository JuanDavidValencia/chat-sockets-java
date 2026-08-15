# Chat Sockets Java

Proyecto de chat en tiempo real inspirado en WhatsApp y Discord.

## Objetivo

Desarrollar una aplicación web que permita la comunicación en tiempo real entre usuarios utilizando WebSockets, persistencia en base de datos y autenticación de usuarios.

## Tecnologías

* Java
* Spring Boot
* React
* SQL
* WebSockets
* Git

## Estado del proyecto

### Módulos implementados

- **Usuario**
  - Registro de usuarios.
  - Refactorización mediante DTOs.
  - Separación entre entidades de persistencia y objetos de transferencia.

- **Autenticación y autorización**
  - Login de usuarios.
  - Generación de tokens JWT.
  - Validación de tokens mediante Spring Security.
  - Protección de endpoints mediante autenticación.
  - Configuración de endpoints públicos y privados.

- **Contacto**
  - Gestión del módulo de contactos.

- **Conversación**
  - Creación y gestión básica de conversaciones.
  - Soporte para diferentes tipos de conversación.

- **Participa**
  - Relación entre usuarios y conversaciones.
  - Implementación mediante clave primaria compuesta.

- **Mensaje**
  - Envío de mensajes.
  - Consulta de mensajes por conversación.
  - Edición de mensajes.
  - Eliminación de mensajes.


### Próximas funcionalidades

- Implementación de comunicación en tiempo real mediante WebSockets.
- Integración de WebSockets con autenticación JWT.
- Chat global.
- Chats privados.
- Persistencia de mensajes enviados mediante WebSockets.
- Estado de conexión de los usuarios.
- Mejoras en la gestión de conversaciones y participantes.

## Funcionalidades MVP

* Registro de usuarios
* Inicio de sesión
* Chat global
* Envío de mensajes
* Historial de mensajes
* Persistencia en base de datos

## Funcionalidades futuras

### Versión 1

* Chats privados
* Estado de conexión
* Hora de envío

### Versión 2

* Editar mensajes
* Eliminar para mí
* Eliminar para todos

### Versión 3

* Gestión de contactos
* Búsqueda de mensajes

## Documentación

La documentación del proyecto se encuentra en la carpeta `docs/`.

## Autor

Juan David Valencia Padilla
