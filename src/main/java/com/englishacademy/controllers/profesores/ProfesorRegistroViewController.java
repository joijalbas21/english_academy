package com.englishacademy.controllers.profesores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.englishacademy.models.Profesor;
import com.englishacademy.models.services.ProfesorService;
import com.englishacademy.utils.AlertUtil;
import com.englishacademy.utils.ContextoApp;
import com.englishacademy.utils.SceneUtil;

public class ProfesorRegistroViewController {

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
    private Label errorLabel;

    private ProfesorService profesorService;
    private Integer idEnEdicion;

    @FXML
    public void initialize() {
        profesorService = ProfesorService.getInstance();
        idEnEdicion = ContextoApp.getIdProfesorEnEdicion();

        if (idEnEdicion != null) {
            tituloLabel.setText("Editar Profesor");
            cargarDatos();
        } else {
            tituloLabel.setText("Nuevo Profesor");
        }
    }

    private void cargarDatos() {
        Profesor profesor = profesorService.obtener(idEnEdicion);
        if (profesor != null) {
            nombreField.setText(profesor.getNombre());
            apellidoField.setText(profesor.getApellido());
            dniField.setText(String.valueOf(profesor.getDni()));
            emailField.setText(profesor.getEmail());
        }
    }

    @FXML
    private void handleGuardar() {
        try {
            errorLabel.setText("");

            if (nombreField.getText().trim().isEmpty() || apellidoField.getText().trim().isEmpty() ||
                dniField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                errorLabel.setText("Por favor completa todos los campos.");
                return;
            }

            Profesor profesor = new Profesor();
            if (idEnEdicion != null) {
                profesor.setIdProfesor(idEnEdicion);
            }
            profesor.setNombre(nombreField.getText().trim());
            profesor.setApellido(apellidoField.getText().trim());
            profesor.setDni(Integer.parseInt(dniField.getText().trim()));
            profesor.setEmail(emailField.getText().trim());

            if (idEnEdicion != null) {
                profesorService.modificar(profesor);
                AlertUtil.showInfo("Éxito", "Profesor modificado correctamente.");
            } else {
                profesorService.registrar(profesor);
                AlertUtil.showInfo("Éxito", "Profesor registrado correctamente.");
            }

            volverAProfesores();
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
        volverAProfesores();
    }

    private void volverAProfesores() {
        ContextoApp.limpiar();
        SceneUtil.cambiarEscena("/com/englishacademy/views/profesores/profesor-view.fxml", tituloLabel);
    }
}
