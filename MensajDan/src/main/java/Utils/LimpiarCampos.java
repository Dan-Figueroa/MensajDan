/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author david_alcazar
 */
public class LimpiarCampos {
    
    public void limpiarTextFields(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }
    
    public void limpiarPasswordFields(JPasswordField... campos) {
        for (JPasswordField campo : campos) {
            campo.setText("");
        }
    }
    
    public void limpiarTextAreas(JTextArea...campos) {
        for (JTextArea campo : campos) {
            campo.setText("");
        }
    }
}
