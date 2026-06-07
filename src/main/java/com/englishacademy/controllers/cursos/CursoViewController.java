package com.englishacademy.controllers.cursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import com.englishacademy.models.Curso;
import com.englishacademy.models.services.CursoService;
import com.englishacademy.utils.AlertUtil;
import com.englishacademy.utils.ContextoApp;
import com.englishacademy.utils.SceneUtil;

public class CursoViewController {

    @FXML
    private TableView<Curso> cursosTable;

    @FXML
    private TableColumn<Curso, Void> accionesColumn;

    private CursoService cursoService;

    @FXML
    public void initialize() {
        cursoService = CursoService.getInstance();
        crearColumnaBotones();
        cargarTabla();
    }

    private void crearColumnaBotones() {
        accionesColumn.setCellFactory(param -> new TableCell<Curso, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Curso curso = getTableRow().getItem();
                    Button btnEditar = new Button("EDITAR");
                    Button btnEliminar = new Button("ELIMINAR");

                    btnEditar.setStyle("-fx-padding: 8 15; -fx-background-color: #00a8ff; -fx-text-fill: #ffffff; -fx-cursor: hand; -fx-font-size: 11; -fx-font-weight: bold;");
                    btnEliminar.setStyle("-fx-padding: 8 15; -fx-background-color: #ff6b6b; -fx-text-fill: #ffffff; -fx-cursor: hand; -fx-font-size: 11; -fx-font-weight: bold;");

                    btnEditar.setOnAction(event -> editarCurso(curso));
                    btnEliminar.setOnAction(event -> eliminarCurso(curso));

                    HBox hbox = new HBox(8);
                    hbox.getChildren().addAll(btnEditar, btnEliminar);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void cargarTabla() {
        ObservableList<Curso> datos = FXCollections.observableArrayList(cursoService.obtenerTodos());
        cursosTable.setItems(datos);
    }

    @FXML
    private void handleNuevoCurso() {
        ContextoApp.setIdCursoEnEdicion(null);
        SceneUtil.cambiarEscena("/com/englishacademy/views/cursos/curso-registro-view.fxml", cursosTable);
    }

    private void editarCurso(Curso curso) {
        if (curso != null) {
            ContextoApp.setIdCursoEnEdicion(curso.getIdCurso());
            SceneUtil.cambiarEscena("/com/englishacademy/views/cursos/curso-registro-view.fxml", cursosTable);
        }
    }

    private void eliminarCurso(Curso curso) {
        if (curso != null) {
            boolean confirmado = AlertUtil.showConfirmation("Confirmar eliminación",
                    "¿Estás seguro de que deseas eliminar el curso " + curso.getNombre() + "?");
            if (confirmado) {
                cursoService.eliminar(curso.getIdCurso());
                AlertUtil.showInfo("Éxito", "Curso eliminado correctamente.");
                cargarTabla();
            }
        }
    }
}
