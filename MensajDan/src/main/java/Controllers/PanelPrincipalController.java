/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Conector;
import Modelo.Contactos;
import Modelo.Conversacion;
import Modelo.Mensaje;
import Modelo.Usuario;
import ModeloDao.ContactoDao;   
import ModeloDao.ConversacionesDao;
import ModeloDao.MensajeDao;
import ModeloDao.UsuarioDao;
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
public class PanelPrincipalController implements ActionListener{
    
    TelefonoView principalV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;
    private UsuarioDao useDao;
    private String ip;
    private String ipContacto;
    private String NombreUser;
    private boolean esServidor = false;
    

    public PanelPrincipalController(TelefonoView principalV,boolean esServidor) {
        this.principalV = principalV;
        this.esServidor = esServidor;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        this.useDao = new UsuarioDao();
        btn.hacerBotonesInvisibles(principalV.jButtonAgregarContacto, principalV.jButtonBuscarContacto, principalV.jButtonPerfil, principalV.jButtonChats);
        this.principalV.jButtonAgregarContacto.addActionListener(this);
        this.principalV.jButtonBuscarContacto.addActionListener(this);
        this.principalV.jButtonPerfil.addActionListener(this);
        this.principalV.jButtonChats.addActionListener(this);
        principalV.jTableContac.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = principalV.jTableContac.getSelectedRow();
                if (selectedRow != -1) {
                    String nombreContacto = principalV.jTableContac.getValueAt(selectedRow, 0).toString();
                    ipContacto = ObtenerIpContacto(nombreContacto);
                    CrearConversacion();
                    panelUtil.mostrarPanel(principalV.jPanelMensajeria);
                    principalV.mensajeC.mensaV.jLabelNombreContac.setText(nombreContacto);
                    principalV.mensajeC.mensaV.jLabelPruebaIPconta.setText(ipContacto);
                    principalV.jTableContac.clearSelection();
                    PasarDatos();
                    // Ejecutar la inicialización del servidor en un nuevo hilo
                    new Thread(() -> Servicios.Messeger.iniciarserver()).start();
                    panelUtil.cerrarPanel(principalV.jPanelPrincipal);
                }
            }
        });
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.principalV.jButtonAgregarContacto == ae.getSource()){
            panelUtil.mostrarPanel(principalV.jPanelChat);
            principalV.ChatContaC.inicializarVista(true);
            panelUtil.cerrarPanel(principalV.jPanelPrincipal);
        }else if(this.principalV.jButtonBuscarContacto == ae.getSource()){
            System.out.println("busco contacto");
            BuscarContactos();
        }else if(this.principalV.jButtonPerfil == ae.getSource()){
            panelUtil.mostrarPanel(principalV.jPanelUser);
            PasarId();
            panelUtil.cerrarPanel(principalV.jPanelPrincipal);
        }else if(this.principalV.jButtonChats == ae.getSource()){
            PasarId();
        }
    }
    
    public void inicializarVista(boolean cargarContactos) {
        if (cargarContactos) {
            Mostrar(); // Carga los contactos en la tabla
        } else {
            System.out.println("Vista inicializada sin cargar contactos.");
        }
    }
    
    private void CrearConversacion(){
        Conversacion conver = new Conversacion();
        conver.setIpUsuario1(ip);
        conver.setIpUsuario2(ipContacto);
        ConversacionesDao converDao = new ConversacionesDao();
        if(converDao.existeConversacion(conver.getIpUsuario1(), conver.getIpUsuario2())){
            conver.setIdConv(converDao.obtenerIdConversacion(conver.getIpUsuario1(), conver.getIpUsuario2()));
            principalV.mensajeC.setIdConversacion(conver.getIdConv());
            MensajeDao mensaDao = new MensajeDao();
            ArrayList <Mensaje> mensa = mensaDao.obtenerMensajesPorConversacion(conver.getIdConv());
            StringBuilder mensajesText = new StringBuilder();
            // Iterar sobre los mensajes y agregarlos al StringBuilder
            for (Mensaje mensaje : mensa) {
                // Puedes agregar el contenido del mensaje y la fecha, si lo deseas
                mensajesText.append("De: ").append(NombreUser).append("\n").append(mensaje.getContenido()).append("\n\n");
            }
            // Establecer el texto acumulado en el JTextArea
            principalV.mensajeC.mensaV.jTextArea1.setText(mensajesText.toString());
        }else{
            if(converDao.agregarConversacion(conver)){
                conver.setIdConv(converDao.obtenerIdConversacion(conver.getIpUsuario1(), conver.getIpUsuario2()));
                principalV.mensajeC.setIdConversacion(conver.getIdConv());
                MensajeDao mensaDao = new MensajeDao();
                ArrayList <Mensaje> mensa = mensaDao.obtenerMensajesPorConversacion(conver.getIdConv());
                StringBuilder mensajesText = new StringBuilder();
                // Iterar sobre los mensajes y agregarlos al StringBuilder
                for (Mensaje mensaje : mensa) {
                    // Puedes agregar el contenido del mensaje y la fecha, si lo deseas
                    mensajesText.append("De: ").append(mensaje.getIpUsuario()).append("\n").append(mensaje.getContenido()).append("\n\n");
                }
                // Establecer el texto acumulado en el JTextArea
                principalV.mensajeC.mensaV.jTextArea1.setText(mensajesText.toString());
            }
        }
    }
    
    public void setUsuario(String ip) {
        this.ip = ip;
        System.out.println("La IP del usuario es: " + ip);
        //Mostrar(); // Carga los contactos
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
    
    public void PasarId(){
        MostrarPerfil(useDao.obtenerUsuarioPorIp(ip));
    }
    
    public void MostrarPerfil(Usuario user){
        principalV.jTextFieldNombreUser.setText(user.getNombre());
        principalV.jTextFieldNombreUser.setEditable(false);
        principalV.jLabelIPUser.setText(user.getIpUsuario());
        principalV.jTextFieldInformacionUser.setText(user.getInformacion());
        principalV.jTextFieldInformacionUser.setEditable(false);
    }
    
    public void PasarDatos(){
        Usuario user = new Usuario();
        user = useDao.obtenerUsuarioPorIp(ip);
        user = useDao.obtenerUsuarioPorIp(ip);
        NombreUser= user.getNombre();
        principalV.mensajeC.setUsuario(user.getIpUsuario(), user.getNombre());
    }
    
    
    public String ObtenerIpContacto(String nom){
        ContactoDao contactoDao = new ContactoDao();
        String ipContacto = contactoDao.obtenerIpContactoPorNombre(nom, ip);
        if (ipContacto != null) {
            System.out.println("La IP del contacto es: " + ipContacto);
        } else {
            System.out.println("Contacto no encontrado.");
        }
        return ipContacto;
    }
}