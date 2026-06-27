package com.englishacademy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import com.englishacademy.utils.ContextoApp;
import com.englishacademy.utils.SceneUtil;

public class MenuViewController {

	@FXML
	private VBox root;

	private static final String ESTILO_ACTIVO =
		"-fx-padding: 12 20; -fx-background-color: #00d4d4; -fx-text-fill: #0f1419; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-width: 0 0 1 0;";
	private static final String ESTILO_INACTIVO =
		"-fx-padding: 12 20; -fx-background-color: transparent; -fx-text-fill: #999999; -fx-border-color: #333333; -fx-border-width: 0 0 1 0; -fx-cursor: hand;";

	@FXML
	public void initialize() {
		if (root != null) {
			root.getChildren().stream()
				.filter(node -> node instanceof Button)
				.map(node -> (Button) node)
				.forEach(btn -> {
					aplicarEstilo(btn, btn.getUserData().equals(ContextoApp.getPaginaActual()));
					configurarHover(btn);
				});
		}
	}

	private void aplicarEstilo(Button btn, boolean activo) {
		btn.setStyle(activo ? ESTILO_ACTIVO : ESTILO_INACTIVO);
	}

	private void configurarHover(Button btn) {
		btn.setOnMouseEntered(e -> btn.setStyle(btn.getStyle() + "; -fx-background-color: #2a4a4a;"));
		btn.setOnMouseExited(e -> aplicarEstilo(btn, btn.getUserData().equals(ContextoApp.getPaginaActual())));
	}

	@FXML
	private void handle(ActionEvent event) {
		Button btn = (Button) event.getSource();
		String pageName = (String) btn.getUserData();
		navigateTo(pageName, btn);
	}

	private void navigateTo(String pageName, Button btn) {
		String fxmlFile = switch(pageName) {
			case "dashboard" -> "/com/englishacademy/views/dashboard-view.fxml";
			case "alumnos" -> "/com/englishacademy/views/alumnos/alumno-view.fxml";
			case "cursos" -> "/com/englishacademy/views/cursos/curso-view.fxml";
			case "profesores" -> "/com/englishacademy/views/profesores/profesor-view.fxml";
			case "aulas" -> "/com/englishacademy/views/aula-view.fxml";
			default -> null;
		};

		if (fxmlFile != null) {
			ContextoApp.setPaginaActual(pageName);
			SceneUtil.cambiarEscena(fxmlFile, btn);
		}
	}
}
