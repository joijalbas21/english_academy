package com.englishacademy.controllers.cursos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.Time;
import com.englishacademy.models.Aula;
import com.englishacademy.models.CicloLectivo;
import com.englishacademy.models.Curso;
import com.englishacademy.models.services.AulaService;
import com.englishacademy.models.services.CicloLectivoService;
import com.englishacademy.models.services.CursoService;
import com.englishacademy.utils.AlertUtil;
import com.englishacademy.utils.ContextoApp;
import com.englishacademy.utils.SceneUtil;

public class CursoRegistroViewController {

    @FXML
    private Label tituloLabel;

    @FXML
    private TextField nombreField;

    @FXML
    private ComboBox<String> nivelCombo;

    @FXML
    private ComboBox<String> diaCombo;

    @FXML
    private TextField horaInicioField;

    @FXML
    private TextField horaFinField;

    @FXML
    private TextArea descripcionField;

    @FXML
    private ComboBox<CicloLectivo> cicloCombo;

    @FXML
    private ComboBox<Aula> aulaCombo;

    @FXML
    private Label errorLabel;

    private CursoService cursoService;
    private CicloLectivoService cicloService;
    private AulaService aulaService;
    private Integer idEnEdicion;

    @FXML
    public void initialize() {
        cursoService = CursoService.getInstance();
        cicloService = CicloLectivoService.getInstance();
        aulaService = AulaService.getInstance();
        idEnEdicion = ContextoApp.getIdCursoEnEdicion();

        cargarNiveles();
        cargarDias();
        cargarAulas();
        cargarCiclos();

        if (idEnEdicion != null) {
            tituloLabel.setText("Editar Curso");
            cargarDatos();
        } else {
            tituloLabel.setText("Nuevo Curso");
        }
    }

    private void cargarCiclos() {
        cicloCombo.setItems(FXCollections.observableArrayList(cicloService.obtenerTodos()));
        if (!cicloCombo.getItems().isEmpty()) {
            cicloCombo.getSelectionModel().selectFirst();
        }
    }

    private void cargarNiveles() {
        nivelCombo.setItems(FXCollections.observableArrayList("Beginner", "Intermediate", "Expert"));
        nivelCombo.getSelectionModel().selectFirst();
    }

    private void cargarDias() {
        diaCombo.setItems(FXCollections.observableArrayList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        diaCombo.getSelectionModel().selectFirst();
    }

    private void cargarAulas() {
        aulaCombo.setItems(FXCollections.observableArrayList(aulaService.obtenerTodos()));
        if (!aulaCombo.getItems().isEmpty()) {
            aulaCombo.getSelectionModel().selectFirst();
        }
    }

    private void cargarDatos() {
        Curso curso = cursoService.obtener(idEnEdicion);
        if (curso != null) {
            nombreField.setText(curso.getNombre());
            nivelCombo.getSelectionModel().select(curso.getNivel());
            if (curso.getDiaInicio() != null) {
                diaCombo.getSelectionModel().select(curso.getDiaInicio());
            }
            if (curso.getHoraInicio() != null) {
                horaInicioField.setText(curso.getHoraInicio().toString().substring(0, 5));
            }
            if (curso.getHoraFin() != null) {
                horaFinField.setText(curso.getHoraFin().toString().substring(0, 5));
            }
            descripcionField.setText(curso.getDescripcion());
            CicloLectivo ciclo = cicloService.obtener(curso.getIdCicloLectivo());
            if (ciclo != null) {
                cicloCombo.getSelectionModel().select(ciclo);
            }
            if (curso.getIdAula() > 0) {
                Aula aula = aulaService.obtener(curso.getIdAula());
                if (aula != null) {
                    aulaCombo.getSelectionModel().select(aula);
                }
            }
        }
    }

    @FXML
    private void handleGuardar() {
        try {
            errorLabel.setText("");

            if (nombreField.getText().trim().isEmpty() || nivelCombo.getSelectionModel().isEmpty() ||
                cicloCombo.getSelectionModel().isEmpty()) {
                errorLabel.setText("Por favor completa todos los campos obligatorios.");
                return;
            }

            Curso curso = new Curso();
            if (idEnEdicion != null) {
                curso.setIdCurso(idEnEdicion);
            }
            curso.setNombre(nombreField.getText().trim());
            curso.setNivel(nivelCombo.getSelectionModel().getSelectedItem());
            curso.setDiaInicio(diaCombo.getSelectionModel().getSelectedItem());

            boolean horaInicioLleno = !horaInicioField.getText().trim().isEmpty();
            boolean horaFinLleno = !horaFinField.getText().trim().isEmpty();

            if (horaInicioLleno != horaFinLleno) {
                errorLabel.setText("Debes completar ambas horas o dejar ambas vacías.");
                return;
            }

            if (horaInicioLleno) {
                try {
                    curso.setHoraInicio(Time.valueOf(horaInicioField.getText().trim() + ":00"));
                } catch (IllegalArgumentException e) {
                    errorLabel.setText("Formato de hora inicio inválido (HH:mm).");
                    return;
                }
            }
            if (horaFinLleno) {
                try {
                    curso.setHoraFin(Time.valueOf(horaFinField.getText().trim() + ":00"));
                } catch (IllegalArgumentException e) {
                    errorLabel.setText("Formato de hora fin inválido (HH:mm).");
                    return;
                }
            }
            curso.setDescripcion(descripcionField.getText().trim());
            curso.setIdCicloLectivo(cicloCombo.getSelectionModel().getSelectedItem().getIdCicloLectivo());

            if (aulaCombo.getSelectionModel().getSelectedItem() != null) {
                Aula aulaSeleccionada = aulaCombo.getSelectionModel().getSelectedItem();
                curso.setIdAula(aulaSeleccionada.getIdAula());

                // Validar disponibilidad (CU013)
                if (curso.getHoraInicio() != null && curso.getHoraFin() != null && curso.getDiaInicio() != null) {
                    int idCursoExcluir = idEnEdicion != null ? idEnEdicion : -1;
                    if (!aulaService.validarDisponibilidad(aulaSeleccionada.getIdAula(), curso.getDiaInicio(),
                                                           curso.getHoraInicio(), curso.getHoraFin(), idCursoExcluir)) {
                        errorLabel.setText("El aula ya está ocupada en ese horario.");
                        return;
                    }
                }
            }

            if (idEnEdicion != null) {
                cursoService.modificar(curso);
                AlertUtil.showInfo("Éxito", "Curso modificado correctamente.");
            } else {
                cursoService.registrar(curso);
                AlertUtil.showInfo("Éxito", "Curso registrado correctamente.");
            }

            volverACursos();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorLabel.setText("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        volverACursos();
    }

    private void volverACursos() {
        ContextoApp.limpiar();
        SceneUtil.cambiarEscena("/com/englishacademy/views/cursos/curso-view.fxml", tituloLabel);
    }
}
