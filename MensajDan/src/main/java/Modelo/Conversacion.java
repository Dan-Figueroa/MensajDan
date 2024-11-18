/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.security.Timestamp;

/**
 *
 * @author david_alcazar
 * clase para guardar todos las conversaciones 
 */
public class Conversacion {
    private int idConv;
    private String ipUsuario1;
    private String ipUsuario2;
    private String fechaInicio;

    public Conversacion(int idConv, String ipUsuario1, String ipUsuario2, String fechaInicio) {
        this.idConv = idConv;
        this.ipUsuario1 = ipUsuario1;
        this.ipUsuario2 = ipUsuario2;
        this.fechaInicio = fechaInicio;
    }

    public Conversacion() {
    }

    // Getters y Setters
    public int getIdConv() {
        return idConv;
    }

    public void setIdConv(int idConv) {
        this.idConv = idConv;
    }

    public String getIpUsuario1() {
        return ipUsuario1;
    }

    public void setIpUsuario1(String ipUsuario1) {
        this.ipUsuario1 = ipUsuario1;
    }

    public String getIpUsuario2() {
        return ipUsuario2;
    }

    public void setIpUsuario2(String ipUsuario2) {
        this.ipUsuario2 = ipUsuario2;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }  
}
