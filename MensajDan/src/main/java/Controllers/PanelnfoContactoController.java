/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Contactos;
import ModeloDao.ContactoDao;
import Servicios.ContactoServicio;
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
public class PanelnfoContactoController implements ActionListener{
    
    TelefonoView infoContactV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;
    private ContactoDao contactoDao;
    private String ip;
    private ContactoServicio service;

    public PanelnfoContactoController(TelefonoView infoContactV) {
        this.infoContactV = infoContactV;
        this.panelUtil = new PanelesVisibles();
        this.contactoDao = new ContactoDao();
        this.btn = new BotonesInvisibles();
        this.service = new ContactoServicio();
        this.infoContactV.jButtonRegre.addActionListener(this);
        this.infoContactV.jButtonActualizarContacto.addActionListener(this);
        botonesInvisible();
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.infoContactV.jButtonRegre == ae.getSource()){
            RegresarOptionChat();
        }else if(this.infoContactV.jButtonActualizarContacto == ae.getSource()){
            System.out.println("funciono");
            ObtenerDatosContacto();
        }
    }
    
    public void setUsuario(String ip) {
        this.ip = ip;
        System.out.println("La IP del usuario es: " + ip);
    }
    
    
    private void ObtenerDatosContacto(){
        if(service.Validacion(infoContactV.jTextFieldNameContacto)){
            String NuevoName = infoContactV.jTextFieldNameContacto.getText();
            String IpCon = infoContactV.jLabelIPContacto.getText();
            if(contactoDao.actualizarNombreContacto(ip, IpCon, NuevoName)){
               JOptionPane.showMessageDialog(null, "EL NUEVO NOMBRE A SIDO ACTUALILZADO CORRECTAMENTE");
            }else{
                JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR EL NUEVO NOMBRE DEL CONTACTO");
            }
        }
    }
    
    private void RegresarOptionChat(){
        panelUtil.mostrarPanel(infoContactV.jPanelChat);
        panelUtil.cerrarPanel(infoContactV.jPanelnfoContacto);
    }
    
    private void botonesInvisible(){
        btn.hacerBotonesInvisibles(infoContactV.jButtonRegre,infoContactV.jButtonActualizarContacto);
    }
    
}
