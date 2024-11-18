/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Utils.VerificarCampos;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author david_alcazar
 */
public class NuevoUserServicio {
    private VerificarCampos verificar;

    public NuevoUserServicio() {
        this.verificar = new VerificarCampos();
    }
    
    public boolean validarCredenciales(JTextField nombre, JTextField ipField, JPasswordField passwordField) {
        if(!verificar.verificarCampos(nombre)){
            JOptionPane.showMessageDialog(null, "Ingrese su nombre, por favor");
            return false;
        }
        if (!verificar.verificarCampos(ipField)) {
            JOptionPane.showMessageDialog(null, "Ingrese su IP, por favor");
            return false;
        }
        if (!verificar.esIpValida(ipField.getText())) {
            JOptionPane.showMessageDialog(null, "Formato de IP inválido, por favor");
            return false;
        }
        if (!verificar.verificarCamposC(passwordField)) {
            return false;
        }
        return true; // Retorna true solo si todas las validaciones se cumplen
    }
    
    public boolean Validacion(JTextField nombre,JTextField informacion){
        if(!verificar.verificarCampos(nombre)){
            JOptionPane.showMessageDialog(null, "No deje vacio el nombre");
            return false;
        }if(!verificar.verificarCampos(informacion)){
            JOptionPane.showMessageDialog(null, "No deje vacio su informacion");
            return false;
        }
        return true;
    }
    
}
