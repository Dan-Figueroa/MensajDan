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
 */
public class PanelChatController implements ActionListener{
    
    TelefonoView chatV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;

    public PanelChatController(TelefonoView chatV) {
        this.chatV = chatV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        btn.hacerBotonesInvisibles(chatV.jButtonCerrarChats, chatV.jButtonBuscar, chatV.jButtonNuevoContacto, chatV.jButtonActualizarContacto, chatV.jButtonNuevoGrupo);
        this.chatV.jButtonCerrarChats.addActionListener(this);
        this.chatV.jButtonBuscar.addActionListener(this);
        this.chatV.jButtonNuevoContacto.addActionListener(this);
        this.chatV.jButtonActualizarContacto.addActionListener(this);
        this.chatV.jButtonNuevoGrupo.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.chatV.jButtonCerrarChats == ae.getSource()){
            panelUtil.mostrarPanel(chatV.jPanelPrincipal);
            panelUtil.cerrarPanel(chatV.jPanelChat);
        }else if(this.chatV.jButtonNuevoContacto == ae.getSource()){
            panelUtil.mostrarPanel(chatV.jPanelContacto);
            panelUtil.cerrarPanel(chatV.jPanelChat);
        }
    }
    
}
