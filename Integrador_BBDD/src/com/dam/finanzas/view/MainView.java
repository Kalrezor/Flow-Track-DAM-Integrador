package com.dam.finanzas.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import com.dam.finanzas.model.SesionUsuario;
import com.dam.finanzas.model.bbdd.TablaIngresos;
import com.dam.finanzas.model.bbdd.TablaGastos;
import com.dam.finanzas.model.bbdd.TablaTransferencia;

public class MainView extends JFrame {
    private JPanel sidebar;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private Map<String, Double> gastosMap;
    private int idUsuarioActual;
    private JLabel ingresosValueLabel;
    private JLabel gastosValueLabel;
    private JLabel beneficioNetoValueLabel;

    public MainView(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
        setTitle("Finanzas Personales");
        setSize(862, 601);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gastosMap = new HashMap<>();
        initComponents();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        // Barra lateral
        sidebar = createSidebar();
        getContentPane().add(sidebar, BorderLayout.WEST);

        // Contenedor principal con CardLayout
        cardLayout = new CardLayout();
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

        actualizarTotales();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1));
        sidebar.setOpaque(true);
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(150, 600));

        String[] botones = {
            "Inicio",
            "Transacciones",
            "Deudas",
            "Objetivos",
            "Estadísticas",
            "Usuario",
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
                    case "Usuario":
                        cardLayout.show(contentPanel, "USUARIO");
                        System.out.println("Usuario");
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
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String mesActual = fechaActual.format(formatter);

        JLabel mesActualLabel = new JLabel(mesActual);
        mesActualLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mesActualLabel.setHorizontalAlignment(SwingConstants.CENTER);
        datosFinancierosPanel.add(mesActualLabel, BorderLayout.NORTH);

        // Recuadro para Ingresos, Gastos y Beneficio Neto
        JPanel finanzasPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        finanzasPanel.setBackground(new Color(192, 192, 192));

        // Recuadro para Ingresos
        JPanel ingresosPanel = new JPanel(new BorderLayout());
        ingresosPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel ingresosLabel = new JLabel("Ingresos");
        ingresosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ingresosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ingresosValueLabel = new JLabel("0 €");
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
        gastosValueLabel = new JLabel("0 €");
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
        beneficioNetoValueLabel = new JLabel("0 €");
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

        // Obtener datos de transferencias de la base de datos
        TablaTransferencia tablaTransferencia = new TablaTransferencia();
        Object[][] dataTransferencias = tablaTransferencia.obtenerTransferencias(idUsuarioActual);

        // Nombres de las columnas
        String[] columnNamesTransferencias = {"Remitente", "Destinatario", "Cantidad"};

        // Crear la tabla de transferencias
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

        // Panel para la lista de gastos
        JPanel gastosListPanel = new JPanel(new GridLayout(0, 1, 0, 1));
        gastosListPanel.setBackground(Color.LIGHT_GRAY);

        // Título "Tipos de Gastos:"
        JLabel tiposGastosLabel = new JLabel("Tipos de Gastos:");
        tiposGastosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tiposGastosLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(tiposGastosLabel);

        JLabel ocioLabel = new JLabel("Ocio y entretenimiento  ");
        ocioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ocioLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(ocioLabel);

        JLabel ociodLabel = new JLabel("0" + " €");
        ociodLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ociodLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(ociodLabel);

        JLabel ropaLabel = new JLabel("Ropa y accesorios  ");
        ropaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ropaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(ropaLabel);

        JLabel ropadLabel = new JLabel("0" + " €");
        ropadLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ropadLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(ropadLabel);

        JLabel tecnoLabel = new JLabel("Tecnología y gadgets  ");
        tecnoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tecnoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(tecnoLabel);

        JLabel tecnodLabel = new JLabel("0" + " €");
        tecnodLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tecnodLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(tecnodLabel);

        JLabel saludLabel = new JLabel("Salud y cuidado personal  ");
        saludLabel.setFont(new Font("Arial", Font.BOLD, 14));
        saludLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(saludLabel);

        JLabel saluddLabel = new JLabel("0" + " €");
        saluddLabel.setFont(new Font("Arial", Font.BOLD, 14));
        saluddLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(saluddLabel);

        JLabel transpLabel = new JLabel("Transporte y movilidad  ");
        transpLabel.setFont(new Font("Arial", Font.BOLD, 14));
        transpLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(transpLabel);

        JLabel transpdLabel = new JLabel("0" + " €");
        transpdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        transpdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(transpdLabel);

        JLabel comidaLabel = new JLabel("Comida y supermercado  ");
        comidaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        comidaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(comidaLabel);

        JLabel comidadLabel = new JLabel("0" + " €");
        comidadLabel.setFont(new Font("Arial", Font.BOLD, 14));
        comidadLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(comidadLabel);

        JLabel hogarLabel = new JLabel("Hogar y decoración  ");
        hogarLabel.setFont(new Font("Arial", Font.BOLD, 14));
        hogarLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(hogarLabel);

        JLabel hogardLabel = new JLabel("0" + " €");
        hogardLabel.setFont(new Font("Arial", Font.BOLD, 14));
        hogardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(hogardLabel);

        JLabel educLabel = new JLabel("Educación y formación  ");
        educLabel.setFont(new Font("Arial", Font.BOLD, 14));
        educLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gastosListPanel.add(educLabel);

        JLabel educdLabel = new JLabel("0" + " €");
        educdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        educdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gastosListPanel.add(educdLabel);

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
        TablaIngresos tablaIngresos = new TablaIngresos();
        double totalIngresos = tablaIngresos.obtenerTotalIngresos(idUsuarioActual);

        TablaGastos tablaGastos = new TablaGastos();
        double totalGastos = tablaGastos.obtenerTotalGastos(idUsuarioActual);

        // Calcular el beneficio neto
        double beneficioNeto = totalIngresos - totalGastos;

        // Actualizar las etiquetas
        ingresosValueLabel.setText(String.format("%.2f €", totalIngresos));
        gastosValueLabel.setText(String.format("%.2f €", totalGastos));
        beneficioNetoValueLabel.setText(String.format("%.2f €", beneficioNeto));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            int idUsuarioActual = 1; // Cambiar para que funcione con todos los Usuarios
            MainView frame = new MainView(idUsuarioActual);
            frame.setVisible(true);
        });
    }

    public Map<String, Double> getGastosMap() {
        return gastosMap;
    }
}
