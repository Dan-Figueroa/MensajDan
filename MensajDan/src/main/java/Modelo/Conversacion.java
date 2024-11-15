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
    private int idConversacion;
    //private String tipo;
    private Timestamp fechaCreacion;

    public Conversacion() {
    }

    public Conversacion(int idConversacion, Timestamp fechaCreacion) {
        this.idConversacion = idConversacion;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(int idConversacion) {
        this.idConversacion = idConversacion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    
    
}
