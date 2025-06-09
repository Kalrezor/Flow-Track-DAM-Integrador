package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.dam.finanzas.model.Deuda;
import com.dam.finanzas.model.ObjetivoFinanciero;
import com.dam.finanzas.model.bbdd.TablaDeuda;
import com.dam.finanzas.model.bbdd.TablaGastos;
import com.dam.finanzas.model.bbdd.TablaIngresos;
import com.dam.finanzas.model.bbdd.TablaObjetivoFinanciero;
import com.dam.finanzas.model.bbdd.TablaTransferencia;
import java.awt.*;
import java.util.List;

public class EstadisticasView {
	private int idUsuarioActual;
    private DefaultTableModel objetivosTableModel;
    private JTable objetivosTable;

    public EstadisticasView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
    }

    public void actualizarTablaObjetivos() {
        objetivosTableModel.setRowCount(0);
        TablaObjetivoFinanciero tablaObjetivoFinanciero = new TablaObjetivoFinanciero();
        List<ObjetivoFinanciero> objetivosList = tablaObjetivoFinanciero.obtenerObjetivosPorUsuario(idUsuarioActual);

        for (ObjetivoFinanciero objetivo : objetivosList) {
            Object[] rowData = {
                objetivo.getDescripcion(),
                String.format("%.2f €", objetivo.getCosto()),
                String.format("%.2f €", objetivo.getAhorroMensualSugerido()),
                objetivo.getTiempoNecesario(),
                objetivo.getEstado()
            };
            objetivosTableModel.addRow(rowData);
        }
    }

    private JPanel createObjetivosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Objetivos"));

        String[] objetivosColumnNames = {"Descripción", "Costo Total", "Ahorro Mensual Sugerido", "Tiempo Necesario", "Estado"};
        objetivosTableModel = new DefaultTableModel(objetivosColumnNames, 0);
        objetivosTable = new JTable(objetivosTableModel);

        JScrollPane objetivosScrollPane = new JScrollPane(objetivosTable);
        panel.add(objetivosScrollPane, BorderLayout.CENTER);

        // Cargar datos inicialmente
        actualizarTablaObjetivos();

        return panel;
    }

    public JPanel createEstadisticasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Estadísticas Financieras");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel estadisticasContainerPanel = new JPanel(new BorderLayout());
        estadisticasContainerPanel.setBackground(Color.LIGHT_GRAY);

        JLabel estadisticasLabel = new JLabel("Estadísticas");
        estadisticasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        estadisticasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        estadisticasContainerPanel.add(estadisticasLabel, BorderLayout.NORTH);

        JPanel finanzasPanel = createFinanzasPanel();
        estadisticasContainerPanel.add(finanzasPanel, BorderLayout.CENTER);

        panel.add(estadisticasContainerPanel, BorderLayout.NORTH);

        JPanel tablesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        tablesPanel.setBackground(Color.LIGHT_GRAY);
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel transferenciasPanel = createTransferenciasPanel();
        tablesPanel.add(transferenciasPanel);

        JPanel deudasPanel = createDeudasPanel();
        tablesPanel.add(deudasPanel);

        JPanel objetivosPanel = createObjetivosPanel();
        tablesPanel.add(objetivosPanel);

        panel.add(tablesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFinanzasPanel() {
        JPanel finanzasPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        finanzasPanel.setBackground(new Color(192, 192, 192));

        JPanel ingresosPanel = new JPanel(new BorderLayout());
        ingresosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel ingresosLabel = new JLabel("Ingresos");
        ingresosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel ingresosValueLabel = new JLabel("0 €");
        ingresosValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingresosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosPanel.add(ingresosLabel, BorderLayout.NORTH);
        ingresosPanel.add(ingresosValueLabel, BorderLayout.CENTER);

        JPanel gastosPanel = new JPanel(new BorderLayout());
        gastosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel gastosLabel = new JLabel("Gastos");
        gastosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gastosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel gastosValueLabel = new JLabel("0 €");
        gastosValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gastosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosPanel.add(gastosLabel, BorderLayout.NORTH);
        gastosPanel.add(gastosValueLabel, BorderLayout.CENTER);

        finanzasPanel.add(ingresosPanel);
        finanzasPanel.add(gastosPanel);

        actualizarTotales(ingresosValueLabel, gastosValueLabel);

        return finanzasPanel;
    }

    private void actualizarTotales(JLabel ingresosValueLabel, JLabel gastosValueLabel) {
        TablaIngresos tablaIngresos = new TablaIngresos();
        double totalIngresos = tablaIngresos.obtenerTotalIngresos(idUsuarioActual);

        TablaGastos tablaGastos = new TablaGastos();
        double totalGastos = tablaGastos.obtenerTotalGastos(idUsuarioActual);

        ingresosValueLabel.setText(String.format("%.2f €", totalIngresos));
        gastosValueLabel.setText(String.format("%.2f €", totalGastos));
    }

    private JPanel createTransferenciasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Transferencias"));

        TablaTransferencia tablaTransferencia = new TablaTransferencia();
        Object[][] transferenciasData = tablaTransferencia.obtenerTransferencias(idUsuarioActual);

        String[] transferenciasColumnNames = {"Remitente", "Destinatario", "Monto"};
        DefaultTableModel transferenciasTableModel = new DefaultTableModel(transferenciasData, transferenciasColumnNames);
        JTable transferenciasTable = new JTable(transferenciasTableModel);

        JScrollPane transferenciasScrollPane = new JScrollPane(transferenciasTable);
        panel.add(transferenciasScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDeudasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Deudas"));

        TablaDeuda tablaDeuda = new TablaDeuda();
        List<Deuda> deudasList = tablaDeuda.obtenerDeudasPorUsuario(idUsuarioActual);

        String[] deudasColumnNames = {"Descripción", "Monto Total", "Monto Pendiente", "Fecha Vencimiento", "Estado"};
        DefaultTableModel deudasTableModel = new DefaultTableModel(deudasColumnNames, 0);

        for (Deuda deuda : deudasList) {
            Object[] rowData = {
                deuda.getDescripcion(),
                String.format("%.2f €", deuda.getMontoTotal()),
                String.format("%.2f €", deuda.getMontoPendiente()),
                deuda.getFechaVencimiento(),
                deuda.getEstado()
            };
            deudasTableModel.addRow(rowData);
        }

        JTable deudasTable = new JTable(deudasTableModel);
        JScrollPane deudasScrollPane = new JScrollPane(deudasTable);
        panel.add(deudasScrollPane, BorderLayout.CENTER);

        return panel;
    }

}