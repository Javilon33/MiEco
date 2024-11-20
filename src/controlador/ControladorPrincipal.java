package controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelo.ConsultaCuentas;
import modelo.ConsultaMovimientos;
import modelo.entidades.Usuario;
import vista.Paneles.PanelAdmin;
import vista.Paneles.PanelBolsa;
import vista.Paneles.PanelCuentas;
import vista.Paneles.PanelDepositos;
import vista.Paneles.PanelFondos;
import vista.Paneles.PanelInformes;
import vista.Paneles.PanelPrincipal;
import vista.VistaLogin;
import vista.VistaPrincipal;

public class ControladorPrincipal {

    private final VistaPrincipal vista; // Instancia de la vista principal
    private final Usuario usuario; // Usuario logueado
    private int xMouse, yMouse;

    // Definición de los paneles y controladores
    private PanelCuentas vistaCuentas;
    private ControladorCuentas controladorCuentas;
    private PanelPrincipal panelPrincipal;
    private PanelDepositos panelDepositos;
    private PanelFondos panelFondos;
    private PanelBolsa panelBolsa;
    private PanelInformes panelInformes;
    private PanelAdmin panelAdmin;

    public ControladorPrincipal(VistaPrincipal vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        inicializarEventos();
        cargarDatosUsuario();
        cargarPaneles(); // Inicialización de los paneles y controladores

        // Establece el Panel principal al inicio de la aplicación
        mostrarPanel(panelPrincipal);

        // Oculta el botón Admin si el usuario no es administrador
        if (usuario.getAdmin() == 0) {
            vista.btnAdmin.setVisible(false);
        }
    }

    private void inicializarEventos() {
        // Mueve la ventana con el encabezado
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

        // Configura el evento del botón de salida
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
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres cerrar la aplicación?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        //BOTON PRINCIPAL
        // Evento para mostrar el panel principal al hacer clic en el botón PRINCIPAL
        vista.btnPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnPrincipal);
                mostrarPanel(panelPrincipal);
            }
        });
        //Evento para el texto JLabel y que funcione el click
        vista.etiPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnPrincipal);
                mostrarPanel(panelPrincipal);
            }
        });

        //BOTON CUENTAS
        // Evento para mostrar el panel de cuentas al hacer clic en el botón CUENTAS
        vista.btnCuentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnCuentas);
                mostrarPanel(vistaCuentas);
            }
        });
        //Evento para el texto JLabel y que funcione el click
        vista.etiCuentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnCuentas);
                mostrarPanel(vistaCuentas);
            }
        });

        //BOTON DEPOSITOS
        // Evento para mostrar el panel de cuentas al hacer clic en el botón DEPOSITOS
        vista.btnDepositos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnDepositos);
                mostrarPanel(panelDepositos);
            }
        });
        //Evento para el texto JLabel y que funcione el click
        vista.etiDepositos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnDepositos);
                mostrarPanel(panelDepositos);
            }
        });

        //BOTON FONDOS
        // Evento para mostrar el panel de cuentas al hacer clic en el botón FONDOS
        vista.btnFondos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnFondos);
                mostrarPanel(panelFondos);
            }
        });
        //Evento para el texto JLabel y que funcione el click
        vista.etiFondos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnFondos);
                mostrarPanel(panelFondos);
            }
        });

        //BOTON BOLSA
        // Evento para mostrar el panel de cuentas al hacer clic en el botón BOLSA
        vista.btnBolsa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnBolsa);
                mostrarPanel(panelBolsa);
            }
        });
        //Evento para el texto JLabel y que funcione el click
        vista.etiBolsa.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnBolsa);
                mostrarPanel(panelBolsa);
            }
        });

        //BOTON INFORMES
        // Evento para mostrar el panel de cuentas al hacer clic en el botón INFORMES
        vista.btnInformes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnInformes);
                mostrarPanel(panelInformes);
            }
        });
        //Evento para el texto JLabel y que funcione el click
        vista.etiInformes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnInformes);
                mostrarPanel(panelInformes);
            }
        });

        //BOTON ADMIN
        // Evento para mostrar el panel de cuentas al hacer clic en el botón ADMIN
        vista.btnAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnAdmin);
                mostrarPanel(panelAdmin);
            }
        });
        //Evento para el texto JLabel y que funcione el click
        vista.etiAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                cambiarColorBotones(vista.btnAdmin);
                mostrarPanel(panelAdmin);
            }
        });

        //BOTON LOGOUT
        vista.btnLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres desloguearte?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    // Cierra la ventana principal                    
                    vista.dispose();

                    // Muestra la vista de inicio de sesión
                    VistaLogin vistaLogin = new VistaLogin(); // Crear una nueva instancia de la vista de inicio de sesión
                    ControladorLogin controladorLogin = new ControladorLogin(vistaLogin); // Pasar la vista al controlador del login
                    vistaLogin.setVisible(true); // Mostrar la vista de inicio de sesión
                }
            }
        });
    }

    private void cargarDatosUsuario() {
        vista.etiEmail.setText(usuario.getEmail());  // Muestra el email en la vista Principal
        vista.etiNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
    }

    // Método para instanciar y configurar todos los paneles y controladores
    private void cargarPaneles() {
        // Panel principal
        panelPrincipal = new PanelPrincipal();
        panelPrincipal.setSize(970, 600);

        // Panel de cuentas y su controlador
        vistaCuentas = new PanelCuentas();
        ConsultaCuentas consultaCuentas = new ConsultaCuentas();
        ConsultaMovimientos consultamMovimiento = new ConsultaMovimientos();
        controladorCuentas = new ControladorCuentas(vistaCuentas, consultaCuentas, usuario, consultamMovimiento);

        // Otros paneles
        panelDepositos = new PanelDepositos();
        panelFondos = new PanelFondos();
        panelBolsa = new PanelBolsa();
        panelInformes = new PanelInformes();
        panelAdmin = new PanelAdmin();
    }

    private void mostrarPanel(JPanel panel) {
        panel.setSize(970, 600);
        panel.setLocation(0, 0);
        vista.panelContenedor.removeAll();
        vista.panelContenedor.add(panel, BorderLayout.CENTER);
        vista.panelContenedor.revalidate();
        vista.panelContenedor.repaint();
    }

    private void cambiarColorBotones(JPanel botonSeleccionado) {
        resetColor(vista.btnPrincipal);
        resetColor(vista.btnCuentas);
        resetColor(vista.btnDepositos);
        resetColor(vista.btnFondos);
        resetColor(vista.btnBolsa);
        resetColor(vista.btnInformes);
        resetColor(vista.btnAdmin);
        setColor(botonSeleccionado);
    }

    private void setColor(JPanel panel) {
        panel.setBackground(new Color(21, 101, 192));
    }

    private void resetColor(JPanel panel) {
        panel.setBackground(new Color(18, 90, 173));
    }
}
