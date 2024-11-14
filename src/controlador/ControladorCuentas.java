package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaCuentas;
import modelo.ConsultaMovimientos;
import modelo.Cuenta;
import modelo.Usuario;
import vista.PanelCuentas;
import vista.PanelMovimientosCuenta;
import vista.componentes.IconRendererEditor;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorCuentas {

    private final PanelCuentas vista; // La vista donde se muestran las cuentas
    private final ConsultaCuentas consultaCuentas; // El modelo para obtener los datos de las cuentas
    private final Usuario usuario; // Usuario actual para obtener sus datos
    private final ConsultaMovimientos consultaMovimientos;

    // Constructor para inicializar la vista, el modelo y el usuario
    public ControladorCuentas(PanelCuentas vista, ConsultaCuentas consultaCuentas, Usuario usuario, ConsultaMovimientos consultaMovimientos) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;
        this.usuario = usuario;
        this.consultaMovimientos = consultaMovimientos;
        inicializarEventos(); // Inicializar eventos de la interfaz
        cargarCuentas(); // Cargar las cuentas en la tabla al inicio
    }

    // Configura eventos básicos (clicks en botones y etiquetas)
    private void inicializarEventos() {
        // Saludo con el nombre del usuario
        vista.etiNombre.setText("Hola, " + usuario.getNombre() + "!!");
        vista.etiSaldoTotal.setText(consultaCuentas.obtenerSaldoTotal(usuario.getCodigo()) + "€");

        // Evento para el botón y etiqueta de "Añadir cuenta"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAñadirCuenta(); // Abre el panel para agregar cuentas
            }
        });
        vista.etiAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAñadirCuenta();
            }
        });

        // Evento para el botón y etiqueta de "Eliminar cuenta"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarCuentaSeleccionada(); // Llama a eliminar cuenta
            }
        });
        vista.etiElminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarCuentaSeleccionada();
            }
        });

        // Evento para el botón y etiqueta de "Modificar cuenta"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarCuenta(); // Llama a modificar cuenta
            }
        });
        vista.etiModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarCuenta();
            }
        });
    }

    // Cargar y mostrar las cuentas del usuario en la tabla
    private void cargarCuentas() {
        List<Cuenta> cuentas = consultaCuentas.obtenerCuentas(usuario.getCodigo()); // Trae las cuentas del usuario

        // Configura el modelo de la tabla para limpiar datos previos
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaCuentas.getModel();
        modelo.setRowCount(0); // Limpiar las filas

        // Rellena la tabla con los datos de las cuentas
        for (Cuenta cuenta : cuentas) {
            Object[] fila = {
                cuenta.getIdCuenta(),
                cuenta.getBanco(),
                cuenta.getAlias(),
                cuenta.getIban(),
                cuenta.getSaldo(),
                "Detalles" // Esto se verá como el botón de detalles
            };
            modelo.addRow(fila);
        }

        // Configura el icono en la columna de detalles para abrir el panel movimientos
        vista.tablaCuentas.getColumnModel().getColumn(5).setCellRenderer(new IconRendererEditor(vista.tablaCuentas, this));
        vista.tablaCuentas.getColumnModel().getColumn(5).setCellEditor(new IconRendererEditor(vista.tablaCuentas, this));

        // Elimina cualquier MouseListener previo en la columna de detalles
        for (MouseListener listener : vista.tablaCuentas.getMouseListeners()) {
            vista.tablaCuentas.removeMouseListener(listener);
        }

        // Añade el listener solo para la columna de detalles
    vista.tablaCuentas.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = vista.tablaCuentas.rowAtPoint(e.getPoint());
            int columna = vista.tablaCuentas.columnAtPoint(e.getPoint());

            // Actúa solo si el clic fue en la columna de detalles (columna 5)
            if (columna == 5) {
                mostrarPanelMovimientos(fila);
            } else {
                // Permite la selección de filas si el clic no es en la columna de detalles
                vista.tablaCuentas.setRowSelectionInterval(fila, fila);
            }
        }
    });
    }

    // Muestra un panel emergente para añadir una cuenta nueva
    public void mostrarPanelAñadirCuenta() {
        // Campos de entrada para el alias, IBAN, banco y saldo
        JTextField campoAlias = new JTextField(15);        
        JTextField campoIban = new JTextField(15);
        JTextField campoBanco = new JTextField(15);
        JTextField campoSaldo = new JTextField(10);
        

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.add(new JLabel("Alias:"));
        panel.add(campoAlias);
        panel.add(new JLabel("IBAN:"));
        panel.add(campoIban);
        panel.add(new JLabel("Banco:"));
        panel.add(campoBanco);
        panel.add(new JLabel("Saldo inicial:"));
        panel.add(campoSaldo);

        // Abre un diálogo para añadir la cuenta
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Añadir nueva cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Recoge y valida los datos introducidos
                String alias = campoAlias.getText();
                String iban = campoIban.getText();
                String banco = campoBanco.getText();
                double saldoInicial = Double.parseDouble(campoSaldo.getText());

                // Llama al modelo para añadir la cuenta
                boolean cuentaAñadida = consultaCuentas.añadirCuenta(usuario.getCodigo(), banco, alias, iban, saldoInicial);

                // Actualiza la etiqueta de saldo total
                vista.etiSaldoTotal.setText(consultaCuentas.obtenerSaldoTotal(usuario.getCodigo()) + "€");

                if (cuentaAñadida) {
                    JOptionPane.showMessageDialog(vista, "Cuenta añadida correctamente.");
                    cargarCuentas(); // Refresca la tabla con la nueva cuenta
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al añadir la cuenta. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos de banco y saldo.");
            }
        }
    }

    // Elimina la cuenta seleccionada en la tabla
    public void eliminarCuentaSeleccionada() {
        int filaSeleccionada = vista.tablaCuentas.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona una cuenta para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCuenta = (int) vista.tablaCuentas.getValueAt(filaSeleccionada, 0); // ID de la cuenta seleccionada

        // Pregunta si el usuario realmente quiere eliminar la cuenta
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar la cuenta seleccionada?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Llama al modelo para eliminar la cuenta
            if (consultaCuentas.eliminarCuenta(idCuenta)) {
                JOptionPane.showMessageDialog(vista, "La cuenta ha sido eliminada correctamente.");

                // Recarga la tabla y actualiza el saldo total
                cargarCuentas();
                vista.etiSaldoTotal.setText(consultaCuentas.obtenerSaldoTotal(usuario.getCodigo()) + "€");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Muestra un panel emergente para modificar la cuenta seleccionada
    public void mostrarPanelModificarCuenta() {
        int filaSeleccionada = vista.tablaCuentas.getSelectedRow();

        // Verifica que haya una cuenta seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona una cuenta para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCuenta = (int) vista.tablaCuentas.getValueAt(filaSeleccionada, 0);

        // Obtén los datos de la cuenta a modificar
        Cuenta cuenta = consultaCuentas.obtenerCuentaPorId(idCuenta);
        if (cuenta == null) {
            JOptionPane.showMessageDialog(vista, "Error al obtener los datos de la cuenta seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Campos de entrada con los datos actuales de la cuenta
        JTextField campoAlias = new JTextField(cuenta.getAlias(), 15);
        JTextField campoIban = new JTextField(cuenta.getIban(), 15);
        JTextField campoBanco = new JTextField(cuenta.getBanco(), 15);
        JTextField campoSaldo = new JTextField(String.valueOf(cuenta.getSaldo()), 10);
        campoSaldo.setEditable(false); // Hacer que el saldo no sea editable
        
        
        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.add(new JLabel("Alias:"));
        panel.add(campoAlias);
        panel.add(new JLabel("IBAN:"));
        panel.add(campoIban);
        panel.add(new JLabel("Banco:"));
        panel.add(campoBanco);
        panel.add(new JLabel("Saldo actual:"));
        panel.add(campoSaldo);

        // Abre un cuadro de diálogo para modificar la cuenta
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Recoge los datos modificados
                String alias = campoAlias.getText();
                String iban = campoIban.getText();
                String banco = campoBanco.getText();
                double saldoActual = Double.parseDouble(campoSaldo.getText());

                // Llama al modelo para modificar la cuenta
                boolean cuentaModificada = consultaCuentas.modificarCuenta(idCuenta, banco, alias, iban, saldoActual);

                if (cuentaModificada) {
                    JOptionPane.showMessageDialog(vista, "Cuenta modificada correctamente.");
                    cargarCuentas(); // Refresca la tabla con los cambios
                    vista.etiSaldoTotal.setText(consultaCuentas.obtenerSaldoTotal(usuario.getCodigo()) + "€");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar la cuenta. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos de banco y saldo.");
            }
        }
    }

    public void mostrarPanelMovimientos(int fila) {
        int idCuenta = (int) vista.tablaCuentas.getValueAt(fila, 0);

        // Crea el panel de movimientos y el controlador para manejarlo
        PanelMovimientosCuenta panelMovimientos = new PanelMovimientosCuenta(idCuenta);
        new ControladorMovimientosCuenta(panelMovimientos, consultaCuentas, idCuenta, consultaMovimientos);

        JDialog dialogoMovimientos = new JDialog();
        dialogoMovimientos.setTitle("Movimientos de la Cuenta");
        dialogoMovimientos.setModal(true);
        dialogoMovimientos.getContentPane().add(panelMovimientos);
        dialogoMovimientos.pack();
        dialogoMovimientos.setLocationRelativeTo(vista);
        dialogoMovimientos.setVisible(true);
    }

}
