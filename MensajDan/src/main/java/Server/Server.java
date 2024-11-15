/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Modelo.Conector;
import View.TelefonoView;

/**
 *
 * @author danfigueroa
 */
public class Server {

    private static Conector servidor;
    private TelefonoView mensaV;
    
    public static void iniciarserver() {
        if (servidor == null) {
            System.out.println("Iniciando el servidor...");
            servidor = new Conector();
            servidor.iniciarServidor(); // Usa el método correcto para iniciar el servidor
            System.out.println("Servidor iniciado.");
        } else {
            System.out.println("El servidor ya está en ejecución.");
        }
    }

    public static void cerrarserver() {
        if (servidor != null) {
            System.out.println("Cerrando el servidor...");
            servidor.detenerServidor(); // Detiene el servidor correctamente
            servidor = null; // Libera el recurso
            System.out.println("Servidor cerrado.");
        } else {
            System.out.println("El servidor no está en ejecución.");
        }
    }
}
