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
    private ContactoDao contactoDao;
    private String ipContacto;

    public PanelChatController(TelefonoView chatV) {
        this.chatV = chatV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        btn.hacerBotonesInvisibles(chatV.jButtonCerrarChats, chatV.jButtonBuscar, chatV.jButtonNuevoContacto, chatV.jButtonNuevoGrupo);
        this.chatV.jButtonCerrarChats.addActionListener(this);
        this.chatV.jButtonBuscar.addActionListener(this);
        this.chatV.jButtonNuevoContacto.addActionListener(this);
        this.chatV.jButtonNuevoGrupo.addActionListener(this);
        chatV.jTable1.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = chatV.jTable1.getSelectedRow();
                    if (selectedRow != -1) {
                        String nombreContacto = chatV.jTable1.getValueAt(selectedRow, 0).toString();
                        String ipContacto = ObtenerIpContacto(nombreContacto);
                        MostrarDatosDelContacto();
                        panelUtil.mostrarPanel(chatV.jPanelnfoContacto);
                        chatV.jTable1.clearSelection();
                        panelUtil.cerrarPanel(chatV.jPanelChat);
                    }
                }
            });
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.chatV.jButtonCerrarChats == ae.getSource()){
            regresar();
        }else if(this.chatV.jButtonNuevoContacto == ae.getSource()){
            panelUtil.mostrarPanel(chatV.jPanelContacto);
            panelUtil.cerrarPanel(chatV.jPanelChat);
        }else if(this.chatV.jButtonBuscar == ae.getSource()){
            BuscarContactos();
        }
    }
    
    public void regresar(){
        panelUtil.mostrarPanel(chatV.jPanelPrincipal);
        chatV.pricipalC.inicializarVista(true);
        panelUtil.cerrarPanel(chatV.jPanelChat);
    }
    
    public void inicializarVista(boolean cargarContactos) {
        if (cargarContactos) {
            Mostrar(); // Carga los contactos en la tabla
        } else {
            System.out.println("Vista inicializada sin cargar contactos.");
        }
    }
    
    public void setUsuario(String ip) {
        this.ip = ip;
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
    
    public String ObtenerIpContacto(String nom){
        contactoDao = new ContactoDao();
        ipContacto = contactoDao.obtenerIpContactoPorNombre(nom, ip);
        if (ipContacto != null) {
            System.out.println("La IP del contacto es: " + ipContacto);
        } else {
            System.out.println("Contacto no encontrado.");
        }
        return ipContacto;
    }
    
    private void MostrarDatosDelContacto(){
        contactoDao = new ContactoDao();
        Contactos contacto = new Contactos();
        contacto = contactoDao.obtenerInformacionContacto(ip, ipContacto);
        chatV.jTextFieldNameContacto.setText(contacto.getNombreCon());
        chatV.jLabelIPContacto.setText(contacto.getIpUsCont());
        chatV.jLabelInformacionContacto.setText(contacto.getSetInformacionContacto());
    }
    
}
