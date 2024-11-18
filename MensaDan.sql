drop database mensadan;

create user Servidor with password 'servidor';
ALTER USER servidor WITH CREATEDB;

\c postgres servidor;

Create database mensadan;

\c mensadan

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS Usuario (
    ipUsuario VARCHAR PRIMARY KEY, -- Dirección IP como identificador único
    nombre VARCHAR NOT NULL, -- Nombre del usuario
    contraseña VARCHAR NOT NULL, -- Contraseña del usuario
    estado VARCHAR CHECK (estado IN ('en línea', 'desconectado')), -- Estado actual
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha de creación
);


-- Tabla de contactos
CREATE TABLE IF NOT EXISTS Contacto (
    idCont SERIAL PRIMARY KEY, -- Identificador único del contacto
    nombreCon VARCHAR, -- Nombre del contacto
    ipUsuario VARCHAR NOT NULL, -- Usuario que registra al contacto
    ipUsCont VARCHAR NOT NULL, -- IP del contacto registrado
    UNIQUE (ipUsuario, ipUsCont), -- Evita duplicados
    FOREIGN KEY (ipUsuario) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE, -- Vincula al usuario dueño del contacto
    FOREIGN KEY (ipUsCont) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE -- Vincula al usuario registrado como contacto
);


-- Tabla de conversaciones
CREATE TABLE IF NOT EXISTS Conversacion (
    idConv SERIAL PRIMARY KEY, -- Identificador único de la conversación
    ipUsuario1 VARCHAR NOT NULL, -- Usuario que inicia la conversación
    ipUsuario2 VARCHAR NOT NULL, -- Usuario receptor
    fechaInicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de inicio de la conversación
    FOREIGN KEY (ipUsuario1) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE, -- Vincula al usuario iniciador
    FOREIGN KEY (ipUsuario2) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE -- Vincula al usuario receptor
);


-- Tabla de mensajes
CREATE TABLE IF NOT EXISTS Mensaje (
    idMnj SERIAL PRIMARY KEY, -- Identificador único del mensaje
    idConv INT NOT NULL, -- Relacionado con una conversación
    ipUsuario VARCHAR NOT NULL, -- Usuario que envía el mensaje
    contenido TEXT NOT NULL, -- Contenido del mensaje
    fechaEnvio TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de envío del mensaje
    estado VARCHAR DEFAULT 'enviado' CHECK (estado IN ('enviado', 'recibido', 'leído')), -- Estado del mensaje
    FOREIGN KEY (idConv) REFERENCES Conversacion(idConv) ON DELETE CASCADE, -- Vincula a la conversación
    FOREIGN KEY (ipUsuario) REFERENCES Usuario(ipUsuario) ON DELETE CASCADE -- Vincula al usuario que envía el mensaje
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
ALTER TABLE Usuario
ADD COLUMN informacion TEXT DEFAULT '!Estoy utilizando MensaDan¡';
-----------------------------------------------
CREATE OR REPLACE VIEW VistaInformacionContacto AS
SELECT 
    C.nombreCon AS nombre_registrado,  -- Nombre que el usuario le asignó al contacto
    U.informacion AS informacion_contacto, -- Información pública del contacto
    U.ipUsuario AS ip_contacto,  -- IP del contacto
    C.ipUsuario AS ipUsuario    -- Añadir la IP del usuario (que se puede filtrar)
FROM 
    Contacto C
JOIN 
    Usuario U ON C.ipUsCont = U.ipUsuario;
---------------------------------


