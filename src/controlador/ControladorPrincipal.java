/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Usuario;
import vista.PanelAdmin;
import vista.PanelBolsa;
import vista.PanelCuentas;
import vista.PanelDepositos;
import vista.PanelFondos;
import vista.PanelInformes;
import vista.PanelPrincipal;
import vista.VistaPrincipal;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorPrincipal {

    private final VistaPrincipal vista; //Instancia de la vistaLogin
    //private final ConsultaPrincipal consultaPrincipal = new ConsultaPrincipal(); //Instancia de ConsultaPrincipal
    private final Usuario usuario;  // Almacena el usuario logueado
    private int xMouse, yMouse;

    public ControladorPrincipal(VistaPrincipal vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        inicializarEventos();
        cargarDatosUsuario();

        //Establece el Panel principal al inicio de la aplicación
        PanelPrincipal p1 = new PanelPrincipal();
        p1.setSize(970, 600);
        p1.setLocation(0, 0);
        vista.panelContenedor.removeAll();
        vista.panelContenedor.add(p1, BorderLayout.CENTER);
        vista.panelContenedor.revalidate();
        vista.panelContenedor.repaint();
        
        //Establece en oculto el botón Admin si no es Administrador
        if(usuario.getAdmin()==0){
        vista.btnAdmin.setVisible(false);
        }

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
                resetColor(vista.btnAdmin);

                //Establece el Panel principal al pulsar PRINCIPAL
                PanelPrincipal p1 = new PanelPrincipal();
                p1.setSize(970, 600);
                p1.setLocation(0, 0);
                vista.panelContenedor.removeAll();
                vista.panelContenedor.add(p1, BorderLayout.CENTER);
                vista.panelContenedor.revalidate();
                vista.panelContenedor.repaint();
            }
        });
        //Evento para el JLabel dentro del btnPrincipal y que funcione el click
        vista.etiPtincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                vista.btnPrincipal.dispatchEvent(evt);  // Redirige el evento al JPanel
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
                resetColor(vista.btnAdmin);
                
                //Establece el Panel Cuentas al pulsar CUENTAS
                PanelCuentas p2 = new PanelCuentas();
                p2.setSize(970, 600);
                p2.setLocation(0, 0);
                vista.panelContenedor.removeAll();
                vista.panelContenedor.add(p2, BorderLayout.CENTER);
                vista.panelContenedor.revalidate();
                vista.panelContenedor.repaint();
            }
        });
        //Evento para el JLabel dentro del btnCuentas y que funcione el click
        vista.etiCuentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                vista.btnCuentas.dispatchEvent(evt);  // Redirige el evento al JPanel
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
                resetColor(vista.btnAdmin);
                
                //Establece el Panel Depósitos al pulsar DEPOSITOS
                PanelDepositos p3 = new PanelDepositos();
                p3.setSize(970, 600);
                p3.setLocation(0, 0);
                vista.panelContenedor.removeAll();
                vista.panelContenedor.add(p3, BorderLayout.CENTER);
                vista.panelContenedor.revalidate();
                vista.panelContenedor.repaint();
            }
        });
        //Evento para el JLabel dentro del btnDepositos y que funcione el click
        vista.etiDepositos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                vista.btnDepositos.dispatchEvent(evt);  // Redirige el evento al JPanel
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
                resetColor(vista.btnAdmin);
                
                //Establece el Panel Fondos al pulsar FONDOS
                PanelFondos p4 = new PanelFondos();
                p4.setSize(970, 600);
                p4.setLocation(0, 0);
                vista.panelContenedor.removeAll();
                vista.panelContenedor.add(p4, BorderLayout.CENTER);
                vista.panelContenedor.revalidate();
                vista.panelContenedor.repaint();
            }
        });
        //Evento para el JLabel dentro del btnFondos y que funcione el click
        vista.etiFondos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                vista.btnFondos.dispatchEvent(evt);  // Redirige el evento al JPanel
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
                resetColor(vista.btnAdmin);
                
                //Establece el Panel Bolsa al pulsar BOLSA
                PanelBolsa p5 = new PanelBolsa();
                p5.setSize(970, 600);
                p5.setLocation(0, 0);
                vista.panelContenedor.removeAll();
                vista.panelContenedor.add(p5, BorderLayout.CENTER);
                vista.panelContenedor.revalidate();
                vista.panelContenedor.repaint();
            }
        });
        //Evento para el JLabel dentro del btnBolsa y que funcione el click
        vista.etiBolsa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                vista.btnBolsa.dispatchEvent(evt);  // Redirige el evento al JPanel
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
                resetColor(vista.btnAdmin);
                
                //Establece el Panel Informes al pulsar INFORMES
                PanelInformes p6 = new PanelInformes();
                p6.setSize(970, 600);
                p6.setLocation(0, 0);
                vista.panelContenedor.removeAll();
                vista.panelContenedor.add(p6, BorderLayout.CENTER);
                vista.panelContenedor.revalidate();
                vista.panelContenedor.repaint();
            }
        });
        //Evento para el JLabel dentro del btnInformes y que funcione el click
        vista.etiInformes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                vista.btnInformes.dispatchEvent(evt);  // Redirige el evento al JPanel
            }
        });

        // Evento del botón ADMINISTRAR
        vista.btnAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                resetColor(vista.btnPrincipal);
                resetColor(vista.btnCuentas);
                resetColor(vista.btnDepositos);
                resetColor(vista.btnFondos);
                resetColor(vista.btnBolsa);
                resetColor(vista.btnInformes);
                setColor(vista.btnAdmin);
                
                //Establece el Panel Admin al pulsar ADMINISTRAR
                PanelAdmin p7 = new PanelAdmin();
                p7.setSize(970, 600);
                p7.setLocation(0, 0);
                vista.panelContenedor.removeAll();
                vista.panelContenedor.add(p7, BorderLayout.CENTER);
                vista.panelContenedor.revalidate();
                vista.panelContenedor.repaint();
            }
        });
        //Evento para el JLabel dentro del btnAdmin y que funcione el click
        vista.etiAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                vista.btnAdmin.dispatchEvent(evt);  // Redirige el evento al JPanel
            }
        });
    }

    //Pone de un azul más intenso el botón seleccionado
    void setColor(JPanel panel) {
        panel.setBackground(new Color(21, 101, 192));
    }
    //Devuelve los botones a su color original
    void resetColor(JPanel panel) {
        panel.setBackground(new Color(18, 90, 173));
    }
    
    //Método para cargar los datos del usuario logueado
    private void cargarDatosUsuario() {
        vista.etiEmail.setText(usuario.getEmail());  //Muestra el email en la vista Principal
        vista.etiNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
    }
}
