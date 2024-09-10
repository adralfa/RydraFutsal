/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.HeadlessException;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Adra Zulfi A
 */
public class Inventaris extends javax.swing.JFrame {
    Statement st;
    ResultSet rs;
    koneksi koneksi;
    Map param = new HashMap();
    
    public Inventaris() throws InstantiationException, IllegalAccessException, SQLException {
        koneksi = new koneksi();
        initComponents();
        loadData();
        autoID();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }
    
    private void loadData() {
        Object header[] = {"ID", "Inventaris", "Jumlah", "Satuan"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        jTable1.setModel(data);
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
    
    private void autoID() {
        try {
            st = koneksi.kon.createStatement();
            String sql = "SELECT * FROM inventaris ORDER BY id_inv desc";
            rs = st.executeQuery(sql);
            if(rs.next()) {
                String ID = rs.getString("id_inv").substring(4);
                String NO = "" + (Integer.parseInt(ID) + 1);
                String Nol = "";
                switch (NO.length()) {
                    case 1: Nol = "000"; break;
                    case 2: Nol = "00"; break;
                    case 3: Nol = "0"; break;
                    default: break;
                }
                jTextField1.setText("IRF" + Nol + NO);
            } else {
                jTextField1.setText("IRF0001");
            }
        } catch(NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void clearForm() {
        jTextField1.setText("");
        autoID();
        jTextField2.setText("");
        jTextField3.setText("");
        jComboBox1.setSelectedIndex(0);
        
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }
    
    public void inputData() {
        int validate = JOptionPane.showOptionDialog(null, "Apakah Anda yakin akan menyimpan data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(validate == JOptionPane.YES_OPTION) {
            try {
                int cekKosong;
                String id = jTextField1.getText();
                String inv = jTextField2.getText();
                String jml = jTextField3.getText();
                String satuan = (String) jComboBox1.getSelectedItem();
                
                if(id.equals("") || inv.equals("") || jml.equals("") || satuan.equals("")) { cekKosong = 1; }
                else { cekKosong = 0; }
                
                if(cekKosong == 1) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi!", "Peringatan", JOptionPane.ERROR_MESSAGE); }
                else { 
                    String sql = "INSERT INTO inventaris VALUES ('" + id + "', '" + inv
                        + "', " + Integer.parseInt(jml) + ", '" + satuan + "')";
                    st.execute(sql);
                    JOptionPane.showMessageDialog(null, "Data Inventaris baru berhasil dimasukkan!");
                    loadData();
                    clearForm();
                }
            } catch (HeadlessException | NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public void editData() {
        int validate = JOptionPane.showOptionDialog(null, "Apakah Anda yakin perubahan data tersebut akan disimpan?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(validate == JOptionPane.YES_OPTION) {
            try {
                int cekKosong;
                String id = jTextField1.getText();
                String inv = jTextField2.getText();
                String jml = jTextField3.getText();
                String satuan = (String) jComboBox1.getSelectedItem();
                
                if(id.equals("") || inv.equals("") || jml.equals("") || satuan.equals("")) { cekKosong = 1; }
                else { cekKosong = 0; }
                
                if(cekKosong == 1) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi!", "Peringatan", JOptionPane.ERROR_MESSAGE); }
                else { 
                    String sql = "UPDATE inventaris SET id_inv = '" + id
                    + "', inventaris = '" + inv
                    + "', jumlah = " + Integer.parseInt(jml)
                    + ", satuan = '" + satuan
                    + "' WHERE id_inv = '" + id + "'";
                    st.execute(sql);
                    JOptionPane.showMessageDialog(null, "Data Inventaris berhasil diupdate!");
                    loadData();
                    clearForm();
                }
            } catch (HeadlessException | NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public void deleteData() {
        int validate = JOptionPane.showOptionDialog(null, "Apakah Anda yakin akan menghapus data tersebut?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(validate == JOptionPane.YES_OPTION) {
            try {
                st = koneksi.kon.createStatement();
                String sql_delete = "DELETE FROM inventaris WHERE id_inv = '" + jTextField1.getText() + "'";
                st.executeUpdate(sql_delete);
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                loadData();
                clearForm();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RYDRA Futsal - Inventaris");

        jPanel1.setBackground(new java.awt.Color(0, 102, 0));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setText("FORM INVENTARIS");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("ID INVENTARIS");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("INVENTARIS");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("JUMLAH");

        jTextField1.setEditable(false);

        jTextField2.setToolTipText("");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(80);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(80);
            jTable1.getColumnModel().getColumn(1).setMinWidth(200);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(2).setMinWidth(60);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(3).setMinWidth(60);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(60);
        }

        jLabel9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel9.setText("TABEL INVENTARIS");

        jButton1.setText("SIMPAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("EDIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("HAPUS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "buah", "slop", "pack" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jTextField3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA INVENTARIS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        inputData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int bar = jTable1.getSelectedRow();
        String a = jTable1.getValueAt(bar, 0).toString();
        String b = jTable1.getValueAt(bar, 1).toString();
        String c = jTable1.getValueAt(bar, 2).toString();
        String d = jTable1.getValueAt(bar, 3).toString();
        
        jTextField1.setText(a);
        jTextField2.setText(b);
        jTextField3.setText(c);
        jComboBox1.setSelectedItem(d);
        
        jButton1.setEnabled(false);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deleteData();
    }//GEN-LAST:event_jButton3ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inventaris.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Inventaris().setVisible(true);
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Inventaris.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
