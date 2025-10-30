
package com.mycompany.proyectointegrador.excepciones;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Credenciales inválidas.");
    }

    public InvalidCredentialsException(String mensaje) {
        super(mensaje);
    }
}
