/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import vista.VistaPrincipal;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorPrincipal {

    private final VistaPrincipal vista; //Instancia de la vistaLogin
    //private final ConsultaPrincipal consultaPrincipal = new ConsultaPrincipal(); //Instancia de ConsultaPrincipal
    private int xMouse, yMouse;

    public ControladorPrincipal(VistaPrincipal vista) {
        this.vista = vista;
        inicializarEventos();

    }

    private void inicializarEventos() {

        // Eventos para mover la ventana
        vista.header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                xMouse = evt.getX();
                yMouse = evt.getY();
            }
        });

        vista.header.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                int x = evt.getXOnScreen();
                int y = evt.getYOnScreen();
                vista.setLocation(x - xMouse, y - yMouse);
            }
        });

        // Evento del botón de salida
        vista.exitTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                vista.exitBtn.setBackground(Color.red);
                vista.exitTxt.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                vista.exitBtn.setBackground(Color.white);
                vista.exitTxt.setForeground(Color.black);
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Evento del botón PRINCIPAL
        vista.btnPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                setColor(vista.btnPrincipal);
                resetColor(vista.btnCuentas);
                resetColor(vista.btnDepositos);
                resetColor(vista.btnFondos);
                resetColor(vista.btnBolsa);
                resetColor(vista.btnInformes);
            }
        });
        
        // Evento del botón CUENTAS
        vista.btnCuentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                resetColor(vista.btnPrincipal);
                setColor(vista.btnCuentas);
                resetColor(vista.btnDepositos);
                resetColor(vista.btnFondos);
                resetColor(vista.btnBolsa);
                resetColor(vista.btnInformes);
            }
        });
        
        // Evento del botón DEPOSITOS
        vista.btnDepositos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                resetColor(vista.btnPrincipal);
                resetColor(vista.btnCuentas);
                setColor(vista.btnDepositos);
                resetColor(vista.btnFondos);
                resetColor(vista.btnBolsa);
                resetColor(vista.btnInformes);
            }
        });
        
        // Evento del botón FONDOS
        vista.btnFondos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                resetColor(vista.btnPrincipal);
                resetColor(vista.btnCuentas);
                resetColor(vista.btnDepositos);
                setColor(vista.btnFondos);
                resetColor(vista.btnBolsa);
                resetColor(vista.btnInformes);
            }
        });
        
        // Evento del botón BOLSA
        vista.btnBolsa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                resetColor(vista.btnPrincipal);
                resetColor(vista.btnCuentas);
                resetColor(vista.btnDepositos);
                resetColor(vista.btnFondos);
                setColor(vista.btnBolsa);
                resetColor(vista.btnInformes);
            }
        });
        
        // Evento del botón INFORMES
        vista.btnInformes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                resetColor(vista.btnPrincipal);
                resetColor(vista.btnCuentas);
                resetColor(vista.btnDepositos);
                resetColor(vista.btnFondos);
                resetColor(vista.btnBolsa);
                setColor(vista.btnInformes);
            }
        });
    }

    void setColor(JPanel panel) {
        panel.setBackground(new Color(21, 101, 192));
    }

    void resetColor(JPanel panel) {
        panel.setBackground(new Color(18, 90, 173));
    }
}
