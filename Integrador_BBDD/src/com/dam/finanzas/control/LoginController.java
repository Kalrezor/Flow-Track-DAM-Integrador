package com.dam.finanzas.control;

import java.awt.event.ActionEvent;
import javax.swing.*;
import com.dam.finanzas.view.LoginView;
import com.dam.finanzas.view.MainView;
import com.dam.finanzas.view.RegisterView;
import com.dam.finanzas.model.GestorDatos;
import com.dam.finanzas.model.SesionUsuario;
import com.dam.finanzas.model.Usuario;

public class LoginController implements java.awt.event.ActionListener {
    private LoginView vlog;
    private GestorDatos gestorDatos;

    public LoginController(LoginView vlog) {
        this.vlog = vlog;
        this.gestorDatos = new GestorDatos(); // Inicializa GestorDatos
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if ("Iniciar Sesión".equals(actionCommand)) {
            String correo = vlog.getCorreo();
            String contrasena = vlog.getContrasena();

            Usuario usuario = gestorDatos.autenticarUsuario(correo, contrasena);

            if (usuario != null) {
                // Establecer el ID y el nombre del usuario en la sesión
                SesionUsuario.getInstancia().setIdUsuarioActual(usuario.getIdUsuario());
                SesionUsuario.getInstancia().setNombreUsuario(usuario.getNombre());

                // Crear una instancia de MainView pasando el idUsuarioActual
                MainView vp = new MainView(usuario.getIdUsuario());
                vp.setVisible(true);
                vlog.dispose();
            } else {
                JOptionPane.showMessageDialog(vlog, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if ("Crear Cuenta".equals(actionCommand)) {
            vlog.dispose();
            RegisterView vreg = new RegisterView();
            RegisterController creg = new RegisterController(vreg);
            vreg.setController(creg);
            vreg.setVisible(true);
        }
    }
}
