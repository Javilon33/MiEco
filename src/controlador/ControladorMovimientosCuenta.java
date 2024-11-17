package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaCuentas;
import modelo.ConsultaMovimientos;
import modelo.entidades.Movimiento;
import rojeru_san.componentes.RSDateChooser;
import vista.Paneles.PanelEditarMovimientos;
import vista.Paneles.PanelMovimientosCuenta;

public class ControladorMovimientosCuenta {

    private final PanelMovimientosCuenta vista; // Vista del panel MovimientosCuenta
    private final ConsultaCuentas consultaCuentas; // ControladorCuentas para obtener los movimientos
    private final ConsultaMovimientos consultaMovimientos;
    private final int idCuenta; // ID de la cuenta seleccionada
    private final ControladorCuentas controladorCuentas; // Referencia al ControladorCuentas

    public ControladorMovimientosCuenta(PanelMovimientosCuenta vista, ConsultaCuentas consultaCuentas, int idCuenta, ConsultaMovimientos consultaMovimientos, ControladorCuentas controladorCuentas) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;
        this.idCuenta = idCuenta;
        this.consultaMovimientos = consultaMovimientos;
        this.controladorCuentas = controladorCuentas;
        inicializarEventos();  // Inicializar eventos de la interfaz
        cargarMovimientos(); // Cargar los movimientos al inicio
    }

    // Método para inicializar la vista con el nombre de la cuenta
    private void inicializarEventos() {
        // Obtener el nombre de la cuenta
        String nombreCuenta = consultaCuentas.obtenerCuentaPorId(idCuenta).getAlias();
        //Obtener el nombre del banco
        String banco = consultaCuentas.obtenerCuentaPorId(idCuenta).getBanco();
        // Establecer el texto del título con el nombre del banco y la cuenta
        vista.etiBanco.setText(banco);
        vista.etiNombreCuenta.setText(nombreCuenta);

        // Evento para el botón y etiqueta de "Añadir movimiento"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //mostrarPanel();
                mostrarPanelAddMovimiento();
            }
        });
        vista.etiAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //mostrarPanel();
                mostrarPanelAddMovimiento();
            }
        });

        // Evento para el botón y etiqueta de "Eliminar movimiento"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarMovimientoSeleccionado(); // Llama a eliminar movimiento
            }
        });
        vista.etiElminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                eliminarMovimientoSeleccionado();
            }
        });

        // Evento para el botón y etiqueta de "Modificar movimiento"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //mostrarPanelModificarMovimiento(); // Llama a modificar movimiento
            }
        });
        vista.etiModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //mostrarPanelModificarMovimiento();
            }
        });
    }

    //Método para mostrar el panelEditarMovimientos
    private void mostrarPanel() {

        // Crear el panel y asignar controlador y consulta
        PanelEditarMovimientos panel = new PanelEditarMovimientos();
        ConsultaMovimientos consul = new ConsultaMovimientos();
        ControladorEditarMovimientos control = new ControladorEditarMovimientos(panel, consul);
        // Crear el JDialog
        JDialog dialog = new JDialog();  // Crear un JDialog sin parámetros
        dialog.setUndecorated(true);  // Quitar el borde y la barra de título
        dialog.setModal(true);  // Para que sea modal (impide interacción con la ventana principal hasta cerrarlo)

        // Añadir el panel al JDialog
        dialog.add(panel);

        // Configurar tamaño del dialog
        dialog.setSize(880, 149);  // Ajusta el tamaño de la ventana emergente

        // Centrar la ventana emergente en la pantalla
        dialog.setLocationRelativeTo(null);  // Lo pone en el centro de la pantalla

        // Pasar el dialog al controlador 
        control.setDialog(dialog);

        // Hacer visible el JDialog
        dialog.setVisible(true);
    }

    // Método para cargar los movimientos de la cuenta y mostrarlos en la tabla
    private void cargarMovimientos() {
        // Obtener los movimientos de la cuenta desde el controlador
        List<Movimiento> movimientos = consultaMovimientos.obtenerMovimientos(idCuenta);

        // Obtener el saldo inicial de la cuenta
        double saldoActual = consultaCuentas.obtenerSaldoInicial(idCuenta);

        // Obtener el modelo de la tabla y limpiar cualquier dato existente
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMovimientos.getModel();
        modelo.setRowCount(0); // Limpiar las filas de la tabla

        // Añadir los movimientos a la tabla
        for (Movimiento movimiento : movimientos) {
            // Ajustar el saldo actual basado en el tipo de movimiento
            saldoActual += movimiento.getImporte();
            /*if (movimiento.getTipo() == 1) {
                saldoActual += movimiento.getImporte();
            } else if (movimiento.getTipo() == 2) {
                saldoActual -= movimiento.getImporte();
            }*/
            
            // Obtener los nombres de Tipo Movimiento, subtipo y gasto
            String nombreTipo = consultaMovimientos.obtenerNombreTipoMovimiento(movimiento.getId_movimiento());
            String nombreSubtipo = consultaMovimientos.obtenerNombreSubtipoMovimiento(movimiento.getId_movimiento());
            String nombreGasto = movimiento.getTipoGasto() != null ? consultaMovimientos.obtenerNombreGasto(movimiento.getId_movimiento()) : ""; // Dejar vacío si no hay tipo Gasto

            // Verificar si el movimiento es un pago, y si es así, convertir el importe a negativo
            double importe = movimiento.getImporte();
            if (movimiento.getTipo() == 2) {
                importe = -importe; // Invertir el signo del importe para los pagos
            }

            // Crear un array con los datos del movimiento para cada fila de la tabla
            Object[] fila = {
                movimiento.getId_movimiento(), // Incluir el id_movimiento (oculto)
                movimiento.getFecha(),
                nombreTipo,
                nombreSubtipo,
                nombreGasto,
                movimiento.getNotas(),
                movimiento.getImporte(),
                saldoActual
            };
            // Añadir la fila al modelo de la tabla
            modelo.addRow(fila);
        }

        // Oculta la columna del id_movimiento
        vista.tablaMovimientos.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tablaMovimientos.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tablaMovimientos.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Actualizar el saldo en la base de datos
        if (!consultaCuentas.actualizarSaldoCuenta(idCuenta, saldoActual)) {
            System.err.println("Error al actualizar el saldo de la cuenta.");
        }
    }

    // Muestra un panel emergente para AÑADIR CUENTA 
    public void mostrarPanelAddMovimiento() {
        // Campos de entrada para los datos
        RSDateChooser campoFecha = new RSDateChooser();
        JComboBox cbTipo = new JComboBox();
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"INGRESO", "PAGO"}));
        JTextField campoNotas = new JTextField(20);
        JTextField campoImporte = new JTextField(10);

        // ComboBox para seleccionar categoría
        JComboBox<String> cbCategoria = new JComboBox<>();
        Map<Integer, String> listaCategoria = consultaMovimientos.listaSubtipos(1); // Por defecto, "1 = Ingreso"
        cbCategoria.setModel(new DefaultComboBoxModel<>(listaCategoria.values().toArray(new String[0])));

        // Escucha para actualizar las categorías cuando cambia el tipo
        cbTipo.addActionListener(e -> {
            int tipoSeleccionado = cbTipo.getSelectedIndex() + 1; //(1 = Ingreso, 2 = Pago)
            Map<Integer, String> nuevasCategorias = consultaMovimientos.listaSubtipos(tipoSeleccionado);
            cbCategoria.setModel(new DefaultComboBoxModel<>(nuevasCategorias.values().toArray(new String[0])));
        });

        // ComboBox para seleccionar el Gasto
        JComboBox<String> cbGasto = new JComboBox<>();

        // Escucha para actualizar el ComboBox de Gasto cuando cambia el tipo
        cbTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tipoSeleccionado = cbTipo.getSelectedIndex() + 1;  // 1 = Ingreso, 2 = Pago
                int categoriaseleccionada = cbCategoria.getSelectedIndex();  // Categoría seleccionada

                if (tipoSeleccionado == 2 && categoriaseleccionada == 0) {
                    // Rellenamos el ComboBox con los gastos
                    Map<String, String> listaGasto = consultaMovimientos.listaGastos();  // Obtenemos la lista de gastos
                    cbGasto.setModel(new DefaultComboBoxModel<>(listaGasto.values().toArray(new String[1])));
                } else {
                    // Si las condiciones no se cumplen limpiamos el ComboBox
                    cbGasto.setModel(new DefaultComboBoxModel<>(new String[0]));
                }
            }
        });

        // Escucha para actualizar los gastos cuando cambia la categoría
        cbCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tipoSeleccionado = cbTipo.getSelectedIndex() + 1;  // 1 = Ingreso, 2 = Pago
                int categoriaseleccionada = cbCategoria.getSelectedIndex();  // Categoría seleccionada

                if (tipoSeleccionado == 2 && categoriaseleccionada == 0) {

                    // Rellenamos el ComboBox con los gastos
                    Map<String, String> listaGasto = consultaMovimientos.listaGastos();  // Obtenemos la lista de gastos                    
                    cbGasto.setModel(new DefaultComboBoxModel<>(listaGasto.values().toArray(new String[1])));
                } else {

                    // Si las condiciones no se cumplen limpiamos el ComboBox
                    cbGasto.setModel(new DefaultComboBoxModel<>(new String[0]));

                }
            }
        });

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(campoFecha);
        panel.add(new JLabel("Tipo:"));
        panel.add(cbTipo);
        panel.add(new JLabel("Categoría:"));
        panel.add(cbCategoria);
        panel.add(new JLabel("Tipo de Gasto:"));
        panel.add(cbGasto);
        panel.add(new JLabel("Notas:"));
        panel.add(campoNotas);
        panel.add(new JLabel("Importe:"));
        panel.add(campoImporte);

        // Abre un diálogo para añadir el movimiento
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Añadir nuevo movimiento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Recoge y valida los datos introducidos
                //FORMATO A LA FECHA    
                String formatoFecha = "yyyy/MM/dd";
                Date fechaDate = campoFecha.getDatoFecha();
                SimpleDateFormat formato = new SimpleDateFormat(formatoFecha);
                String fecha = formato.format(fechaDate);

                // Recoge y valida los datos introducidos                
                int tipo = cbTipo.getSelectedIndex() + 1;
                System.out.println("tipo" + tipo);
                int categoria = cbCategoria.getSelectedIndex() + 1;
                System.out.println("categoria" + categoria);
                Integer gasto = 1;
                if (cbGasto.getSelectedIndex() != -1) {
                    gasto = cbGasto.getSelectedIndex() + 1;
                }
                System.out.println("gasto" + gasto);
                String notas = campoNotas.getText();
                System.out.println(notas);
                double importe = Double.parseDouble(campoImporte.getText());

                // Llama al modelo para añadir el movimiento
                boolean movimientoInsertado = consultaMovimientos.addMovimiento(idCuenta, fecha, tipo, categoria, gasto, notas, importe);

                if (movimientoInsertado) {
                    JOptionPane.showMessageDialog(vista, "Movimiento añadido correctamente.");
                    cargarMovimientos(); // Refresca la tabla con el nuevo movimiento
                    controladorCuentas.cargarCuentas(); // Refresca la tabla de cuentas con el nuevo movimiento
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al añadir el movimiento. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
            }
        }
    }

    // ELIMINAR MOVIMIENTO seleccionado en la tabla
    public void eliminarMovimientoSeleccionado() {
        int filaSeleccionada = vista.tablaMovimientos.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona el movimiento que quieres eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMovimiento = (int) vista.tablaMovimientos.getValueAt(filaSeleccionada, 0); // ID del movimiento selecionado

        // Pregunta si el usuario realmente quiere eliminar la cuenta
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar el movimiento seleccionado?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Llama al modelo para eliminar la cuenta
            if (consultaMovimientos.eliminarMovimiento(idMovimiento)) {
                JOptionPane.showMessageDialog(vista, "El movimiento ha sido eliminado correctamente.");

                // Recarga la tabla y actualiza el saldo total
                cargarMovimientos(); // Refresca la tabla sin el movimiento elimnado
                controladorCuentas.cargarCuentas(); // Refresca la tabla de cuentas sin el movimiento elimnado

            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
