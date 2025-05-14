package com.dam.finanzas.view;

import javax.swing.*;

import com.dam.finanzas.control.AppControlador;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegisterView extends JFrame {
    private JTextField txtUsuario;
    private JTextField txtEmail;
    private JPasswordField txtContraseña;
    private JPasswordField txtRepetirContraseña;
    private JButton btnCrearCuenta;

    public RegisterView() {
        setTitle("Crear Cuenta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(44, 62, 80));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(new Color(52, 152, 219));
        registerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel lblIconoUsuario = new JLabel("👤");
        lblIconoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblIconoUsuario.setFont(new Font("Arial", Font.BOLD, 48));
        lblIconoUsuario.setForeground(Color.WHITE);
        GridBagConstraints gbcIconoUsuario = new GridBagConstraints();
        gbcIconoUsuario.gridx = 0;
        gbcIconoUsuario.gridy = 0;
        gbcIconoUsuario.gridwidth = 2;
        gbcIconoUsuario.anchor = GridBagConstraints.CENTER;
        gbcIconoUsuario.insets = new Insets(10, 10, 10, 10);
        registerPanel.add(lblIconoUsuario, gbcIconoUsuario);

        txtUsuario = new JTextField("Usuario", 20);
        txtUsuario.setForeground(Color.GRAY);
        txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbcUsuario = new GridBagConstraints();
        gbcUsuario.gridx = 0;
        gbcUsuario.gridy = 1;
        gbcUsuario.gridwidth = 2;
        gbcUsuario.fill = GridBagConstraints.HORIZONTAL;
        gbcUsuario.insets = new Insets(10, 10, 10, 10);
        registerPanel.add(txtUsuario, gbcUsuario);

        txtUsuario.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtUsuario.getText().equals("Usuario")) {
                    txtUsuario.setText("");
                    txtUsuario.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtUsuario.getText().isEmpty()) {
                    txtUsuario.setText("Usuario");
                    txtUsuario.setForeground(Color.GRAY);
                }
            }
        });

        txtEmail = new JTextField("Email", 20);
        txtEmail.setForeground(Color.GRAY);
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbcEmail = new GridBagConstraints();
        gbcEmail.gridx = 0;
        gbcEmail.gridy = 2;
        gbcEmail.gridwidth = 2;
        gbcEmail.fill = GridBagConstraints.HORIZONTAL;
        gbcEmail.insets = new Insets(10, 10, 10, 10);
        registerPanel.add(txtEmail, gbcEmail);

        txtEmail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtEmail.getText().equals("Email")) {
                    txtEmail.setText("");
                    txtEmail.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().isEmpty()) {
                    txtEmail.setText("Email");
                    txtEmail.setForeground(Color.GRAY);
                }
            }
        });

        txtContraseña = new JPasswordField("Contraseña", 20);
        txtContraseña.setEchoChar((char) 0);
        txtContraseña.setForeground(Color.GRAY);
        txtContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbcContraseña = new GridBagConstraints();
        gbcContraseña.gridx = 0;
        gbcContraseña.gridy = 3;
        gbcContraseña.gridwidth = 2;
        gbcContraseña.fill = GridBagConstraints.HORIZONTAL;
        gbcContraseña.insets = new Insets(10, 10, 10, 10);
        registerPanel.add(txtContraseña, gbcContraseña);

        txtContraseña.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(txtContraseña.getPassword()).equals("Contraseña")) {
                    txtContraseña.setText("");
                    txtContraseña.setEchoChar('●');
                    txtContraseña.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(txtContraseña.getPassword()).isEmpty()) {
                    txtContraseña.setText("Contraseña");
                    txtContraseña.setEchoChar((char) 0);
                    txtContraseña.setForeground(Color.GRAY);
                }
            }
        });

        txtRepetirContraseña = new JPasswordField("Repetir Contraseña", 20);
        txtRepetirContraseña.setEchoChar((char) 0);
        txtRepetirContraseña.setForeground(Color.GRAY);
        txtRepetirContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbcRepetirContraseña = new GridBagConstraints();
        gbcRepetirContraseña.gridx = 0;
        gbcRepetirContraseña.gridy = 4;
        gbcRepetirContraseña.gridwidth = 2;
        gbcRepetirContraseña.fill = GridBagConstraints.HORIZONTAL;
        gbcRepetirContraseña.insets = new Insets(10, 10, 10, 10);
        registerPanel.add(txtRepetirContraseña, gbcRepetirContraseña);

        txtRepetirContraseña.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(txtRepetirContraseña.getPassword()).equals("Repetir Contraseña")) {
                    txtRepetirContraseña.setText("");
                    txtRepetirContraseña.setEchoChar('●');
                    txtRepetirContraseña.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(txtRepetirContraseña.getPassword()).isEmpty()) {
                    txtRepetirContraseña.setText("Repetir Contraseña");
                    txtRepetirContraseña.setEchoChar((char) 0);
                    txtRepetirContraseña.setForeground(Color.GRAY);
                }
            }
        });

        btnCrearCuenta = new JButton("Crear Cuenta");
        btnCrearCuenta.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnCrearCuenta.setBackground(new Color(44, 62, 80));
        btnCrearCuenta.setForeground(Color.WHITE);
        GridBagConstraints gbcCrearCuenta = new GridBagConstraints();
        gbcCrearCuenta.gridx = 0;
        gbcCrearCuenta.gridy = 5;
        gbcCrearCuenta.gridwidth = 2;
        gbcCrearCuenta.anchor = GridBagConstraints.CENTER;
        gbcCrearCuenta.insets = new Insets(10, 10, 10, 10);
        registerPanel.add(btnCrearCuenta, gbcCrearCuenta);

        GridBagConstraints gbcRegisterPanel = new GridBagConstraints();
        gbcRegisterPanel.gridx = 0;
        gbcRegisterPanel.gridy = 0;
        gbcRegisterPanel.anchor = GridBagConstraints.CENTER;
        contentPane.add(registerPanel, gbcRegisterPanel);

        setContentPane(contentPane);
    }

    public void setController(AppControlador controller) {
        btnCrearCuenta.setActionCommand("Registrarse"); // Establece el actionCommand
        btnCrearCuenta.addActionListener(controller);
    }


    public String getUsuario() {
        return txtUsuario.getText().equals("Usuario") ? "" : txtUsuario.getText();
    }

    public String getEmail() {
        return txtEmail.getText().equals("Email") ? "" : txtEmail.getText();
    }

    public String getContrasena() {
        return new String(txtContraseña.getPassword()).equals("Contraseña") ? "" : new String(txtContraseña.getPassword());
    }

    public String getRepetirContrasena() {
        return new String(txtRepetirContraseña.getPassword()).equals("Repetir Contraseña") ? "" : new String(txtRepetirContraseña.getPassword());
    }
}
