# English Academy — Sistema de Gestión Académica
# Trabajo Práctico Univesidad Siglo XXI
# SEMINARIO DE PRÁCTICA DE INFORMATICA

## Descripción

Este proyecto es un sistema integral de gestión académica para la academia de Inglés English Academy. 
Permite administrar el ciclo completo de operaciones académicas: 
[] Gestión de alumnos, matriculaciones 
[] Profesores
[] Cursos 
[] Aulas (WIP)

El sistema fue desarrollado como prototipo operacional aplicando el Proceso Unificado de Desarrollo (PUD) y patrones de diseño estándar para la industria. 
Cada módulo funciona de manera independiente pero integrado, permitiendo evolucionar hacia un sistema completo sin rediseño arquitectónico.

## Tecnologías

| Componente | Versión | Justificación |
|-----------|---------|---------------|
| **Java** | 21 | Lenguaje principal del proyecto; versión LTS moderna con mejoras en módulos y records. |
| **JavaFX** | 21.0.6 | Framework de UI moderno, nativo y multiplataforma. Reemplaza Swing para interfaces contemporáneas. |
| **MySQL** | 8.0+ | Base de datos relacional estándar. Uso de JDBC 9.3.0 como driver directo. |
| **Gradle** | 9.3.0 | Build tool moderno que simplifica gestión de dependencias y compilación. |

## Arquitectura

El sistema sigue el patrón **Model-View-Controller (MVC)**:

```
src/main/java/com/englishacademy/
├── models/              # Entidades del dominio y servicios de negocio
│   ├── Persona.java     # Clase abstracta base (herencia)
│   ├── Alumno.java
│   ├── Profesor.java
│   ├── Curso.java
│   ├── Aula.java
│   ├── Matriculacion.java
│   └── services/        # Capa de servicios con lógica de persistencia
│       ├── AlumnoService.java
│       ├── ProfesorService.java
│       ├── CursoService.java
│       ├── AulaService.java
│       └── MatriculacionService.java
├── controllers/         # Controladores de pantallas (binding con FXML)
│   ├── MenuViewController.java
│   ├── DashboardViewController.java
│   ├── alumnos/
│   ├── cursos/
│   ├── profesores/
│   └── aulas/
├── views/              # Archivos FXML (UI layout)
│   ├── dashboard-view.fxml
│   ├── alumnos/
│   ├── cursos/
│   ├── profesores/
│   └── shared/
├── infra/              # Infraestructura técnica
│   └── DatabaseConnection.java  # Singleton JDBC
├── utils/              # Utilidades y helpers
│   ├── SceneUtil.java
│   ├── AlertUtil.java
│   ├── ValidacionesHelper.java
│   ├── JdbcHelper.java          # Helpers para JDBC
│   └── ContextoApp.java         # Estado compartido entre vistas
└── Main.java           # Punto de entrada
```

### Decisiones Arquitectónicas

**1. Singleton para Services**
Cada servicio (AlumnoService, CursoService, etc.) es un Singleton. Esto garantiza una única instancia en memoria y acceso centralizado a la lógica de persistencia. 
Se inicializa de forma lazy la primera vez que se solicita.

```java
public static AlumnoService getInstance() {
    if (instancia == null) {
        instancia = new AlumnoService();
    }
    return instancia;
}
```

**2. Persistencia con JDBC Directo (sin DAO)**
Se implementó JDBC directamente en los servicios sin capa intermedia de DAO/Repository.

**3. Clase Abstracta Persona**
`Alumno` y `Profesor` heredan de `Persona` para reutilizar propiedades comunes (nombre, apellido, DNI, email). Cumple requisito de aplicar herencia y polimorfismo.

Los servicios retornan `List<T>` para permitir operaciones funcionales. Arrays se usan internamente cuando se necesita tamaño fijo.

**4. Manejo de Excepciones Diferenciado**
- `SQLException` → `RuntimeException` (problemas infraestructura/BD)
- `IllegalArgumentException` (validación de negocio, dato no encontrado)

Esto permite que el controlador distinga errores recuperables (mostrar alerta) de fallos críticos.

## Instalación y Ejecución

