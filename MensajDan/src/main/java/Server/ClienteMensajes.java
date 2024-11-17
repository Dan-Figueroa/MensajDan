/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author danfigueroa
 */
import java.io.*;
import java.net.*;

public class ClienteMensajes {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean isRunning = true;

    public ClienteMensajes(String ip, int puerto) {
        try {
            socket = new Socket(ip, puerto);
            System.out.println("Conectado al servidor: " + ip + ":" + puerto);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error al conectar al servidor: " + e.getMessage());
        }
    }

    public void enviarMensaje(String mensaje) {
        if (output != null) {
            output.println(mensaje);
        }
    }

    public void escucharMensajes() {
        new Thread(() -> {
            try {
                String mensaje;
                while ((mensaje = input.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                }
            } catch (IOException e) {
                System.out.println("Error al recibir mensajes: " + e.getMessage());
            }
        }).start();
    }
    
    public void cerrarCliente() {
        isRunning = false; // Detiene cualquier lectura activa
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // Cierra la conexión con el servidor
                System.out.println("Cliente cerrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
