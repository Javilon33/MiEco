package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.ConsultaDepositos;
import modelo.ConsultaMovimientos;
import modelo.entidades.Deposito;
import modelo.entidades.Usuario;
import rojeru_san.componentes.RSDateChooser;
import vista.Paneles.PanelDepositos;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorDepositos {

    private final PanelDepositos vista; // La vista donde se muestran los depósitos
    private final ConsultaDepositos consultaDepositos; // El modelo para obtener los datos de los depósitos
    private final Usuario usuario; // Usuario actual para obtener sus datos
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)

    public ControladorDepositos(PanelDepositos vista, ConsultaDepositos consultaDepositos, Usuario usuario) {
        this.vista = vista;
        this.consultaDepositos = consultaDepositos;
        this.usuario = usuario;

        // Configurar el formato para importes (una sola vez)
        this.formato = NumberFormat.getInstance(new Locale("es", "ES"));
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);

        inicializarEventos(); // Inicializar eventos de la interfaz
        cargarDepositos(); // Cargar los depositos en la tabla al inicio          
    }

    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {

        //Muestra el saldo actual de todos los depósitos (con 2 decimales)
        double saldoTotal = consultaDepositos.obtenerTotalDepositos(usuario.getCodigo());
        vista.etiTotal.setText(formato.format(+saldoTotal) + " €");

        // Evento para el botón y etiqueta de "Añadir depósito"
        vista.btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelAddDeposito(); // Abre el panel para agregar depósitos
            }
        });

        // Evento para el botón y etiqueta de "Modificar depósito"
        vista.btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mostrarPanelModificarDeposito(); // Abre el panel para modificar depósitos
            }
        });

        // Evento para el botón y etiqueta de "Eliminar depósito"
        vista.btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    eliminarDepositoSeleccionado(); // Llama a eliminar el depósito
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Evento para el botón y etiqueta de "Pasar a la Cuenta"
        vista.btnPasar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                depositoVencido(); // Genera los movimientos en la cuenta y elimina el depósito
            }
        });

    }

    //Método para cargar los depósitos desde la BD
    public void cargarDepositos() {

        //Muestra el saldo actual de todos los depósitos (con 2 decimales)
        double saldoTotal = consultaDepositos.obtenerTotalDepositos(usuario.getCodigo());
        vista.etiTotal.setText(formato.format(+saldoTotal) + " €");

        try {
            List<Deposito> depositos = consultaDepositos.obtenerDepositos(usuario.getCodigo());
            configurarPaneles(depositos);
            rellenarTablaDepositos(depositos);
        } catch (Exception ex) {
            Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, "Error al cargar depósitos", ex);
            JOptionPane.showMessageDialog(vista, "Error al cargar los depósitos. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Activa los paneles según los depósitos que haya
    private void configurarPaneles(List<Deposito> depositos) throws NoSuchFieldException, IllegalAccessException {
        Date hoy = new Date();
        for (int i = 0; i < 8; i++) {
            JPanel panel = (JPanel) vista.getClass().getDeclaredField("panelDeposito" + (i + 1)).get(vista);
            panel.setVisible(i < depositos.size());

            if (i < depositos.size()) {
                Deposito deposito = depositos.get(i);
                actualizarEtiquetasPanel(panel, deposito, hoy, i + 1);
            }
        }
        //He tenido que incluir revalidate y repaint para hacer un refresco después de cada cambio ya que el panel vencido salía mal
        vista.revalidate();
        vista.repaint();
    }

    //Rellena las etiquetas de los paneles activos
    private void actualizarEtiquetasPanel(JPanel panel, Deposito deposito, Date hoy, int indice) throws NoSuchFieldException, IllegalAccessException {
        JLabel etiDeposito = (JLabel) vista.getClass().getDeclaredField("etiDeposito" + indice).get(vista);
        etiDeposito.setText("DEPÓSITO Nº " + deposito.getIdDeposito());

        JLabel etiFecha = (JLabel) vista.getClass().getDeclaredField("etiFecha" + indice).get(vista);
        etiFecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(deposito.getFechaInicio()));

        JLabel etiImporteInicial = (JLabel) vista.getClass().getDeclaredField("etiImporteInicial" + indice).get(vista);
        etiImporteInicial.setText(formato.format(deposito.getImporteInicial()) + " €");

        JLabel etiImporteFinal = (JLabel) vista.getClass().getDeclaredField("etiImporteFinal" + indice).get(vista);
        etiImporteFinal.setText(formato.format(deposito.getImporteFinal()) + " €");

        double beneficio = deposito.getImporteFinal() - deposito.getImporteInicial();
        JLabel etiBeneficio = (JLabel) vista.getClass().getDeclaredField("etiBeneficio" + indice).get(vista);
        etiBeneficio.setText(formato.format(beneficio) + " €");

        JLabel fechaFin = (JLabel) vista.getClass().getDeclaredField("fechaFin" + indice).get(vista);
        fechaFin.setText(new SimpleDateFormat("dd-MM-yyyy").format(deposito.getFechaVencimiento()));

        JPanel panelVencido = (JPanel) vista.getClass().getDeclaredField("panelVencido" + indice).get(vista);
        panelVencido.setVisible(deposito.getFechaVencimiento().before(hoy));
    }

    //Rellena la tabla con los depósitos
    private void rellenarTablaDepositos(List<Deposito> depositos) {
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaDepositos.getModel();
        modelo.setRowCount(0);

        // Supongamos que tienes un mapa que contiene las cuentas con su alias y banco
        Map<Integer, String> cuentasConAliasYBanco = consultaDepositos.obtenerCuentasConAliasYBanco(usuario.getCodigo()); // Obtener este mapa previamente

        for (Deposito deposito : depositos) {
            // Obtener el alias+banco correspondiente a la cuenta
            String aliasBanco = cuentasConAliasYBanco.get(deposito.getIdCuenta());

            Object[] fila = {
                deposito.getIdDeposito(),
                deposito.getNombre(),
                aliasBanco, // Mostrar alias+nombre del banco en lugar de idCuenta
                new SimpleDateFormat("dd-MM-yyyy").format(deposito.getFechaInicio()),
                deposito.getMeses(),
                formato.format(deposito.getImporteInicial()) + " €",
                formato.format(deposito.getInteresAnual()) + "%",
                deposito.getFechaVencimiento().before(new Date()) ? "SI" : "NO"
            };
            modelo.addRow(fila);
        }
        ajustarAnchoColumnas(); // Llamamos a este método para ajustar el ancho de las columnas
    }

    //Muestra un panel emergente para AÑADIR un Deposito nuevo
    public void mostrarPanelAddDeposito() {
        // Campos de entrada para los datos del depósito
        JTextField campoNombre = new JTextField(20);
        JComboBox<String> cbCuenta = new JComboBox<>(); // ComboBox para las cuentas disponibles del usuario
        RSDateChooser fechaInicio = new RSDateChooser();
        JTextField campoMeses = new JTextField(5);
        JTextField campoImporte = new JTextField(15);
        JTextField campoInteres = new JTextField(5);

        // Carga de la lista de cuentas
        Map<Integer, String> listaCuentas = consultaDepositos.obtenerCuentasConAliasYBanco(usuario.getCodigo()); // Obtiene cuentas directamente
        Map<String, Integer> cuentaIds = new HashMap<>(); // Relación inversa para obtener el ID de la cuenta desde el alias
        for (Map.Entry<Integer, String> entry : listaCuentas.entrySet()) {
            String displayText = entry.getValue();
            cbCuenta.addItem(displayText);
            cuentaIds.put(displayText, entry.getKey());
        }

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(fechaInicio);
        panel.add(new JLabel("Nombre del depósito:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Cuenta:"));
        panel.add(cbCuenta);
        panel.add(new JLabel("Duración (meses):"));
        panel.add(campoMeses);
        panel.add(new JLabel("Importe:"));
        panel.add(campoImporte);
        panel.add(new JLabel("Interés anual:"));
        panel.add(campoInteres);

        // Abre un diálogo para añadir el depósito
        int resultado = JOptionPane.showConfirmDialog(vista, panel, "Añadir nuevo depósito", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (fechaInicio.getDatoFecha() != null) {
            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    // Obtener los datos
                    String fecha = new SimpleDateFormat("yyyy/MM/dd").format(fechaInicio.getDatoFecha());
                    int meses = Integer.parseInt(campoMeses.getText());
                    String nombre = campoNombre.getText();
                    String cuentaSeleccionada = (String) cbCuenta.getSelectedItem();
                    int cuentaId = cuentaIds.getOrDefault(cuentaSeleccionada, 0);

                    // Crear un NumberFormat basado en la configuración regional
                    NumberFormat formatoNumero = NumberFormat.getInstance(Locale.getDefault());
                    double importe = formatoNumero.parse(campoImporte.getText()).doubleValue();
                    double interes = formatoNumero.parse(campoInteres.getText()).doubleValue();

                    // Llama al modelo para añadir el depósito y obtener el ID generado
                    int idDeposito = consultaDepositos.addDeposito(usuario.getCodigo(), cuentaId, nombre, fecha, meses, importe, interes);

                    if (idDeposito > 0) { // Verifica si se obtuvo un ID válido
                        // Añade el movimiento asociado al depósito
                        addMovimientoContratarDeposito(cuentaId, fecha, nombre, importe, idDeposito);
                        JOptionPane.showMessageDialog(vista, "Depósito y movimiento creados correctamente.");
                        cargarDepositos(); // Refresca la tabla
                    } else {
                        JOptionPane.showMessageDialog(vista, "Error al añadir el depósito. Inténtalo de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
                } catch (IllegalArgumentException | ParseException ex) {
                    Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No has introducido Fecha");
        }

    }

    //Muestra un panel emergente para MODIFICAR el Deposito seleccionado
    public void mostrarPanelModificarDeposito() {
        int filaSeleccionada = vista.tablaDepositos.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un depósito en la lista para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Recupera los datos del depósito seleccionado por idDeposito
        int idDeposito = (int) vista.tablaDepositos.getValueAt(filaSeleccionada, 0);
        Deposito deposito = consultaDepositos.obtenerDepositoPorId(idDeposito);

        if (deposito == null) {
            JOptionPane.showMessageDialog(vista, "No se pudo recuperar la información del deposito.");
            return;
        }

        // Campos de entrada para los datos del depósito
        RSDateChooser campoFecha = new RSDateChooser();
        campoFecha.setDatoFecha(deposito.getFechaInicio());
        JTextField campoNombre = new JTextField(deposito.getNombre(), 20);
        JComboBox<String> cbCuenta = new JComboBox<>(); // ComboBox para las cuentas disponibles del usuario
        JTextField campoMeses = new JTextField(String.valueOf(deposito.getMeses()), 5);
        JTextField campoImporte = new JTextField(String.valueOf(deposito.getImporteInicial()), 15);
        JTextField campoInteres = new JTextField(String.valueOf(deposito.getInteresAnual()), 5);

        // Carga de la lista de cuentas
        Map<Integer, String> listaCuentas = consultaDepositos.obtenerCuentasConAliasYBanco(usuario.getCodigo()); // Obtiene cuentas directamente
        Map<String, Integer> cuentaIds = new HashMap<>(); // Relación inversa para obtener el ID de la cuenta desde el alias
        for (Map.Entry<Integer, String> entry : listaCuentas.entrySet()) {
            String displayText = entry.getValue();
            cbCuenta.addItem(displayText);
            cuentaIds.put(displayText, entry.getKey());
        }

        // Selecciona la cuenta asociada al depósito
        int cuentaSeleccionada = deposito.getIdCuenta();
        String cuentaAlias = cuentaIds.entrySet().stream()
                .filter(entry -> entry.getValue() == cuentaSeleccionada)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        cbCuenta.setSelectedItem(cuentaAlias);

        // Añade los campos al panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Fecha:"));
        panel.add(campoFecha);
        panel.add(new JLabel("Nombre del depósito:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Cuenta:"));
        panel.add(cbCuenta);
        panel.add(new JLabel("Duración (meses):"));
        panel.add(campoMeses);
        panel.add(new JLabel("Importe:"));
        panel.add(campoImporte);
        panel.add(new JLabel("Interés anual:"));
        panel.add(campoInteres);

        // Mostrar diálogo
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Modificar Depósito", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Validar y obtener datos
                String formatoFecha = "yyyy/MM/dd";
                SimpleDateFormat formatt = new SimpleDateFormat(formatoFecha);
                String fecha = formatt.format(campoFecha.getDatoFecha());
                String nombre = campoNombre.getText();
                String cuentaSeleccionadaModificada = (String) cbCuenta.getSelectedItem();
                int cuentaId = cuentaIds.getOrDefault(cuentaSeleccionadaModificada, 0); // Obtiene el ID de la cuenta seleccionada
                int meses = Integer.parseInt(campoMeses.getText());
                // Normalizar el importe para que acepte comas
                String importeTexto = campoImporte.getText().replace(",", ".");
                double importe = Double.parseDouble(importeTexto);
                // Normalizar el importe para que acepte comas
                String interesTexto = campoInteres.getText().replace(",", ".");
                double interes = Double.parseDouble(interesTexto);

                // Actualizar el depósito
                boolean actualizado = consultaDepositos.modificarDeposito(deposito.getIdDeposito(), cuentaId, nombre, fecha, meses, importe, interes);

                if (actualizado) {
                    JOptionPane.showMessageDialog(vista, "Depósito modificado correctamente.");
                    cargarDepositos(); // Refresca la tabla
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar el depósito.");
                }

                //Actualizar el movimiento asociado al deposito
                if (idDeposito > 0) { // Verifica si se obtuvo un ID válido
                    
                    // Modifica el movimiento asociado al depósito 
                    modificarMovimientoDeposito(cuentaId,consultaDepositos.obtenerIdMovimientoDeposito(idDeposito), fecha, nombre, importe, idDeposito);

                    JOptionPane.showMessageDialog(vista, "Depósito y movimiento modificados correctamente.");
                    cargarDepositos(); // Refresca la tabla
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar el depósito. Inténtalo de nuevo.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Error en los datos. Verifica los campos.");
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ControladorDepositos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // ELIMINAR el depósito seleccionado en la tabla
    public void eliminarDepositoSeleccionado() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        int filaSeleccionada = vista.tablaDepositos.getSelectedRow(); // Obtiene la fila seleccionada

        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un depósito en la lista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idDeposito = (int) vista.tablaDepositos.getValueAt(filaSeleccionada, 0); // ID del depósito seleccionado

        JOptionPane.showMessageDialog(vista, "¡¡Ojo, estás eliminando un depósito que tiene un movimiento asociado en la cuenta, se eliminará también el movimiento!!.");

        // Pregunta si el usuario realmente quiere eliminar el depósito
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que quieres eliminar el depósito seleccionado?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Llama al modelo para eliminar el depósito
            if (consultaDepositos.eliminarDeposito(idDeposito)) {
                JOptionPane.showMessageDialog(vista, "El depósito ha sido eliminado correctamente.");

                // Recarga la tabla y actualiza los paneles
                cargarDepositos();

            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el depósito.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Llama al modelo para eliminar los movimientos del depósito
            if (consultaDepositos.eliminarMovimientoDeposito(idDeposito)) {
                JOptionPane.showMessageDialog(vista, "El movimiento ha sido eliminado correctamente.");

                // Recarga la tabla y actualiza los paneles
                cargarDepositos();

            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el movimiento, no existe en la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Método para ajustar el ancho de las columnas de la tabla manualmente
    private void ajustarAnchoColumnas() {
        JTable tabla = vista.tablaDepositos;  // Suponiendo que 'tablaDepositos' es el nombre de la tabla

        // Obtener el modelo de columnas de la tabla
        TableColumnModel columnModel = tabla.getColumnModel();

        // Ajustar el ancho de cada columna (aquí puedes especificar el tamaño que desees para cada columna)
        columnModel.getColumn(0).setPreferredWidth(30);  // Columna 0: ID del depósito
        columnModel.getColumn(1).setPreferredWidth(150); // Columna 1: Nombre del depósito
        columnModel.getColumn(2).setPreferredWidth(200); // Columna 2: Alias y banco
        columnModel.getColumn(3).setPreferredWidth(100); // Columna 3: Fecha inicio
        columnModel.getColumn(4).setPreferredWidth(70); // Columna 4: Meses
        columnModel.getColumn(5).setPreferredWidth(100); // Columna 5: Importe
        columnModel.getColumn(6).setPreferredWidth(80); // Columna 6: Interés
        columnModel.getColumn(7).setPreferredWidth(60);  // Columna 7: Vencimiento
    }

    // Método para añadir automáticamente a la cuenta el MOVIMIENTO de CONTRATAR DEPOSITO
    public void addMovimientoContratarDeposito(int idCuenta, String fecha, String notas, double importe, int idDeposito) {
        // Definir parámetros para el movimiento
        ConsultaMovimientos consulta = new ConsultaMovimientos();
        int tipo = 2; // Tipo de movimiento: 2 (Pago)
        Integer idGasto = null; // No hay gasto asociado, se deja como null
        int idSubtipoMovimiento = 11; // Subtipo de movimiento: 11 (Contrato de depósito)
        importe = -Math.abs(importe); // Convierte el importe en negativo para reflejar un pago

        // Llamar al método addMovimiento para agregar el movimiento a la cuenta
        boolean movimientoCorrecto = consulta.addMovimiento(idCuenta, fecha, tipo, idSubtipoMovimiento, idGasto, notas, importe, idDeposito);

        if (!movimientoCorrecto) {
            System.err.println("Error al añadir el movimiento al contratar el depósito.");
        } else {
            System.out.println("Movimiento asociado al depósito creado correctamente.");
        }
    }

    //Metodo para modificar automaticamente en la cuenta el MOVIMIENTO del DEPOSITO MODIFICADO
    public void modificarMovimientoDeposito(int idCuenta, int idMovimiento, String fecha, String notas, double importe, int idDeposito) {
        // Definir parámetros para el movimiento
        ConsultaMovimientos consulta = new ConsultaMovimientos();
        int tipo = 2; // Tipo de movimiento: 2 (Pago)
        Integer idGasto = null; // No hay gasto asociado, se deja como null
        int idSubtipoMovimiento = 11; // Subtipo de movimiento: 11 (Contrato de depósito)
        importe = -Math.abs(importe); // Convierte el importe en negativo para reflejar un pago

        // Llamar al método modificarMovimiento para agregar el movimiento a la cuenta
        boolean movimientoCorrecto = consulta.modificarMovimiento(idCuenta, idMovimiento, fecha, tipo, idSubtipoMovimiento, idGasto, notas, importe, idDeposito);

        if (!movimientoCorrecto) {
            System.err.println("Error al modificar el movimiento asociado al depósito. (El movimiento no existe)");
        } else {
            System.out.println("Movimiento asociado al depósito modificado correctamente.");
        }
    }

    // Método para añadir automáticamente a la cuenta los MOVIMIENTOS de pasar a la cuenta DEPOSITO VENCIDO
    public void depositoVencido() {
        int filaSeleccionada = vista.tablaDepositos.getSelectedRow(); // Obtiene la fila seleccionada
        if (filaSeleccionada == -1) { // Verifica que haya una selección
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona un depósito en la lista para pasar a la cuenta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idDeposito = (int) vista.tablaDepositos.getValueAt(filaSeleccionada, 0); // ID del depósito seleccionado
        //Instancia el depósito
        Deposito deposito = consultaDepositos.obtenerDepositoPorId(idDeposito);

        // Instancia de ConsultaMovimientos
        ConsultaMovimientos consulta = new ConsultaMovimientos();
        Date hoy = new Date();
        if (!deposito.getFechaVencimiento().before(hoy)) {
            JOptionPane.showMessageDialog(vista, "No puedes pasar a la cuenta un depósito no vencido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Movimiento para el nominal del depósito
        int idCuenta = deposito.getIdCuenta();
        String fecha = new SimpleDateFormat("yyyy/MM/dd").format(deposito.getFechaVencimiento());
        int tipoNominal = 1; // Tipo de movimiento: 1 (Ingreso)
        Integer idGastoNominal = null; // No hay gasto asociado
        int idSubtipoMovimientoNominal = 3; // Subtipo de movimiento: 3 (Nominal de depósito vencido)
        String notas = deposito.getNombre();
        double nominal = deposito.getImporteInicial();
        double beneficio = deposito.getImporteFinal() - deposito.getImporteInicial();

        // Pregunta si el usuario realmente quiere eliminar el depósito
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro que quieres pasar los importes a la cuenta? Ojo, El depósito será eliminado automaticamente después.",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );
        if (opcion == JOptionPane.YES_OPTION) {

            boolean movimientoNominalCorrecto = consulta.addMovimiento(idCuenta, fecha, tipoNominal, idSubtipoMovimientoNominal, idGastoNominal, notas, nominal, idDeposito);
            if (!movimientoNominalCorrecto) {
                System.err.println("Error al añadir el movimiento para el nominal del depósito vencido.");
            } else {
                System.out.println("Movimiento para el nominal del depósito vencido añadido correctamente.");
            }

            // Movimiento para el beneficio del depósito
            int tipoBeneficio = 1; // Tipo de movimiento: 1 (Ingreso)
            Integer idGastoBeneficio = null; // No hay gasto asociado
            int idSubtipoMovimientoBeneficio = 4; // Subtipo de movimiento: 4 (Beneficio de depósito vencido)

            boolean movimientoBeneficioCorrecto = consulta.addMovimiento(idCuenta, fecha, tipoBeneficio, idSubtipoMovimientoBeneficio, idGastoBeneficio, notas, beneficio, idDeposito);
            if (!movimientoBeneficioCorrecto) {
                System.err.println("Error al añadir el movimiento para el beneficio del depósito vencido.");
            } else {
                System.out.println("Movimiento para el beneficio del depósito vencido añadido correctamente.");
            }

            // Llama al modelo para eliminar el depósito
            if (consultaDepositos.eliminarDeposito(idDeposito)) {
                JOptionPane.showMessageDialog(vista, "El depósito ha sido eliminado correctamente.");

                // Recarga la tabla y actualiza los paneles
                cargarDepositos();

            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el depósito.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
