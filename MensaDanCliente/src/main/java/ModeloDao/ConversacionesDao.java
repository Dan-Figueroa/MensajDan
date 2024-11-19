/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloDao;

import Conexion.Conexion;
import Modelo.Conversacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author david_alcazar
 */
public class ConversacionesDao {
    
    private final Conexion conexion = Conexion.getInstance();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public boolean agregarConversacion(Conversacion conversacion) {
        String sql = "INSERT INTO Conversacion (ipUsuario1, ipUsuario2) VALUES (?, ?)";
        boolean resultado = false;

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, conversacion.getIpUsuario1());
            ps.setString(2, conversacion.getIpUsuario2());

            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar conversación: " + e.getMessage());
        } finally {
            closeResources();
        }

        return resultado;
    }
    
     public ArrayList<Conversacion> obtenerConversacionesPorUsuario(String ipUsuario) {
        String sql = "SELECT * FROM Conversacion WHERE ipUsuario1 = ? OR ipUsuario2 = ?";
        ArrayList<Conversacion> conversaciones = new ArrayList<>();

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ipUsuario);
            ps.setString(2, ipUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                Conversacion conversacion = new Conversacion();
                conversacion.setIdConv(rs.getInt("idConv"));
                conversacion.setIpUsuario1(rs.getString("ipUsuario1"));
                conversacion.setIpUsuario2(rs.getString("ipUsuario2"));
                conversacion.setFechaInicio(rs.getString("fechaInicio"));

                conversaciones.add(conversacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conversaciones: " + e.getMessage());
        } finally {
            closeResources();
        }

        return conversaciones;
    }
     
    public boolean existeConversacion(String ipUsuario1, String ipUsuario2) {
        String sql = "SELECT * FROM Conversacion WHERE (ipUsuario1 = ? AND ipUsuario2 = ?) OR (ipUsuario1 = ? AND ipUsuario2 = ?)";
        boolean existe = false;

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ipUsuario1);
            ps.setString(2, ipUsuario2);
            ps.setString(3, ipUsuario2);
            ps.setString(4, ipUsuario1);
            rs = ps.executeQuery();

            if (rs.next()) {
                existe = true; // Si se encuentra una fila, significa que la conversación ya existe
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la existencia de la conversación: " + e.getMessage());
        } finally {
            closeResources();
        }

        return existe;
    }


    public Conversacion obtenerConversacion(String ipUsuario1, String ipUsuario2) {
        String sql = "SELECT * FROM Conversacion WHERE (ipUsuario1 = ? AND ipUsuario2 = ?) OR (ipUsuario1 = ? AND ipUsuario2 = ?)";
        Conversacion conversacion = null;

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ipUsuario1);
            ps.setString(2, ipUsuario2);
            ps.setString(3, ipUsuario2);
            ps.setString(4, ipUsuario1);
            rs = ps.executeQuery();

            if (rs.next()) {
                conversacion = new Conversacion();
                conversacion.setIdConv(rs.getInt("idConv"));
                conversacion.setIpUsuario1(rs.getString("ipUsuario1"));
                conversacion.setIpUsuario2(rs.getString("ipUsuario2"));
                conversacion.setFechaInicio(rs.getString("fechaInicio"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conversación: " + e.getMessage());
        } finally {
            closeResources();
        }

        return conversacion;
    }
    
    public int obtenerIdConversacion(String ipUsuario1, String ipUsuario2) {
        String sql = "SELECT idConv FROM Conversacion " +
                     "WHERE (ipUsuario1 = ? AND ipUsuario2 = ?) " +
                     "OR (ipUsuario1 = ? AND ipUsuario2 = ?)";
        int idConv = -1; // Si no se encuentra, devolvemos -1

        try {
            con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ipUsuario1);
            ps.setString(2, ipUsuario2);
            ps.setString(3, ipUsuario2);
            ps.setString(4, ipUsuario1);
            rs = ps.executeQuery();

            if (rs.next()) {
                idConv = rs.getInt("idConv"); // Obtenemos el id de la conversación
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener id de la conversación: " + e.getMessage());
        } finally {
            closeResources();
        }

        return idConv; // Retorna -1 si no se encuentra la conversación, o el id si existe
    }

    
    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
