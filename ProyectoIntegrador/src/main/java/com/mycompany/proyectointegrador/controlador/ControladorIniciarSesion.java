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

    public boolean iniciarSesion(String nombreUsuario, String contraseña) throws CredencialesInvalidasException, SQLException, NoSuchAlgorithmException {
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

        } catch (TipoUsuarioInvalidoException e) {
            throw new CredencialesInvalidasException("Usuario Inválido.");
        } catch (SQLException e) {
            throw e;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo criptográfico no disponible.");
        }
    }
    
    public void cerrarSesion() {
        if (usuarioActual != null) {
            usuarioActual.setSesionIniciada(false); // Marca la sesión como cerrada
            usuarioActual = null;                   // Limpia la referencia al usuario
        }
    }

    

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
