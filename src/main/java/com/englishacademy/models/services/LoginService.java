package com.englishacademy.models.services;

public class LoginService {

	// Implementación fuera del scope del proyecto y no considerado en los CU definidos.
	// Se deja implementación mínima para simular autenticación de administrador.
	public boolean autenticar(String email, String password) {
		return email.equals("admin@englishacademy.com.ar") && password.equals("admin123");
	}
}
