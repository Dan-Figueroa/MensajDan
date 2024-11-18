/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Usuario;
import ModeloDao.UsuarioDao;
import Servicios.NuevoUserServicio;
import Utils.BotonesInvisibles;
import Utils.LimpiarCampos;
import Utils.PanelesVisibles;
import View.TelefonoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author david_alcazar
 */
public class PanelNuevoUserController implements ActionListener{
    
    TelefonoView nuevoUser;
    private BotonesInvisibles btn;
    NuevoUserServicio service;
    private final PanelesVisibles panelUtil;
    private Usuario usuario;
    private LimpiarCampos limpiarCampos;
    private UsuarioDao useDao;

    public PanelNuevoUserController(TelefonoView nuevoUser) {
        this.nuevoUser = nuevoUser;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        this.service = new NuevoUserServicio();
        this.limpiarCampos = new LimpiarCampos();
        OcultarBotones();
        this.nuevoUser.jButtonCrearUser.addActionListener(this);
        this.nuevoUser.jButtonRegresarNuevoUser.addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.nuevoUser.jButtonCrearUser == ae.getSource()){
            ObtenerDatos();
        }else if(this.nuevoUser.jButtonRegresarNuevoUser == ae.getSource()){
            regresar();
        }
    }
    
    private void regresar(){
        panelUtil.mostrarPanel(nuevoUser.jPanelLoggin);
        panelUtil.cerrarPanel(nuevoUser.jPaneNuevoUser);
    }
    
    private void ObtenerDatos(){
        if(service.validarCredenciales(nuevoUser.jTextFieldNom, nuevoUser.jTextFieldIPRegis, nuevoUser.jPasswordFieldContraRegis)){
            usuario = new Usuario();
            useDao = new UsuarioDao();
            usuario.setNombre(nuevoUser.jTextFieldNom.getText());
            usuario.setIpUsuario(nuevoUser.jTextFieldIPRegis.getText());
            usuario.setContraseña(new String(nuevoUser.jPasswordFieldContraRegis.getPassword()));
            if(useDao.agregarUsuario(usuario)){
                JOptionPane.showMessageDialog(null, "USUARIO CREADO");
                panelUtil.mostrarPanel(nuevoUser.jPanelLoggin);
                panelUtil.cerrarPanel(nuevoUser.jPaneNuevoUser); 
                limpiardatos();
                
            }else{
                JOptionPane.showMessageDialog(null, "ERROR AL CREAR USUARIO");
            }
        }
    }
    
    private void limpiardatos(){
        limpiarCampos.limpiarTextFields(nuevoUser.jTextFieldNom);
        limpiarCampos.limpiarPasswordFields(nuevoUser.jPasswordFieldContraRegis);
    }
    
    private void OcultarBotones(){
        btn.hacerBotonesInvisibles(nuevoUser.jButtonCrearUser, nuevoUser.jButtonRegresarNuevoUser);
    }

}
