/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Modelo.Usuario;
import ModeloDao.UsuarioDao;
import Servicios.logginServicio;
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
public class PanelLogginController implements ActionListener{
    
    private TelefonoView logginV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;
    private logginServicio service;
    private Usuario usuario;
    private UsuarioDao useDao;
    private LimpiarCampos limpiaCampo;

    public PanelLogginController(TelefonoView logginV) {
        this.logginV = logginV;
        this.btn = new BotonesInvisibles();
        this.panelUtil = new PanelesVisibles();
        this.service = new logginServicio();
        this.useDao = new UsuarioDao();
        this.limpiaCampo = new LimpiarCampos();
        btn.hacerBotonesInvisibles(logginV.jButtonIngresarLoggin, logginV.jButtonRegistrarseLoggin);
        this.logginV.jButtonIngresarLoggin.addActionListener(this);
        this.logginV.jButtonRegistrarseLoggin.addActionListener(this);
        panelUtil.inicializar(logginV.jPanelLoggin, logginV.jPaneNuevoUser, logginV.jPanelPrincipal, logginV.jPanelUser, logginV.jPanelChat, logginV.jPanelMensajeria, logginV.jPanelContacto, logginV.jPanelnfoContacto);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.logginV.jButtonIngresarLoggin == ae.getSource()){
            auten();
        }else if(this.logginV.jButtonRegistrarseLoggin == ae.getSource()){
            panelUtil.mostrarPanel(logginV.jPaneNuevoUser);
            panelUtil.cerrarPanel(logginV.jPanelLoggin);
        }
    }
    
    public void auten(){
        Usuario usuarioAutenticado = validarInicioSesion();
        if (usuarioAutenticado != null) {
            logginV.pricipalC.setUsuario(usuarioAutenticado.getIpUsuario());
            logginV.pricipalC.inicializarVista(true);
            logginV.ChatContaC.setUsuario(usuarioAutenticado.getIpUsuario());
            logginV.contactoC.setUsuario(usuarioAutenticado.getIpUsuario());
            logginV.infoContacC.setUsuario(usuarioAutenticado.getIpUsuario());
            System.out.println("Inicio de sesión exitoso. IP del usuario: " + usuarioAutenticado.getIpUsuario());
            panelUtil.mostrarPanel(logginV.jPanelPrincipal);
            panelUtil.cerrarPanel(logginV.jPanelLoggin);
            limpiardatos();
        } else {
            JOptionPane.showMessageDialog(logginV, "Inicio de sesión fallido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiardatos(){
        limpiaCampo.limpiarTextFields(logginV.jTextFieldIngresaIP);
        limpiaCampo.limpiarPasswordFields(logginV.jPasswordFieldLoggin);
    }

    
    public Usuario validarInicioSesion() {
        if (service.validarCredenciales(logginV.jTextFieldIngresaIP, logginV.jPasswordFieldLoggin)) {
            usuario = new Usuario();
            usuario.setIpUsuario(logginV.jTextFieldIngresaIP.getText());
            usuario.setContraseña(new String(logginV.jPasswordFieldLoggin.getPassword()));
            // Verifica si las credenciales son correctas
            if (useDao.ingresar(usuario.getIpUsuario(), usuario.getContraseña())) {
                //MostrarPerfil(useDao.obtenerUsuarioPorIp(usuario.getIpUsuario()));
                panelUtil.mostrarPanel(logginV.jPanelPrincipal);
                panelUtil.cerrarPanel(logginV.jPanelLoggin);
                return usuario; // Retorna el usuario si se autentica correctamente
            } else {
                JOptionPane.showMessageDialog(null, "IP Y LA CONTRASEÑA NO COINCIDEN");
            }
        }
        return null; // Retorna null si la autenticación falla
    }
    
    
}
