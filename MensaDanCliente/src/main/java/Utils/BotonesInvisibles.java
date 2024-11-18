/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.JButton;

/**
 *
 * @author david_alcazar
 */
public class BotonesInvisibles {
    
        public static void hacerBotonesInvisibles(JButton... botones) {
        for (JButton boton : botones) {
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setOpaque(false);
        }
    }
}
