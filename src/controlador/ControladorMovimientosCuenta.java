package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaCuentas;
import modelo.ConsultaMovimientos;
import modelo.Movimiento;
import vista.PanelEditarMovimientos;
import vista.PanelMovimientosCuenta;

public class ControladorMovimientosCuenta {

    private final PanelMovimientosCuenta vista; // Vista del panel MovimientosCuenta
    private final ConsultaCuentas consultaCuentas; // ControladorCuentas para obtener los movimientos
    private final ConsultaMovimientos consultaMovimientos;
    private final int idCuenta; // ID de la cuenta seleccionada

    public ControladorMovimientosCuenta(PanelMovimientosCuenta vista, ConsultaCuentas consultaCuentas, int idCuenta, ConsultaMovimientos consultaMovimientos) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;
        this.idCuenta = idCuenta;
        this.consultaMovimientos = consultaMovimientos;
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
                mostrarPanel();
            }
        });
        vista.etiAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanel();
            }
        });

        // Evento para el botón y etiqueta de "Eliminar movimiento"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //eliminarMovimientoSeleccionado(); // Llama a eliminar movimiento
            }
        });
        vista.etiElminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                //eliminarMovimientoSeleccionado();
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
    private void mostrarPanel(){
        
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
            if (movimiento.getTipo().equals("Ingreso")) {
                saldoActual += movimiento.getImporte();
            } else if (movimiento.getTipo().equals("Pago")) {
                saldoActual -= movimiento.getImporte();
            }
            // Obtener los nombres de categoría y subcategoría
            String nombreCategoria = consultaCuentas.obtenerNombreCategoria(movimiento.getIdCategoria());
            String nombreSubcategoria = movimiento.getIdSubcategoria() != null
                    ? consultaCuentas.obtenerNombreSubcategoria(movimiento.getIdSubcategoria())
                    : ""; // Dejar vacío si no hay subcategoría

            // Crear un array con los datos del movimiento para cada fila de la tabla
            Object[] fila = {
                movimiento.getFecha(),
                movimiento.getTipo(),
                nombreCategoria, // Mostrar el nombre de la categoría
                nombreSubcategoria, // Mostrar el nombre de la subcategoría
                movimiento.getNotas(),
                movimiento.getImporte(),
                saldoActual
            };
            // Añadir la fila al modelo de la tabla
            modelo.addRow(fila);
        }
        // Actualizar el saldo en la base de datos
        if (!consultaCuentas.actualizarSaldoCuenta(idCuenta, saldoActual)) {
            System.err.println("Error al actualizar el saldo de la cuenta.");
        }
    }

}
