package com.englishacademy.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.stage.Modality;

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
			if (fxmlLoader.getLocation() == null) {
				AlertUtil.showError("Error", "La página solicitada no existe. Intenta de nuevo.");
				System.err.println("[ERROR] FXML no encontrado: " + fxmlPath);
				return;
			}
			Scene scene = new Scene(fxmlLoader.load(), width, height);
			Stage stage = (Stage) control.getScene().getWindow();
			stage.setScene(scene);
		} catch (Exception e) {
			System.err.println("[ERROR] Error al cargar FXML " + fxmlPath + ": " + e.getMessage());
			e.printStackTrace();
			AlertUtil.showError("Error", "No se pudo abrir la página. Intenta de nuevo.");
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

	/**
	 * Abre una vista FXML en un popup modal (ventana independiente).
	 *
	 * @param fxmlPath la ruta del archivo FXML a cargar
	 * @param titulo el título del popup
	 */
	public static void abrirPopup(String fxmlPath, String titulo) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(SceneUtil.class.getResource(fxmlPath));
			if (fxmlLoader.getLocation() == null) {
				AlertUtil.showError("Error", "La página solicitada no existe. Intenta de nuevo.");
				System.err.println("[ERROR] FXML no encontrado: " + fxmlPath);
				return;
			}
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle(titulo);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (Exception e) {
			System.err.println("[ERROR] Error al cargar FXML " + fxmlPath + ": " + e.getMessage());
			e.printStackTrace();
			AlertUtil.showError("Error", "No se pudo abrir la página. Intenta de nuevo.");
		}
	}
}
