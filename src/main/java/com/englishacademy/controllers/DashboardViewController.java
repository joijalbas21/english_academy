package com.englishacademy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.englishacademy.models.services.AlumnoService;
import com.englishacademy.models.services.CursoService;
import com.englishacademy.models.services.ProfesorService;

public class DashboardViewController {

	@FXML
	private Label labelCursos;

	@FXML
	private Label labelAlumnos;

	@FXML
	private Label labelProfesores;

	@FXML
	public void initialize() {
		labelCursos.setText(String.valueOf(CursoService.getInstance().obtenerTodos().size()));
		labelAlumnos.setText(String.valueOf(AlumnoService.getInstance().obtenerTodos().size()));
		labelProfesores.setText(String.valueOf(ProfesorService.getInstance().obtenerTodos().size()));
	}
}
