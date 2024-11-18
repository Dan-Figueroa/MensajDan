/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelo.Conector;

/**
 *
 * @author danfigueroa
 */
public class Messeger {
    public static Conector Servidor;
    
    public static void iniciarserver()
    {
        Servidor = new Conector();
        Servidor.start();
    }
    
    public static void cerrarserver()
    {
        Servidor = new Conector();
        Servidor.desconectar();
    }
    
    
}
