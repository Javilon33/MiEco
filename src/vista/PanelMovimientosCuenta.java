/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import controlador.ControladorEditarMovimientos;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author PC-Casa
 */
public class PanelMovimientosCuenta extends javax.swing.JPanel {
    private int idCuenta;
    private ControladorEditarMovimientos control;
    
    /**
     * Creates new form PanelMovimientosCuenta
     */
    public PanelMovimientosCuenta(int idCuenta) {
        this.idCuenta = idCuenta;        
        initComponents();
        
        //Modificaciones al JTable para mostrar las cuentas
        tablaMovimientos.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        tablaMovimientos.getTableHeader().setOpaque(false);
        tablaMovimientos.getTableHeader().setBackground(new Color(0,134,190));
        tablaMovimientos.getTableHeader().setForeground(new Color(255,255,255));
        tablaMovimientos.setRowHeight(25);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelCuentas = new javax.swing.JScrollPane();
        tablaMovimientos = new javax.swing.JTable();
        panelMenu = new javax.swing.JPanel();
        btnAdd = new javax.swing.JPanel();
        etiAdd = new javax.swing.JLabel();
        btnModificar = new javax.swing.JPanel();
        etiModificar = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JPanel();
        etiElminar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        etiBanco = new javax.swing.JLabel();
        etiNombreCuenta = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaMovimientos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        tablaMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Ingreso/Pago", "Categoria", "Subcategoria", "Notas", "Importe", "Saldo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMovimientos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaMovimientos.setFocusable(false);
        tablaMovimientos.setGridColor(new java.awt.Color(255, 255, 255));
        tablaMovimientos.setRowHeight(25);
        tablaMovimientos.setRowMargin(1);
        tablaMovimientos.setSelectionBackground(new java.awt.Color(255, 248, 236));
        tablaMovimientos.setSelectionForeground(new java.awt.Color(51, 51, 51));
        tablaMovimientos.setShowGrid(false);
        tablaMovimientos.setShowHorizontalLines(true);
        tablaMovimientos.getTableHeader().setReorderingAllowed(false);
        panelCuentas.setViewportView(tablaMovimientos);

        jPanel1.add(panelCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 880, 390));

        panelMenu.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAdd.setBackground(new java.awt.Color(190, 56, 0));
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setLayout(new java.awt.GridBagLayout());

        etiAdd.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        etiAdd.setForeground(new java.awt.Color(255, 255, 255));
        etiAdd.setText("AÑADIR");
        etiAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.add(etiAdd, new java.awt.GridBagConstraints());

        panelMenu.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 20));

        btnModificar.setBackground(new java.awt.Color(190, 56, 0));
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificar.setLayout(new java.awt.GridBagLayout());

        etiModificar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        etiModificar.setForeground(new java.awt.Color(255, 255, 255));
        etiModificar.setText("MODIFICAR");
        etiModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.add(etiModificar, new java.awt.GridBagConstraints());

        panelMenu.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 110, 20));

        btnEliminar.setBackground(new java.awt.Color(190, 56, 0));
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setLayout(new java.awt.GridBagLayout());

        etiElminar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        etiElminar.setForeground(new java.awt.Color(255, 255, 255));
        etiElminar.setText("ELIMINAR");
        etiElminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.add(etiElminar, new java.awt.GridBagConstraints());

        panelMenu.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 110, 20));

        jPanel1.add(panelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 370, 40));

        jPanel2.setBackground(new java.awt.Color(0, 134, 190));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MOVIMIENTOS");

        etiBanco.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        etiBanco.setForeground(new java.awt.Color(255, 255, 255));
        etiBanco.setText("Nombre Banco");

        etiNombreCuenta.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        etiNombreCuenta.setForeground(new java.awt.Color(255, 255, 255));
        etiNombreCuenta.setText("Nombre cuenta");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiBanco)
                    .addComponent(etiNombreCuenta))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiBanco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiNombreCuenta)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 6, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel btnAdd;
    public javax.swing.JPanel btnEliminar;
    public javax.swing.JPanel btnModificar;
    public javax.swing.JLabel etiAdd;
    public javax.swing.JLabel etiBanco;
    public javax.swing.JLabel etiElminar;
    public javax.swing.JLabel etiModificar;
    public javax.swing.JLabel etiNombreCuenta;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane panelCuentas;
    public javax.swing.JPanel panelMenu;
    public javax.swing.JTable tablaMovimientos;
    // End of variables declaration//GEN-END:variables
}