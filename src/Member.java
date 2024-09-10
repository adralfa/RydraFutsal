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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adra Zulfi A
 */
public class Member extends javax.swing.JFrame {
    Statement st;
    ResultSet rs;
    koneksi koneksi;
    Map param = new HashMap();
    
    public Member() throws InstantiationException, IllegalAccessException, SQLException {
        koneksi = new koneksi();
        initComponents();
        loadData();
        autoID();
        date();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }
    
    private void loadData() {
        Object header[] = {"ID", "Nama", "No HP", "Mulai", "Lama", "Username", "Password"};
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
                String k6 = rs.getString(6);
                String k7 = rs.getString(7);
                String k[] = {k1, k2, k3, k4, k5, k6, k7};
                data.addRow(k);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void autoID() {
        try {
            st = koneksi.kon.createStatement();
            String sql = "SELECT * FROM member ORDER BY id_member desc";
            rs = st.executeQuery(sql);
            if(rs.next()) {
                String ID = rs.getString("id_member").substring(4);
                String NO = "" + (Integer.parseInt(ID) + 1);
                String Nol = "";
                switch (NO.length()) {
                    case 1: Nol = "000"; break;
                    case 2: Nol = "00"; break;
                    case 3: Nol = "0"; break;
                    default: break;
                }
                jTextField1.setText("MRF" + Nol + NO);
            } else {
                jTextField1.setText("MRF0001");
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
        jTextField4.setText("");
        date();
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }
    
    private void date() {
        Date tglnow = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String tgl = s.format(tglnow);
        jTextField4.setText(tgl);
    }
    
    public void inputData() {
        int validate = JOptionPane.showOptionDialog(null, "Apakah Anda yakin akan menyimpan data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(validate == JOptionPane.YES_OPTION) {
            try {
                int cekKosong;
                String id = jTextField1.getText();
                String nama = jTextField2.getText();
                String nohp = jTextField3.getText();
                String mulai = jTextField4.getText();
                String lama = jTextField5.getText();
                String usn = jTextField6.getText();
                String psw = jTextField7.getText();
                
                if(id.equals("") || nama.equals("") || nohp.equals("") || mulai.equals("") || lama.equals("") || usn.equals("") || psw.equals("")) { cekKosong = 1; }
                else { cekKosong = 0; }
                
                if(cekKosong == 1) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi!", "Peringatan", JOptionPane.ERROR_MESSAGE); }
                else { 
                    String sql = "INSERT INTO member VALUES ('" + id + "', '" + nama
                        + "', '" + nohp + "', '" + mulai + "', " + Integer.parseInt(lama)
                        + ", '" + usn + "', '" + psw + "')";
                    st.execute(sql);
                    JOptionPane.showMessageDialog(null, "Data Member baru berhasil dimasukkan!");
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
                String nama = jTextField2.getText();
                String nohp = jTextField3.getText();
                String mulai = jTextField4.getText();
                String lama = jTextField5.getText();
                String usn = jTextField6.getText();
                String psw = jTextField7.getText();
                
                if(id.equals("") || nama.equals("") || nohp.equals("") || mulai.equals("")|| lama.equals("")  || usn.equals("")|| psw.equals("")) { cekKosong = 1; }
                else { cekKosong = 0; }
                
                if(cekKosong == 1) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi!", "Peringatan", JOptionPane.ERROR_MESSAGE); }
                else { 
                    String sql = "UPDATE member SET nama = '" + nama
                            + "', nohp = '" + nohp
                            + "', mulai_member = '" + mulai
                            + "', lama_member = " + Integer.parseInt(lama)
                            + ", usn = '" + usn
                            + "', psw = '" + psw
                            + "' WHERE id_member = '" + id + "'";
                    st.execute(sql);
                    JOptionPane.showMessageDialog(null, "Data Member berhasil diupdate!");
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
                String sql_delete = "DELETE FROM member WHERE id_member = '" + jTextField1.getText() + "'";
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
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RYDRA Futsal - Member");

        jPanel1.setBackground(new java.awt.Color(0, 102, 0));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setText("FORM MEMBER");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("ID MEMBER");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("NAMA MEMBER");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("NO HP");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("MULAI MEMBER");

        jTextField1.setEditable(false);

        jTextField2.setToolTipText("");

        jTextField4.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("LAMA MEMBER");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("BULAN");

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(55);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(55);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(55);
            jTable1.getColumnModel().getColumn(1).setMinWidth(140);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(140);
            jTable1.getColumnModel().getColumn(2).setMinWidth(75);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(75);
            jTable1.getColumnModel().getColumn(3).setMinWidth(75);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(75);
            jTable1.getColumnModel().getColumn(4).setMinWidth(55);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(55);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(55);
        }

        jLabel9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel9.setText("TABEL MEMBER");

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

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("USERNAME");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("PASSWORD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8))
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6)
                            .addComponent(jTextField7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA MEMBER");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jLabel1)))
                .addGap(39, 39, 39))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
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
        String e = jTable1.getValueAt(bar, 4).toString();
        String f = jTable1.getValueAt(bar, 5).toString();
        String g = jTable1.getValueAt(bar, 6).toString();
        
        jTextField1.setText(a);
        jTextField2.setText(b);
        jTextField3.setText(c);
        jTextField4.setText(d);
        jTextField5.setText(e);
        jTextField6.setText(f);
        jTextField7.setText(g);
        
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
            java.util.logging.Logger.getLogger(Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Member().setVisible(true);
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
