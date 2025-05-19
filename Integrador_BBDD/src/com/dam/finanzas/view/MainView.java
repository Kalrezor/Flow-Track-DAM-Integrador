package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import com.dam.finanzas.model.SesionUsuario;
import com.dam.finanzas.model.bbdd.TablaTransacciones;

public class MainView extends JFrame {
    private JPanel sidebar; // Barra lateral de Navegación
    private CardLayout cardLayout; // Layout para cambiar entre pantallas
    private JPanel contentPanel; // El panel Principal que contiene las vistas
    private Map<String, Double> gastosMap; // Mapa para almacenar los gastos por categoría
    private int idUsuarioActual; // ID del usuario actual
    private JLabel ingresosValueLabel; // Etiqueta para mostrar el total de ingresos
    private JLabel gastosValueLabel; // Etiqueta para mostrar el total de gastos
    private JLabel beneficioNetoValueLabel; // Etiqueta para mostrar el beneficio neto

    public MainView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
        setTitle("Finanzas Personales");
        setSize(862, 601);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gastosMap = new HashMap<>(); // Inicializar el mapa de gastos
        initComponents();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        // Barra lateral
        sidebar = createSidebar();
        getContentPane().add(sidebar, BorderLayout.WEST); // colocar el sidebar a la izquierda

