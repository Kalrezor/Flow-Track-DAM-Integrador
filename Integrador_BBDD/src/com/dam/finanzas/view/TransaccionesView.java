package com.dam.finanzas.view;


import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class TransaccionesView {
    private Map<String, Double> gastosMap;


    public TransaccionesView(Map<String, Double> gastosMap) {
        this.gastosMap = gastosMap;
    }

    /**
     * @wbp.parser.entryPoint
     */
    public JPanel createTransaccionesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);


        // Título
        JLabel titleLabel = new JLabel("GESTIÓN DE TRANSACCIONES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);


        // Pestañas de transacciones
        JTabbedPane transaccionesTabs = new JTabbedPane(); // Panel en forma de tabla que va cargando los paneles Ingresos, Gastos, Ytransferencias
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


        // Botón de registro
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBounds(52, 145, 345, 56);
        registrarButton.setPreferredSize(new Dimension(100, 30));


        // Acción del botón
        registrarButton.addActionListener(e -> {
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());
                if (cantidad > 0) {
                    JOptionPane.showMessageDialog(null, "Ingreso registrado: " + cantidad + "€", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cantidadField.setText("");
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


        // ComboBox para categorías
        String[] categorias = {
            "Ocio y entretenimiento",
            "Ropa y accesorios",
            "Tecnología y gadgets",
            "Salud y cuidado personal",
            "Transporte y movilidad",
            "Comida y supermercado",
            "Hogar y decoración",
            "Educación y formación"
        };
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias);
        categoriaComboBox.setBounds(6, 112, 433, 33);
        categoriaComboBox.setPreferredSize(new Dimension(150, 25));


        // Botón de registro
        JButton registrarButton = new JButton("Registrar");
        registrarButton.setBounds(77, 175, 286, 42);
        registrarButton.setPreferredSize(new Dimension(100, 30));


        // Acción del botón
        registrarButton.addActionListener(e -> {
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());
                String categoria = (String) categoriaComboBox.getSelectedItem();


                if (cantidad > 0) {
                    gastosMap.put(categoria, gastosMap.getOrDefault(categoria, 0.0) + cantidad);
                    JOptionPane.showMessageDialog(null, "Gasto registrado: " + cantidad + "€ en " + categoria, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cantidadField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.setLayout(null);


        // Diseño ordenado con ancho reducido
        JLabel label = new JLabel("Cantidad:");
        label.setBounds(6, 17, 433, 33);
        panel.add(label);
        panel.add(cantidadField);
        JLabel label_1 = new JLabel("Categoría:");
        label_1.setBounds(6, 86, 433, 33);
        panel.add(label_1);
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


        // Acción del botón
        registrarButton.addActionListener(e -> {
            String opcion = (String) tipoTransferenciaComboBox.getSelectedItem();
            if (opcion == null || opcion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de transferencia", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String destinatario = destinatarioField.getText();
            String asunto = asuntoField.getText();
            try {
                double cantidad = Double.parseDouble(cantidadField.getText());


                if (cantidad > 0) {
                    String mensaje = opcion.equals("Enviar Dinero")
                            ? "Dinero enviado: " + cantidad + "€ a " + destinatario + " por " + asunto
                            : "Dinero recibido: " + cantidad + "€ de " + destinatario + " por " + asunto;
                    JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    destinatarioField.setText("");
                    asuntoField.setText("");
                    cantidadField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.setLayout(null);


        // Diseño ordenado con ancho reducido
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


    private static class PlaceholderFocusListener implements java.awt.event.FocusListener {
        private final JTextField textField;
        private final String placeholder;


        public PlaceholderFocusListener(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
        }


        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }


        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
            }
        }
    }
}