### Requisitos Previos
- **Java 21+**: Descargar desde [oracle.com](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- **MySQL 8.0+**: Servidor local ejecutándose en `localhost:3306`
- **Git**: Para clonar el repositorio

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/english-academy.git
   cd english-academy
   ```

2. **Crear la base de datos**
   ```bash
   mysql -u root -p < migrations/schema.sql
   ```
   Esto crea la base de datos `english_academy` con todas las tablas.

3. **Configurar credenciales de BD**
   Editar `src/main/resources/db.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/english_academy?useSSL=false&serverTimezone=UTC
   db.user=root
   db.password=tu_contraseña
   ```

4. **Compilar el proyecto**
   ```bash
   ./gradlew compileJava
   ```

5. **Ejecutar la aplicación**
   ```bash
   ./gradlew run
   ```

### Verificación de Instalación
- La aplicación abrirá en una ventana JavaFX
- Pantalla de login: usuario/contraseña (credenciales de prueba: `admin@englishacademy.com.ar` / `admin123`)
- Dashboard mostrará conteos reales de alumnos, cursos y profesores desde la BD

## Patrones de Diseño

### Singleton
**Uso**: `DatabaseConnection`, todos los Services  
**Beneficio**: Instancia única, control centralizado, eficiencia de recursos

### Model-View-Controller (MVC)
- **Model**: Clases de entidad + servicios (lógica de persistencia)
- **View**: Archivos FXML (layout y estructura)
- **Controller**: Clases `*ViewController` (lógica de interacción)

### Try-with-Resources
Todos los `PreparedStatement` y `ResultSet` se envuelven en try-with-resources para cierre automático:
```java
try (PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setInt(1, id);
    try (ResultSet rs = ps.executeQuery()) {
        // procesar
    }
} catch (SQLException e) {
    throw new RuntimeException("Error al consultar", e);
}
```

**Nota**: La `Connection` NO se cierra (Singleton estatica gestionada por `DatabaseConnection`).

## Base de Datos

### Tablas Principales

**alumnos**
```sql
id INT PRIMARY KEY AUTO_INCREMENT
nombre VARCHAR(100) NOT NULL
apellido VARCHAR(100) NOT NULL
dni INT UNIQUE NOT NULL
email VARCHAR(100)
telefono VARCHAR(20)
```

**profesores**
```sql
id INT PRIMARY KEY AUTO_INCREMENT
nombre VARCHAR(100) NOT NULL
apellido VARCHAR(100) NOT NULL
dni INT UNIQUE NOT NULL
email VARCHAR(100) NOT NULL
```

**cursos**
```sql
id INT PRIMARY KEY AUTO_INCREMENT
nombre VARCHAR(100) NOT NULL
diaSemana VARCHAR(20)
horaInicio TIME
horaFin TIME
id_profesor INT, FOREIGN KEY (id_profesor) REFERENCES profesores(id)
id_aula INT, FOREIGN KEY (id_aula) REFERENCES aulas(id)
id_ciclo_lectivo INT, FOREIGN KEY (id_ciclo_lectivo) REFERENCES ciclos_lectivos(id)
```

**matriculas**
```sql
id_alumno INT, FOREIGN KEY (id_alumno) REFERENCES alumnos(id)
id_curso INT, FOREIGN KEY (id_curso) REFERENCES cursos(id)
fecha_inscripcion DATE
estado VARCHAR(50)
PRIMARY KEY (id_alumno, id_curso)
```

**aulas**
```sql
id INT PRIMARY KEY AUTO_INCREMENT
nombre VARCHAR(100) NOT NULL
capacidad INT
```

### Migraciones

Los archivos en `migrations/` aplican cambios incrementales:
- `db_init.sql`: Instacia todas las tablas.
- `data_init.sql`: Crea los primeros datos y popula las tablas principales (Para prueba)
- `fix_fk_and_values.sql`: Fixea diferentes propiedades que se corrigieron durante las pruebas de la plataforma.

Aplicar manualmente con MySQL.

## Flujo de Datos

```
Usuario → Controller (ViewController) 
          ↓
        Validación (ValidacionesHelper)
          ↓
        Service.registrar/modificar/eliminar
          ↓
        JDBC → MySQL
          ↓
        Service retorna List o boolean
          ↓
        Controller actualiza TableView/Label
          ↓
        Usuario ve cambios
```

### Ejemplo: Registro de Alumno
1. Usuario rellena formulario en `AlumnoRegistroViewController`
2. Controller valida con `ValidacionesHelper` (email, DNI, etc.)
3. Llama `AlumnoService.getInstance().registrar(alumno)`
4. Service prepara SQL INSERT con PreparedStatement
5. MySQL genera auto-increment id
6. Service asigna id al objeto con `alumno.setIdAlumno(...)`
7. Controller recarga tabla con `alumnoService.obtenerTodos()`
8. TableView muestra nuevo alumno

## Decisiones Técnicas Importantes

### Por qué ArrayList en servicios
- Necesidad de retornar colecciones dinámicas
- Soporte a operaciones funcionales (filter, map, etc. si es necesario)
- Separación clara: BD vs. transferencia de datos

### Por qué exception handling diferenciado
- `IllegalArgumentException` para errores lógicos (validación, no encontrado)
- `RuntimeException` para fallos de infraestructura (BD, conexión)
- Permite que Controllers distingan y manejen adecuadamente

### Por qué ContextoApp (estado compartido - globales)
Almacena estado transitorio entre pantallas:
- Página actual (para menu active state)
- Alumno/Curso seleccionado (para operaciones de detalle)
- Evita pasar parámetros complejos entre Controllers

## Recursos

- [JavaFX 21 Documentation](https://openjfx.io/javadoc/21/)
- [MySQL JDBC Driver](https://dev.mysql.com/downloads/connector/j/)
- [Design Patterns - Refactoring Guru](https://refactoring.guru/design-patterns)

---

**Fecha de desarrollo**: Marzo-Junio 2026  
**Estado**: Entregable: Prototipo operacional — Curso "Seminario de Práctica", Universidad Siglo 21
