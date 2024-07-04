
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    ruta VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE roles_menus (
    id SERIAL PRIMARY KEY,
    rol_id INT NOT NULL,
    menu_id INT NOT NULL,
    FOREIGN KEY(rol_id) REFERENCES roles(id),
    FOREIGN KEY(menu_id) REFERENCES menus(id)
);


CREATE TABLE tematicas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    password_reset VARCHAR(255) NULL,
    rol_id INT NOT NULL,
    tematica_id INT NOT NULL,
    FOREIGN KEY(rol_id) REFERENCES roles(id),
    FOREIGN KEY(tematica_id) REFERENCES tematicas(id)
 );


CREATE TABLE estudiantes (
    ci VARCHAR(20) NOT NULL PRIMARY KEY,
    nombre VARCHAR(255)   NULL,
    apellido_pat VARCHAR(255)   NULL,
    apellido_mat VARCHAR(255)   NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255) NULL,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    fecha_nacimiento VARCHAR NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
    
 );




CREATE TABLE administrativos (
    ci VARCHAR(20) NOT NULL PRIMARY KEY,
    nombre VARCHAR(255)   NULL,
    apellido_pat VARCHAR(255)   NULL,
    apellido_mat VARCHAR(255)   NULL,
    telefono VARCHAR(255) NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    fecha_nacimiento VARCHAR NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
  
);

CREATE TABLE docentes (
    ci VARCHAR(20) NOT NULL PRIMARY KEY,
    nombre VARCHAR(255)   NULL,
    apellido_pat VARCHAR(255)   NULL,
    apellido_mat VARCHAR(255)   NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    kardex VARCHAR(255) NULL,
    curriculum VARCHAR(255) NULL,
     usuario_id INT NOT NULL, -- Agregado usuario_id
     FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
);
 

-- ///////////////////////////////////////////////////////////
-- ///////////////////////////////////////////////////////////
CREATE OR REPLACE FUNCTION insertar_usuario(
    p_email VARCHAR,
    p_password VARCHAR,
    p_rol_id INT,
    p_tematica_id INT
)
RETURNS INT AS $$
DECLARE
    v_usuario_id INT;
BEGIN
    -- Insertar un nuevo usuario en la tabla usuarios
    INSERT INTO usuarios (email, password, rol_id, tematica_id)
    VALUES (p_email, p_password, p_rol_id, p_tematica_id)
    RETURNING id INTO v_usuario_id;

    RETURN v_usuario_id;
END;
$$ LANGUAGE plpgsql;


-- ///////////////////////////////////////////////////////////

CREATE OR REPLACE FUNCTION insertar_estudiante(
    p_ci VARCHAR,
    p_nombre VARCHAR,
    p_apellido_pat VARCHAR,
    p_apellido_mat VARCHAR,
    p_email VARCHAR,
    p_telefono VARCHAR,
    p_sexo CHAR,
    p_fecha_nacimiento VARCHAR
)
RETURNS INT AS $$
DECLARE
    v_usuario_id INT;
 BEGIN
    -- Insertar un nuevo usuario y obtener su ID
    v_usuario_id := insertar_usuario(p_email, p_ci, 3, 1);  -- Ajustar rol_id y tematica_id según sea necesario

    -- Insertar un nuevo estudiante en la tabla estudiantes
    INSERT INTO estudiantes (ci, nombre, apellido_pat, apellido_mat, email, telefono, sexo, fecha_nacimiento, usuario_id)
    VALUES (p_ci, p_nombre, p_apellido_pat, p_apellido_mat, p_email, p_telefono, p_sexo, p_fecha_nacimiento, v_usuario_id);

    RETURN v_usuario_id;
