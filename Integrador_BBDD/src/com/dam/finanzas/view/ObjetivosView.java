package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.dam.finanzas.model.ObjetivoFinanciero;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class ObjetivosView {
    private List<ObjetivoFinanciero> objetivosList; // Lista para almacenar los objetivos
    private DefaultTableModel tableModel;
    private JTable table;
    private int idUsuarioActual;

    public ObjetivosView() {
    	this.idUsuarioActual = idUsuarioActual;
        objetivosList = new ArrayList<>();
        initializeTableModel();
    }
    
    private void initializeTableModel() {
    	String[] columnNames = {"Descripción", "Costo Total", "Ahorro Mensual Disponible", "Meses Necesarios", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
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
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
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

        JTextField gananciasMensualesField = new JTextField("Ganancias Mensuales (€)", 20);
        gananciasMensualesField.setForeground(Color.GRAY);
        gananciasMensualesField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Ganancias Mensuales (€):"));
        inputPanel.add(gananciasMensualesField);

        gananciasMensualesField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (gananciasMensualesField.getText().equals("Ganancias Mensuales (€)")) {
                    gananciasMensualesField.setText("");
                    gananciasMensualesField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (gananciasMensualesField.getText().isEmpty()) {
                    gananciasMensualesField.setText("Ganancias Mensuales (€)");
                    gananciasMensualesField.setForeground(Color.GRAY);
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

        // Botón para agregar objetivo
        JButton addButton = new JButton("Agregar Objetivo");
        addButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        addButton.setBackground(new Color(44, 62, 80));
        addButton.setForeground(Color.WHITE);
        inputPanel.add(addButton);

        addButton.addActionListener((ActionEvent e) -> {
            try {
                String descripcion = descripcionField.getText();
                double costo = Double.parseDouble(costoField.getText());
                double gananciasMensuales = Double.parseDouble(gananciasMensualesField.getText());
                double gastosMensuales = Double.parseDouble(gastosMensualesField.getText());

                if (gananciasMensuales <= 0 || gastosMensuales < 0) {
                    JOptionPane.showMessageDialog(panel, "Las ganancias deben ser mayores que cero y los gastos no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Calcular el ahorro mensual disponible
                double ahorroMensualDisponible = gananciasMensuales - gastosMensuales;

                if (ahorroMensualDisponible <= 0) {
                    JOptionPane.showMessageDialog(panel, "No tienes ahorro mensual disponible para alcanzar este objetivo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Calcular el tiempo necesario para alcanzar el objetivo
                int mesesNecesarios = (int) Math.ceil(costo / ahorroMensualDisponible);

                // Crear un nuevo objetivo financiero
                ObjetivoFinanciero nuevoObjetivo = new ObjetivoFinanciero(
                    0, // ID del objetivo (se generará en la base de datos)
                    1, // ID del usuario (deberías obtenerlo de la sesión actual)
                    descripcion,
                    costo,
                    ahorroMensualDisponible,
                    "2023-12-31", // Fecha meta (deberías calcularla o establecerla)
                    "En progreso" // Estado inicial
                );
                objetivosList.add(nuevoObjetivo);

                // Actualizar la tabla
                updateTable();

                // Mostrar mensaje con el tiempo estimado
                JOptionPane.showMessageDialog(
                    panel,
                    String.format("Con tus finanzas actuales, tardarás aproximadamente %d meses en cumplir este objetivo.", mesesNecesarios),
                    "Tiempo Estimado",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Limpiar los campos
                descripcionField.setText("");
                costoField.setText("");
                gananciasMensualesField.setText("");
                gastosMensualesField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Por favor, ingresa valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tabla de objetivos
        String[] columnNames = {"Descripción", "Costo Total", "Ahorro Mensual", "Fecha Meta", "Estado"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false); // Desactivar la capacidad de reordenar las columnas
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón para marcar un objetivo como completado
        JButton completeButton = new JButton("Marcar como Cumplido");
        completeButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        completeButton.setBackground(new Color(44, 62, 80));
        completeButton.setForeground(Color.WHITE);
        completeButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "Selecciona un objetivo para marcar como cumplido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ObjetivoFinanciero objetivo = objetivosList.get(selectedRow);
            objetivo.setEstado("Cumplido");
            updateTable();
        });

        // Organizar todo
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(completeButton, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);

        return panel;
    }

    /*private void updateTable() {
        // Implementa este método para actualizar la tabla con los datos de objetivosList
        tableModel.setRowCount(0);
        for (ObjetivoFinanciero objetivo : objetivosList) {
            Object[] rowData = {
                objetivo.getDescripcion(),
                String.format("%.2f €", objetivo.getCosto()),
                String.format("%.2f €", objetivo.getAhorroMensual()),
                objetivo.getFechaMeta(),
                objetivo.getEstado()
            };
            tableModel.addRow(rowData);
        }
    }*/


    // Método para actualizar la tabla
    private void updateTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < objetivosList.size(); i++) {
            ObjetivoFinanciero objetivo = objetivosList.get(i);
            Object[] rowData = {
                i + 1,
                objetivo.getDescripcion(),
                String.format("%.2f €", objetivo.getCosto()),
                String.format("%.2f €", objetivo.getAhorroMensualSugerido()),
                //objetivo.getMesesNecesarios(),
                objetivo.getEstado()
            };
            tableModel.addRow(rowData);
        }
    }
}
