package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;
import com.dam.finanzas.model.bbdd.TablaIngresos;
import com.dam.finanzas.model.bbdd.TablaGastos;
import com.dam.finanzas.model.bbdd.TablaTransferencia;
import com.dam.finanzas.model.Ingreso;
import com.dam.finanzas.model.Gasto;
import com.dam.finanzas.model.Transferencia;

public class TransaccionesView {
    private Map<String, Double> gastosMap;
    private int idUsuarioActual;

    public TransaccionesView(Map<String, Double> gastosMap, int idUsuarioActual) {
        this.gastosMap = gastosMap;
        this.idUsuarioActual = idUsuarioActual;
    }

    public JPanel createTransaccionesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        // Título
        JLabel titleLabel = new JLabel("GESTIÓN DE TRANSACCIONES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Pestañas de transacciones
        JTabbedPane transaccionesTabs = new JTabbedPane();
        transaccionesTabs.addTab("INGRESOS", createIngresosPanel());
        transaccionesTabs.addTab("GASTOS", createGastosPanel());
        transaccionesTabs.addTab("TRANSFERENCIAS", createTransferenciasPanel());
        panel.add(transaccionesTabs, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createIngresosPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Ingreso"));

        // Campo de cantidad
        JTextField cantidadField = new JTextField(10);
        cantidadField.setBounds(6, 49, 433, 43);
        cantidadField.setText("Cantidad");
        cantidadField.setForeground(Color.GRAY);
        cantidadField.addFocusListener(new PlaceholderFocusListener(cantidadField, "Cantidad"));
        cantidadField.setPreferredSize(new Dimension(150, 25));

        // Campo de descripción
        JTextField descripcionField = new JTextField(10);
        descripcionField.setBounds(6, 99, 433, 43);
        descripcionField.setText("Descripción");
        descripcionField.setForeground(Color.GRAY);
        descripcionField.addFocusListener(new PlaceholderFocusListener(descripcionField, "Descripción"));
        descripcionField.setPreferredSize(new Dimension(150, 25));

        // Botón de registro
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBounds(52, 145, 345, 56);
        registrarButton.setPreferredSize(new Dimension(100, 30));

        // Acción del botón
        registrarButton.addActionListener(e -> {
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());
                String descripcion = descripcionField.getText();

                if (cantidad > 0) {
                    Ingreso ingreso = new Ingreso(idUsuarioActual, descripcion, cantidad, "CURRENT_DATE");
                    TablaIngresos tablaIngresos = new TablaIngresos();
                    int resultado = tablaIngresos.registrarIngreso(ingreso);

                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Ingreso registrado: " + cantidad + "€", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cantidadField.setText("");
                        descripcionField.setText("");

                        MainView mainView = (MainView) SwingUtilities.getWindowAncestor(registrarButton);
                        if (mainView != null) {
                            mainView.actualizarTotales();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar el ingreso", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.setLayout(null);
        JLabel label = new JLabel("Cantidad:");
        label.setBounds(6, 16, 433, 36);
        panel.add(label);
        panel.add(cantidadField);

        JLabel labelDescripcion = new JLabel("Descripción:");
        labelDescripcion.setBounds(6, 66, 433, 36);
        panel.add(labelDescripcion);
        panel.add(descripcionField);

        panel.add(registrarButton);

        return panel;
    }

    private JPanel createGastosPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Gasto"));

        // Campo de cantidad
        JTextField cantidadField = new JTextField(10);
        cantidadField.setBounds(6, 42, 433, 33);
        cantidadField.setText("Cantidad");
        cantidadField.setForeground(Color.GRAY);
        cantidadField.addFocusListener(new PlaceholderFocusListener(cantidadField, "Cantidad"));
        cantidadField.setPreferredSize(new Dimension(150, 25));

        // Campo de descripción
        JTextField descripcionField = new JTextField(10);
        descripcionField.setBounds(6, 92, 433, 33);
        descripcionField.setText("Descripción");
        descripcionField.setForeground(Color.GRAY);
        descripcionField.addFocusListener(new PlaceholderFocusListener(descripcionField, "Descripción"));
        descripcionField.setPreferredSize(new Dimension(150, 25));

        // ComboBox para categorías
        String[] categorias = {
            "Ocio y Entretenimiento",
            "Ropa y Accesorios",
            "Tecnología y Gadgets",
            "Salud y Cuidado Personal",
            "Transporte y Movilidad",
            "Comida y Supermercado",
            "Hogar y Decoración",
            "Educación y Formación"
        };
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias);
        categoriaComboBox.setBounds(6, 142, 433, 33);
        categoriaComboBox.setPreferredSize(new Dimension(150, 25));

        // Botón de registro
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBounds(77, 195, 286, 42);
        registrarButton.setPreferredSize(new Dimension(100, 30));

        // Acción del botón
        registrarButton.addActionListener(e -> {
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());
                String descripcion = descripcionField.getText();
                String categoria = (String) categoriaComboBox.getSelectedItem();

                if (cantidad > 0) {
                    // Verificar que la categoría no sea null o vacía
                    if (categoria == null || categoria.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Seleccione una categoría válida", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Verificar que la categoría sea una de las permitidas
                    boolean categoriaValida = false;
                    for (String cat : categorias) {
                        if (cat.equals(categoria)) {
                            categoriaValida = true;
                            break;
                        }
                    }

                    if (!categoriaValida) {
                        JOptionPane.showMessageDialog(null, "Seleccione una categoría válida", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Gasto gasto = new Gasto(idUsuarioActual, descripcion, categoria, cantidad, "CURRENT_DATE");
                    TablaGastos tablaGastos = new TablaGastos();
                    int resultado = tablaGastos.registrarGasto(gasto);

                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Gasto registrado: " + cantidad + "€ en " + categoria, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cantidadField.setText("");
                        descripcionField.setText("");

                        MainView mainView = (MainView) SwingUtilities.getWindowAncestor(registrarButton);
                        if (mainView != null) {
                            mainView.actualizarTotales();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar el gasto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.setLayout(null);
        JLabel label = new JLabel("Cantidad:");
        label.setBounds(6, 17, 433, 33);
        panel.add(label);
        panel.add(cantidadField);

        JLabel labelDescripcion = new JLabel("Descripción:");
        labelDescripcion.setBounds(6, 67, 433, 33);
        panel.add(labelDescripcion);
        panel.add(descripcionField);

        JLabel labelCategoria = new JLabel("Categoría:");
        labelCategoria.setBounds(6, 117, 433, 33);
        panel.add(labelCategoria);
        panel.add(categoriaComboBox);

        panel.add(registrarButton);

        return panel;
    }

    private JPanel createTransferenciasPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Transferencia"));

        // Opción para enviar, recibir o dejar en blanco
        String[] opciones = {"", "Enviar Dinero", "Recibir Dinero"};
        JComboBox<String> tipoTransferenciaComboBox = new JComboBox<>(opciones);
        tipoTransferenciaComboBox.setBounds(6, 38, 433, 25);
        tipoTransferenciaComboBox.setPreferredSize(new Dimension(150, 25));

        // Campos dinámicos
        JTextField destinatarioField = new JTextField(10);
        destinatarioField.setBounds(6, 92, 433, 25);
        destinatarioField.setPreferredSize(new Dimension(150, 25));
        JTextField asuntoField = new JTextField(10);
        asuntoField.setBounds(6, 142, 433, 25);
        asuntoField.setPreferredSize(new Dimension(150, 25));
        JTextField cantidadField = new JTextField(10);
        cantidadField.setBounds(6, 192, 433, 25);
        cantidadField.setPreferredSize(new Dimension(150, 25));

        // Botón de registro
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBounds(6, 217, 433, 25);
        registrarButton.setPreferredSize(new Dimension(100, 30));

        // Configurar campos iniciales
        destinatarioField.setEnabled(false);
        asuntoField.setEnabled(false);
        cantidadField.setEnabled(false);

        // Cambiar campos según la opción seleccionada
        tipoTransferenciaComboBox.addActionListener(e -> {
            String opcion = (String) tipoTransferenciaComboBox.getSelectedItem();
            if ("Enviar Dinero".equals(opcion)) {
                destinatarioField.setText("Destinatario");
                asuntoField.setText("Asunto");
                cantidadField.setText("Cantidad");
            } else if ("Recibir Dinero".equals(opcion)) {
                destinatarioField.setText("Remitente");
                asuntoField.setText("Asunto");
                cantidadField.setText("Cantidad");
            } else {
                destinatarioField.setText("");
                asuntoField.setText("");
                cantidadField.setText("");
                destinatarioField.setEnabled(false);
                asuntoField.setEnabled(false);
                cantidadField.setEnabled(false);
            }
            if (!"".equals(opcion)) {
                destinatarioField.setEnabled(true);
                asuntoField.setEnabled(true);
                cantidadField.setEnabled(true);
            }
        });

        // Agregar placeholders
        destinatarioField.setForeground(Color.GRAY);
        asuntoField.setForeground(Color.GRAY);
        cantidadField.setForeground(Color.GRAY);

        destinatarioField.addFocusListener(new PlaceholderFocusListener(destinatarioField, "Destinatario"));
        asuntoField.addFocusListener(new PlaceholderFocusListener(asuntoField, "Asunto"));
        cantidadField.addFocusListener(new PlaceholderFocusListener(cantidadField, "Cantidad"));

        registrarButton.addActionListener(e -> {
            String opcion = (String) tipoTransferenciaComboBox.getSelectedItem();
            if (opcion == null || opcion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de transferencia", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String nombreDestinatarioRemitente = destinatarioField.getText();
            String asunto = asuntoField.getText();
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());

                if (cantidad > 0) {
                    int idDestino = 1; // Asumiendo que el ID de destino es 1
                    if ("Enviar Dinero".equals(opcion)) {
                        // Si el usuario está enviando dinero, el destinatario es el ID de destino
                        idDestino = obtenerIdUsuarioPorNombre(nombreDestinatarioRemitente);
                    } else if ("Recibir Dinero".equals(opcion)) {
                        // Si el usuario está recibiendo dinero, el remitente es el ID de destino
                        idDestino = obtenerIdUsuarioPorNombre(nombreDestinatarioRemitente);
                    }

                    Transferencia transferencia = new Transferencia(idUsuarioActual, idDestino, cantidad, asunto);
                    TablaTransferencia tablaTransferencia = new TablaTransferencia();
                    int resultado = tablaTransferencia.registrarTransferencia(transferencia);

                    if (resultado > 0) {
                        String mensaje = opcion.equals("Enviar Dinero")
                                ? "Dinero enviado: " + cantidad + "€ a " + nombreDestinatarioRemitente + " por " + asunto
                                : "Dinero recibido: " + cantidad + "€ de " + nombreDestinatarioRemitente + " por " + asunto;
                        JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        destinatarioField.setText("");
                        asuntoField.setText("");
                        cantidadField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar la transferencia", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.setLayout(null);
        JLabel label = new JLabel("Tipo de Transferencia:");
        label.setBounds(6, 17, 433, 25);
        panel.add(label);
        panel.add(tipoTransferenciaComboBox);
        JLabel label_1 = new JLabel("Destinatario/Remitente:");
        label_1.setBounds(6, 67, 433, 25);
        panel.add(label_1);
        panel.add(destinatarioField);
        JLabel label_2 = new JLabel("Asunto:");
        label_2.setBounds(6, 117, 433, 25);
        panel.add(label_2);
        panel.add(asuntoField);
        JLabel label_3 = new JLabel("Cantidad:");
        label_3.setBounds(6, 167, 433, 25);
        panel.add(label_3);
        panel.add(cantidadField);
        panel.add(registrarButton);

        return panel;
    }

    private int obtenerIdUsuarioPorNombre(String nombre) {
        // Implementa la lógica para obtener el ID de usuario por nombre
        // Esto es solo un ejemplo, debes ajustarlo según tu base de datos
        return 1; // Valor de ejemplo
    }

    private static class PlaceholderFocusListener implements FocusListener {
        private final JTextField textField;
        private final String placeholder;

        public PlaceholderFocusListener(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    }
}
