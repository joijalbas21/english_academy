package com.englishacademy.controllers.alumnos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import com.englishacademy.models.Alumno;
import com.englishacademy.models.services.AlumnoService;
import com.englishacademy.utils.AlertUtil;
import com.englishacademy.utils.ContextoApp;
import com.englishacademy.utils.SceneUtil;

public class AlumnoViewController {

    @FXML
    private TableView<Alumno> alumnosTable;

    @FXML
    private TableColumn<Alumno, Void> accionesColumn;

    private AlumnoService alumnoService;

    @FXML
    public void initialize() {
        alumnoService = AlumnoService.getInstance();
        crearColumnaBotones();
        cargarTabla();
    }

    private void crearColumnaBotones() {
        accionesColumn.setCellFactory(param -> new TableCell<Alumno, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Alumno alumno = getTableRow().getItem();
                    Button btnEditar = new Button("EDITAR");
                    Button btnEliminar = new Button("ELIMINAR");

                    btnEditar.setStyle("-fx-padding: 8 15; -fx-background-color: #00a8ff; -fx-text-fill: #ffffff; -fx-cursor: hand; -fx-font-size: 11; -fx-font-weight: bold;");
                    btnEliminar.setStyle("-fx-padding: 8 15; -fx-background-color: #ff6b6b; -fx-text-fill: #ffffff; -fx-cursor: hand; -fx-font-size: 11; -fx-font-weight: bold;");

                    btnEditar.setOnAction(event -> editarAlumno(alumno));
                    btnEliminar.setOnAction(event -> eliminarAlumno(alumno));

                    HBox hbox = new HBox(8);
                    hbox.getChildren().addAll(btnEditar, btnEliminar);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void cargarTabla() {
        ObservableList<Alumno> datos = FXCollections.observableArrayList(alumnoService.obtenerTodos());
        alumnosTable.setItems(datos);
    }

    @FXML
    private void handleNuevoAlumno() {
        ContextoApp.setIdAlumnoEnEdicion(null);
        SceneUtil.cambiarEscena("/com/englishacademy/views/alumnos/alumno-registro-view.fxml", alumnosTable);
    }

    private void editarAlumno(Alumno alumno) {
        if (alumno != null) {
            ContextoApp.setIdAlumnoEnEdicion(alumno.getIdAlumno());
            SceneUtil.cambiarEscena("/com/englishacademy/views/alumnos/alumno-registro-view.fxml", alumnosTable);
        }
    }

    private void eliminarAlumno(Alumno alumno) {
        if (alumno != null) {
            boolean confirmado = AlertUtil.showConfirmation("Confirmar eliminación",
                    "¿Estás seguro de que deseas eliminar a " + alumno.getNombre() + "?");
            if (confirmado) {
                alumnoService.eliminar(alumno.getIdAlumno());
                AlertUtil.showInfo("Éxito", "Alumno eliminado correctamente.");
                cargarTabla();
            }
        }
    }
}
