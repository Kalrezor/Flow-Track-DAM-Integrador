package com.dam.finanzas.control;

import java.awt.event.ActionEvent;
import javax.swing.*;
import com.dam.finanzas.view.LoginView;
import com.dam.finanzas.view.RegisterView;
import com.dam.finanzas.model.GestorDatos;
import com.dam.finanzas.model.Usuario;

public class RegisterController implements java.awt.event.ActionListener {
    private RegisterView vreg;

    public RegisterController(RegisterView vreg) {
        this.vreg = vreg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if ("Crear Cuenta".equals(actionCommand)) {
            String nombre = vreg.getUsuario();
            String correo = vreg.getEmail();
            String contrasena = vreg.getContrasena();
            String repetirContrasena = vreg.getRepetirContrasena();

            if (!contrasena.equals(repetirContrasena)) {
                JOptionPane.showMessageDialog(vreg, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(vreg, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un nuevo usuario con el constructor correcto
            Usuario nuevoUsuario = new Usuario(0, nombre, correo, contrasena); // Asumimos que el idUsuario se generará en la base de datos
            GestorDatos.registrarUsuario(nuevoUsuario);

            JOptionPane.showMessageDialog(vreg, "Usuario registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vreg.dispose();

            LoginView vlog = new LoginView();
            LoginController clog = new LoginController(vlog);
            vlog.setController(clog);
            vlog.setVisible(true);
        }
    }
}
