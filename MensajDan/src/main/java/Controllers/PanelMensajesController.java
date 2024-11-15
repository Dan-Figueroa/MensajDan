/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Conector;
import Server.Server;
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
    public static Conector conector;
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
            Server.cerrarserver();
        }else if(this.mensaV.jButtonEnviar == ae.getSource()){
            conector.enviarMSG(this.mensaV.jLabelNombreContac.getText()+" : \n"+
            this.mensaV.jTextFieldMensaje.getText());
            this.mensaV.jTextArea1.setForeground(Color.black);
            this.mensaV.jTextArea1.setText(this.mensaV.jTextArea1.getText()+"\n"+
            this.mensaV.jLabelNombreContac.getText()+" : \n"+
            this.mensaV.jTextFieldMensaje.getText()+" : "+hora.format(horaactual));
            mensaV.jTextFieldMensaje.setText("");
            }
    }
}
