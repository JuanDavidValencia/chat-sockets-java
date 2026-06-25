\# Proyecto Chat con Sockets



\## Objetivo



Crear un chat que permita la comunicación entre múltiples usuarios

conectados desde distintos navegadores, inspirado en WhatsApp y Discord.



\## Tecnologías



\- Java

\- Spring Boot

\- SQL

\- React



\## MVP



\- Registro

\- Login

\- Chat global

\- Envío de mensajes

\- Historial de mensajes

\- Persistencia en SQL

\- Comunicación entre usuarios



\## Roadmap



\### Versión 1



\- Chats privados

\- Hora de envío

\- Estado de conexión



\### Versión 2



\- Editar mensajes

\- Eliminar para mí

\- Eliminar para todos



\### Versión 3



\- Búsqueda de mensajes

\- Gestión de contactos



\## Entidades principales



\- Usuario

\- Conversación

\- Mensaje

\- Contacto



\## Reglas de negocio



\### Chat



\- Existe chat global.

\- Existen chats privados.

\- Todos los usuarios pueden comunicarse.



\### Mensajes



\- Los mensajes se almacenan al enviarse.

\- El autor puede editar sus mensajes.

\- El autor puede eliminar para todos.

\- Cualquier usuario puede eliminar un mensaje para sí mismo.



\### Conectividad



\- Los usuarios pueden recibir mensajes aunque hayan estado desconectados.

