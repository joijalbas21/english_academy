package com.englishacademy.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 * Utilidad para manejar cambios de escena en la aplicación.
 */
public class SceneUtil {

	/**
	 * Carga una vista FXML y reemplaza la escena actual.
	 *
	 * @param fxmlPath la ruta del archivo FXML a cargar (ej: "/com/englishacademy/views/dashboard-view.fxml")
	 * @param control un control de la ventana actual para obtener el Stage
	 * @param width ancho de la nueva escena
	 * @param height alto de la nueva escena
	 */
	public static void cambiarEscena(String fxmlPath, Control control, int width, int height) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(SceneUtil.class.getResource(fxmlPath));
			Scene scene = new Scene(fxmlLoader.load(), width, height);
			Stage stage = (Stage) control.getScene().getWindow();
			stage.setScene(scene);
		} catch (Exception e) {
			AlertUtil.showError("Error", "No se pudo cargar la vista: " + fxmlPath);
		}
	}

	/**
	 * Carga una vista FXML y reemplaza la escena actual con dimensiones por defecto.
	 *
	 * @param fxmlPath la ruta del archivo FXML a cargar
	 * @param control un control de la ventana actual para obtener el Stage
	 */
	public static void cambiarEscena(String fxmlPath, Control control) {
		cambiarEscena(fxmlPath, control, 1200, 700);
	}
}
