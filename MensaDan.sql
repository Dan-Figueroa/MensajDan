drop database mensadan;

create user Servidor with password 'servidor';
ALTER USER servidor WITH CREATEDB;

\c postgres servidor;

Create database mensadan;

\c mensadan

CREATE TABLE IF NOT EXISTS Usuario (
    ipUsuario VARCHAR PRIMARY KEY,
    nombre VARCHAR NOT NULL,
    contraseña VARCHAR NOT NULL,
    estado VARCHAR CHECK (estado IN ('en línea', 'desconectado')),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Contacto (
    idCont SERIAL PRIMARY KEY,
    nombreCon VARCHAR,
    ipUsuario VARCHAR NOT NULL,--ESTE ES EL ID DEL USUARIO QUE VA A DAR DE ALTA A SU CONTACTO
    ipUsCont VARCHAR NOT NULL,--IP DEL CONTACO DEL USUARIO DE ARRIBA 
    UNIQUE (ipUsuario, ipUsCont),
    FOREIGN KEY (ipUsuario) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE,
    FOREIGN KEY (ipUsCont) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Conversacion (
    idConv SERIAL PRIMARY KEY,
    tipo VARCHAR NOT NULL, --personal o en grupo
    fechaInicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Participante (
    idPart SERIAL PRIMARY KEY,
    idConv INT NOT NULL,
    ipUsuario VARCHAR NOT NULL,
    fechaEntrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idConv) REFERENCES Conversacion(idConv) ON DELETE CASCADE,
    FOREIGN KEY (ipUsuario) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Mensaje (
    idMnj SERIAL PRIMARY KEY,
    idConv INT NOT NULL,
    ipUsuario VARCHAR NOT NULL,
    contenido TEXT,
    fechaEnvio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR DEFAULT 'enviado',
    FOREIGN KEY (idConv) REFERENCES Conversacion(idConv) ON DELETE CASCADE,
    FOREIGN KEY (ipUsuario) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE
);

--------------------------------------------------------------
CREATE OR REPLACE VIEW VistaNombresContactos AS
SELECT 
    c.ipUsuario AS ipUsuarioPrincipal,
    c.nombreCon AS nombreContacto
FROM 
    Contacto c
JOIN 
    Usuario u ON c.ipUsCont = u.ipUsuario;
-------------------------------------------------------------------
