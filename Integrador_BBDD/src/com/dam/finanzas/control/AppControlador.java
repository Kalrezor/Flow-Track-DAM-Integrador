package com.dam.finanzas.control;

import java.awt.event.ActionEvent;
import javax.swing.*;
import com.dam.finanzas.view.LoginView;
import com.dam.finanzas.view.MainView;
import com.dam.finanzas.view.RegisterView;
import com.dam.finanzas.model.Usuario;
import com.dam.finanzas.model.bbdd.TablaUsuario;
import com.dam.finanzas.model.SesionUsuario;

public class AppControlador implements java.awt.event.ActionListener {
    private LoginView vlog;
    private RegisterView vreg;
    private TablaUsuario datos;

    public AppControlador(LoginView vlog, RegisterView vreg) {
        this.vlog = vlog;
        this.vreg = vreg;
        this.datos = new TablaUsuario();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if ("Iniciar Sesión".equals(actionCommand)) {
            String correo = vlog.getCorreo();
            String contrasena = vlog.getContrasena();

            Usuario usuario = datos.comprobarExiste(correo);

            if (usuario != null && usuario.getContrasena().equals(contrasena)) {
                SesionUsuario.getInstancia().setIdUsuarioActual(usuario.getIdUsuario());
                SesionUsuario.getInstancia().setNombreUsuario(usuario.getNombre());

                MainView vp = new MainView(usuario.getIdUsuario());
                vp.setVisible(true);
                vlog.dispose();
            } else {
                JOptionPane.showMessageDialog(vlog, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if ("Crear Cuenta".equals(actionCommand)) {
            vlog.dispose();
            vreg.setVisible(true);
        } else if ("Registrarse".equals(actionCommand)) {
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

            Usuario nuevoUsuario = new Usuario(0, nombre, correo, contrasena);
            int resultado = datos.registrarUsuario(nuevoUsuario);

            if (resultado == 1) {
                JOptionPane.showMessageDialog(vreg, "Usuario registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vreg.dispose();
                vlog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vreg, "Error al registrar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
