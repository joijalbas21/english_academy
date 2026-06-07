package com.englishacademy.controllers.alumnos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.englishacademy.models.Alumno;
import com.englishacademy.models.services.AlumnoService;
import com.englishacademy.utils.AlertUtil;
import com.englishacademy.utils.ContextoApp;
import com.englishacademy.utils.SceneUtil;

public class AlumnoRegistroViewController {

    @FXML
    private Label tituloLabel;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidoField;

    @FXML
    private TextField dniField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoField;

    @FXML
    private Label errorLabel;

    private AlumnoService alumnoService;
    private Integer idEnEdicion;

    @FXML
    public void initialize() {
        alumnoService = AlumnoService.getInstance();
        idEnEdicion = ContextoApp.getIdAlumnoEnEdicion();

        if (idEnEdicion != null) {
            tituloLabel.setText("Editar Alumno");
            cargarDatos();
        } else {
            tituloLabel.setText("Nuevo Alumno");
        }
    }

    private void cargarDatos() {
        Alumno alumno = alumnoService.obtener(idEnEdicion);
        if (alumno != null) {
            nombreField.setText(alumno.getNombre());
            apellidoField.setText(alumno.getApellido());
            dniField.setText(String.valueOf(alumno.getDni()));
            emailField.setText(alumno.getEmail());
            telefonoField.setText(alumno.getTelefono());
        }
    }

    @FXML
    private void handleGuardar() {
        try {
            errorLabel.setText("");

            if (nombreField.getText().trim().isEmpty() || apellidoField.getText().trim().isEmpty() ||
                dniField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||
                telefonoField.getText().trim().isEmpty()) {
                errorLabel.setText("Por favor completa todos los campos.");
                return;
            }

            Alumno alumno = new Alumno();
            if (idEnEdicion != null) {
                alumno.setIdAlumno(idEnEdicion);
            }
            alumno.setNombre(nombreField.getText().trim());
            alumno.setApellido(apellidoField.getText().trim());
            alumno.setDni(Integer.parseInt(dniField.getText().trim()));
            alumno.setEmail(emailField.getText().trim());
            alumno.setTelefono(telefonoField.getText().trim());

            if (idEnEdicion != null) {
                alumnoService.modificar(alumno);
                AlertUtil.showInfo("Éxito", "Alumno modificado correctamente.");
            } else {
                alumnoService.registrar(alumno);
                AlertUtil.showInfo("Éxito", "Alumno registrado correctamente.");
            }

            volverAAlumnos();
        } catch (NumberFormatException e) {
            errorLabel.setText("El DNI debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorLabel.setText("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        volverAAlumnos();
    }

    private void volverAAlumnos() {
        ContextoApp.limpiar();
        SceneUtil.cambiarEscena("/com/englishacademy/views/alumnos/alumno-view.fxml", tituloLabel);
    }
}
