    CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);



CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    password_reset VARCHAR(255) NULL,
    rol_id INT NOT NULL,
    FOREIGN KEY(rol_id) REFERENCES roles(id)
 );



CREATE TABLE personas (
    ci VARCHAR(20) NOT NULL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido_pat VARCHAR(255) NOT NULL,
    apellido_mat VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NULL,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    fecha_nacimiento DATE NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
    

 );
CREATE TABLE estudiantes (
    id SERIAL PRIMARY KEY,
    personas_ci VARCHAR(20) NOT NULL,
    FOREIGN KEY(personas_ci) REFERENCES personas(ci)

  
 );
CREATE TABLE administrativos (
    id SERIAL PRIMARY KEY,
    personas_ci VARCHAR(20) NOT NULL,
    FOREIGN KEY(personas_ci) REFERENCES personas(ci) 
);

CREATE TABLE docentes (
    id SERIAL PRIMARY KEY,
    personas_ci VARCHAR(20) NOT NULL,
    FOREIGN KEY(personas_ci) REFERENCES personas(ci)
);



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

CREATE TABLE grupos (
    sigla VARCHAR(10) NOT NULL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    materia_sigla VARCHAR(255)  NOT NULL,
    carrera_sigla VARCHAR(255)  NOT NULL,
    docente_id  INT NOT NULL,
     FOREIGN KEY(docente_id) REFERENCES docentes(id),
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

CREATE TABLE grupos_horarios (
    grupo_sigla VARCHAR(10) NOT NULL,
    horario_id INT NOT NULL,
    dia_id INT NOT NULL,
    PRIMARY KEY(grupo_sigla,horario_id),
    FOREIGN KEY(dia_id)  REFERENCES dias(id),
     FOREIGN KEY(grupo_sigla) REFERENCES grupos(sigla),
     FOREIGN KEY(horario_id) REFERENCES horarios(id)
 );

CREATE TABLE notas (
    id SERIAL PRIMARY KEY,
    estudiante_id INT NOT NULL,
    docente_id INT NOT NULL,
    grupo_sigla VARCHAR(10) NOT NULL,
    nota_final DECIMAL(5,2),
    FOREIGN KEY(docente_id) REFERENCES docentes(id),
    FOREIGN KEY(estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY(grupo_sigla) REFERENCES grupos(sigla)
);

CREATE TABLE inscripciones (
    id SERIAL PRIMARY KEY,
    estudiante_id INT NOT NULL, 
    grupo_sigla VARCHAR(10) NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY(estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY(grupo_sigla) REFERENCES grupos(sigla)
 );

CREATE TABLE pagos (
    id SERIAL PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    fecha DATE NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    inscripcion_id INT NOT NULL, 
    FOREIGN KEY(inscripcion_id) REFERENCES inscripciones(id)
 
 );
 CREATE TABLE egresos (
    id SERIAL PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    fecha DATE NOT NULL,
    concepto VARCHAR(255) NOT NULL,
    gestion_codigo INT NOT NULL,
    FOREIGN KEY(gestion_codigo) REFERENCES gestiones(codigo)
 
 );

--  DATOS
-- Datos de roles
INSERT INTO roles (nombre) VALUES
('Admin'),
('Docente'),
('Estudiante');

-- Datos de usuarios
INSERT INTO usuarios (email, password, password_reset, rol_id) VALUES
('titocarlos080@gmail.com', '123', NULL, 1),
('admin@tecnicaeta.com', 'adminpassword', NULL, 1),
('docente1@tecnicaeta.com', 'docentepassword1', NULL, 2),
('docente2@tecnicaeta.com', 'docentepassword2', NULL, 2),
('estudiante1@tecnicaeta.com', 'estudiantepassword1', NULL, 3),
('estudiante2@tecnicaeta.com', 'estudiantepassword2', NULL, 3);

-- Datos de personas
INSERT INTO personas (ci, nombre, apellido_pat, apellido_mat, telefono, sexo, fecha_nacimiento, usuario_id) VALUES
('12345678', 'Juan', 'Perez', 'Gomez', '123456789', 'M', '1980-05-15', 1),
('23456789', 'Maria', 'Lopez', 'Diaz', '234567890', 'F', '1985-06-20', 2),
('34567890', 'Pedro', 'Garcia', 'Martinez', '345678901', 'M', '1990-07-25', 3),
('45678901', 'Ana', 'Rodriguez', 'Fernandez', '456789012', 'F', '1995-08-30', 4),
('56789012', 'Luis', 'Gonzalez', 'Sanchez', '567890123', 'M', '2000-09-05', 5);

-- Datos de estudiantes
INSERT INTO estudiantes (personas_ci) VALUES
('45678901'),
('56789012');

-- Datos de administrativos
INSERT INTO administrativos (personas_ci) VALUES
('12345678');

-- Datos de docentes
INSERT INTO docentes (personas_ci) VALUES
('23456789'),
('34567890');

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
INSERT INTO grupos (sigla, descripcion, materia_sigla, carrera_sigla, docente_id) VALUES
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

-- Datos de grupos_horarios
INSERT INTO grupos_horarios (grupo_sigla, horario_id, dia_id) VALUES
('G1', 1, 1),
('G2', 2, 2),
('G3', 3, 3);

-- Datos de inscripciones
INSERT INTO inscripciones (estudiante_id, grupo_sigla, fecha) VALUES
(1, 'G1', '2024-02-01'),
(2, 'G2', '2024-02-01');

-- Datos de pagos
INSERT INTO pagos (monto, fecha, concepto, inscripcion_id) VALUES
(500.00, '2024-02-15', 'Pago de Inscripcion', 1),
(500.00, '2024-02-15', 'Pago de Inscripcion', 2);

-- Datos de notas
INSERT INTO notas (estudiante_id, docente_id, grupo_sigla, nota_final) VALUES
(1, 1, 'G1', 85.50),
(2, 2, 'G2', 90.00);

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
