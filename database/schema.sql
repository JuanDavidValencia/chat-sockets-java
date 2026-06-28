CREATE DATABASE IF NOT EXISTS ChatSockets
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;

USE ChatSockets;

CREATE TABLE Usuario (
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre TEXT NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(30),
    user_password VARCHAR(20),
    fecha_registro DATE
);

CREATE TABLE Contacto (
	id_contacto INT AUTO_INCREMENT PRIMARY KEY,
    contacto_id INT NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario)
    REFERENCES Usuario(id_usuario),
    FOREIGN KEY (contacto_id)
	REFERENCES Usuario(id_usuario)
);

CREATE TABLE Conversacion (
	id_conversacion INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(15),
    fecha_creacion DATE
);


CREATE TABLE Participa (
	id_usuario INT NOT NULL,
    id_conversacion INT,
    PRIMARY KEY (id_usuario, id_conversacion),
    FOREIGN KEY (id_usuario)
	REFERENCES Usuario(id_usuario),
	FOREIGN KEY (id_conversacion)
	REFERENCES Conversacion(id_conversacion)
);

CREATE TABLE Mensaje (
	id_mensaje INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    hora TIME,
    contenido TEXT,
    remitente_id INT,
    estado VARCHAR(20),
    id_conversacion INT,
    FOREIGN KEY (id_conversacion)
    REFERENCES Conversacion(id_conversacion),
    FOREIGN KEY (remitente_id)
	REFERENCES Usuario(id_usuario)
);




