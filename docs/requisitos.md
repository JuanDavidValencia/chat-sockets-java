# Proyecto Chat con Sockets

## Objetivo

Crear un chat que permita la comunicación entre múltiples usuarios conectados desde distintos navegadores, inspirado en WhatsApp y Discord.

## Tecnologías

- Java
- Spring Boot
- SQL
- React

## Requerimientos funcionales

### Usuario

RF-01: El sistema debe permitir registrar usuarios mediante su información personal relevante, tales como nombre, teléfono, correo electrónico y contraseña.
RF-02: El sistema debe permitir a los usuarios iniciar sesión mediante correo y contraseña.
RF-03: El sistema debe permitir actualizar la información de un usuario mediante su identificador.
RF-04: El sistema debe permitir buscar usuarios registrados mediante su nombre, teléfono o correo electrónico.
RF-05: El sistema debe permitir cambiar el estado de una cuenta de usuario entre ACTIVO e INACTIVO para indicar si la cuenta se encuentra habilitada o deshabilitada.
RF-06: El sistema debe permitir desactivar una cuenta conservando la información del usuario mediante eliminación lógica.

### Conversación

RF-07: El sistema debe permitir crear una conversación entre usuarios que puede ser tanto de tipo GLOBAL o PRIVADA.
RF-08: El sistema debe permitir buscar una conversación en específico a través de su identificador.
RF-09: El sistema debe permitir listar todas las conversaciones que un usuario ha tenido.
RF-10: El sistema debe permitir eliminar una conversación de la lista de conversaciones de un usuario mediante su identificador.

### Mensaje

RF-11: El sistema debe permitir enviar mensajes entre usuarios tanto de tipo GLOBAL como PRIVADA.
RF-12: El sistema debe permitir editar el contenido de un mensaje a partir de su identificador.
RF-13: El sistema debe permitir consultar el historial de mensajes de una conversación mediante su identificador.
RF-14: El sistema debe permitir responder al mensaje enviado por otro usuario a través del identificador del mensaje original.
RF-15: El sistema debe permitir eliminar mensajes enviados, ya sea para todos los participantes de la conversación o únicamente para el usuario que realiza la eliminación.

### Contacto

RF-16: El sistema debe permitir crear un contacto asociado a un usuario, permitiendo asignarle un nombre personalizado.
RF-17: El sistema debe permitir buscar un contacto en específico de su lista a través del nombre con el que lo creó.
RF-18: El sistema debe permitir editar el nombre del contacto que ha creado a través de su identificador.
RF-19: El sistema debe permitir eliminar un contacto a través de su identificador.

### Conectividad

RF-20: El sistema debe registrar la fecha y hora de la última desconexión de un usuario para indicar cuándo utilizó la aplicación por última vez.


## Requerimientos No Funcionales

### Seguridad

RNF-01: El sistema debe almacenar las contraseñas de los usuarios utilizando funciones de hash seguras, evitando su almacenamiento en texto plano dentro de la base de datos.
RNF-02: El sistema debe verificar las credenciales de los usuarios mediante la comparación segura de la contraseña ingresada con el hash almacenado en la base de datos, sin exponer la contraseña original.
RNF-03: El sistema debe garantizar que únicamente el propietario de una cuenta pueda modificar su información personal mediante mecanismos de autenticación y autorización.
RNF-04: El sistema debe garantizar la integridad de la información almacenada, evitando la pérdida permanente de datos mediante mecanismos de eliminación lógica.
RNF-05: El sistema debe garantizar que las conversaciones privadas solo sean accesibles por los usuarios participantes, mientras que las conversaciones globales deben estar disponibles para los usuarios autorizados.
RNF-06: El sistema debe restringir la búsqueda de conversaciones privadas únicamente a los usuarios que formen parte de ellas.
RNF-07: El sistema debe garantizar que los usuarios únicamente puedan acceder a las conversaciones en las que tengan participación autorizada, independientemente de si son de tipo PRIVADA o GLOBAL.
RNF-08: El sistema debe garantizar la integridad y persistencia de la información almacenada, evitando la eliminación accidental de conversaciones o mensajes de otros usuarios.
RNF-09: El sistema debe garantizar que únicamente los usuarios autenticados y autorizados puedan acceder a los endpoints protegidos.


### Rendimiento

RNF-10: El sistema debe responder a las peticiones realizadas por el usuario desde la aplicación cliente sin demoras que afecten de manera significativa la experiencia de usuario.
RNF-11: El sistema debe procesar el envío de mensajes entre usuarios sin latencia perceptible, permitiendo una conversación asíncrona fluida entre el remitente y el destinatario.



## MVP

- Registro
- Login
- Chat global
- Envío de mensajes
- Historial de mensajes
- Persistencia en SQL
- Comunicación entre usuarios

## Roadmap

### Versión 1

- Chats privados
- Hora de envío
- Estado de conexión

### Versión 2

- Editar mensajes
- Eliminar para mí
- Eliminar para todos

### Versión 3

- Búsqueda de mensajes
- Gestión de contactos

## Entidades principales

- Usuario
- Conversación
- Mensaje
- Contacto

## Reglas de negocio

### Chat

- Existe un chat global.
- Existen chats privados.
- Todos los usuarios pueden comunicarse.

### Mensajes

- Los mensajes se almacenan al enviarse.
- El autor puede editar sus mensajes.
- El autor puede eliminar un mensaje para todos.
- Cualquier usuario puede eliminar un mensaje para sí mismo.

### Conectividad

- Los usuarios pueden recibir mensajes aunque hayan estado desconectados.

## Estado del proyecto

🚧 En fase de diseño y construcción.

Actualmente se cuenta con:

- Diagrama Entidad-Relación (DER).
- Modelo relacional.
- Planificación del MVP.
- Definición de tecnologías.

## Documentación

La documentación adicional se encuentra en la carpeta `docs/`.

## Autor

Juan David Valencia Padilla
