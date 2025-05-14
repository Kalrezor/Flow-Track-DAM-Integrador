package com.dam.finanzas.model;

import com.dam.finanzas.model.bbdd.ConexionBBDD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorDatos {

    public static Usuario autenticarUsuario(String correo, String contrasena) {
        String sql = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";
        Usuario usuario = null;

        try (Connection conn = new ConexionBBDD().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, contrasena);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    correo,
                    contrasena
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public static void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario(nombre, correo, contraseña) VALUES(?, ?, ?)";

        try (Connection conn = new ConexionBBDD().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getContrasena());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
