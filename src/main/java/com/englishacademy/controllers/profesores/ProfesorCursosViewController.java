package com.englishacademy.controllers.profesores;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.CheckBox;
import javafx.util.Callback;
import com.englishacademy.models.Curso;
import com.englishacademy.models.CursoSeleccion;
import com.englishacademy.models.services.CursoService;
import com.englishacademy.utils.ContextoApp;
import javafx.stage.Stage;
import java.util.List;

public class ProfesorCursosViewController {

    @FXML
    private ListView<CursoSeleccion> cursosListView;

    @FXML
    private Button guardarButton;

    private CursoService cursoService;
    private Integer idProfesorEnEdicion;

    @FXML
    public void initialize() {
        cursoService = CursoService.getInstance();
        idProfesorEnEdicion = ContextoApp.getIdProfesorEnEdicion();

        cargarCursos();
    }

    private void cargarCursos() {
        List<CursoSeleccion> items = FXCollections.observableArrayList();
        for (Curso curso : cursoService.obtenerTodos()) {
            boolean estaAsignado = curso.getIdProfesor() == idProfesorEnEdicion;
            items.add(new CursoSeleccion(curso, estaAsignado));
        }

        cursosListView.setItems(FXCollections.observableArrayList(items));
        cursosListView.setCellFactory(new Callback<ListView<CursoSeleccion>, ListCell<CursoSeleccion>>() {
            @Override
            public ListCell<CursoSeleccion> call(ListView<CursoSeleccion> param) {
                return new ListCell<CursoSeleccion>() {
                    @Override
                    protected void updateItem(CursoSeleccion item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            CheckBox checkBox = new CheckBox(item.toString());
                            checkBox.setSelected(item.isSeleccionado());
                            checkBox.selectedProperty().bindBidirectional(item.seleccionadoProperty());
                            setGraphic(checkBox);
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void handleGuardar() {
        for (CursoSeleccion cs : cursosListView.getItems()) {
            Curso curso = cs.getCurso();
            if (cs.isSeleccionado()) {
                curso.setIdProfesor(idProfesorEnEdicion);
            } else {
                // Desasignar si no está seleccionado
                if (curso.getIdProfesor() == idProfesorEnEdicion) {
                    curso.setIdProfesor(0);
                }
            }
            cursoService.modificar(curso);
        }

        cerrarVentana();
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) guardarButton.getScene().getWindow();
        stage.close();
    }
}
