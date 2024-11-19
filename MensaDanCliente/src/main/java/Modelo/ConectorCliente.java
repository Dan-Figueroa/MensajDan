/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import View.TelefonoView;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingUtilities;

/**
 *
 * @author danfigueroa
 */
public class ConectorCliente extends Thread {
    private TelefonoView mensaV;
    private Socket s;
    private ServerSocket ss;
    private String ip;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private static final int PUERTO = 8000;
    private boolean conectado = false; // Inicialmente no está conectado
    private static final int MAX_RETRIES = 5; // Número máximo de reintentos
    private static final int RETRY_DELAY = 2000; // Retraso entre reintentos (en milisegundos)

    // Constructor para el cliente
    public ConectorCliente(String ip) {
        this.ip = ip;
        // Inicia el hilo que intentará conectarse en segundo plano
        start();
    }

    @Override
    public void run() {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                System.out.println("Intentando conectar al servidor...");
                s = new Socket(ip, PUERTO); // Intentamos conectar al servidor
                entrada = new DataInputStream(s.getInputStream());
                salida = new DataOutputStream(s.getOutputStream());
                conectado = true;
                System.out.println("Conexión establecida.");
                break; // Si se conecta, salimos del bucle
            } catch (IOException e) {
                retries++;
                System.out.println("Error al conectar, reintentando... (" + retries + "/" + MAX_RETRIES + ")");
                try {
                    Thread.sleep(RETRY_DELAY); // Esperamos antes de reintentar
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (retries == MAX_RETRIES) {
            System.out.println("No se pudo conectar al servidor después de " + MAX_RETRIES + " intentos.");
            conectado = false;
        }

        // Si se ha conectado, se inicia la recepción de mensajes
        if (conectado) {
            escucharMensajes();
        }
    }

    // Método para escuchar mensajes desde el servidor
    private void escucharMensajes() {
        while (conectado) {
            try {
                String texto = entrada.readUTF();
                SwingUtilities.invokeLater(() -> {
                    TelefonoView.jTextArea1.setText(TelefonoView.jTextArea1.getText() + "\n" + texto);
                });
            } catch (EOFException | SocketException e) {
                System.out.println("El servidor se ha desconectado.");
                desconectar();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                desconectar();
                break;
            }
        }
    }

    public void enviarMSG(String msg) {
        if (!conectado) return;
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Date horaActual = new Date();
        try {
            salida.writeUTF(hora.format(horaActual) + " - " + msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        conectado = false;
        try {
            if (s != null) s.close();
            if (ss != null) ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConectado() {
        return conectado;
    }
}