/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Tius
 */
public class Laporan extends javax.swing.JFrame {
private static Object ex;
    Connection conn;
    Statement stat;
    ResultSet res;
    String sql;
    public Connection koneksi;

    /**
     * Creates new form Laporan
     */
    public Laporan() {
        initComponents();        
        setLocationRelativeTo(this);
        koneksi_database();
        tampil_masuk();
        tampil_keluar();
        tampil_disposisi();
    }
   public void koneksi_database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            koneksi=DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            stat=koneksi.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
    public void tampil_masuk(){
  DefaultTableModel tbl = new DefaultTableModel();
        //judul kolom
        tbl.addColumn("No Agenda");
        tbl.addColumn("Id");
        tbl.addColumn("Jenis Surat");
        tbl.addColumn("Tanggal Terima");
        tbl.addColumn("No Surat");
        tbl.addColumn("Pengirim");
        tbl.addColumn("Perihal");
        tabel_masuk.setModel(tbl);
        try {
            res=stat.executeQuery("select * from tbl_surat_masuk ORDER BY no_agenda");
                while(res.next()){
                    tbl.addRow(new Object[]{
                    res.getString("no_agenda"),
                    res.getString("id"),
                    res.getString("jenis_surat"),
                    res.getString("tanggal_terima"),
                    res.getString("no_surat"),
                    res.getString("pengirim"),
                    res.getString("perihal"),
                    
                    });
                    tabel_masuk.setModel(tbl);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void tampil_keluar(){
        DefaultTableModel tbl = new DefaultTableModel();
        //judul kolom
        tbl.addColumn("No Agenda");
        tbl.addColumn("Id");
        tbl.addColumn("Jenis Surat");
        tbl.addColumn("Tanggal kirim");
        tbl.addColumn("No Surat");
        tbl.addColumn("Pengirim");
        tbl.addColumn("Balasan");
        tabel_keluar.setModel(tbl);
        try {
            res=stat.executeQuery("select * from tbl_surat_keluar ORDER BY no_agenda");
                while(res.next()){
                    tbl.addRow(new Object[]{
                    res.getString("no_agenda"),
                    res.getString("id"),
                    res.getString("jenis_surat"),
                    res.getString("tanggal_kirim"),
                    res.getString("no_surat"),
                    res.getString("pengirim"),
                    res.getString("balasan"),
                    
                    });
                    tabel_keluar.setModel(tbl);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void tampil_disposisi(){
        DefaultTableModel tbl = new DefaultTableModel();
        //judul kolom
        tbl.addColumn("No Disposis");
        tbl.addColumn("No Agenda");
        tbl.addColumn("No Surat");
        tbl.addColumn("Jenis Surat");
        tbl.addColumn("Kepada");
        tbl.addColumn("Keterangan");
        tbl.addColumn("Status Surat");
        tbl.addColumn("tanggapan");
        tabel_disposisi.setModel(tbl);
        try {
            res=stat.executeQuery("select * from tbl_disposisi ORDER BY no_disposisi");
                while(res.next()){
                    tbl.addRow(new Object[]{
                    res.getString("no_disposisi"),
                    res.getString("no_agenda"),
                    res.getString("no_surat"),
                    res.getString("jenis_surat"),
                    res.getString("kepada"),
                    res.getString("keterangan"),
                    res.getString("status_surat"),
                    res.getString("tanggapan"),
                    
                    });
                    tabel_disposisi.setModel(tbl);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabel_masuk = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabel_keluar = new javax.swing.JTable();
        jToggleButton3 = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_disposisi = new javax.swing.JTable();
        jToggleButton5 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(204, 255, 204));
        jTabbedPane1.setAlignmentX(5.0F);
        jTabbedPane1.setAlignmentY(5.0F);
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(150, 100));

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tampil Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        tabel_masuk.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_masuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_masukMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabel_masukMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(tabel_masuk);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/print.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Laporan Surat Masuk", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tampil Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        tabel_keluar.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_keluarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabel_keluarMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(tabel_keluar);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
        );

        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/print.png"))); // NOI18N
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jToggleButton3))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToggleButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Laporan Surat Keluar", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tampil Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        tabel_disposisi.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_disposisi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_disposisiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_disposisi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
        );

        jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/print.png"))); // NOI18N
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jToggleButton5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jToggleButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Disposisi", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabel_masukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_masukMouseClicked

        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_masukMouseClicked

    private void tabel_masukMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_masukMousePressed
             // TODO add your handling code here:
    }//GEN-LAST:event_tabel_masukMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
try {
            String NamaFile = "./src/laporan/Laporan_Masuk.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            HashMap hash = new HashMap(2);
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, hash, koneksi);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception ex) {
            System.out.println(ex);
        }       // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabel_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_keluarMouseClicked
       
    }//GEN-LAST:event_tabel_keluarMouseClicked

    private void tabel_keluarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_keluarMousePressed
      
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_keluarMousePressed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
try {
            String NamaFile = "./src/laporan/Laporan_Keluar.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            HashMap hash = new HashMap(2);
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, hash, koneksi);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception ex) {
            System.out.println(ex);
        }  
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void tabel_disposisiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_disposisiMouseClicked

    }//GEN-LAST:event_tabel_disposisiMouseClicked

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
                try {
                        String NamaFile = "./src/laporan/Laporan_Disposis.jasper";
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
                        HashMap hash = new HashMap(2);
                        JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, hash, koneksi);
                        JasperViewer.viewReport(JPrint, false);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JTable tabel_disposisi;
    private javax.swing.JTable tabel_keluar;
    private javax.swing.JTable tabel_masuk;
    // End of variables declaration//GEN-END:variables
}
