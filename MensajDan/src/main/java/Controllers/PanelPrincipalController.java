/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Contactos;
import Modelo.Usuario;
import ModeloDao.ContactoDao;
import Server.Server;
import Utils.BotonesInvisibles;
import Utils.PanelesVisibles;
import Utils.VerificarCampos;
import View.TelefonoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author david_alcazar
 */
public class PanelPrincipalController implements ActionListener{
    
    TelefonoView principalV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;
    private String ip;

    public PanelPrincipalController(TelefonoView principalV) {
        this.principalV = principalV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        btn.hacerBotonesInvisibles(principalV.jButtonAgregarContacto, principalV.jButtonBuscarContacto, principalV.jButtonPerfil, principalV.jButtonChats);
        this.principalV.jButtonAgregarContacto.addActionListener(this);
        this.principalV.jButtonBuscarContacto.addActionListener(this);
        this.principalV.jButtonPerfil.addActionListener(this);
        this.principalV.jButtonChats.addActionListener(this);
        principalV.jTableContac.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Solo actúa cuando la selección finaliza
                    int selectedRow = principalV.jTableContac.getSelectedRow();
                    if (selectedRow != -1) {
                        String nombreContacto = principalV.jTableContac.getValueAt(selectedRow, 0).toString();
                        System.out.println("Contacto seleccionado: " + nombreContacto);
                        panelUtil.mostrarPanel(principalV.jPanelMensajeria);
                        principalV.mensajeC.mensaV.jLabelNombreContac.setText(nombreContacto);
                        Server.iniciarserver();  
                        panelUtil.cerrarPanel(principalV.jPanelPrincipal);
                    }
                }
            }
        });
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.principalV.jButtonAgregarContacto == ae.getSource()){
            panelUtil.mostrarPanel(principalV.jPanelChat);
            panelUtil.cerrarPanel(principalV.jPanelPrincipal);
        }else if(this.principalV.jButtonBuscarContacto == ae.getSource()){
            System.out.println("busco contacto");
            BuscarContactos();
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
    
    public void BuscarContactos(){
        String nombre = principalV.jTextFieldBuscarContacto.getText();
        if (nombre != null && !nombre.isEmpty()) {
            BuscarContacto(nombre);  // Solo llamamos a BuscarContacto si el nombre no está vacío
        } else {
            System.out.println("El campo de búsqueda está vacío.");
            Mostrar();
        }
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
        tablaContactos.getColumnModel().getColumn(0).setPreferredWidth(202);
        return tablaContactos;
    }
    
    public void Mostrar() {
        ContactoDao contDao = new ContactoDao();
        ArrayList<Contactos> contac = contDao.obtenerNombresContactos(ip);
        System.out.println("Datos recuperados: " + contac.size() + " contactos."+ ip);
        JTable tabla1 = MostrarContactos(contac);
        principalV.jTableContac.setModel(tabla1.getModel());
        principalV.jTableContac.getColumnModel().getColumn(0).setPreferredWidth(202);
        principalV.jTableContac.revalidate();
        principalV.jTableContac.repaint();
    }
    
    public void BuscarContacto(String nombre) {
     ContactoDao contDao = new ContactoDao();
     ArrayList<Contactos> contac = contDao.buscarContactosPorNombre(nombre, ip);
     if (contac.isEmpty()) {
         Mostrar(); 
     } else {
         JTable tabla1 = MostrarContactos(contac);
         principalV.jTableContac.setModel(tabla1.getModel());
         principalV.jTableContac.getColumnModel().getColumn(0).setPreferredWidth(202);
         principalV.jTableContac.revalidate();
         principalV.jTableContac.repaint();
     }
 }



}
