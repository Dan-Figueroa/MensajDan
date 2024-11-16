/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Contactos;
import ModeloDao.ContactoDao;
import Utils.BotonesInvisibles;
import Utils.PanelesVisibles;
import View.TelefonoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author david_alcazar
 */
public class PanelChatController implements ActionListener{
    
    TelefonoView chatV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;
    private String ip;

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
        }else if(this.chatV.jButtonBuscar == ae.getSource()){
            BuscarContactos();
        }
    }
    
    
    public void setUsuario(String ip) {
        this.ip = ip;
        System.out.println("La IP del usuario es: " + ip);
        Mostrar(); // Carga los contactos o la información del usuario si es necesario
    }
    
    private void BuscarContactos(){
        String nom = chatV.jTextFieldBuscarContac.getText();
        if(nom != null && !nom.isEmpty()){
            BuscarContacto(nom);
        }else{
            Mostrar();
        }
    }
    
    public void BuscarContacto(String nombre) {
        ContactoDao contDao = new ContactoDao();
        ArrayList<Contactos> contac = contDao.buscarContactosPorNombre(nombre, ip);
        if (contac.isEmpty()) {
            Mostrar(); 
        } else {
            JTable tabla1 = MostrarContactos(contac);
            chatV.jTable1.setModel(tabla1.getModel());
            chatV.jTable1.getColumnModel().getColumn(0).setPreferredWidth(203);
            chatV.jTable1.revalidate();
            chatV.jTable1.repaint();
        }
    }
    
    public void Mostrar() {
        ContactoDao contDao = new ContactoDao();
        ArrayList<Contactos> contac = contDao.obtenerNombresContactos(ip);
        System.out.println("Datos recuperados: " + contac.size() + " contactos."+ ip);
        JTable tabla1 = MostrarContactos(contac);
        chatV.jTable1.setModel(tabla1.getModel());
        chatV.jTable1.getColumnModel().getColumn(0).setPreferredWidth(203);
        chatV.jTable1.revalidate();
        chatV.jTable1.repaint();
    }
    
    public JTable MostrarContactos(ArrayList<Contactos> contacto) {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace todas las celdas no editables
            }
        };
        modeloTabla.addColumn("");
        for (Contactos gente : contacto) {
            modeloTabla.addRow(new Object[]{gente.getNombreCon()});
        }
        JTable tablaContactos = new JTable(modeloTabla);
        tablaContactos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaContactos.getColumnModel().getColumn(0).setPreferredWidth(203);
        return tablaContactos;
    }
    
}
