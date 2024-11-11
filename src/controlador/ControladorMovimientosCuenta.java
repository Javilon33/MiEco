package controlador;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaCuentas;
import modelo.Movimiento;
import vista.PanelMovimientosCuenta;

public class ControladorMovimientosCuenta {

    private final PanelMovimientosCuenta vista; // Vista del panel MovimientosCuenta
    private final ConsultaCuentas consultaCuentas; // ControladorCuentas para obtener los movimientos
    private final int idCuenta; // ID de la cuenta seleccionada

    public ControladorMovimientosCuenta(PanelMovimientosCuenta vista, ConsultaCuentas consultaCuentas, int idCuenta) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;
        this.idCuenta = idCuenta;
        inicializarVista(); // Inicializar los componentes de la vista
        cargarMovimientos(); // Cargar los movimientos al inicio
    }

    // Método para inicializar la vista con el nombre de la cuenta
    private void inicializarVista() {
        // Obtener el nombre de la cuenta
        String nombreCuenta = consultaCuentas.obtenerNombreCuenta(idCuenta);
        //Obtener el nombre del banco
        String banco = consultaCuentas.obtenerNombreBanco(idCuenta);
        // Establecer el texto del título con el nombre de la cuenta
        vista.etiNombreCuenta.setText(nombreCuenta+ " (" + banco + ")");
    }

    // Método para cargar los movimientos de la cuenta y mostrarlos en la tabla
    private void cargarMovimientos() {
        // Obtener los movimientos de la cuenta desde el controlador
        List<Movimiento> movimientos = consultaCuentas.obtenerMovimientos(idCuenta);

        // Obtener el modelo de la tabla y limpiar cualquier dato existente
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMovimientos.getModel();
        modelo.setRowCount(0); // Limpiar las filas de la tabla

        // Añadir los movimientos a la tabla
        for (Movimiento movimiento : movimientos) {
            // Crear un array con los datos del movimiento para cada fila de la tabla
            Object[] fila = {
                movimiento.getFecha(),
                movimiento.getTipo(),
                movimiento.getIdCategoria(),
                movimiento.getIdSubcategoria(),
                movimiento.getNotas(),
                movimiento.getImporte(),
                movimiento.getSaldoResultante()
            };
            // Añadir la fila al modelo de la tabla
            modelo.addRow(fila);
        }
    }
}
