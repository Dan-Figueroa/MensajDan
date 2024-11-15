/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Conector;
import Utils.BotonesInvisibles;
import Utils.PanelesVisibles;
import View.TelefonoView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author david_alcazar
 * Controlador para controlar el envio de mensaje a cada contacto
 */
public class PanelMensajesController implements ActionListener{

    TelefonoView mensaV;
    private PanelesVisibles panelUtil;
    private BotonesInvisibles btn;
    public static Conector Servidor;
    DateFormat  hora = new SimpleDateFormat("HH:mm:ss");
    Date horaactual= new Date();

    public PanelMensajesController(TelefonoView mensaV) {
        this.mensaV = mensaV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        btn.hacerBotonesInvisibles(mensaV.jButtonRegresarMen,mensaV.jButtonEnviar );
        this.mensaV.jButtonRegresarMen.addActionListener(this);
        this.mensaV.jButtonEnviar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.mensaV.jButtonRegresarMen == ae.getSource()){
            panelUtil.mostrarPanel(mensaV.jPanelPrincipal);
            panelUtil.cerrarPanel(mensaV.jPanelMensajeria);
        }else if(this.mensaV.jButtonEnviar == ae.getSource()){
            Servidor.enviarMSG(this.mensaV.jLabelNombreContac.getText()+" : \n"+
            this.mensaV.jTextFieldMensaje.getText());
            this.mensaV.jTextArea1.setForeground(Color.black);
            this.mensaV.jTextArea1.setText(this.mensaV.jTextArea1.getText()+"\n"+
            this.mensaV.jLabelNombreContac.getText()+" : \n"+
            this.mensaV.jTextFieldMensaje.getText()+" : "+hora.format(horaactual));
            mensaV.jTextFieldMensaje.setText("");
            }
    }
    
    public void EnviarMensaje(){
        String mensaje = mensaV.jTextFieldMensaje.getText();
        if (mensaje != null && !mensaje.trim().isEmpty()) {
            mensaV.jTextArea1.append("Yo: " + mensaje + "\n");
            mensaV.jTextFieldMensaje.setText("");
        } else {
            System.out.println("El campo de mensaje está vacío.");
        }
    }
    
     public static void iniciarserver(){
        Servidor = new Conector();
        Servidor.start();
    }
    
     //Messenger.iniciarserver(); esto debe de ir cuando se selecciona el contacto para activar el servidor en el boton 
}