END;
$$ LANGUAGE plpgsql;
-- //////////////////////////////////////////////////////////
CREATE OR REPLACE FUNCTION insertar_docente(
    p_ci VARCHAR,
    p_nombre VARCHAR,
    p_apellido_pat VARCHAR,
    p_apellido_mat VARCHAR,
    p_email VARCHAR,
    p_kardex VARCHAR,
    p_curriculum VARCHAR
)
RETURNS INT AS $$
DECLARE
    v_usuario_id INT;
 BEGIN
    -- Insertar un nuevo usuario y obtener su ID
    v_usuario_id := insertar_usuario(p_email, p_ci, 2, 1);  -- Ajustar rol_id y tematica_id según sea necesario

    -- Insertar un nuevo docente en la tabla docentes
    INSERT INTO docentes (ci, nombre, apellido_pat, apellido_mat, email, kardex, curriculum, usuario_id)
    VALUES (p_ci, p_nombre, p_apellido_pat, p_apellido_mat, p_email, p_kardex, p_curriculum, v_usuario_id);

    RETURN v_usuario_id;
END;
$$ LANGUAGE plpgsql;
-- //////////////////////////////////////////////////////////

CREATE OR REPLACE FUNCTION insertar_administrativo(
    p_ci VARCHAR,
    p_nombre VARCHAR,
    p_apellido_pat VARCHAR,
    p_apellido_mat VARCHAR,
    p_email VARCHAR,
    p_telefono VARCHAR,
    p_sexo CHAR,
    p_fecha_nacimiento VARCHAR
)
RETURNS INT AS $$
DECLARE
    v_usuario_id INT;
 BEGIN
    -- Insertar un nuevo usuario y obtener su ID
    v_usuario_id := insertar_usuario(p_email, p_ci, 1, 1);  -- Ajustar rol_id y tematica_id según sea necesario

    -- Insertar un nuevo administrativo en la tabla administrativos
    INSERT INTO administrativos (ci, nombre, apellido_pat, apellido_mat, telefono, email, sexo, fecha_nacimiento, usuario_id)
    VALUES (p_ci, p_nombre, p_apellido_pat, p_apellido_mat, p_telefono, p_email, p_sexo, p_fecha_nacimiento, v_usuario_id);

    RETURN v_usuario_id;
END;
$$ LANGUAGE plpgsql;

-- ////////////////////////////////////////////////////////////////////


-- ///////////### TRIGGER  ###/////////////////////

CREATE TABLE gestiones (
    codigo SERIAL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    fecha_inicio DATE ,
    fecha_fin DATE
 );

CREATE TABLE carreras (
    sigla VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    gestion_codigo INT NOT NULL,
    FOREIGN KEY(gestion_codigo) REFERENCES gestiones(codigo)
);
 

CREATE TABLE niveles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE materias (
    sigla VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    observacion VARCHAR(255) NOT NULL,
    creditos INT NOT NULL

);

CREATE TABLE carreras_materias (
    nivel_id INT NOT NULL,
    materia_sigla VARCHAR(255)  NOT NULL,
    carrera_sigla VARCHAR(255)  NOT NULL,
    PRIMARY KEY(materia_sigla,carrera_sigla),
    FOREIGN KEY(nivel_id) REFERENCES niveles(id),
    FOREIGN KEY(materia_sigla) REFERENCES materias(sigla),
    FOREIGN KEY(carrera_sigla) REFERENCES carreras(sigla)
);

CREATE TABLE estudiantes_carrera (
    id SERIAL PRIMARY KEY,
    fecha_inscripcion DATE NOT NULL,
    estudiante_ci VARCHAR(20)  NOT NULL,
    carrera_sigla VARCHAR(255)  NOT NULL,
     FOREIGN KEY(estudiante_ci) REFERENCES estudiantes(ci),
     FOREIGN KEY(carrera_sigla) REFERENCES carreras(sigla)
);


-- ////////////////////////////////////////////////////////////////////////////
CREATE TABLE grupos_materias (
    sigla VARCHAR(10) NOT NULL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    materia_sigla VARCHAR(255)  NOT NULL,
    carrera_sigla VARCHAR(255)  NOT NULL,
    docente_ci  VARCHAR(20) NOT NULL,
     FOREIGN KEY(docente_ci) REFERENCES docentes(ci),
     FOREIGN KEY(materia_sigla) REFERENCES materias(sigla),
    FOREIGN KEY(carrera_sigla) REFERENCES carreras(sigla)
 
 );
