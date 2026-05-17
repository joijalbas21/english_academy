CREATE DATABASE IF NOT EXISTS english_academy
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
 
USE english_academy;

-- =========================
-- TABLA: Alumnos
-- =========================
CREATE TABLE Alumnos (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    dni INT NOT NULL,
    email VARCHAR(50),
    telefono VARCHAR(20),

    PRIMARY KEY (id)
);

-- =========================
-- TABLA: Profesores
-- =========================
CREATE TABLE Profesores (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    dni INT NOT NULL,
    email VARCHAR(50),

    PRIMARY KEY (id)
);

-- =========================
-- TABLA: Aulas
-- =========================
CREATE TABLE Aulas (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    capacidad INT,

    PRIMARY KEY (id)
);

-- =========================
-- TABLA: CicloLectivos
-- =========================
CREATE TABLE CicloLectivos (
    id INT NOT NULL AUTO_INCREMENT,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado VARCHAR(5) NOT NULL,

    PRIMARY KEY (id)
);

-- =========================
-- TABLA: Cursos
-- =========================
CREATE TABLE Cursos (
    id INT NOT NULL AUTO_INCREMENT,

    id_profesor INT NOT NULL,
    id_aula INT NOT NULL,
    id_ciclo_lectivo INT NOT NULL,

    nombre VARCHAR(20) NOT NULL,
    nivel VARCHAR(10) NOT NULL,
    descripcion VARCHAR(50),
    diaSemana VARCHAR(3),
    horaInicio TIME,
    horaFin TIME,

    PRIMARY KEY (id),

    CONSTRAINT fk_cursos_profesores
        FOREIGN KEY (id_profesor)
        REFERENCES Profesores(id),

    CONSTRAINT fk_cursos_aulas
        FOREIGN KEY (id_aula)
        REFERENCES Aulas(id),

    CONSTRAINT fk_cursos_ciclolectivos
        FOREIGN KEY (id_ciclo_lectivo)
        REFERENCES CicloLectivos(id)
);

-- =========================
-- TABLA: Matriculas
-- =========================
CREATE TABLE Matriculas (
    id_alumno INT NOT NULL,
    id_curso INT NOT NULL,
    fecha_inscripcion DATE,
    estado VARCHAR(5),

    PRIMARY KEY (id_alumno, id_curso),

    CONSTRAINT fk_matriculas_alumnos
        FOREIGN KEY (id_alumno)
        REFERENCES Alumnos(id),

    CONSTRAINT fk_matriculas_cursos
        FOREIGN KEY (id_curso)
        REFERENCES Cursos(id)
);