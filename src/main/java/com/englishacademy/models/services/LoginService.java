package com.englishacademy.models.services;

public class LoginService {

	public boolean autenticar(String email, String password) {
		// Credenciales de prueba (TODO: Implementar autenticación real con BD)
		return email.equals("test@test.com") && password.equals("test123");
	}
}
