/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Utils.BotonesInvisibles;
import Utils.PanelesVisibles;
import View.TelefonoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author david_alcazar
 */
public class PanelnfoContactoController implements ActionListener{
    
    TelefonoView infoContactV;
    private BotonesInvisibles btn;
    private PanelesVisibles panelUtil;

    public PanelnfoContactoController(TelefonoView infoContactV) {
        this.infoContactV = infoContactV;
        this.panelUtil = new PanelesVisibles();
        this.btn = new BotonesInvisibles();
        this.infoContactV.jButtonRegre.addActionListener(this);
        this.infoContactV.jButtonActualizarContacto.addActionListener(this);
        botonesInvisible();
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.infoContactV.jButtonRegre == ae.getSource()){
            RegresarOptionChat();
        }else if(this.infoContactV.jButtonActualizarContacto == ae.getSource()){
            
        }
    }
    
    
    private void ObtenerDatosContacto(){
        
    }
    
    private void RegresarOptionChat(){
        panelUtil.mostrarPanel(infoContactV.jPanelChat);
        panelUtil.cerrarPanel(infoContactV.jPanelnfoContacto);
    }
    
    private void botonesInvisible(){
        btn.hacerBotonesInvisibles(infoContactV.jButtonRegre,infoContactV.jButtonActualizarContacto);
    }
    
}
