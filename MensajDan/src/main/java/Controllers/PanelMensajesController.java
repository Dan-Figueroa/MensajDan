/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Utils.BotonesInvisibles;
import Utils.PanelesVisibles;
import View.TelefonoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author david_alcazar
 * Controlador para controlar el envio de mensaje a cada contacto
 */
public class PanelMensajesController implements ActionListener{

    TelefonoView mensaV;
    private PanelesVisibles panelUtil;
    private BotonesInvisibles btn;

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
            EnviarMensaje();
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
    
}
