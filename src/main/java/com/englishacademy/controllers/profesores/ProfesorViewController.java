package com.englishacademy.controllers.profesores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import com.englishacademy.models.Profesor;
import com.englishacademy.models.services.ProfesorService;
import com.englishacademy.utils.AlertUtil;
import com.englishacademy.utils.ContextoApp;
import com.englishacademy.utils.SceneUtil;

public class ProfesorViewController {

    @FXML
    private TableView<Profesor> profesoresTable;

    @FXML
    private TableColumn<Profesor, Void> accionesColumn;

    private ProfesorService profesorService;

    @FXML
    public void initialize() {
        profesorService = ProfesorService.getInstance();
        crearColumnaBotones();
        cargarTabla();
    }

    private void crearColumnaBotones() {
        accionesColumn.setCellFactory(param -> new TableCell<Profesor, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Profesor profesor = getTableRow().getItem();
                    Button btnEditar = new Button("EDITAR");
                    Button btnEliminar = new Button("ELIMINAR");

                    btnEditar.setStyle("-fx-padding: 8 15; -fx-background-color: #00a8ff; -fx-text-fill: #ffffff; -fx-cursor: hand; -fx-font-size: 11; -fx-font-weight: bold;");
                    btnEliminar.setStyle("-fx-padding: 8 15; -fx-background-color: #ff6b6b; -fx-text-fill: #ffffff; -fx-cursor: hand; -fx-font-size: 11; -fx-font-weight: bold;");

                    btnEditar.setOnAction(event -> editarProfesor(profesor));
                    btnEliminar.setOnAction(event -> eliminarProfesor(profesor));

                    HBox hbox = new HBox(8);
                    hbox.getChildren().addAll(btnEditar, btnEliminar);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void cargarTabla() {
        ObservableList<Profesor> datos = FXCollections.observableArrayList(profesorService.obtenerTodos());
        profesoresTable.setItems(datos);
    }

    @FXML
    private void handleNuevoProfesor() {
        ContextoApp.setIdProfesorEnEdicion(null);
        SceneUtil.cambiarEscena("/com/englishacademy/views/profesores/profesor-registro-view.fxml", profesoresTable);
    }

    private void editarProfesor(Profesor profesor) {
        if (profesor != null) {
            ContextoApp.setIdProfesorEnEdicion(profesor.getIdProfesor());
            SceneUtil.cambiarEscena("/com/englishacademy/views/profesores/profesor-registro-view.fxml", profesoresTable);
        }
    }

    private void eliminarProfesor(Profesor profesor) {
        if (profesor != null) {
            boolean confirmado = AlertUtil.showConfirmation("Confirmar eliminación",
                    "¿Estás seguro de que deseas eliminar a " + profesor.getNombre() + "?");
            if (confirmado) {
                profesorService.eliminar(profesor.getIdProfesor());
                AlertUtil.showInfo("Éxito", "Profesor eliminado correctamente.");
                cargarTabla();
            }
        }
    }
}
