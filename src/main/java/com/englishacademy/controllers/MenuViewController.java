package com.englishacademy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import com.englishacademy.utils.SceneUtil;

public class MenuViewController {

	@FXML
	private VBox root;

	/**
	 * Configura listeners de hover para todos los botones del menú.
	 */
	@FXML
	public void initialize() {
		if (root != null) {
			root.getChildren().stream()
				.filter(node -> node instanceof Button)
				.map(node -> (Button) node)
				.forEach(this::configureButtonHover);
		}
	}

	/**
	 * Cambia el color del botón al pasar el mouse y lo restaura al salir.
	 *
	 * @param btn el botón a configurar
	 */
	private void configureButtonHover(Button btn) {
		btn.setOnMouseEntered(e -> btn.setStyle(btn.getStyle() + "; -fx-background-color: #2a4a4a;"));
		btn.setOnMouseExited(e -> {
			String original = btn.getUserData().equals("dashboard")
				? "-fx-padding: 12 20; -fx-background-color: #00d4d4; -fx-text-fill: #0f1419; -fx-font-weight: bold; -fx-cursor: hand;"
				: "-fx-padding: 12 20; -fx-background-color: transparent; -fx-text-fill: #999999; -fx-border-color: #333333; -fx-cursor: hand;";
			btn.setStyle(original + " -fx-border-width: 0 0 1 0;");
		});
	}

	/**
	 * Obtiene el nombre de la página del botón y navega a ella.
	 *
	 * @param event el evento del click
	 */
	@FXML
	private void handle(ActionEvent event) {
		Button btn = (Button) event.getSource();
		String pageName = (String) btn.getUserData();
		navigateTo(pageName, btn);
	}

	/**
	 * Carga la vista FXML correspondiente y cambia la escena actual.
	 *
	 * @param pageName el nombre de la página a cargar
	 * @param btn el botón que disparó la navegación
	 */
	private void navigateTo(String pageName, Button btn) {
		String fxmlFile = switch(pageName) {
			case "dashboard" -> "/com/englishacademy/views/dashboard-view.fxml";
			case "alumnos" -> "/com/englishacademy/views/alumnos/alumno-view.fxml";
			case "cursos" -> "/com/englishacademy/views/curso-view.fxml";
			case "profesores" -> "/com/englishacademy/views/profesores/profesor-view.fxml";
			case "aulas" -> "/com/englishacademy/views/aula-view.fxml";
			default -> null;
		};

		if (fxmlFile != null) {
			SceneUtil.cambiarEscena(fxmlFile, btn);
		}
	}
}
