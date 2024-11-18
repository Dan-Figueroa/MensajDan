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
public class Messenger 
{
    public static ConectorCliente Cliente;
    
    public static void iniciarcliente()
    {
        Cliente = new ConectorCliente();
       
    }
    
    public static void iniciarcliente(String ip)
    {
        Cliente = new ConectorCliente(ip);
        Cliente.start();
    }
    
    public static void cerrarcliente()
    {
        Cliente = new ConectorCliente();
        Cliente.desconectar();
       
    }
    
}