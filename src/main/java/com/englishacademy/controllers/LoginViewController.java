package com.englishacademy.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.englishacademy.utils.AlertUtil;
import com.englishacademy.models.services.LoginService;
import java.io.IOException;

public class LoginViewController {

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	private final LoginService loginService = new LoginService();

	/**
	 * Permite presionar Enter en el campo password para ejecutar el login.
	 */
	@FXML
	public void initialize() {
		passwordField.setOnAction(e -> handleLogin());
	}

	/**
	 * Valida email y contraseña, luego autentica con el servicio de login.
	 */
	@FXML
	private void handleLogin() {
		String email = emailField.getText().trim();
		String password = passwordField.getText();

		if (email.isEmpty() || password.isEmpty()) {
			AlertUtil.showWarning("Validación", "Por favor completa todos los campos");
			return;
		}

		if (!email.contains("@")) {
			AlertUtil.showWarning("Validación", "Ingresa un email válido");
			return;
		}

		boolean loginExitoso = loginService.autenticar(email, password);
		if (loginExitoso) {
			navigateToDashboard();
		} else {
			AlertUtil.showError("Error", "Email o contraseña incorrectos");
		}

		passwordField.clear();
	}

	/**
	 * Carga la vista del dashboard y cambia la escena actual.
	 */
	private void navigateToDashboard() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/englishacademy/views/dashboard-view.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
			Stage stage = (Stage) emailField.getScene().getWindow();
			stage.setScene(scene);
			stage.setResizable(false);
		} catch (IOException e) {
			AlertUtil.showError("Error", "No se pudo cargar el dashboard");
		}
	}
}
