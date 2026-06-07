package com.englishacademy.utils;

public class ValidacionesHelper {

    /**
     * Valida el nombre o apellido de una persona.
     *
     * Parámetro correcto: 3-20 caracteres, solo letras (incluyendo acentos) y espacios.
     * Ej: "Juan", "María José", "Martín López"
     *
     * @param nombre el nombre a validar
     * @param campo etiqueta del campo (ej: "El nombre", "El apellido")
     * @throws IllegalArgumentException si el nombre no es válido
     */
    public static void validarNombrePersona(String nombre, String campo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " es incorrecto.");
        }
        if (nombre.length() < 3 || nombre.length() > 20) {
            throw new IllegalArgumentException(campo + " es incorrecto.");
        }
        if (!nombre.matches("[a-zA-ZáéíóúñÁÉÍÓÚÑ\\s]+")) {
            throw new IllegalArgumentException(campo + " es incorrecto.");
        }
    }

    /**
     * Valida el nombre de una entidad.
     *
     * Parámetro correcto: 3-20 caracteres, alfanúmerico y espacios.
     * Ej: "Inglés 1", "Principiantes", "Anual"
     *
     * @param nombre el nombre a validar
     * @param campo etiqueta del campo (ej: "El nombre", "El titulo")
     * @throws IllegalArgumentException si el nombre no es válido
     */
    public static void validarNombre(String nombre, String campo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " es incorrecto.");
        }
        if (nombre.length() < 3 || nombre.length() > 20) {
            throw new IllegalArgumentException(campo + " es incorrecto.");
        }
        if (!nombre.matches("[a-zA-Z0-9áéíóúñÁÉÍÓÚÑ\\s]+")) {
            throw new IllegalArgumentException(campo + " es incorrecto.");
        }
    }

    /**
     * Valida el DNI (Documento Nacional de Identidad).
     *
     * Parámetro correcto: número entero entre 1 y 99.999.999.
     * Ej: 35706793, 12345678
     *
     * @param dni el DNI a validar
     * @throws IllegalArgumentException si el DNI no está en el rango válido
     */
    public static void validarDNI(int dni) {
        if (dni <= 0 || dni > 99999999) {
            throw new IllegalArgumentException("El DNI es incorrecto.");
        }
    }

    /**
     * Valida una dirección de correo electrónico.
     *
     * Parámetro correcto: formato email@dominio.com (5-50 caracteres).
     * Ej: usuario@gmail.com, nombre.apellido@empresa.com.ar
     *
     * @param email el email a validar
     * @throws IllegalArgumentException si el email no tiene un formato válido
     */
    public static void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es incorrecto.");
        }
        if (email.length() < 5 || email.length() > 50) {
            throw new IllegalArgumentException("El email es incorrecto.");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El email es incorrecto.");
        }
    }

    /**
     * Valida un número de teléfono.
     *
     * Parámetro correcto: 8-20 caracteres, solo números y caracteres +, (, ), -, espacios.
     * Ej: 3434045828, +54 9 11 2345 6789, (011) 1234-5678
     *
     * @param telefono el teléfono a validar
     * @throws IllegalArgumentException si el teléfono no es válido
     */
    public static void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es incorrecto.");
        }
        if (telefono.length() < 8 || telefono.length() > 20) {
            throw new IllegalArgumentException("El teléfono es incorrecto.");
        }
        if (!telefono.matches("[0-9+()\\-\\s]+")) {
            throw new IllegalArgumentException("El teléfono es incorrecto.");
        }
    }
}
