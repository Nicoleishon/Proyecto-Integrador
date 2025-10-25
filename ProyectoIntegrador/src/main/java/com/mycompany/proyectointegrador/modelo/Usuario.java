package com.mycompany.proyectointegrador.modelo;


abstract class Usuario {
    private String nombreUsuario;
    private String hashContraseña;
    private String rol; 

    public Usuario(String nombreUsuario, String hashContraseña, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.hashContraseña = hashContraseña;
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getHashContraseña() {
        return hashContraseña;
    }

    public void setHashContraseña(String hashContraseña) {
        this.hashContraseña = hashContraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
}
