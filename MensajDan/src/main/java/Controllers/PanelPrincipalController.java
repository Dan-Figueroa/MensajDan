/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Contactos;
import Modelo.Usuario;
import ModeloDao.ContactoDao;
import Utils.BotonesInvisibles;
import Utils.PanelesVisibles;
import Utils.VerificarCampos;
import View.TelefonoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author david_alcazar
 */
public class PanelPrincipalController implements ActionListener{
    
    TelefonoView principalV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;
    String ip;

    public PanelPrincipalController(TelefonoView principalV) {
        this.principalV = principalV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        btn.hacerBotonesInvisibles(principalV.jButtonAgregarContacto, principalV.jButtonBuscarContacto, principalV.jButtonPerfil, principalV.jButtonChats);
        this.principalV.jButtonAgregarContacto.addActionListener(this);
        this.principalV.jButtonBuscarContacto.addActionListener(this);
        this.principalV.jButtonPerfil.addActionListener(this);
        this.principalV.jButtonChats.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.principalV.jButtonAgregarContacto == ae.getSource()){
            panelUtil.mostrarPanel(principalV.jPanelChat);
            panelUtil.cerrarPanel(principalV.jPanelPrincipal);
        }else if(this.principalV.jButtonBuscarContacto == ae.getSource()){
            System.out.println("busco contacto");
            Mostrar();
        }else if(this.principalV.jButtonPerfil == ae.getSource()){
            panelUtil.mostrarPanel(principalV.jPanelUser);
            panelUtil.cerrarPanel(principalV.jPanelPrincipal);
        }else if(this.principalV.jButtonChats == ae.getSource()){

        }
    }
    
    public void setUsuario(String ip) {
        this.ip = ip;
        System.out.println("La IP del usuario es: " + ip);
        Mostrar(); // Carga los contactos o la información del usuario si es necesario
    }

    
    
    public JTable MostrarContactos(ArrayList<Contactos> contacto){
         DefaultTableModel modeloTabla = new DefaultTableModel();
         modeloTabla.addColumn("");
         for (Contactos gente : contacto) {
            modeloTabla.addRow(new Object[]{
                gente.getNombreCon(),
            });
        }
        JTable tablaContactos = new JTable(modeloTabla);
        tablaContactos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaContactos.getColumnModel().getColumn(0).setPreferredWidth(200); 
        return tablaContactos;
    }
    
    public void Mostrar() {
        ContactoDao contDao = new ContactoDao();
        ArrayList<Contactos> contac = contDao.obtenerNombresContactos(ip);
        System.out.println("Datos recuperados: " + contac.size() + " contactos."+ ip);
        JTable tabla1 = MostrarContactos(contac);
        principalV.jTableContac.setModel(tabla1.getModel());
        principalV.jTableContac.getColumnModel().getColumn(0).setPreferredWidth(200);
        principalV.jTableContac.revalidate();
        principalV.jTableContac.repaint();
    }


}
