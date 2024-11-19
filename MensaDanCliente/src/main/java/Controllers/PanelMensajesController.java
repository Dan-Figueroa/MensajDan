/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
import Modelo.Mensaje;
import ModeloDao.MensajeDao;
import Utils.BotonesInvisibles;
import Utils.LimpiarCampos;
import Utils.PanelesVisibles;
import View.TelefonoView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author david_alcazar
 * Controlador para controlar el envio de mensaje a cada contacto
 */
public class PanelMensajesController implements ActionListener{

    TelefonoView mensaV;
    private PanelesVisibles panelUtil;
    private LimpiarCampos limpiaCampo;
    private BotonesInvisibles btn;
    private String nombre;
    private String ip;
    private int id;
    DateFormat  hora = new SimpleDateFormat("HH:mm:ss");
    Date horaactual= new Date();

    public PanelMensajesController(TelefonoView mensaV) {
        this.mensaV = mensaV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        this.limpiaCampo = new LimpiarCampos();
        btn.hacerBotonesInvisibles(mensaV.jButtonRegresarMen,mensaV.jButtonEnviar );
        this.mensaV.jButtonRegresarMen.addActionListener(this);
        this.mensaV.jButtonEnviar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.mensaV.jButtonRegresarMen == ae.getSource()) {
            CerrarServidor();
        } else if (this.mensaV.jButtonEnviar == ae.getSource()) {
            EnviarMensaje();
            
        }
    }
    
    private void guardarMensajes(){
        MensajeDao mensaDao = new MensajeDao();
        Mensaje mensa = new Mensaje();
        mensa.setIdConv(id);
        mensa.setIpUsuario(ip);
        mensa.setEstado("leído");
        mensa.setContenido(mensaV.jTextFieldMensaje.getText());
        if(mensaDao.agregarMensaje(mensa)){
            System.out.println("mensaje agregado");
        }
    }
    
    private void EnviarMensaje(){
        Servicios.Messenger.Cliente.enviarMSG(nombre+" : \n"+
        this.mensaV.jTextFieldMensaje.getText());
        //this.mensaV.jTextArea1.setForeground(Color.BLUE);
        this.mensaV.jTextArea1.setText(this.mensaV.jTextArea1.getText()+" \n "+
        nombre + " : \n "+
        this.mensaV.jTextFieldMensaje.getText()+" : "+hora.format(horaactual));
        guardarMensajes();
        mensaV.jTextFieldMensaje.setText("");
    }
    
    private void CerrarServidor(){
        panelUtil.mostrarPanel(mensaV.jPanelPrincipal);
        Servicios.Messenger.cerrarcliente();
        limpiaCampo.limpiarTextAreas(mensaV.jTextArea1);
        panelUtil.cerrarPanel(mensaV.jPanelMensajeria);
        System.out.println("cerrado servidor");
    }
    
    public void limpiardatos(){
        limpiaCampo.limpiarTextFields(mensaV.jTextFieldMensaje);
        //limpiaCampo.limpiarTextAreas(mensaV.jTextArea1);
    }
    public void setUsuario(String ip, String nombre) {
        this.ip = ip;
        this.nombre = nombre;
    }
    
    public void setIdConversacion(int id){
        this.id = id;
    }
    
}