package com.englishacademy.models.services;

/**
 * Servicio de autenticación de usuarios.
 * Implementación mínima para simular autenticación de administrador.
 */
public class LoginService {

	/**
	 * Autentica un usuario comparando email y contraseña con credenciales predefinidas.
	 * Implementación fuera del scope del proyecto y no considerado en los CU definidos.
	 *  Se deja implementación mínima para simular autenticación de administrador.
	 *
	 * @param email el email del usuario a autenticar
	 * @param password la contraseña del usuario a autenticar
	 * @return true si las credenciales son válidas, false en caso contrario
	 */
	public boolean autenticar(String email, String password) {
		return email.equals("admin@englishacademy.com.ar") && password.equals("admin123");
	}
}
