/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author david_alcazar
 */
public class Contactos {
    private int idCont;
    private String nombreCon; // Nombre del contacto
    private String ipUsuario; // IP del usuario que está agregando el contacto
    private String ipUsCont;  // IP del contacto agregado
    private String InformacionContacto;

    public Contactos() {
    }

    public Contactos(String nombreCon) {
        this.nombreCon = nombreCon;
    }

    
    // Constructor
    public Contactos(String nombreCon, String ipUsuario, String ipUsCont) {
        this.nombreCon = nombreCon;
        this.ipUsuario = ipUsuario;
        this.ipUsCont = ipUsCont;
    }

    public String getSetInformacionContacto() {
        return InformacionContacto;
    }

    public void setSetInformacionContacto(String InformacionContacto) {
        this.InformacionContacto = InformacionContacto;
    }

    // Getters y setters
    public int getIdCont() {
        return idCont;
    }

    public void setIdCont(int idCont) {
        this.idCont = idCont;
    }

    public String getNombreCon() {
        return nombreCon;
    }

    public void setNombreCon(String nombreCon) {
        this.nombreCon = nombreCon;
    }

    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    public String getIpUsCont() {
        return ipUsCont;
    }

    public void setIpUsCont(String ipUsCont) {
        this.ipUsCont = ipUsCont;
    }
}
