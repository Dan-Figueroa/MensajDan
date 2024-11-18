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
public class ConectorCliente extends Thread
{
    TelefonoView mensaV;
    private Socket s;
    private ServerSocket ss;
    private InputStreamReader entradaSocket;  
    private DataOutputStream salida;          
    private BufferedReader entrada;           
    final int puerto = 8000;   
    
    
    public ConectorCliente()
    {
        try{
           ss=new ServerSocket(puerto);   
           s=ss.accept();
           entradaSocket=new InputStreamReader(s.getInputStream());
           entrada=new BufferedReader(entradaSocket);     
           salida=new DataOutputStream(s.getOutputStream());   
        }catch(Exception e){};
    
    }
    public ConectorCliente(String ip)
    {
        try{

           s=new Socket(ip,this.puerto);      
           entradaSocket = new InputStreamReader(s.getInputStream());
           entrada = new BufferedReader(entradaSocket);       
           salida = new DataOutputStream(s.getOutputStream());

         }catch(Exception e){};
     }
     public void run()
     {             
         String texto="text";
         while(true)
         {
             try{
                  texto=entrada.readLine();
                   mensaV.jTextArea1.setText(mensaV.jTextArea1.getText()+"\n"+texto);
                }catch(IOException e){};
         }
     }
    public void enviarMSG(String msg) {
        DateFormat hora = new SimpleDateFormat("HH:mm:ss");
        Date horaActual = new Date();

        try {
            salida.writeUTF(hora.format(horaActual) + " - " + msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
     public String leerMSG(){ 
       try{
            return entrada.readLine();
          }catch(IOException e){};
       return null;
    }
     
    public void desconectar(){  
        try{
            s.close();
           }catch(IOException e){};
        try{
            ss.close();
            }catch(IOException e){};
     }   
}
