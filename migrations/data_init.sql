USE english_academy;

-- =========================
-- INSERT: Alumno
-- =========================
INSERT INTO Alumnos (
    nombre,
    apellido,
    dni,
    email,
    telefono
)
VALUES (
    'Joaquin',
    'Ijalba',
    35706793
    'joaquinijalba@gmail.com',
    '3434045828'
);

-- =========================
-- INSERT: Profesor
-- =========================
INSERT INTO Profesores (
    nombre,
    apellido,
    dni,
    email
)
VALUES (
    'Martin',
    'Lopez',
    30123456,
    'mlopez@academy.com'
);

-- =========================
-- INSERT: Aula
-- =========================
INSERT INTO Aulas (
    nombre,
    capacidad
)
VALUES (
    'Aula A',
    25
);

-- =========================
-- INSERT: Ciclo Lectivo
-- =========================
INSERT INTO CicloLectivos (
    fecha_inicio,
    fecha_fin,
    estado
)
VALUES (
    '2026-03-01',
    '2026-12-15',
    'ACTVO'
);

-- =========================
-- INSERT: Curso
-- =========================
INSERT INTO Cursos (
    id_profesor,
    id_aula,
    id_ciclo_lectivo,
    nombre,
    nivel,
    descripcion,
    diaSemana,
    horaInicio,
    horaFin
)
VALUES (
    1,
    1,
    1,
    'English B1',
    'Intermedio',
    'Curso nivel intermedio',
    'LUN',
    '18:00:00',
    '20:00:00'
);

-- =========================
-- INSERT: Matricula
-- =========================
INSERT INTO Matriculas (
    id_alumno,
    id_curso,
    fecha_inscripcion,
    estado
)
VALUES (
    1,
    1,
    CURRENT_DATE,
    'ACTVO'
);