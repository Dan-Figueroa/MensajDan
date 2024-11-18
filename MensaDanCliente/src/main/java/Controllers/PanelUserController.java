/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import ModeloDao.UsuarioDao;
import Servicios.NuevoUserServicio;
import Utils.BotonesInvisibles;
import Utils.PanelesVisibles;
import View.TelefonoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 *
 * @author david_alcazar
 */
public class PanelUserController implements ActionListener{
    
    TelefonoView userV;
    private BotonesInvisibles btn;
    private final PanelesVisibles panelUtil;
    private NuevoUserServicio service;

    public PanelUserController(TelefonoView userV) {
        this.userV = userV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        this.service = new NuevoUserServicio();
        BotonesInvisibles();
        this.userV.jButtonActualizarUser.addActionListener(this);
        this.userV.jButtonCerrarSesion.addActionListener(this);
        this.userV.jButtonChatPrincipal.addActionListener(this);
        this.userV.jButtonHabilitarJtext.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.userV.jButtonCerrarSesion == ae.getSource()){
            CerrarSesion();
        }else if(this.userV.jButtonChatPrincipal == ae.getSource()){
            Volverprincivpal();
        }else if(userV.jButtonHabilitarJtext == ae.getSource()){
            HabilitarJtextfield();
        }else if(userV.jButtonActualizarUser == ae.getSource()){
            ActualizarUser();
        }
    }
    
    private void Volverprincivpal(){
        panelUtil.mostrarPanel(userV.jPanelPrincipal);
        userV.jTextFieldInformacionUser.setEditable(false);
        userV.jTextFieldNombreUser.setEditable(false);
        panelUtil.cerrarPanel(userV.jPanelUser);
    }
    
    private void HabilitarJtextfield(){
        userV.jTextFieldInformacionUser.setEditable(true);
        userV.jTextFieldNombreUser.setEditable(true);
    }
    
    private void CerrarSesion(){
        panelUtil.mostrarPanel(userV.jPanelLoggin);
        panelUtil.cerrarPanel(userV.jPanelUser);
    }
    
    private void ActualizarUser(){
        UsuarioDao userdao = new UsuarioDao();
        if(service.Validacion(userV.jTextFieldNombreUser, userV.jTextFieldInformacionUser)){
            String Ip = userV.jLabelIPUser.getText();
            String nombre = userV.jTextFieldNombreUser.getText();
            String info = userV.jTextFieldInformacionUser.getText();
            if(userdao.actualizarNombreEInformacion(Ip, nombre, info)){
                JOptionPane.showMessageDialog(null, "SUS DATOS HAN SIDO ACTUALILZADO CORRECTAMENTE");
            }else{
                JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR SUS DATOS");
            }
        }
    }
    
    private void BotonesInvisibles(){
        btn.hacerBotonesInvisibles(userV.jButtonCerrarSesion,userV.jButtonChatPrincipal, userV.jButtonHabilitarJtext, userV.jButtonActualizarUser);
    }
}
