
package vista;

import controlador.ControladorLogin;


/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class VistaRegistro extends javax.swing.JFrame {

   
    public VistaRegistro() {
        initComponents();
        
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
        citybg = new javax.swing.JLabel();
        favicon = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        passLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        passTxt2 = new javax.swing.JPasswordField();
        header = new javax.swing.JPanel();
        guardarBtn = new javax.swing.JPanel();
        guardarBtnTxt = new javax.swing.JLabel();
        cancelarBtn = new javax.swing.JPanel();
        cancelarBtnTxt = new javax.swing.JLabel();
        passLabel1 = new javax.swing.JLabel();
        passTxt1 = new javax.swing.JPasswordField();
        apellidosTxt = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        nombreTxt = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        fechaTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        citybg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/img1.jpg"))); // NOI18N
        citybg.setToolTipText("");
        bg.add(citybg, new org.netbeans.lib.awtextra.AbsoluteConstraints(661, 0, 290, -1));

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 36)); // NOI18N
        favicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/favicon.png"))); // NOI18N
        favicon.setText("MiEco");
        bg.add(favicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 200, 50));

        title.setFont(new java.awt.Font("Roboto Medium", 1, 24)); // NOI18N
        title.setText("REGISTRO NUEVO USUARIO");
        bg.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        userLabel.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        userLabel.setText("USUARIO");
        bg.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 70, -1));

        emailTxt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        emailTxt.setToolTipText("");
        emailTxt.setBorder(null);
        bg.add(emailTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 520, 30));
        bg.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 540, -1));

        passLabel2.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel2.setText("REPITA CONTRASEÑA");
        bg.add(passLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 160, -1));
        bg.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, 540, -1));

        passTxt2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        passTxt2.setBorder(null);
        bg.add(passTxt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, 240, 30));

        header.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        bg.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 40));

        guardarBtn.setBackground(new java.awt.Color(0, 134, 190));
        guardarBtn.setToolTipText("");

        guardarBtnTxt.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        guardarBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        guardarBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        guardarBtnTxt.setText("GUARDAR");
        guardarBtnTxt.setToolTipText("Pulsa para entrar en tu cuenta");
        guardarBtnTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout guardarBtnLayout = new javax.swing.GroupLayout(guardarBtn);
        guardarBtn.setLayout(guardarBtnLayout);
        guardarBtnLayout.setHorizontalGroup(
            guardarBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(guardarBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        guardarBtnLayout.setVerticalGroup(
            guardarBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(guardarBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        bg.add(guardarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 120, 40));

        cancelarBtn.setBackground(new java.awt.Color(0, 134, 190));

        cancelarBtnTxt.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        cancelarBtnTxt.setForeground(new java.awt.Color(255, 255, 255));
        cancelarBtnTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cancelarBtnTxt.setText("CANCELAR");
        cancelarBtnTxt.setToolTipText("Pulsa para crear una nueva cuenta");
        cancelarBtnTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout cancelarBtnLayout = new javax.swing.GroupLayout(cancelarBtn);
        cancelarBtn.setLayout(cancelarBtnLayout);
        cancelarBtnLayout.setHorizontalGroup(
            cancelarBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cancelarBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        cancelarBtnLayout.setVerticalGroup(
            cancelarBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cancelarBtnTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        bg.add(cancelarBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 120, 40));

        passLabel1.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        passLabel1.setText("CONTRASEÑA");
        bg.add(passLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, 100, -1));

        passTxt1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        passTxt1.setBorder(null);
        bg.add(passTxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 240, 30));

        apellidosTxt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        apellidosTxt.setBorder(null);
        bg.add(apellidosTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 320, 30));
        bg.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 540, -1));

        nombreTxt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        nombreTxt.setBorder(null);
        bg.add(nombreTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 210, 30));
        bg.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 540, -1));

        fechaTxt.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        fechaTxt.setToolTipText("");
        fechaTxt.setBorder(null);
        bg.add(fechaTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 160, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField apellidosTxt;
    private javax.swing.JPanel bg;
    public javax.swing.JPanel cancelarBtn;
    public javax.swing.JLabel cancelarBtnTxt;
    private javax.swing.JLabel citybg;
    public javax.swing.JTextField emailTxt;
    private javax.swing.JLabel favicon;
    public javax.swing.JTextField fechaTxt;
    public javax.swing.JPanel guardarBtn;
    public javax.swing.JLabel guardarBtnTxt;
    public javax.swing.JPanel header;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    public javax.swing.JTextField nombreTxt;
    public javax.swing.JLabel passLabel1;
    public javax.swing.JLabel passLabel2;
    public javax.swing.JPasswordField passTxt1;
    public javax.swing.JPasswordField passTxt2;
    private javax.swing.JLabel title;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}