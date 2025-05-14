package com.dam.finanzas.model.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
