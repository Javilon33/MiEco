/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.ControladorPrincipal;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class VistaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VistaPrincipal
     */
    public VistaPrincipal() {
        initComponents();
        //ControladorPrincipal cp = new ControladorPrincipal(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnPrincipal = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        etiPrincipal = new javax.swing.JLabel();
        btnCuentas = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        etiCuentas = new javax.swing.JLabel();
        btnDepositos = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        etiDepositos = new javax.swing.JLabel();
        btnFondos = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        etiFondos = new javax.swing.JLabel();
        btnBolsa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        etiBolsa = new javax.swing.JLabel();
        btnInformes = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        etiInformes = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        favicon1 = new javax.swing.JLabel();
        btnAdmin = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        etiAdmin = new javax.swing.JLabel();
        btnLogout = new javax.swing.JPanel();
        etiLogout = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        exitBtn = new javax.swing.JPanel();
        exitTxt = new javax.swing.JLabel();
        panelContenedor = new javax.swing.JPanel();
        BarraSuperior = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        etiNombre = new javax.swing.JLabel();
        etiEmail = new javax.swing.JLabel();
        iconoUsuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(0, 134, 190));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        menu.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, 152, 10));

        btnPrincipal.setBackground(new java.awt.Color(21, 101, 192));
        btnPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home-outline.png"))); // NOI18N
        btnPrincipal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        etiPrincipal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etiPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        etiPrincipal.setText("Principal");
        etiPrincipal.setToolTipText("");
        etiPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrincipal.add(etiPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, -1, -1));

        menu.add(btnPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 230, 50));

        btnCuentas.setBackground(new java.awt.Color(18, 90, 173));
        btnCuentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCuentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Cuentas1.png"))); // NOI18N
        btnCuentas.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        etiCuentas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etiCuentas.setForeground(new java.awt.Color(255, 255, 255));
        etiCuentas.setText("Cuentas");
        etiCuentas.setToolTipText("");
        btnCuentas.add(etiCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 14, -1, -1));

        menu.add(btnCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 230, 50));

        btnDepositos.setBackground(new java.awt.Color(18, 90, 173));
        btnDepositos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDepositos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Depos.png"))); // NOI18N
        btnDepositos.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        etiDepositos.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etiDepositos.setForeground(new java.awt.Color(255, 255, 255));
        etiDepositos.setText("Depósitos");
        etiDepositos.setToolTipText("");
        btnDepositos.add(etiDepositos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, -1, -1));

        menu.add(btnDepositos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 230, 50));

        btnFondos.setBackground(new java.awt.Color(18, 90, 173));
        btnFondos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFondos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fond.png"))); // NOI18N
        btnFondos.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        etiFondos.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etiFondos.setForeground(new java.awt.Color(255, 255, 255));
        etiFondos.setText("Fondos Inversión");
        etiFondos.setToolTipText("");
        btnFondos.add(etiFondos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, -1, -1));

        menu.add(btnFondos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 230, 50));

        btnBolsa.setBackground(new java.awt.Color(18, 90, 173));
        btnBolsa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBolsa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bols.png"))); // NOI18N
        btnBolsa.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 8, -1, -1));

        etiBolsa.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etiBolsa.setForeground(new java.awt.Color(255, 255, 255));
        etiBolsa.setText("Acciones Bolsa");
        etiBolsa.setToolTipText("");
        btnBolsa.add(etiBolsa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, -1, -1));

        menu.add(btnBolsa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 230, 50));

        btnInformes.setBackground(new java.awt.Color(18, 90, 173));
        btnInformes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInformes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/infor_1.png"))); // NOI18N
        btnInformes.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        etiInformes.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etiInformes.setForeground(new java.awt.Color(255, 255, 255));
        etiInformes.setText("Informes");
        etiInformes.setToolTipText("");
        btnInformes.add(etiInformes, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 12, -1, -1));

        menu.add(btnInformes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 230, 50));

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        menu.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 152, 10));

        favicon1.setFont(new java.awt.Font("Roboto Black", 1, 36)); // NOI18N
        favicon1.setForeground(new java.awt.Color(255, 255, 255));
        favicon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icono3.png"))); // NOI18N
        favicon1.setText("MiEco");
        menu.add(favicon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 205, 60));

        btnAdmin.setBackground(new java.awt.Color(18, 90, 173));
        btnAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/admin.png"))); // NOI18N
        btnAdmin.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 6, -1, 38));

        etiAdmin.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        etiAdmin.setForeground(new java.awt.Color(255, 255, 255));
        etiAdmin.setText("Administrar");
        etiAdmin.setToolTipText("");
        btnAdmin.add(etiAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 14, -1, -1));

        menu.add(btnAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 230, 50));

        btnLogout.setBackground(new java.awt.Color(0, 134, 190));
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        etiLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.add(etiLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, -1, -1));

        menu.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 580, 80, 70));

        bg.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 700));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));

        exitBtn.setBackground(new java.awt.Color(255, 255, 255));

        exitTxt.setBackground(new java.awt.Color(255, 255, 255));
        exitTxt.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        exitTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitTxt.setText("X");
        exitTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout exitBtnLayout = new javax.swing.GroupLayout(exitBtn);
        exitBtn.setLayout(exitBtnLayout);
        exitBtnLayout.setHorizontalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exitTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        exitBtnLayout.setVerticalGroup(
            exitBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exitTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(1162, Short.MAX_VALUE)
                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 40));

        panelContenedor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 970, Short.MAX_VALUE)
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        bg.add(panelContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 970, 600));

        BarraSuperior.setBackground(new java.awt.Color(255, 255, 255));
        BarraSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 134, 190));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        etiNombre.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        etiNombre.setForeground(new java.awt.Color(255, 255, 255));
        etiNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiNombre.setText("Nombre");
        etiNombre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(etiNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 10, 260, -1));

        etiEmail.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        etiEmail.setForeground(new java.awt.Color(255, 255, 255));
        etiEmail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiEmail.setText("email");
        etiEmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(etiEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 260, -1));

        iconoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N
        jPanel1.add(iconoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 40));

        BarraSuperior.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 340, 60));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/barra.png"))); // NOI18N
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        BarraSuperior.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 60));

        bg.add(BarraSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 970, 60));

        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JPanel bg;
    public javax.swing.JPanel btnAdmin;
    public javax.swing.JPanel btnBolsa;
    public javax.swing.JPanel btnCuentas;
    public javax.swing.JPanel btnDepositos;
    public javax.swing.JPanel btnFondos;
    public javax.swing.JPanel btnInformes;
    public javax.swing.JPanel btnLogout;
    public javax.swing.JPanel btnPrincipal;
    public javax.swing.JLabel etiAdmin;
    public javax.swing.JLabel etiBolsa;
    public javax.swing.JLabel etiCuentas;
    public javax.swing.JLabel etiDepositos;
    public javax.swing.JLabel etiEmail;
    public javax.swing.JLabel etiFondos;
    public javax.swing.JLabel etiInformes;
    public javax.swing.JLabel etiLogout;
    public javax.swing.JLabel etiNombre;
    public javax.swing.JLabel etiPrincipal;
    public javax.swing.JPanel exitBtn;
    public javax.swing.JLabel exitTxt;
    private javax.swing.JLabel favicon1;
    public javax.swing.JPanel header;
    private javax.swing.JLabel iconoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel menu;
    public javax.swing.JPanel panelContenedor;
    // End of variables declaration//GEN-END:variables
}
