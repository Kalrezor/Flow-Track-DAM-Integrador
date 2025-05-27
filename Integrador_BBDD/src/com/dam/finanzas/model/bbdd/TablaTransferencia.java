package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dam.finanzas.model.Transferencia;

public class TablaTransferencia {

    static final String NOM_TABLA_TRAN = "Transferencia";
    static final String NOM_COL_ID_TRAN = "id_transferencia";
    static final String NOM_COL_ID_ORI = "id_origen";
    static final String NOM_COL_ID_DES = "id_destino";
    static final String NOM_COL_MONTO_TRAN = "monto";
    static final String NOM_COL_DESC_TRAN = "descripcion";

    private ConexionBBDD conBBDD;

    public TablaTransferencia() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarTransferencia(Transferencia transferencia) {
        int res = 0;
        String query = "INSERT INTO " + NOM_TABLA_TRAN + "(" + NOM_COL_ID_ORI
                + ", " + NOM_COL_ID_DES + ", " + NOM_COL_MONTO_TRAN + ", " + NOM_COL_DESC_TRAN + ") VALUES (?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, transferencia.getIdOrigen());
            pstmt.setInt(2, transferencia.getIdDestino());
            pstmt.setDouble(3, transferencia.getMonto());
            pstmt.setString(4, transferencia.getDescripcion());

            res = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public Object[][] obtenerTransferencias(int idUsuario) {
        List<Object[]> transferencias = new ArrayList<>();
        String query = "SELECT id_origen, id_destino, monto, descripcion FROM Transferencia WHERE id_origen = ? OR id_destino = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idUsuario);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idOrigen = rs.getInt("id_origen");
                int idDestino = rs.getInt("id_destino");
                double monto = rs.getDouble("monto");
                String descripcion = rs.getString("descripcion");

                String remitente = obtenerNombreUsuario(idOrigen, "Usuario Desconocido");
                String destinatario = obtenerNombreUsuario(idDestino, "Usuario Desconocido");

                // Determinar si el usuario es el remitente o el destinatario
                String usuarioRemitente = (idOrigen == idUsuario) ? "Tú" : remitente;
                String usuarioDestinatario = (idDestino == idUsuario) ? "Tú" : destinatario;

                transferencias.add(new Object[]{usuarioRemitente, usuarioDestinatario, monto});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Object[][] data = new Object[transferencias.size()][3];
        for (int i = 0; i < transferencias.size(); i++) {
            data[i] = transferencias.get(i);
        }

        return data;
    }

    private String obtenerNombreUsuario(int idUsuario, String nombrePorDefecto) {
        String nombreUsuario = nombrePorDefecto; // Usar el nombre proporcionado por defecto
        String query = "SELECT nombre FROM Usuario WHERE id_usuario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                nombreUsuario = rs.getString("nombre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return nombreUsuario;
    }

}
