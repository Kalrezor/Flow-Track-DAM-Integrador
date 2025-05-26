package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.dam.finanzas.model.ObjetivoFinanciero;
import com.dam.finanzas.model.bbdd.TablaObjetivoFinanciero;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class ObjetivosView {
    private List<ObjetivoFinanciero> objetivosList;
    private DefaultTableModel tableModel;
    private JTable table;
    private int idUsuarioActual;
    private TablaObjetivoFinanciero tablaObjetivoFinanciero;

    public ObjetivosView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
        this.tablaObjetivoFinanciero = new TablaObjetivoFinanciero();
        objetivosList = new ArrayList<>();
        initializeTableModel();
        cargarObjetivos();
    }

    private void initializeTableModel() {
        String[] columnNames = {"Descripción", "Costo Total", "Ahorro Mensual Sugerido", "Meses Necesarios", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
    }

    private void cargarObjetivos() {
        objetivosList = tablaObjetivoFinanciero.obtenerObjetivosPorUsuario(idUsuarioActual);
        updateTable();
    }

    public JPanel createObjetivosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        // Encabezado de la pantalla
        JLabel titleLabel = new JLabel("Gestión de Objetivos Financieros");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Panel para ingresar datos de objetivos
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos de entrada
        JTextField descripcionField = new JTextField("Descripción", 20);
        descripcionField.setForeground(Color.GRAY);
        descripcionField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Descripción:"));
        inputPanel.add(descripcionField);

        descripcionField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (descripcionField.getText().equals("Descripción")) {
                    descripcionField.setText("");
                    descripcionField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (descripcionField.getText().isEmpty()) {
                    descripcionField.setText("Descripción");
                    descripcionField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField costoField = new JTextField("Costo Total (€)", 20);
        costoField.setForeground(Color.GRAY);
        costoField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Costo Total (€):"));
        inputPanel.add(costoField);

        costoField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (costoField.getText().equals("Costo Total (€)")) {
                    costoField.setText("");
                    costoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (costoField.getText().isEmpty()) {
                    costoField.setText("Costo Total (€)");
                    costoField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField ingresosMensualesField = new JTextField("Ingresos Mensuales (€)", 20);
        ingresosMensualesField.setForeground(Color.GRAY);
        ingresosMensualesField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Ingresos Mensuales (€):"));
        inputPanel.add(ingresosMensualesField);

        ingresosMensualesField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ingresosMensualesField.getText().equals("Ingresos Mensuales (€)")) {
                    ingresosMensualesField.setText("");
                    ingresosMensualesField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ingresosMensualesField.getText().isEmpty()) {
                    ingresosMensualesField.setText("Ingresos Mensuales (€)");
                    ingresosMensualesField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField gastosMensualesField = new JTextField("Gastos Mensuales (€)", 20);
        gastosMensualesField.setForeground(Color.GRAY);
        gastosMensualesField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Gastos Mensuales (€):"));
        inputPanel.add(gastosMensualesField);

        gastosMensualesField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (gastosMensualesField.getText().equals("Gastos Mensuales (€)")) {
                    gastosMensualesField.setText("");
                    gastosMensualesField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (gastosMensualesField.getText().isEmpty()) {
                    gastosMensualesField.setText("Gastos Mensuales (€)");
                    gastosMensualesField.setForeground(Color.GRAY);
                }
            }
        });

        JTextField ahorroMensualDeseadoField = new JTextField("Ahorro Mensual Deseado (€)", 20);
        ahorroMensualDeseadoField.setForeground(Color.GRAY);
        ahorroMensualDeseadoField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Ahorro Mensual Deseado (€):"));
        inputPanel.add(ahorroMensualDeseadoField);

        ahorroMensualDeseadoField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ahorroMensualDeseadoField.getText().equals("Ahorro Mensual Deseado (€)")) {
                    ahorroMensualDeseadoField.setText("");
                    ahorroMensualDeseadoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ahorroMensualDeseadoField.getText().isEmpty()) {
                    ahorroMensualDeseadoField.setText("Ahorro Mensual Deseado (€)");
                    ahorroMensualDeseadoField.setForeground(Color.GRAY);
                }
            }
        });

        // Botón para agregar objetivo
        JButton addButton = new JButton("Agregar Objetivo");
        addButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        addButton.setBackground(new Color(44, 62, 80));
        addButton.setForeground(Color.WHITE);
        inputPanel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String descripcion = descripcionField.getText();
                    double costo = Double.parseDouble(costoField.getText());
                    double ingresosMensuales = Double.parseDouble(ingresosMensualesField.getText());
                    double gastosMensuales = Double.parseDouble(gastosMensualesField.getText());
                    double ahorroMensualDeseado = Double.parseDouble(ahorroMensualDeseadoField.getText());

                    if (ingresosMensuales <= 0 || gastosMensuales < 0) {
                        JOptionPane.showMessageDialog(panel, "Los ingresos deben ser mayores que cero y los gastos no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Calcular el ahorro mensual disponible
                    double ahorroMensualDisponible = ingresosMensuales - gastosMensuales;

                    if (ahorroMensualDeseado > ahorroMensualDisponible) {
                        JOptionPane.showMessageDialog(panel, "El ahorro mensual deseado excede el margen disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int mesesNecesarios = (int) Math.ceil(costo / ahorroMensualDeseado);

                    ObjetivoFinanciero nuevoObjetivo = new ObjetivoFinanciero(
                        0,
                        idUsuarioActual,
                        descripcion,
                        costo,
                        ahorroMensualDeseado,
                        "2023-12-31",
                        "En progreso"
                    );

                    tablaObjetivoFinanciero.registrarObjetivoFinanciero(nuevoObjetivo);
                    cargarObjetivos();

                    JOptionPane.showMessageDialog(
                        panel,
                        String.format("Con tus finanzas actuales, tardarás aproximadamente %d meses en cumplir este objetivo.", mesesNecesarios),
                        "Tiempo Estimado",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    descripcionField.setText("");
                    costoField.setText("");
                    ingresosMensualesField.setText("");
                    gastosMensualesField.setText("");
                    ahorroMensualDeseadoField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Por favor, ingresa valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Tabla de objetivos
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón para marcar un objetivo como completado
        JButton completeButton = new JButton("Marcar como Cumplido");
        completeButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        completeButton.setBackground(new Color(44, 62, 80));
        completeButton.setForeground(Color.WHITE);
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(panel, "Selecciona un objetivo para marcar como cumplido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ObjetivoFinanciero objetivo = objetivosList.get(selectedRow);
                objetivo.setEstado("Cumplido");
                tablaObjetivoFinanciero.actualizarObjetivoFinanciero(objetivo);
                cargarObjetivos();
            }
        });

        // Organizar todo
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(completeButton, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);

        return panel;
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (ObjetivoFinanciero objetivo : objetivosList) {
            Object[] rowData = {
                objetivo.getDescripcion(),
                String.format("%.2f €", objetivo.getCosto()),
                String.format("%.2f €", objetivo.getAhorroMensualSugerido()),
                objetivo.getFechaMeta(),
                objetivo.getEstado()
            };
            tableModel.addRow(rowData);
        }
    }
}