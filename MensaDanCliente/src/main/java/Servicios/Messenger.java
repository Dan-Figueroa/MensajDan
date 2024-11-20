/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;
import Modelo.ConectorCliente;

/**
 *
 * @author danfigueroa
 */
public class Messenger {
    public static ConectorCliente Cliente;

    public static void iniciarcliente(String ip) {
        Cliente = new ConectorCliente(ip);
        // El cliente se conecta en segundo plano, y si no lo logra, manejará el error
        // El hilo sigue su ejecución y no bloquea el hilo principal
    }
    
    public static void iniciarcliente()
    {
        Cliente = new ConectorCliente();
       
    }

    public static void cerrarcliente() {
        if (Cliente != null) {
            Cliente.desconectar();
        }
    }
}