        // Contenedor principal con CardLayout
        cardLayout = new CardLayout(); // Creacion Layout para cambiar entre pantallas
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.LIGHT_GRAY);

        // Pantalla inicial (HOME)
        JPanel homePanel = createHomePanel();
        contentPanel.add(homePanel, "HOME");

        // Pantalla de Transacciones
        TransaccionesView transaccionesView = new TransaccionesView(gastosMap, idUsuarioActual);
        JPanel transaccionesPanel = transaccionesView.createTransaccionesPanel();
        contentPanel.add(transaccionesPanel, "TRANSACCIONES");

        // Pantalla de Deudas
        DeudasView deudasView = new DeudasView(idUsuarioActual);
        JPanel deudasPanel = deudasView.createDeudasPanel();
        contentPanel.add(deudasPanel, "DEUDAS");

        // Pantalla de Objetivos
        ObjetivosView objetivosView = new ObjetivosView();
        JPanel objetivosPanel = objetivosView.createObjetivosPanel();
        contentPanel.add(objetivosPanel, "OBJETIVOS");

        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(7, 1)); // 7 botones (6 de contenido + 1 de salir)
        sidebar.setOpaque(true);
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(150, 600));

        // Botones con la navegacion del menu
        String[] botones = {
            "Inicio",
            "Transacciones",
            "Deudas",
            "Objetivos",
            "Estadísticas",
            "Salir"
        };

        for (String texto : botones) {
            JButton button = new JButton(texto);
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(44, 62, 80));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(140, 40));

            button.addActionListener(e -> {
                switch (texto) {
                    case "Inicio":
                        cardLayout.show(contentPanel, "HOME");
                        System.out.println("Inicio");
                        break;
                    case "Transacciones":
                        cardLayout.show(contentPanel, "TRANSACCIONES");
                        System.out.println("Transacciones");
                        break;
                    case "Deudas":
                        cardLayout.show(contentPanel, "DEUDAS");
                        System.out.println("Deudas");
                        break;
                    case "Objetivos":
                        cardLayout.show(contentPanel, "OBJETIVOS");
                        System.out.println("Objetivos");
                        break;
                    case "Estadísticas":
                        cardLayout.show(contentPanel, "ESTADISTICAS");
                        System.out.println("Estadísticas");
                        break;
                    case "Salir":
                        System.exit(0);
                        break;
                }
            });

            sidebar.add(button);
        }

        return sidebar;
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        // Encabezado: Bienvenida al usuario
        JLabel welcomeLabel = new JLabel("¡Bienvenido " + SesionUsuario.getInstancia().getNombreUsuario() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Panel para los datos financieros
        JPanel datosFinancierosPanel = new JPanel(new BorderLayout());
        datosFinancierosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        datosFinancierosPanel.setBackground(Color.LIGHT_GRAY);

        // Mes actual
        JLabel mesActualLabel = new JLabel("Mes Actual: Enero 2023");
        mesActualLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mesActualLabel.setHorizontalAlignment(SwingConstants.CENTER);
        datosFinancierosPanel.add(mesActualLabel, BorderLayout.NORTH);

        // Recuadro para Ingresos, Gastos y Beneficio Neto
        JPanel finanzasPanel = new JPanel(new GridLayout(1, 3, 10, 0)); // Una fila, tres columnas, espacio entre elementos
        finanzasPanel.setBackground(new Color(192, 192, 192));

        // Recuadro para Ingresos
        JPanel ingresosPanel = new JPanel(new BorderLayout());
        ingresosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel ingresosLabel = new JLabel("Ingresos");
        ingresosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosValueLabel = new JLabel("$0"); // Inicialización de ingresosValueLabel
        ingresosValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingresosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosPanel.add(ingresosLabel, BorderLayout.NORTH);
        ingresosPanel.add(ingresosValueLabel, BorderLayout.CENTER);

        // Recuadro para Gastos
        JPanel gastosPanel = new JPanel(new BorderLayout());
        gastosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel gastosLabel = new JLabel("Gastos");
        gastosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gastosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosValueLabel = new JLabel("$0"); // Inicialización de gastosValueLabel
        gastosValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gastosValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosPanel.add(gastosLabel, BorderLayout.NORTH);
        gastosPanel.add(gastosValueLabel, BorderLayout.CENTER);

        // Recuadro para Beneficio Neto
        JPanel beneficioNetoPanel = new JPanel(new BorderLayout());
        beneficioNetoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel beneficioNetoLabel = new JLabel("Beneficio Neto");
        beneficioNetoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        beneficioNetoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioNetoValueLabel = new JLabel("$0"); // Inicialización de beneficioNetoValueLabel
        beneficioNetoValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        beneficioNetoValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beneficioNetoPanel.add(beneficioNetoLabel, BorderLayout.NORTH);
        beneficioNetoPanel.add(beneficioNetoValueLabel, BorderLayout.CENTER);

        // Añadir recuadros al panel de datos financieros
        finanzasPanel.add(ingresosPanel);
        finanzasPanel.add(gastosPanel);
        finanzasPanel.add(beneficioNetoPanel);

        // Organizar todo
        datosFinancierosPanel.add(finanzasPanel, BorderLayout.CENTER);

        // Tabla de Transferencias
        JPanel transaccionesPanel = new JPanel(new BorderLayout());
        transaccionesPanel.setBackground(new Color(192, 192, 192));
        transaccionesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Título de Transferencias
        JLabel transferenciasLabel = new JLabel("Transferencias");
        transferenciasLabel.setFont(new Font("Arial", Font.BOLD, 14));
        transferenciasLabel.setHorizontalAlignment(SwingConstants.CENTER);
        transaccionesPanel.add(transferenciasLabel, BorderLayout.NORTH);

        // Ejemplo de tabla de transferencias
        String[] columnNamesTransferencias = {"Remitente", "Destinatario", "Monto"};
        Object[][] dataTransferencias = {
            {"Usuario1", "Usuario2", "$100"},
            {"Usuario3", "Usuario4", "$200"},
            {"Usuario5", "Usuario6", "$300"}
        };
        JTable transferenciasTable = new JTable(dataTransferencias, columnNamesTransferencias);
        JScrollPane scrollPane = new JScrollPane(transferenciasTable);
        transaccionesPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel derecho
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);

        // Encabezado del panel derecho
        JPanel rightHeader = new JPanel(new BorderLayout());
        rightHeader.setBackground(Color.LIGHT_GRAY);
        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel userNameLabel = new JLabel(SesionUsuario.getInstancia().getNombreUsuario());
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rightHeader.add(userIcon, BorderLayout.WEST);
        rightHeader.add(userNameLabel, BorderLayout.CENTER);
        rightPanel.add(rightHeader, BorderLayout.NORTH);

        // Lista de categorías de gastos
        JPanel gastosListPanel = new JPanel(new GridLayout(0, 1));
        gastosListPanel.setBackground(Color.LIGHT_GRAY);
        String[] categorias = {
            "", // Opción vacía predeterminada
            "Ocio y entretenimiento",
            "Ropa y accesorios",
            "Tecnología y gadgets",
            "Salud y cuidado personal",
            "Transporte y movilidad",
            "Comida y supermercado",
            "Hogar y decoración",
            "Educación y formación"
        };
        for (String categoria : categorias) {
            JPanel categoriaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            categoriaPanel.setBackground(Color.LIGHT_GRAY);
            JLabel nombreCategoria = new JLabel(categoria.isEmpty() ? "Sin categoría" : categoria);
            JLabel montoCategoria = new JLabel("0€");
            categoriaPanel.add(nombreCategoria);
            categoriaPanel.add(Box.createHorizontalGlue()); // Espacio flexible
            categoriaPanel.add(montoCategoria);
            gastosListPanel.add(categoriaPanel);
        }
        rightPanel.add(gastosListPanel, BorderLayout.CENTER);

        // Organizar todo
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);

        // Agregar componentes al centro
        centerPanel.add(datosFinancierosPanel, BorderLayout.NORTH);
        centerPanel.add(transaccionesPanel, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    public void actualizarTotales() {
        TablaTransacciones tablaTransacciones = new TablaTransacciones();
        double totalIngresos = tablaTransacciones.obtenerTotalIngresos(idUsuarioActual);
        double totalGastos = tablaTransacciones.obtenerTotalGastos(idUsuarioActual);

        // Calcular el beneficio neto
        double beneficioNeto = totalIngresos - totalGastos;

        // Actualizar las etiquetas
        ingresosValueLabel.setText(String.format("%.2f €", totalIngresos));
        gastosValueLabel.setText(String.format("%.2f €", totalGastos));
        beneficioNetoValueLabel.setText(String.format("%.2f €", beneficioNeto));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Aquí debes proporcionar el idUsuarioActual. Por ahora, usaremos un valor de ejemplo.
            int idUsuarioActual = 1; // Este valor debería ser dinámico y venir de la sesión del usuario.
            MainView frame = new MainView(idUsuarioActual);
            frame.setVisible(true);
        });
    }

    public Map<String, Double> getGastosMap() {
        return gastosMap;
    }
}