package com.mycompany.proyectointegrador.controlador;

import com.mycompany.proyectointegrador.excepciones.CredencialesInvalidasException;
import com.mycompany.proyectointegrador.excepciones.TipoUsuarioInvalidoException;
import com.mycompany.proyectointegrador.modelo.Usuario;
import com.mycompany.proyectointegrador.repositorios.UsuarioRepositorio;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ControladorIniciarSesion {
    private final UsuarioRepositorio usuarioRepo = new UsuarioRepositorio();
    private Usuario usuarioActual = null;

    public boolean iniciarSesion(String nombreUsuario, String contraseña) throws CredencialesInvalidasException {
        try {
            // Buscar id del usuario por nombre
            int idUsuario = usuarioRepo.buscarIdPorNombreUsuario(nombreUsuario);

            // Obtener objeto concreto (Paciente o Recepcionista)
            Usuario usuario = usuarioRepo.buscarPorId(idUsuario);
            if (usuario == null) {
                throw new CredencialesInvalidasException("Usuario no encontrado.");
            }

            // Validar contraseña
            String hashIngresado = usuario.generarHash(contraseña);
            if (!hashIngresado.equals(usuario.getHashContraseña())) {
                throw new CredencialesInvalidasException("Contraseña incorrecta.");
            }

            // Sesión iniciada con éxito
            usuario.setSesionIniciada(true);
            this.usuarioActual = usuario;
            return true;

        } catch (SQLException | TipoUsuarioInvalidoException e) {
            System.err.println("Error al iniciar sesión: " + e.getMessage());
            System.err.println("Causa: " + e.getCause());
            throw new CredencialesInvalidasException("Error en la verificación de credenciales.");
        } catch (NoSuchAlgorithmException e) {
            throw new CredencialesInvalidasException("Error interno en la validación de la contraseña.");
        }
    }

    public String obtenerPanelSesion() {
        if (usuarioActual instanceof com.mycompany.proyectointegrador.modelo.Paciente)
            return "panelPaciente";
        else if (usuarioActual instanceof com.mycompany.proyectointegrador.modelo.Recepcionista)
            return "panelRecepcionista";
        else
            return "panelIniciarSesion";
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
