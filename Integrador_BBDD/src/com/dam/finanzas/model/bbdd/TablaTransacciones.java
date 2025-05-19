package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TablaTransacciones {

    private ConexionBBDD conBBDD;

    public TablaTransacciones() {
        conBBDD = new ConexionBBDD();
    }

    public int registrarIngreso(int idUsuario, double monto, String descripcion) {
        int res = 0;
        String query = "INSERT INTO Ingreso (id_usuario, descripcion, monto, fecha) VALUES (?, ?, ?, CURRENT_DATE)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            pstmt.setString(2, descripcion);
            pstmt.setDouble(3, monto);

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public int registrarGasto(int idUsuario, double monto, String descripcion, String categoria) {
        int res = 0;
        String query = "INSERT INTO Gasto (id_usuario, descripcion, categoria, monto, fecha) VALUES (?, ?, ?, ?, CURRENT_DATE)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            pstmt.setString(2, descripcion);
            pstmt.setString(3, categoria);
            pstmt.setDouble(4, monto);

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public double obtenerTotalIngresos(int idUsuario) {
        double total = 0;
        String query = "SELECT SUM(monto) AS total FROM Ingreso WHERE id_usuario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
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

        return total;
    }

    public double obtenerTotalGastos(int idUsuario) {
        double total = 0;
        String query = "SELECT SUM(monto) AS total FROM Gasto WHERE id_usuario = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idUsuario);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
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

        return total;
    }

    public int registrarTransferencia(int idOrigen, int idDestino, double monto, String descripcion) {
        int res = 0;
        String query = "INSERT INTO Transferencia (id_origen, id_destino, monto, descripcion) VALUES (?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = conBBDD.getConexion();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idOrigen);
            pstmt.setInt(2, idDestino);
            pstmt.setDouble(3, monto);
            pstmt.setString(4, descripcion);

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }
}
