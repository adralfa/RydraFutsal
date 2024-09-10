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
public class Booking extends javax.swing.JFrame {
    Statement st;
    ResultSet rs;
    koneksi koneksi;
    Map param = new HashMap();
    
    public Booking() throws InstantiationException, IllegalAccessException, SQLException {
        koneksi = new koneksi();
        initComponents();
        loadData();
        autoID();
        date();
        memberBox();
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }
    
    private void loadData() {
        Object header[] = {"Tgl Sewa", "ID Booking", "ID Bayar", "Nama", "Lapangan", "Jam Mulai", "Jam Selesai", "Member", "Biaya", "Jml Bayar", "Kembali"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        jTable1.setModel(data);
        String sql1 = "SELECT * FROM pembayaran INNER JOIN booking ON pembayaran.id_booking = booking.id_booking INNER JOIN member ON booking.id_member = member.id_member ORDER BY booking.id_booking DESC";
        try {
            st = koneksi.kon.createStatement();
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                String k1 = rs.getString("tanggal_sewa");
                String k2 = rs.getString("id_booking");
                String k3 = rs.getString("id_pembayaran");
                String k4 = rs.getString("nama_pelanggan");
                String k5 = rs.getString("lapangan");
                String k6 = rs.getString("jam_mulai");
                String k7 = rs.getString("jam_selesai");
                String k8 = rs.getString("biaya");
                String k9 = rs.getString("nama");
                String k10 = rs.getString("jml_bayar");
                String k11 = rs.getString("kembali");
                
                String k[] = {k1, k2, k3, k4, k5, k6, k7, k9, k8, k10, k11};
                data.addRow(k);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + " in load");
        }
        clearForm();
    }
    
