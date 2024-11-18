/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.security.Timestamp;

/**
 *
 * @author david_alcazar
 */
public class Usuario {
    
    private String ipUsuario;
    private String nombre;
    private String contraseña;
    private String estado;
    private String informacion;
    private Timestamp fechaCreacion;

    public Usuario() {
    }

    // Constructor completo
    
    public Usuario(String ipUsuario, String nombre, String contraseña, String estado, String informacion, Timestamp fechaCreacion) {
        this.ipUsuario = ipUsuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.estado = estado;
        this.informacion = informacion;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
    
    

}
