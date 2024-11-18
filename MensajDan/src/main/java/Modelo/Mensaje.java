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
public class Mensaje {
    
    private int idMnj;
    private int idConv;
    private String ipUsuario;
    private String contenido;
    private String fechaEnvio;
    private String estado;

    public Mensaje() {
    }

    public Mensaje(int idMnj, int idConv, String ipUsuario, String contenido, String fechaEnvio, String estado) {
        this.idMnj = idMnj;
        this.idConv = idConv;
        this.ipUsuario = ipUsuario;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
    }
    
    

    // Getters y Setters
    public int getIdMnj() {
        return idMnj;
    }

    public void setIdMnj(int idMnj) {
        this.idMnj = idMnj;
    }

    public int getIdConv() {
        return idConv;
    }

    public void setIdConv(int idConv) {
        this.idConv = idConv;
    }

    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