    private void autoID() {
        try {
            st = koneksi.kon.createStatement();
            String sql = "SELECT * FROM booking ORDER BY id_booking desc";
            rs = st.executeQuery(sql);
            if(rs.next()) {
                String ID = rs.getString("id_booking").substring(4);
                String NO = "" + (Integer.parseInt(ID) + 1);
                String Nol = "";
                switch (NO.length()) {
                    case 1: Nol = "000"; break;
                    case 2: Nol = "00"; break;
                    case 3: Nol = "0"; break;
                    default: break;
                }
                jTextField1.setText("BRF" + Nol + NO);
            } else {
                jTextField1.setText("BRF0001");
            }
            
            String sql2 = "SELECT * FROM pembayaran ORDER BY id_pembayaran desc";
            rs = st.executeQuery(sql2);
            if(rs.next()) {
                String ID = rs.getString("id_pembayaran").substring(4);
                String NO = "" + (Integer.parseInt(ID) + 1);
                String Nol = "";
                switch (NO.length()) {
                    case 1: Nol = "000"; break;
                    case 2: Nol = "00"; break;
                    case 3: Nol = "0"; break;
                    default: break;
                }
                jTextField8.setText("PRF" + Nol + NO);
            } else {
                jTextField8.setText("PRF0001");
            }
        } catch(NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e + " in autoID");
        }
    }
    
    private void clearForm() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField11.setText("");
        autoID();
        
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        
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
    
    private void memberBox() {
        try {
            st = koneksi.kon.createStatement();
            String query = "SELECT * FROM member";
            rs = st.executeQuery(query);
            while(rs.next()) {
                jComboBox2.addItem(rs.getString("nama"));
            }
            rs.close();
        } catch(SQLException e) { JOptionPane.showMessageDialog(null, e + " in memberBox"); }
    }
    
    private void calcBiaya() {
        String start = jTextField5.getText();
        String end = jTextField6.getText();
        int lapangan = jComboBox1.getSelectedIndex();
        int idMem = jComboBox2.getSelectedIndex();
        int hargaLapangan = 0, persenMember, lama;
        
        int hs = Integer.parseInt(start.substring(0, 2));
        int ms = Integer.parseInt(start.substring(3, 5));
        int he = Integer.parseInt(end.substring(0, 2));
        int me = Integer.parseInt(end.substring(3, 5));
        
        if(hs > 23 || he > 23 || ms > 59 || me > 59) {
            JOptionPane.showMessageDialog(null, "Format Jam Tidak Benar!", "Peringatan", JOptionPane.ERROR_MESSAGE);
        } else if (me - ms != 0) {
            JOptionPane.showMessageDialog(null, "Booking Hanya Bisa Per Jam!", "Peringatan", JOptionPane.ERROR_MESSAGE);
        } else {
            lama = he - hs;
            
            switch (lapangan) {
                case 0: hargaLapangan = 80000; break;
                case 1: hargaLapangan = 100000; break;
                case 2: hargaLapangan = 120000; break;
                default: break;
            }
        
            if(idMem == 0) {
                persenMember = 10;
            } else {
                persenMember = 8;
            }
        
            long biayaLapangan = hargaLapangan * lama * persenMember / 10;
            jTextField7.setText(String.valueOf(biayaLapangan));
        }
    }
    
    private void calcKembali() {
        int bayar = Integer.parseInt(jTextField9.getText());
        int biaya = Integer.parseInt(jTextField7.getText());
        
        int kembali = bayar - biaya;
        jTextField11.setText(String.valueOf(kembali));      
    }
    
    public void inputData() {
        int validate = JOptionPane.showOptionDialog(null, "Apakah Anda yakin akan menyimpan data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(validate == JOptionPane.YES_OPTION) {
            try {
                int cekKosong, lunas = 0;
                String mem = null;
                String idBooking = jTextField1.getText();
                String nama = jTextField2.getText();
                String lapangan = (String) jComboBox1.getSelectedItem();
                String tglSewa = jTextField4.getText();
                String jamMulai = jTextField5.getText();
                String jamSelesai = jTextField6.getText();
                String member = (String) jComboBox2.getSelectedItem();
                String biaya = jTextField7.getText();
                String idBayar = jTextField8.getText();
                String bayar = jTextField9.getText();
                String kembali = jTextField11.getText();
                
                if(idBooking.equals("") || nama.equals("") || tglSewa.equals("") || jamMulai.equals("") || jamSelesai.equals("") || biaya.equals("") || idBayar.equals("") || bayar.equals("") || kembali.equals("")) { cekKosong = 1; }
                else {
                    cekKosong = 0;
                    if(Integer.parseInt(bayar) - Integer.parseInt(biaya) < 0) {
                        lunas = 0;
                    } else {
                        lunas = 1;
                    }
                }
                
                if(cekKosong == 1) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi!", "Peringatan", JOptionPane.ERROR_MESSAGE);
                } else if(lunas == 0) {
                    JOptionPane.showMessageDialog(null, "Pembayaran Tidak Sesuai!", "Peringatan", JOptionPane.ERROR_MESSAGE);
                } else {
                    String status = "Lunas";
                    
                    String sqlMember = "SELECT id_member FROM member WHERE nama = '" + member + "'";
                    try {
                        st = koneksi.kon.createStatement();
                        rs = st.executeQuery(sqlMember);
                        while (rs.next()) {
                            mem = rs.getString("id_member");
                        } 
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e + " in input");
                    }
                    
                    String sql = "INSERT INTO booking VALUES ('" + idBooking + "', '" + nama
                        + "', '" + lapangan + "', '" + tglSewa + "', '" + jamMulai + "', '" + jamSelesai + "', " + Integer.parseInt(biaya) + ", '" + mem + "')";
                    st.execute(sql);
                    
                    String sql2 = "INSERT INTO pembayaran VALUES ('" + idBayar + "', '" + idBooking
                        + "', " + Integer.parseInt(bayar) + ", " + Integer.parseInt(kembali) + ", '" + tglSewa + "', '" + status + "')";
                    st.execute(sql2);
                    
                    JOptionPane.showMessageDialog(null, "Data Booking baru berhasil dimasukkan!");
                    loadData();
                }
            } catch (HeadlessException | NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(null, e + " in input");
            }
        }
    }
    
    public void editData() {
        int validate = JOptionPane.showOptionDialog(null, "Apakah Anda yakin perubahan data tersebut akan disimpan?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(validate == JOptionPane.YES_OPTION) {
            try {
                int cekKosong, lunas = 0;
                String mem = null;
                String idBooking = jTextField1.getText();
                String nama = jTextField2.getText();
                String lapangan = (String) jComboBox1.getSelectedItem();
                String tglSewa = jTextField4.getText();
                String jamMulai = jTextField5.getText();
                String jamSelesai = jTextField6.getText();
                String member = (String) jComboBox2.getSelectedItem();
                String biaya = jTextField7.getText();
                String idBayar = jTextField8.getText();
                String bayar = jTextField9.getText();
                String kembali = jTextField11.getText();
                
                if(idBooking.equals("") || nama.equals("") || tglSewa.equals("") || jamMulai.equals("") || jamSelesai.equals("") || biaya.equals("") || idBayar.equals("") || bayar.equals("") || kembali.equals("")) { cekKosong = 1; }
                else {
                    cekKosong = 0;
                    if(Integer.parseInt(bayar) - Integer.parseInt(biaya) < 0) {
                        lunas = 0;
                    } else {
                        lunas = 1;
                    }
                }
                
                if(cekKosong == 1) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi!", "Peringatan", JOptionPane.ERROR_MESSAGE);
                } else if(lunas == 0) {
                    JOptionPane.showMessageDialog(null, "Pembayaran Tidak Sesuai!", "Peringatan", JOptionPane.ERROR_MESSAGE);
                } else {                    
                    String sqlMember = "SELECT id_member FROM member WHERE nama = '" + member + "'";
                    try {
                        st = koneksi.kon.createStatement();
                        rs = st.executeQuery(sqlMember);
                        while (rs.next()) {
                            mem = rs.getString("id_member");
                        } 
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e + " in edit");
                    }
                    
                    String sql = "UPDATE booking SET id_booking = '" + idBooking
                            + "', nama_pelanggan = '" + nama
                            + "', lapangan = '" + lapangan
                            + "', tanggal_sewa = '" + tglSewa
                            + "', jam_mulai = '" + jamMulai
                            + "', jam_selesai = '" + jamSelesai
                            + "', biaya = " + Integer.parseInt(biaya)
                            + ", id_member = '" + mem
                            + "' WHERE id_booking = '" + idBooking + "'";
                    st.execute(sql);
                    
                    String sql2 = "UPDATE pembayaran SET id_pembayaran = '" + idBayar
                            + "', id_booking = '" + idBooking
                            + "', jml_bayar = " + Integer.parseInt(bayar)
                            + ", kembali = " + Integer.parseInt(kembali)
                            + ", tgl_bayar = '" + tglSewa
                            + "' WHERE id_pembayaran = '" + idBayar + "'";
                    st.execute(sql2);
                    
                    JOptionPane.showMessageDialog(null, "Data Member berhasil diupdate!");
                    loadData();
                }
            } catch (HeadlessException | NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(null, e + " in edit");
            }
        }
    }
    
    public void deleteData() {
        int validate = JOptionPane.showOptionDialog(null, "Apakah Anda yakin akan menghapus data tersebut?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(validate == JOptionPane.YES_OPTION) {
            try {
                st = koneksi.kon.createStatement();
                String sql_delete = "DELETE FROM pembayaran WHERE id_booking = '" + jTextField1.getText() + "'";
                String sql_delete2 = "DELETE FROM booking WHERE id_booking = '" + jTextField1.getText() + "'";
                st.executeUpdate(sql_delete); st.executeUpdate(sql_delete2);
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                loadData();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e + " in delete");
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RYDRA Futsal - Booking");

        jPanel1.setBackground(new java.awt.Color(0, 102, 0));

        jButton1.setText("SIMPAN");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMinimumSize(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("EDIT");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("HAPUS");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("BAYAR");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMinimumSize(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("ID BOOKING");

        jTextField1.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("NAMA PELANGGAN");

        jTextField2.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("MEMBER");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("LAPANGAN");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ceramic-1", "Ceramic-2", "Sintetic-1" }));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("TANGGAL SEWA");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("JAM SEWA");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("-");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("BIAYA BOOKING");

        jTextField7.setEditable(false);

        jButton7.setText("HITUNG");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jButton7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10))))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(3, 3, 3)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addGap(26, 26, 26))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("ID PEMBAYARAN");

        jTextField8.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("JUMLAH BAYAR");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("KEMBALI");

        jTextField11.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel12)
                .addGap(3, 3, 3)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DATA BOOKING DAN PEMBAYARAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(296, 296, 296))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        calcBiaya();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        calcKembali();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        inputData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        deleteData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int bar = jTable1.getSelectedRow();
        String a = jTable1.getValueAt(bar, 0).toString();
        String b = jTable1.getValueAt(bar, 1).toString();
        String c = jTable1.getValueAt(bar, 2).toString();
        String d = jTable1.getValueAt(bar, 3).toString(); 
        String e = jTable1.getValueAt(bar, 4).toString();
        String f = jTable1.getValueAt(bar, 5).toString();
        String g = jTable1.getValueAt(bar, 6).toString();
        String h = jTable1.getValueAt(bar, 7).toString();
        String i = jTable1.getValueAt(bar, 8).toString();
        String j = jTable1.getValueAt(bar, 9).toString();
        String k = jTable1.getValueAt(bar, 10).toString();

        jTextField1.setText(b);
        jTextField2.setText(d);
        jTextField4.setText(a);
        jTextField5.setText(f);
        jTextField6.setText(g);
        jTextField7.setText(i);
        jTextField8.setText(c);
        jTextField9.setText(j);
        jTextField11.setText(k);

        jComboBox1.setSelectedItem(e);
        jComboBox2.setSelectedItem(h);

        jButton1.setEnabled(false);
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Booking().setVisible(true);
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
