package com.dam.finanzas.main;

import java.awt.EventQueue;

import com.dam.finanzas.control.LoginController;
import com.dam.finanzas.view.LoginView;

public class Inicio {

	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
        	
            @Override
            public void run() {
            	
                LoginView vlog = new LoginView();
                LoginController clog = new LoginController(vlog);
                vlog.setController(clog);
                vlog.setVisible(true);
                
            }
            
        });
    }
}
