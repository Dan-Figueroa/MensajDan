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
    private SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
    private Date horaActual = new Date();
    private Socket s;
    private ServerSocket ss;
    private InputStreamReader entradaSocket;
    private DataOutputStream salida;
    private BufferedReader entrada;
    private DataInputStream entradaBinaria;
    private String ip;
    private boolean conectado = false;
    private static final int PUERTO = 8000;
    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY = 2000;

    // Constructor para el servidor (modo servidor aceptando conexión)
    public ConectorCliente() {
        try {
            ss = new ServerSocket(PUERTO);
            System.out.println("Esperando conexión...");
            s = ss.accept(); // Espera conexión entrante
            inicializarStreams();
            conectado = true;
            System.out.println("Cliente conectado.");
        } catch (IOException e) {
            System.out.println("Error al iniciar servidor: " + e.getMessage());
        }
    }

    // Constructor para el cliente (modo cliente conectándose a un servidor)
    public ConectorCliente(String ip) {
        this.ip = ip;
        start(); // Inicia el hilo de conexión en segundo plano
    }

    // Inicializa flujos de entrada y salida
    private void inicializarStreams() throws IOException {
        entradaSocket = new InputStreamReader(s.getInputStream());
        entrada = new BufferedReader(entradaSocket);
        entradaBinaria = new DataInputStream(s.getInputStream());
        salida = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        int retries = 0;
        while (retries < MAX_RETRIES && !conectado) {
            try {
                System.out.println("Intentando conectar al servidor...");
                s = new Socket(ip, PUERTO); // Conexión al servidor
                inicializarStreams();
                conectado = true;
                System.out.println("Conexión establecida.");
            } catch (IOException e) {
                retries++;
                System.out.println("Error al conectar, reintentando... (" + retries + "/" + MAX_RETRIES + ")");
                try {
                    Thread.sleep(RETRY_DELAY);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (!conectado) {
            System.out.println("No se pudo conectar al servidor después de " + MAX_RETRIES + " intentos.");
        }

        if (conectado) {
            escucharMensajes(); // Comienza a escuchar mensajes si la conexión se establece
        }
    }

    // Método para escuchar mensajes
    private void escucharMensajes() {
        while (conectado) {
            try {
                String texto = entradaBinaria.readUTF();
                SwingUtilities.invokeLater(() -> {
                    TelefonoView.jTextArea1.setText(
                        TelefonoView.jTextArea1.getText() + "\n" + texto
                    );
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

    // Enviar mensajes
    public void enviarMSG(String msg) {
        if (!conectado) return;
        try {
            horaActual = new Date();
            salida.writeUTF(hora.format(horaActual) + " - " + msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Leer un mensaje (opcional)
    public String leerMSG() {
        try {
            return entrada.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Desconectar
    public void desconectar() {
        conectado = false;
        try {
            if (s != null) s.close();
            if (ss != null) ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Verifica si está conectado
    public boolean isConectado() {
        return conectado;
    }
}