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
public class PanelUserController implements ActionListener{
    
    TelefonoView userV;
    private BotonesInvisibles btn;
    private final PanelesVisibles panelUtil;

    public PanelUserController(TelefonoView userV) {
        this.userV = userV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        btn.hacerBotonesInvisibles(userV.jButtonCerrarSesion,userV.jButtonChatPrincipal, userV.jButtonUser, userV.jButtonHabilitarJtext);
        this.userV.jButtonCerrarSesion.addActionListener(this);
        this.userV.jButtonChatPrincipal.addActionListener(this);
        this.userV.jButtonUser.addActionListener(this);
        this.userV.jButtonHabilitarJtext.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.userV.jButtonCerrarSesion == ae.getSource()){
            panelUtil.mostrarPanel(userV.jPanelLoggin);
            panelUtil.cerrarPanel(userV.jPanelUser);
        }else if(this.userV.jButtonChatPrincipal == ae.getSource()){
            panelUtil.mostrarPanel(userV.jPanelPrincipal);
            userV.jTextFieldInformacionUser.setEditable(false);
            userV.jTextFieldNombreUser.setEditable(false);
            panelUtil.cerrarPanel(userV.jPanelUser);
        }else if(this.userV.jButtonUser == ae.getSource()){
            System.out.println("user ahi mismo");
        }else if(userV.jButtonHabilitarJtext == ae.getSource()){
            userV.jTextFieldInformacionUser.setEditable(true);
            userV.jTextFieldNombreUser.setEditable(true);
        }
    }
    
}
