/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.JPanel;

/**
 *
 * @author david_alcazar
 */
public class PanelesVisibles {
       
    public void mostrarPanel(JPanel panelAMostrar) {
        panelAMostrar.setVisible(true);
    }
    
     public void cerrarPanel(JPanel panelCerrar){
        panelCerrar.setVisible(false);
    }
     
    public void inicializar(JPanel inicioSesion, JPanel nuevoUser, JPanel principal, JPanel User, JPanel Chat, JPanel Mensajes, JPanel Contac, JPanel infoContac) {
        inicioSesion.setVisible(true);
        nuevoUser.setVisible(false);
        principal.setVisible(false);
        User.setVisible(false);
        Chat.setVisible(false);
        Mensajes.setVisible(false);
        Contac.setVisible(false);
        infoContac.setVisible(false);
    }
}
