/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
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
        panelUtil.mostrarPanel(mensaV.jPanelPrincipal);
        panelUtil.cerrarPanel(mensaV.jPanelMensajeria);
        Servicios.Messeger.cerrarserver();
        System.out.println("cerrado servidor");
    } else if (this.mensaV.jButtonEnviar == ae.getSource()) {
        mensaV.jTextArea1.setEditable(false);//checa si funciona esto para que no se puedan borrar o escribir en el textarea
        Servicios.Messeger.Servidor.enviarMSG(this.mensaV.jLabelNombreContac.getText()+" : \n"+
        this.mensaV.jTextFieldMensaje.getText());
        this.mensaV.jTextArea1.setForeground(Color.black);
        this.mensaV.jTextArea1.setText(this.mensaV.jTextArea1.getText()+"\n"+
        this.mensaV.jLabelNombreContac.getText()+" : \n"+
        this.mensaV.jTextFieldMensaje.getText()+" : "+hora.format(horaactual));
        mensaV.jTextFieldMensaje.setText("");
    }
}
    
    public void setUsuario(String ip, String nombre) {
        this.ip = ip;
        this.nombre = nombre;
    }
    
    public void limpiardatos(){
        limpiaCampo.limpiarTextFields(mensaV.jTextFieldMensaje);
        //limpiaCampo.limpiarTextAreas(mensaV.jTextArea1);
    }
}
