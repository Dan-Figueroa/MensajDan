/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import View.TelefonoView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingUtilities;

/**
 *
 * @author danfigueroa
 */
    public class Conector extends Thread {
    private Socket s;
    private ServerSocket ss;
    private DataInputStream entrada; // Cambiado a DataInputStream
    private DataOutputStream salida;
    private boolean running = true;
    private final int puerto = 8000;
    TelefonoView mensaV;
    SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
    Date horaActual = new Date();

    public Conector() {
        try {
            ss = new ServerSocket(puerto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Conector(String ip) {
        try {
            s = new Socket(ip, puerto); // Establecer conexión como servidor remoto
            System.out.println("Conexión establecida con el cliente en " + ip + ":" + puerto);
            entrada = new DataInputStream(s.getInputStream());
            salida = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                System.out.println("Esperando conexión...");
                s = ss.accept();
                entrada = new DataInputStream(s.getInputStream());
                salida = new DataOutputStream(s.getOutputStream());
                salida.writeUTF("Conexión establecida.\n");

                while (running) {
                    String texto = entrada.readUTF();
                    SwingUtilities.invokeLater(() -> {
                        TelefonoView.jTextArea1.setText(TelefonoView.jTextArea1.getText()+"\n"+texto);
                    });
                }
            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                }
            } finally {
                cerrarRecursos();
            }
        }
    }

   public void enviarMSG(String msg) {
        try {
            if (salida != null) {
                salida.writeUTF(hora.format(horaActual) + " - " + msg + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void detener() {
        running = false;
        cerrarRecursos();
    }

    private void cerrarRecursos() {
        try {
            if (s != null) s.close();
            if (ss != null) ss.close();
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}