CREATE TABLE horarios (
    id SERIAL PRIMARY KEY,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL
);

CREATE TABLE dias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE grupo_materia_horarios ( 
    grupo_sigla VARCHAR(10) NOT NULL,
    horario_id INT NOT NULL,
    dia_id INT NOT NULL,
    PRIMARY KEY(grupo_sigla,horario_id),
    FOREIGN KEY(dia_id)  REFERENCES dias(id),
     FOREIGN KEY(grupo_sigla) REFERENCES grupos_materias(sigla),
     FOREIGN KEY(horario_id) REFERENCES horarios(id)
 );

CREATE TABLE estudiante_materia (
    id SERIAL PRIMARY KEY  NOT NULL,
    fecha DATE NOT NULL,
    grupos_materias_sigla VARCHAR(10) NOT NULL,
    estudiante_ci VARCHAR(20) NOT NULL,
     FOREIGN KEY(grupos_materias_sigla)  REFERENCES grupos_materias(sigla),
     FOREIGN KEY(estudiante_ci) REFERENCES estudiantes(ci)
  );


CREATE TABLE notas (
    id SERIAL PRIMARY KEY,
    nota_final NUMERIC(5, 2) NOT NULL CHECK (nota_final >= 1 AND nota_final <= 100),
    estudiante_materia_id INT NOT NULL, 
     FOREIGN KEY(estudiante_materia_id) REFERENCES estudiante_materia(id)
 );

 

CREATE TABLE pagos (
    id SERIAL PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    fecha DATE NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    estudiante_materia_id INT NOT NULL, 
     FOREIGN KEY(estudiante_materia_id) REFERENCES estudiante_materia(id)
 
 );
 CREATE TABLE egresos (
    id SERIAL PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    fecha DATE NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    gestion_codigo INT NOT NULL,
    FOREIGN KEY(gestion_codigo) REFERENCES gestiones(codigo)
 
 );

-- // GESTION DE OFERTAS, GENERACION DE OFERTAS

-- select g.descripcion, ca.descripcion,cm.materia_sigla,gm.descripcion,doc.nombre, dias.nombre ,h.hora_inicio, h.hora_fin
-- from  gestiones g,carreras ca,carreras_materias cm,grupos_materias gm,docentes doc,grupo_materia_horarios gmh,horarios h, dias
-- where g.codigo=ca.gestion_codigo and ca.sigla = cm.carrera_sigla and
-- gm.materia_sigla= cm.materia_sigla  and gm.docente_ci = doc.ci and gm.sigla = gmh.grupo_sigla and
-- gmh.horario_id=h.id and gmh.dia_id= dias.id

CREATE VIEW ofertas AS
SELECT 
    g.descripcion AS gestion_descripcion, 
    ca.descripcion AS carrera_descripcion,
    cm.materia_sigla,
    gm.descripcion AS grupo_materia_descripcion,
    doc.nombre AS docente_nombre,
    dias.nombre AS dia_nombre,
    h.hora_inicio,
    h.hora_fin
FROM  
    gestiones g
    JOIN carreras ca ON g.codigo = ca.gestion_codigo
    JOIN carreras_materias cm ON ca.sigla = cm.carrera_sigla
    JOIN grupos_materias gm ON gm.materia_sigla = cm.materia_sigla
    JOIN docentes doc ON gm.docente_ci = doc.ci
    JOIN grupo_materia_horarios gmh ON gm.sigla = gmh.grupo_sigla
    JOIN horarios h ON gmh.horario_id = h.id
    JOIN dias ON gmh.dia_id = dias.id;


