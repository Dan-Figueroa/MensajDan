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

public class ServidorMensajes extends Thread {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean isRunning = true;

    public ServidorMensajes(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Esperando conexión...");
            socket = serverSocket.accept();
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String mensaje;
            while ((mensaje = input.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensaje);
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el servidor: " + e.getMessage());
            }
        }
    }

    public void enviarMensaje(String mensaje) {
        if (output != null) {
            output.println(mensaje);
        }
    }
    
    public void cerrarServidor() {
        isRunning = false; // Detiene el bucle
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Libera el puerto
                System.out.println("Servidor cerrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}