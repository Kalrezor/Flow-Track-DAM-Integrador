package com.dam.finanzas.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import com.dam.finanzas.model.Deuda;
import com.dam.finanzas.model.bbdd.TablaDeuda;

public class DeudasView {
    private JTextField txtDescripcion;
    private JTextField txtMontoTotal;
    private JTextField txtMontoPendiente;
    private JTextField txtFechaVencimiento;
    private JButton btnRegistrarDeuda;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Deuda> deudasList;
    private int idUsuarioActual; // ID del usuario actual

    public DeudasView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
        deudasList = new ArrayList<>();
        initializeTableModel();
        cargarDeudasUsuario(); // Cargar deudas al iniciar
    }

    private void initializeTableModel() {
        String[] columnNames = {"Descripción", "Monto Total", "Monto Pendiente", "Fecha Vencimiento", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
    }

    private void cargarDeudasUsuario() {
        TablaDeuda tablaDeuda = new TablaDeuda();
        deudasList = tablaDeuda.obtenerDeudasPorUsuario(idUsuarioActual);
        actualizarTablaDeudas(); // Actualizar la tabla con los datos cargados
    }

    public JPanel createDeudasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        // Encabezado de la pantalla
        JLabel titleLabel = new JLabel("Gestión de Deudas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Panel para ingresar datos de deudas
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos de entrada
        txtDescripcion = new JTextField("Descripción", 20);
        txtDescripcion.setForeground(Color.GRAY);
        txtDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Descripción:"));
        inputPanel.add(txtDescripcion);

        txtDescripcion.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDescripcion.getText().equals("Descripción")) {
                    txtDescripcion.setText("");
                    txtDescripcion.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDescripcion.getText().isEmpty()) {
                    txtDescripcion.setText("Descripción");
                    txtDescripcion.setForeground(Color.GRAY);
                }
            }
        });

        txtMontoTotal = new JTextField("Monto Total", 20);
        txtMontoTotal.setForeground(Color.GRAY);
        txtMontoTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Monto Total (€):"));
        inputPanel.add(txtMontoTotal);

        txtMontoTotal.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMontoTotal.getText().equals("Monto Total")) {
                    txtMontoTotal.setText("");
                    txtMontoTotal.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtMontoTotal.getText().isEmpty()) {
                    txtMontoTotal.setText("Monto Total");
                    txtMontoTotal.setForeground(Color.GRAY);
                }
            }
        });

        txtMontoPendiente = new JTextField("Monto Pendiente", 20);
        txtMontoPendiente.setForeground(Color.GRAY);
        txtMontoPendiente.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Monto Pendiente (€):"));
        inputPanel.add(txtMontoPendiente);

        txtMontoPendiente.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMontoPendiente.getText().equals("Monto Pendiente")) {
                    txtMontoPendiente.setText("");
                    txtMontoPendiente.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtMontoPendiente.getText().isEmpty()) {
                    txtMontoPendiente.setText("Monto Pendiente");
                    txtMontoPendiente.setForeground(Color.GRAY);
                }
            }
        });

        txtFechaVencimiento = new JTextField("Fecha Vencimiento", 20);
        txtFechaVencimiento.setForeground(Color.GRAY);
        txtFechaVencimiento.setFont(new Font("Tahoma", Font.PLAIN, 14));
        inputPanel.add(new JLabel("Fecha Vencimiento:"));
        inputPanel.add(txtFechaVencimiento);

        txtFechaVencimiento.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFechaVencimiento.getText().equals("Fecha Vencimiento")) {
                    txtFechaVencimiento.setText("");
                    txtFechaVencimiento.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtFechaVencimiento.getText().isEmpty()) {
                    txtFechaVencimiento.setText("Fecha Vencimiento");
                    txtFechaVencimiento.setForeground(Color.GRAY);
                }
            }
        });

        btnRegistrarDeuda = new JButton("Registrar Deuda");
        btnRegistrarDeuda.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRegistrarDeuda.setBackground(new Color(44, 62, 80));
        btnRegistrarDeuda.setForeground(Color.WHITE);
        inputPanel.add(btnRegistrarDeuda);

        btnRegistrarDeuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarDeuda();
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);

        // Tabla de deudas
        String[] columnNames = {"Descripción", "Monto Total", "Monto Pendiente", "Fecha Vencimiento", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false); // Desactivar la capacidad de reordenar las columnas
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón para editar deuda
        JButton editButton = new JButton("Editar Deuda");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarDeuda(table);
            }
        });

        // Organizar todo
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(editButton, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(bottomPanel, BorderLayout.CENTER);

        return panel;
    }

    private void registrarDeuda() {
        String descripcion = txtDescripcion.getText();
        String montoTotalStr = txtMontoTotal.getText();
        String montoPendienteStr = txtMontoPendiente.getText();
        String fechaVencimiento = txtFechaVencimiento.getText();

        if (descripcion.isEmpty() || montoTotalStr.isEmpty() || montoPendienteStr.isEmpty() || fechaVencimiento.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double montoTotal = Double.parseDouble(montoTotalStr);
            double montoPendiente = Double.parseDouble(montoPendienteStr);

            Deuda nuevaDeuda = new Deuda(idUsuarioActual, montoTotal, montoPendiente, fechaVencimiento, descripcion, "EN PROGRESO");
            TablaDeuda tablaDeuda = new TablaDeuda();
            int resultado = tablaDeuda.registrarDeuda(nuevaDeuda);

            if (resultado > 0) {
                cargarDeudasUsuario(); // Recargar deudas después de registrar una nueva
                JOptionPane.showMessageDialog(null, "Deuda registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la deuda", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa valores válidos para los montos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtDescripcion.setText("Descripción");
        txtDescripcion.setForeground(Color.GRAY);
        txtMontoTotal.setText("Monto Total");
        txtMontoTotal.setForeground(Color.GRAY);
        txtMontoPendiente.setText("Monto Pendiente");
        txtMontoPendiente.setForeground(Color.GRAY);
        txtFechaVencimiento.setText("Fecha Vencimiento");
        txtFechaVencimiento.setForeground(Color.GRAY);
    }

    private void editarDeuda(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una deuda para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Deuda deudaSeleccionada = deudasList.get(selectedRow);

        String nuevoMontoPendienteStr = JOptionPane.showInputDialog(
            null,
            "Ingrese el nuevo monto pendiente:",
            "Editar Monto Pendiente",
            JOptionPane.PLAIN_MESSAGE
        );

        if (nuevoMontoPendienteStr != null) {
            try {
                double nuevoMontoPendiente = Double.parseDouble(nuevoMontoPendienteStr);
                deudaSeleccionada.setMontoPendiente(nuevoMontoPendiente);
                deudaSeleccionada.setEstado(nuevoMontoPendiente > 0 ? "EN PROGRESO" : "FINALIZADO");

                TablaDeuda tablaDeuda = new TablaDeuda();
                int resultado = tablaDeuda.actualizarDeuda(deudaSeleccionada);

                if (resultado > 0) {
                    cargarDeudasUsuario(); // Recargar deudas después de editar
                    JOptionPane.showMessageDialog(null, "Deuda actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la deuda", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa un valor válido para el monto pendiente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTablaDeudas() {
        tableModel.setRowCount(0);
        for (Deuda deuda : deudasList) {
            Object[] rowData = {
                deuda.getDescripcion(),
                String.format("%.2f €", deuda.getMontoTotal()),
                String.format("%.2f €", deuda.getMontoPendiente()),
                deuda.getFechaVencimiento(),
                deuda.getEstado()
            };
            tableModel.addRow(rowData);
        }
    }
}
