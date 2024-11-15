/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import View.TelefonoView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author danfigueroa
 */
public class Conector extends Thread {
    private TelefonoView mensaV;
    private Socket s;
    private ServerSocket ss;
    private InputStreamReader entradaSocket;
    private DataOutputStream salida;
    private BufferedReader entrada;
    private final int puerto = 8000;
    private boolean servidorActivo = true;

    public Conector(TelefonoView mensaV) {
        this.mensaV = mensaV;
    }
    
     public Conector() {
        this.mensaV = null; // Si no hay vista asociada
    }

    public void iniciarServidor() {
        new Thread(() -> {
            try {
                ss = new ServerSocket(puerto);
                System.out.println("Servidor iniciado en el puerto " + puerto);

                while (servidorActivo) {
                    System.out.println("Esperando conexiones...");
                    s = ss.accept(); // Acepta la conexión de un cliente

                    System.out.println("Cliente conectado: " + s.getInetAddress());
                    entradaSocket = new InputStreamReader(s.getInputStream());
                    entrada = new BufferedReader(entradaSocket);
                    salida = new DataOutputStream(s.getOutputStream());

                    salida.writeUTF("****** CONECTADO *******\n \n");

                    // Inicia un nuevo hilo para manejar al cliente conectado
                    new Thread(this::gestionarCliente).start();
                }
            } catch (IOException e) {
                System.out.println("Error en el servidor: " + e.getMessage());
            }
        }).start();
    }

    private void gestionarCliente() {
        try {
            String texto;
            while ((texto = entrada.readLine()) != null) {
                System.out.println("Mensaje recibido: " + texto);
                if (mensaV != null) {
                    mensaV.jTextArea1.setText(mensaV.jTextArea1.getText() + "\n" + texto);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al gestionar cliente: " + e.getMessage());
        }
    }

    public void enviarMSG(String msg) {
        try {
            if (salida != null) {
                DateFormat hora = new SimpleDateFormat("HH:mm:ss");
                Date horaActual = new Date();
                salida.writeUTF(hora.format(horaActual) + " - " + msg + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al enviar mensaje: " + e.getMessage());
        }
    }

    public void detenerServidor() {
        servidorActivo = false;
        try {
            if (ss != null) {
                ss.close();
            }
            if (s != null) {
                s.close();
            }
            System.out.println("Servidor detenido.");
        } catch (IOException e) {
            System.out.println("Error al detener el servidor: " + e.getMessage());
        }
    }
}