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

import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Tius
 */
public class Form_Surat_Keluar extends javax.swing.JFrame {
private static Object ex;
    Connection conn;
    Statement stat;
    ResultSet res;
    String sql;
    public Connection koneksi;

    /**
     * Creates new form Form_Surat_Keluar
     */
    public Form_Surat_Keluar() {
        initComponents(); 
        setLocationRelativeTo(this);
        koneksi_database(); 
        fill();
        tampil_keluar();
        tampil_status();
//        autonumber_keluar();       
        
    }
 private void tampil_status(){
     try {
         koneksi_database();         
         String sql = "select * from tbl_petugas where status = 'AKTIF'";
         res = stat.executeQuery(sql);
         while (res.next()) {
             id_petugas1.setText(res.getString("nama_depan"));
         }
     } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
     }
 }
 private void fillcombo(){
        try {
             res=stat.executeQuery("select * from tbl_disposisi WHERE no_disposisi = '"+no_agenda1.getSelectedItem()
                    +"'");
                while(res.next()){
                   
                    jenis_surat1.setText(res.getString("jenis_surat"));
                    no_surat1.setText(res.getString("no_surat"));
                    pengirim1.setText(res.getString("kepada"));
                    
                }

        } catch (Exception e) {
        }
    }
     private void fill(){
        try {
             res=stat.executeQuery("select * from tbl_disposisi where status_surat = 'Belum Ada Tindakan'");
                while(res.next()){
                    String hakses = res.getString("no_disposisi");
                    no_agenda1.addItem(hakses);
                }
            
        } catch (Exception e) {
        }
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
 /********************** SURAT KELUAR ********************/
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
//     public void autonumber_keluar() {
//        try {
//            String sql = "Select right(no_agenda,2)as no_terakhir from tbl_surat_keluar";
//            res=stat.executeQuery(sql);
//            if(res.first() == false) {
//                no_agenda1.setText("AGD0001");
//            } 
//            else {
//             res.last();
//             int no = res.getInt(1) + 1;
//             String cno = String.valueOf(no);
//             int pjg_cno = cno.length();
//             int i = 0;
//             if (i < 2 - pjg_cno){
//                 cno = "000" + cno;
//                 no_agenda1.setText("AGD" + cno);
//             }
//                else if (i < 3 - pjg_cno){
//                cno = "00" + cno;
//                 no_agenda1.setText("AGD" + cno); 
//                }
//                else if (i < 4 - pjg_cno){
//                cno = "0" + cno;
//                 no_agenda1.setText("AGD" + cno); 
//                }
//                 else if (i < 5 - pjg_cno){
//                cno = "" + cno;                  
//                } 
//             no_agenda1.setText("AGD" + cno);
//            }
//        } catch (Exception DBException){
//            System.err.println("Error =" + DBException);
//        }
//    }
     
     private void bersih_keluar(){
         no_agenda1.setSelectedIndex(0);
       tanggal_keluar1.setCalendar(null);
        jenis_surat1.setText("");
        no_surat1.setText("");
        pengirim1.setText("");
        balasan1.setText(""); 
        agenda.setText("");
        cb_cari1.setSelectedIndex(0);
        txtcari1.setText("");
        
    }
//     private void fillcombo(){
//        try {
//             res=stat.executeQuery("select * from tbl_petugas ORDER BY id");
//                while(res.next()){
//                    String hakses = res.getString("id");
//                    id_petugas1.addItem(hakses);
//                }
//            
//        } catch (Exception e) {
//        }
//    }
     private void simpan_keluar (){
        SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
     String tanggal_keluarr = dateFormat_1.format(tanggal_keluar1.getDate());
        if(  balasan1.getText().length() == 0)
       {
           JOptionPane.showMessageDialog(rootPane, "Isi Data dengan lengkap!", "Dialog Peringatan",
           JOptionPane.WARNING_MESSAGE);
           
       } else {
        try {stat.executeUpdate("insert into tbl_surat_keluar values("
                 + ""+"'"+no_agenda1.getSelectedItem()+"',"
                 + ""+"'"+id_petugas1.getText()+"',"
                 + ""+"'"+jenis_surat1.getText()+"',"
                 + ""+"'"+tanggal_keluarr+"',"
                 + ""+"'"+no_surat1.getText()+"',"
                 + ""+"'"+pengirim1.getText()+"',"
                 + ""+"'"+balasan1.getText()+"')");
                 JOptionPane.showMessageDialog(null,
                    "Data berhasi di simpan"); }
         catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Keterangan EROR : Sudah Mengirim Surat " + e);
         }
            try {stat.executeUpdate("update tbl_disposisi set status_surat = 'Ada Tindakan' "
                 + "where "
                 + "no_disposisi='"+no_agenda1.getSelectedItem()+"'"
         );
                
            } catch (Exception e) {
            }
        } 
         bersih_keluar();
         new Form_Surat_Keluar().show();
         dispose();
    }
     private void hapus_keluar(){
        if (JOptionPane.showConfirmDialog(this,
                " Apakah Anda Yakin Ingin Hapus ?","Konfirmasi",            
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)
            ==JOptionPane.YES_OPTION){
            
            try {
                stat.executeUpdate("delete from tbl_surat_keluar where "
                + "no_agenda ='"+agenda.getText()+"'");
                JOptionPane.showMessageDialog(null, 
                        "Barang Dengan Kode :\n"+agenda.getText()+
                        " Berhasil di Hapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
 
        }
        
      bersih_keluar();
      tampil_keluar();
            }
    private void combobox1() {
        if (cb_cari1.getSelectedIndex() == 1) {
            sql = "select * from tbl_surat_keluar "
                    + "where no_agenda like '%" + txtcari1.getText() + "%' ORDER BY no_agenda";
        }else if (cb_cari1.getSelectedIndex() == 2) {
            sql = "select * from tbl_surat_keluar "
                    + "where id like '%" + txtcari1.getText() + "%' ORDER BY no_agenda";
        }
        else if (cb_cari1.getSelectedIndex() == 3) {
            sql = "select * from tbl_surat_keluar "
                    + "where no_surat like '%" + txtcari1.getText() + "%' ORDER BY no_agenda";
        }
        else if (cb_cari1.getSelectedIndex() == 4) {
            sql = "select * from tbl_surat_keluar "
                    + "where pengirim like '%" + txtcari1.getText() + "%' ORDER BY no_agenda";
        }
    }
    //*******************************************************CARI DATA*****************************************//
    private void caidata1() {
      if (cb_cari1.getSelectedIndex() ==0) {
          JOptionPane.showMessageDialog(null, "Jenis Pencarian Belum Dipilih!");
      }else{
          DefaultTableModel tbl = new DefaultTableModel();
       tbl.addColumn("No Agenda");
        tbl.addColumn("Id");
        tbl.addColumn("Jenis Surat");
        tbl.addColumn("Tanggal kirim");
        tbl.addColumn("No Surat");
        tbl.addColumn("Pengirim");
        tbl.addColumn("Balasan");
          
          tabel_keluar.setModel(tbl);
          try {
              res = stat.executeQuery(sql);
              while (res.next()) {
                  tbl.addRow(new Object[]{
                      res.getString(1),
                      res.getString(2),
                      res.getString(3),
                      res.getString(4),
                      res.getString(5),
                      res.getString(6),
                      res.getString(7),
                  });
                  tabel_keluar.setModel(tbl);
              }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(rootPane,"Ada Kesalahan!" + e);
          }
      }
     
    }
//     private void edit_keluar(){
//        SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
//     String tanggal_masukk = dateFormat_1.format(tanggal_masuk1.getDate());
//     String tanggal_keluarr = dateFormat_1.format(tanggal_keluar1.getDate());
//        try {stat.executeUpdate("update tbl_surat_keluar set "
//                    + "id='"+id_petugas1.getSelectedItem()+"',"
//                    + "jenis_surat='"+jenis_surat1.getSelectedItem()+"',"
//                    + "tanggal_keluar='"+tanggal_keluarr+"',"
//                    + "no_surat='"+no_surat1.getText()+"',"
//                    + "pengirim='"+pengirim1.getText()+"',"
//                    + "balasan='"+balasan1.getText()+"'"                    
//                    + "where no_agenda ='"+no_agenda1.getText()+"'");
//            
//            JOptionPane.showMessageDialog(null, "Supplier dengan kode : \n"
//                    + " "+no_agenda1.getText()+" Berhasil Diubah!!");
//            
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }       
//      
//       autonumber_keluar();
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel21 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        tanggal_keluar1 = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        no_surat1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        pengirim1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        balasan1 = new javax.swing.JTextArea();
        id_petugas1 = new javax.swing.JTextField();
        no_agenda1 = new javax.swing.JComboBox();
        jenis_surat1 = new javax.swing.JTextField();
        agenda = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        simpan_masuk1 = new javax.swing.JButton();
        hapus_masuk1 = new javax.swing.JButton();
        segar_masuk1 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtcari1 = new javax.swing.JTextField();
        cb_cari1 = new javax.swing.JComboBox();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabel_keluar = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SURAT KELUAR");

        jPanel21.setBackground(new java.awt.Color(0, 153, 204));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SURAT KELUAR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Handwriting", 0, 26))); // NOI18N

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tambah Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("No Agenda");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("ID Petugas");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Jenis Surat");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Tanggal Keluar");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("No Surat");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Pengirim");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Balasan");

        balasan1.setColumns(20);
        balasan1.setRows(5);
        jScrollPane3.setViewportView(balasan1);

        id_petugas1.setEditable(false);

        no_agenda1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Pilih Agenda **" }));
        no_agenda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_agenda1ActionPerformed(evt);
            }
        });

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        simpan_masuk1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/44-32.png"))); // NOI18N
        simpan_masuk1.setText("SIMPAN");
        simpan_masuk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan_masuk1ActionPerformed(evt);
            }
        });

        hapus_masuk1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/43-32.png"))); // NOI18N
        hapus_masuk1.setText("Hapus");
        hapus_masuk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapus_masuk1ActionPerformed(evt);
            }
        });

        segar_masuk1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/YPS__email_mail_refresh_update_sendreceive-32.png"))); // NOI18N
        segar_masuk1.setText("segar");
        segar_masuk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segar_masuk1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(simpan_masuk1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hapus_masuk1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(segar_masuk1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan_masuk1)
                    .addComponent(hapus_masuk1)
                    .addComponent(segar_masuk1)))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jenis_surat1))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(id_petugas1))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(no_surat1))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tanggal_keluar1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(pengirim1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(no_agenda1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(agenda, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(agenda, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(no_agenda1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(id_petugas1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jenis_surat1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tanggal_keluar1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(no_surat1)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pengirim1)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pencarian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Cari");

        txtcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcari1ActionPerformed(evt);
            }
        });

        cb_cari1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cb_cari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Cari Berdasarkan **", "No Agenda", "Petugas", "No Surat", "Pengirim" }));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_cari1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtcari1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addComponent(cb_cari1)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tampil Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/print.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcari1ActionPerformed
combobox1();
caidata1();// TODO add your handling code here:
    }//GEN-LAST:event_txtcari1ActionPerformed

    private void tabel_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_keluarMouseClicked
        try {
            int row = tabel_keluar.rowAtPoint(evt.getPoint());
            String a = tabel_keluar.getValueAt(row, 0).toString();
            String b = tabel_keluar.getValueAt(row, 1).toString();
            String c = tabel_keluar.getValueAt(row, 2).toString();
            String f = tabel_keluar.getValueAt(row, 4).toString();
            String g = tabel_keluar.getValueAt(row, 5).toString();
            String h = tabel_keluar.getValueAt(row, 6).toString();

            agenda.setText(String.valueOf(a));
            id_petugas1.setText(String.valueOf(b));
            jenis_surat1.setText(String.valueOf(c));
            no_surat1.setText(String.valueOf(f));
            pengirim1.setText(String.valueOf(g));
            balasan1.setText(String.valueOf(h));

        }   catch (Exception e) {
            JOptionPane.showMessageDialog(null, stat);
        }
    }//GEN-LAST:event_tabel_keluarMouseClicked

    private void tabel_keluarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_keluarMousePressed
        if(evt.getClickCount() == 1){
            tanggal_keluar1.setDate(getTanggalFromTable(tabel_keluar, 3));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_keluarMousePressed
 public static Date getTanggalFromTable(JTable table, int kolom) {
    JTable tabel = table;
    String str_tgl = String.valueOf(tabel.getValueAt(tabel.getSelectedRow(),kolom));
    Date tanggal = null;
        try {
            tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(str_tgl);
        } catch (ParseException e) {
            Logger.getLogger(JTable.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return tanggal;   
    
    }
    private void simpan_masuk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan_masuk1ActionPerformed
       
        simpan_keluar();
        fill();
        tampil_keluar();
        bersih_keluar();// TODO add your handling code here:
    }//GEN-LAST:event_simpan_masuk1ActionPerformed

    private void hapus_masuk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapus_masuk1ActionPerformed
     tampil_keluar();   hapus_keluar(); bersih_keluar();       // TODO add your handling code here:
    }//GEN-LAST:event_hapus_masuk1ActionPerformed

    private void segar_masuk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segar_masuk1ActionPerformed
    tampil_keluar();    bersih_keluar();  
//        autonumber_keluar();       // TODO add your handling code here:
    }//GEN-LAST:event_segar_masuk1ActionPerformed

    private void no_agenda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_agenda1ActionPerformed
fillcombo();        // TODO add your handling code here:
    }//GEN-LAST:event_no_agenda1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
try {
            String NamaFile = "./src/laporan/Surat_Keluarid.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            HashMap hash = new HashMap(2);
            
            
            hash.put("nosuratt",no_surat1.getText());
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, hash, koneksi);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception ex) {
            System.out.println(ex);
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Surat_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Surat_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Surat_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Surat_Keluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Form_Surat_Keluar sk = new Form_Surat_Keluar();
                sk.setExtendedState(MAXIMIZED_BOTH);
                sk.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agenda;
    private javax.swing.JTextArea balasan1;
    private javax.swing.JComboBox cb_cari1;
    private javax.swing.JButton hapus_masuk1;
    private javax.swing.JTextField id_petugas1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jenis_surat1;
    private javax.swing.JComboBox no_agenda1;
    private javax.swing.JTextField no_surat1;
    private javax.swing.JTextField pengirim1;
    private javax.swing.JButton segar_masuk1;
    private javax.swing.JButton simpan_masuk1;
    private javax.swing.JTable tabel_keluar;
    private com.toedter.calendar.JDateChooser tanggal_keluar1;
    private javax.swing.JTextField txtcari1;
    // End of variables declaration//GEN-END:variables
}
