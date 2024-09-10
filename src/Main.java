/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adra Zulfi A
 */
public final class Main extends javax.swing.JFrame {
    Statement st;
    ResultSet rs;
    koneksi koneksi;
    
    public Main() throws InstantiationException, IllegalAccessException, SQLException {
        initComponents();
        tampilTglWkt();
        koneksi = new koneksi();
        load_member();
        load_inv();
        load_booking();
    }
    
    private void load_member() {
        Object header[] = {"ID", "Nama", "No HP", "Mulai", "Lama"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        jTable1.setModel(data);
        String sql = "SELECT * FROM member WHERE id_member <> 'MRF0000'";
        try {
            st = koneksi.kon.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String k1 = rs.getString(1);
                String k2 = rs.getString(2);
                String k3 = rs.getString(3);
                String k4 = rs.getString(4);
                String k5 = rs.getString(5);
                String k[] = {k1, k2, k3, k4, k5};
                data.addRow(k);
            }
            tampilTglWkt();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void load_inv() {
        Object header[] = {"ID", "Inventaris", "Jumlah", "Satuan"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        jTable2.setModel(data);
        String sql = "SELECT * FROM inventaris";
        try {
            st = koneksi.kon.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String k1 = rs.getString(1);
                String k2 = rs.getString(2);
                String k3 = rs.getString(3);
                String k4 = rs.getString(4);
                String k[] = {k1, k2, k3, k4};
                data.addRow(k);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void load_booking() {
        Object header[] = {"ID", "Nama", "Lapangan", "Member", "Tgl Sewa", "Biaya", "Status Bayar"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        jTable3.setModel(data);
        String sql = "SELECT booking.id_booking, booking.nama_pelanggan, booking.lapangan, member.nama, booking.tanggal_sewa, booking.biaya, pembayaran.status_bayar FROM member JOIN booking ON member.id_member = booking.id_member JOIN pembayaran ON booking.id_booking = pembayaran.id_booking";
        try {
            st = koneksi.kon.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String k1 = rs.getString(1);
                String k2 = rs.getString(2);
                String k3 = rs.getString(3);
                String k4 = rs.getString(4);
                String k5 = rs.getString(5);
                String k6 = rs.getString(6);
                String k7 = rs.getString(7);
                String k[] = {k1, k2, k3, k4, k5, k6, k7};
                data.addRow(k);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void tampilTglWkt() {
        Thread p = new Thread() {
            @Override public void run() {
                for(;;) {
                    Date tglnow = new Date();
                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
                    String tgl = s.format(tglnow);
        
                    String noljam = "", nolmenit = "", noldetik = "";
                    int vjam = tglnow.getHours();
                    int vmenit = tglnow.getMinutes();
                    int vdetik = tglnow.getSeconds();
                    
                    if(vjam <= 9) noljam = "0";
                    if(vmenit <= 9) nolmenit = "0";
                    if(vdetik <= 9) noldetik = "0";
                    
                    String jam = noljam + Integer.toString(vjam);
                    String menit = nolmenit + Integer.toString(vmenit);
                    String detik = noldetik + Integer.toString(vdetik);
                    
                    LTgl.setText(tgl);
                    LWkt.setText(jam + ":" + menit + ":" + detik);
                    
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        p.start();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LTgl = new javax.swing.JLabel();
        LWkt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RYDRA Futsal - Halaman Utama");
        setBackground(new java.awt.Color(250, 250, 250));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel2.setText("RYDRA FUTSAL");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("CIRENDANG, KUNINGAN");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel4.setText("MEMBER");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel5.setText("INVENTARIS");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setText("BOOKING LAPANGAN");

        jButton1.setText("DETAIL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("DETAIL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 102, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RydraFutsal.png"))); // NOI18N

        LTgl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LTgl.setForeground(new java.awt.Color(255, 255, 255));
        LTgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LTgl.setText("TANGGAL");

        LWkt.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        LWkt.setForeground(new java.awt.Color(255, 255, 255));
        LWkt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LWkt.setText("WAKTU");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LWkt, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LTgl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LWkt)
                .addGap(45, 45, 45))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "No HP", "Mulai", "Lama"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(55);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(55);
        }

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Inventaris", "Jumlah", "Satuan"
            }
        ));
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(50);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable2.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(1).setMinWidth(190);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(190);
            jTable2.getColumnModel().getColumn(1).setMaxWidth(190);
            jTable2.getColumnModel().getColumn(2).setMinWidth(80);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(2).setMaxWidth(80);
            jTable2.getColumnModel().getColumn(3).setMinWidth(80);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Lapangan", "Member", "Tgl Sewa", "Biaya", "Status Bayar"
            }
        ));
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setMinWidth(60);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTable3.getColumnModel().getColumn(0).setMaxWidth(60);
            jTable3.getColumnModel().getColumn(1).setMinWidth(170);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(170);
            jTable3.getColumnModel().getColumn(1).setMaxWidth(170);
            jTable3.getColumnModel().getColumn(2).setMinWidth(80);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(2).setMaxWidth(80);
            jTable3.getColumnModel().getColumn(3).setMinWidth(100);
            jTable3.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable3.getColumnModel().getColumn(3).setMaxWidth(100);
            jTable3.getColumnModel().getColumn(4).setMinWidth(80);
            jTable3.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(4).setMaxWidth(80);
            jTable3.getColumnModel().getColumn(5).setMinWidth(80);
            jTable3.getColumnModel().getColumn(5).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(5).setMaxWidth(80);
            jTable3.getColumnModel().getColumn(6).setMinWidth(80);
            jTable3.getColumnModel().getColumn(6).setPreferredWidth(80);
            jTable3.getColumnModel().getColumn(6).setMaxWidth(80);
        }

        jButton3.setText("DETAIL");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("REFRESH DATA");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("TENTANG");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("LOGOUT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(413, 413, 413)
                            .addComponent(jButton3))
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setExtendedState(Main.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Member m = null;
        try {
            m = new Member();
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        m.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Inventaris i = null;
        try {
            i = new Inventaris();
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        i.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Booking b = null;
        try {
            b = new Booking();
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        b.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        load_member();
        load_inv();
        load_booking();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Tentang t = new Tentang();
        t.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Login l = null;
        try {
            l = new Login();
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        l.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Main().setVisible(true);
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LTgl;
    private javax.swing.JLabel LWkt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
