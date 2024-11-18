/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Contactos;
import Modelo.Usuario;
import ModeloDao.ContactoDao;
import Servicios.ContactoServicio;
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
public class PanelContactoController implements ActionListener{

    TelefonoView ContacV;
    private BotonesInvisibles btn;
    private Usuario usuario;
    private PanelesVisibles panelUtil;
    ContactoServicio service;
    private Contactos contacto;
    private ContactoDao contaDao;
    private String ip;

    public PanelContactoController(TelefonoView ContacV) {
        this.ContacV = ContacV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        this.service = new ContactoServicio();
        BotonesInvisibles();
        this.ContacV.jButtonCrearContac.addActionListener(this);
        this.ContacV.jButtonRegresarContac.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.ContacV.jButtonCrearContac == ae.getSource()){
            ObtenerDatos();
        }else if(this.ContacV.jButtonRegresarContac == ae.getSource()){
            Regresar();
        }
    }
    
    private void Regresar(){
       panelUtil.mostrarPanel(ContacV.jPanelPrincipal);
       ContacV.pricipalC.inicializarVista(true);
       panelUtil.cerrarPanel(ContacV.jPanelContacto);
    }
    
    private void ObtenerDatos(){
        if(service.validarCredenciales(ContacV.jTextFieldNomContac, ContacV.jTextFieldIPContac)){
            contacto = new Contactos();
            contaDao = new ContactoDao();
            contacto.setNombreCon(ContacV.jTextFieldNomContac.getText());
            contacto.setIpUsCont(ContacV.jTextFieldIPContac.getText());
            contacto.setIpUsuario(ip);
            if(contaDao.agregarContacto(contacto)){
                JOptionPane.showMessageDialog(null, "CONTACTO AGREGADO");
            }else{
                JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR EL CONTACTO");
            }
        }
    }
    
    public void setUsuario(String ip) {
        this.ip = ip;
    }
    
    private void BotonesInvisibles(){
        btn.hacerBotonesInvisibles(ContacV.jButtonCrearContac, ContacV.jButtonRegresarContac);
    }
    
}