/*
REPORTES

Egresos por Gestión


SELECT g.descripcion AS gestion, SUM(e.monto) AS total_egresos
FROM egresos e
JOIN gestiones g ON e.gestion_codigo = g.codigo
GROUP BY g.descripcion;

Ingresos por Gestión

SELECT g.descripcion AS gestion, SUM(p.monto) AS total_ingresos
FROM pagos p
JOIN estudiante_materia em ON p.estudiante_materia_id = em.id
JOIN estudiantes e ON em.estudiante_ci = e.ci
JOIN estudiantes_carrera ec ON e.ci = ec.estudiante_ci
JOIN gestiones g ON ec.carrera_sigla = g.codigo
GROUP BY g.descripcion;

Estudiantes por Carrera

SELECT c.descripcion AS carrera, COUNT(ec.estudiante_ci) AS num_estudiantes
FROM estudiantes_carrera ec
JOIN carreras c ON ec.carrera_sigla = c.sigla
GROUP BY c.descripcion;





*/



--  DATOS
-- Datos de roles
INSERT INTO roles (nombre) VALUES
('Admin'),
('Docente'),
('Estudiante');



INSERT INTO tematicas (nombre) VALUES
('Dia'),
('Noche'),
('Niño'),
('Joven'),
('Adulto');

SELECT insertar_administrativo(
    '123',     -- p_ci
    'Tito',           -- p_nombre
    'Carlos',          -- p_apellido_pat
    'Gutierrez',         -- p_apellido_mat
    'titocarlos080@gmail.com', -- p_email
    '123456789',      -- p_telefono
    'M',              -- p_sexo (F o M)
    '2000-01-15'      -- p_fecha_nacimiento
);
  

-- Datos de gestiones
INSERT INTO gestiones (descripcion, fecha_inicio, fecha_fin) VALUES
('Gestion 2024', '2024-01-01', '2024-12-31');

-- Datos de carreras
INSERT INTO carreras (sigla, descripcion, gestion_codigo) VALUES
('INF', 'Ingenieria Informatica', 1),
('ADM', 'Administracion de Empresas', 1);

-- Datos de niveles
INSERT INTO niveles (nombre) VALUES
('Basico'),
('Intermedio'),
('Avanzado');

-- Datos de materias
INSERT INTO materias (sigla, descripcion, observacion, creditos) VALUES
('MAT101', 'Matematicas Basicas', 'Sin observaciones', 3),
('INF101', 'Introduccion a la Informatica', 'Sin observaciones', 4),
('ADM101', 'Introduccion a la Administracion', 'Sin observaciones', 3);

-- Datos de carreras_materias
INSERT INTO carreras_materias (nivel_id, materia_sigla, carrera_sigla) VALUES
(1, 'MAT101', 'INF'),
(1, 'INF101', 'INF'),
(1, 'ADM101', 'ADM');

-- Datos de grupos
INSERT INTO grupos_materias (sigla, descripcion, materia_sigla, carrera_sigla, docente_id) VALUES
('G1', 'Grupo 1 de Matematicas', 'MAT101', 'INF', 1),
('G2', 'Grupo 2 de Informatica', 'INF101', 'INF', 2),
('G3', 'Grupo 3 de Administracion', 'ADM101', 'ADM', 2);

-- Datos de horarios
INSERT INTO horarios (hora_inicio, hora_fin) VALUES
('08:00:00', '10:00:00'),
('10:00:00', '12:00:00'),
('14:00:00', '16:00:00');

-- Datos de dias
INSERT INTO dias (nombre) VALUES
('Lunes'),
('Martes'),
('Miercoles'),
('Jueves'),
('Viernes');

 

 INSERT INTO egresos (monto, fecha, concepto, gestion_codigo) VALUES
(1500.50, '2023-01-15', 'Compra de materiales de oficina', 1),
(2500.75, '2023-02-20', 'Pago de servicios públicos', 1),
(3200.00, '2023-03-10', 'Reparación de equipos de computación', 1),
(4100.90, '2023-04-05', 'Mantenimiento del edificio', 1)
 






// eliminacion de tablas
DO $$ 
DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;
