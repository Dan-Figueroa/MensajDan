/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author david_alcazar
 */
public class VerificarCampos {
    
     public static boolean verificarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
        
    public static boolean verificarCamposC(JPasswordField contrasena) {
        char[] contrasenaArray = contrasena.getPassword();
        if (contrasenaArray.length == 0) {
            JOptionPane.showMessageDialog(null, "La contraseña no puede estar vacía.");
            return false;
        }
        if (contrasenaArray.length < 8) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres.");
            return false;
        }
        return true; // La contraseña es válida
    }
    
    public boolean esIpValida(String ip) {
        // Expresión regular para validar una IP en formato IPv4
        String ipRegex = "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$";

        return ip != null && ip.matches(ipRegex);
    }

}
