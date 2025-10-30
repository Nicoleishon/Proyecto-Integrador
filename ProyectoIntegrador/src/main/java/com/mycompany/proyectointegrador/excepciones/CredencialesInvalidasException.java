
package com.mycompany.proyectointegrador.excepciones;

public class CredencialesInvalidasException extends Exception {
    public CredencialesInvalidasException() {
        super("Credenciales inválidas.");
    }

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
