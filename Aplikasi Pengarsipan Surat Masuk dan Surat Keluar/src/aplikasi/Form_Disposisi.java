/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Tius
 */
public class Form_Disposisi extends javax.swing.JFrame {
private static Object ex;
    Connection conn;
    Statement stat;
    ResultSet res;
    String sql;
    public Connection koneksi;

    /**
     * Creates new form Form_Disposisi
     */
    public Form_Disposisi() {
        initComponents();
   setLocationRelativeTo(this);
        koneksi_database();
        tampil();
fill();
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
private void fillcombo(){
        try {
             res=stat.executeQuery("select * from tbl_surat_masuk WHERE "
                     + "no_agenda='"+no_agenda.getSelectedItem()
                    +"'");
                while(res.next()){
                   
                    no_surat.setText(res.getString("no_surat"));
                    kepada.setText(res.getString("pengirim"));
                    keterangan.setText(res.getString("perihal"));
                    tanggal_masuk.setText(res.getString("jenis_surat"));
                    
                }

        } catch (Exception e) {
        }
    }
     private void fill(){
        try {
             res=stat.executeQuery("select * from tbl_surat_masuk ORDER BY no_agenda");
                while(res.next()){
                    String hakses = res.getString("no_agenda");
                    no_agenda.addItem(hakses);
                }
            
        } catch (Exception e) {
        }
    }
    
    public void tampil(){
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
        tabel.setModel(tbl);
        try {
            res=stat.executeQuery("select * from tbl_disposisi ORDER BY status_surat = 'Belum Ada Tindakan'");
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
                    tabel.setModel(tbl);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } autonumber();
    }
    public void autonumber() {
        try {
            String sql = "Select right(no_disposisi,2)as no_terakhir from tbl_disposisi";
            res=stat.executeQuery(sql);
            if(res.first() == false) {
                no_disposisi.setText("DIS0001");
            } 
            else {
             res.last();
             int no = res.getInt(1) + 1;
             String cno = String.valueOf(no);
             int pjg_cno = cno.length();
             int i = 0;
             if (i < 2 - pjg_cno){
                 cno = "000" + cno;
                 no_disposisi.setText("DIS" + cno);
             }
                else if (i < 3 - pjg_cno){
                cno = "00" + cno;
                 no_disposisi.setText("DIS" + cno); 
                }
                else if (i < 4 - pjg_cno){
                cno = "0" + cno;
                 no_disposisi.setText("DIS" + cno); 
                }
                 else if (i < 5 - pjg_cno){
                cno = "" + cno;                  
                } 
             no_disposisi.setText("DIS" + cno);
            }
        } catch (Exception DBException){
            System.err.println("Error =" + DBException);
        }
    }
     private void bersih(){
        status_surat.setText("Belum Ada Tindakan");
        no_surat.setText("");
        no_agenda.setSelectedIndex(0);
        kepada.setText(""); 
        no_surat.setText("");
        keterangan.setText("");
        balasan.setText("");        
       tanggal_masuk.setText("");  
       cb_cari.setSelectedIndex(0);
       txtcari.setText("");
        
    }
    private void simpan (){
    //    SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
    // String tanggal_masukk = dateFormat_1.format(tanggal_masuk.getDate());
    // String tanggal_keluarr = dateFormat_1.format(tanggal_keluar.getDate());
        if(  balasan.getText().length() == 0 || no_agenda.getSelectedIndex() ==0)
       {
           JOptionPane.showMessageDialog(rootPane, "Isi Data dengan lengkap!", "Dialog Peringatan",
           JOptionPane.WARNING_MESSAGE);
           
       } else {
        try {stat.executeUpdate("insert into tbl_disposisi values("
                 + ""+"'"+no_disposisi.getText()+"',"
                 + ""+"'"+no_agenda.getSelectedItem()+"',"
                 + ""+"'"+no_surat.getText()+"',"
                 + ""+"'"+tanggal_masuk.getText()+"',"
                 + ""+"'"+kepada.getText()+"',"
                 + ""+"'"+keterangan.getText()+"',"
                 + ""+"'"+status_surat.getText()+"',"
                 + ""+"'"+balasan.getText()+"')");
                 JOptionPane.showMessageDialog(null,
                    "Data berhasi di simpan"); }
         catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Keterangan EROR : " + e);
         } 
        } 
         tampil();
         autonumber(); 
            bersih();
    }
  
    private void hapus(){
        if (JOptionPane.showConfirmDialog(this,
                " Apakah Anda Yakin Ingin Hapus ?","Konfirmasi",            
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)
            ==JOptionPane.YES_OPTION){
            
            try {
                stat.executeUpdate("delete from tbl_disposisi where "
                + "no_disposisi ='"+no_disposisi.getText()+"'");
                JOptionPane.showMessageDialog(null, 
                        "Barang Dengan Kode :\n"+no_disposisi.getText()+
                        " Berhasil di Hapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
 
        }
        tampil();
         autonumber(); 
      bersih();
            }
   
    private void combobox() {
        if (cb_cari.getSelectedIndex() == 1) {
            sql = "select * from tbl_disposisi "
                    + "where no_disposisi like '%" + txtcari.getText() + "%' ORDER BY no_disposisi";
        }else if (cb_cari.getSelectedIndex() == 2) {
            sql = "select * from tbl_disposisi "
                    + "where no_agenda like '%" + txtcari.getText() + "%' ORDER BY no_disposisi";
        }
        else if (cb_cari.getSelectedIndex() == 3) {
            sql = "select * from tbl_disposisi "
                    + "where no_surat like '%" + txtcari.getText() + "%' ORDER BY no_disposisi";
        }
        else if (cb_cari.getSelectedIndex() == 4) {
            sql = "select * from tbl_disposisi "
                    + "where status_surat like '%" + txtcari.getText() + "%' ORDER BY no_disposisi";
        }
    }
    //*******************************************************CARI DATA*****************************************//
    private void caidata() {
      if (cb_cari.getSelectedIndex() ==0) {
          JOptionPane.showMessageDialog(null, "Jenis Pencarian Belum Dipilih!");
      }else{
          DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("No Disposis");
        tbl.addColumn("No Agenda");
        tbl.addColumn("No Surat");
        tbl.addColumn("Jenis Surat");
        tbl.addColumn("Kepada");
        tbl.addColumn("Keterangan");
        tbl.addColumn("Status Surat");
        tbl.addColumn("tanggapan");
          
          tabel.setModel(tbl);
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
                      res.getString(8),
                  });
                  tabel.setModel(tbl);
              }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(rootPane,"Ada Kesalahan!" + e);
          }
      }
     
    }
    private void edit(){
    //    SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
    // String tanggal_masukk = dateFormat_1.format(tanggal_masuk.getDate());
    // String tanggal_keluarr = dateFormat_1.format(tanggal_keluar.getDate());
        try {stat.executeUpdate("update tbl_disposisi set "
                    + "no_agenda='"+no_agenda.getSelectedItem()+"',"
                    + "no_surat='"+no_surat.getText()+"',"
                    + "jenis_surat='"+tanggal_masuk.getText()+"',"
                    + "kepada='"+kepada.getText()+"',"
                    + "keterangan='"+keterangan.getText()+"',"
                    + "status_surat='"+status_surat.getText()+"',"
                    + "tanggapan='"+balasan.getText()+"'"                    
                    + "where no_disposisi ='"+no_disposisi.getText()+"'");
            
            JOptionPane.showMessageDialog(null, "Supplier dengan kode : \n"
                    + " "+no_disposisi.getText()+" Berhasil Diubah!!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }  
        
       
        tampil();
        autonumber();
        bersih();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tanggal_masuk = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        no_disposisi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        no_surat = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        kepada = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        balasan = new javax.swing.JTextArea();
        no_agenda = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        status_surat = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        keterangan = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        cb_cari = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        tanggal_masuk.setText("Tanggal");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DISPOSISI");

        jPanel9.setBackground(new java.awt.Color(0, 153, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DISPOSISI", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Handwriting", 0, 26))); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tambah Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("No Disposisi");

        no_disposisi.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("No Agenda");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Keterangan");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Status Surat");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("No Surat");

        no_surat.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Kepada");

        kepada.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Balasan");

        balasan.setColumns(20);
        balasan.setRows(5);
        jScrollPane2.setViewportView(balasan);

        no_agenda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Pilih Agenda **" }));
        no_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_agendaActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/44-32.png"))); // NOI18N
        jButton1.setText("SIMPAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/18-32.png"))); // NOI18N
        jButton2.setText("EDIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/43-32.png"))); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/YPS__email_mail_refresh_update_sendreceive-32.png"))); // NOI18N
        jButton4.setText("segar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1)))
        );

        status_surat.setText("Belum Ada Tindakan");
        status_surat.setEnabled(false);

        keterangan.setColumns(20);
        keterangan.setRows(5);
        jScrollPane4.setViewportView(keterangan);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(no_surat, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(no_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(no_disposisi, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(kepada, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status_surat, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(no_disposisi, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(no_agenda, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(no_surat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kepada, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(status_surat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pencarian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Cari");

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        cb_cari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Cari Berdasarkan **", "No Disposisi", "No Agenda", "No Surat", "Status (Tidak/ Ada Tindakan)" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_cari, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtcari, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addComponent(cb_cari)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tampil Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        tabel.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/print.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Keterangan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Jika Ingin merubah atau menghapus, anda harus mengkilk dulu tabel yang ada.");

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Di Pojok kanan atas Anda bisa langsung mencetak Disposisi.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void no_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_agendaActionPerformed
        fillcombo();        // TODO add your handling code here:
    }//GEN-LAST:event_no_agendaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        simpan();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        edit();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        hapus();       // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    tampil();    bersih();  autonumber();       // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        combobox();
        caidata();        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        try {
            int row = tabel.rowAtPoint(evt.getPoint());
            String a = tabel.getValueAt(row, 0).toString();
            String b = tabel.getValueAt(row, 1).toString();
            String c = tabel.getValueAt(row, 2).toString();
            String d = tabel.getValueAt(row, 3).toString();
            String e = tabel.getValueAt(row, 4).toString();
            String f = tabel.getValueAt(row, 5).toString();
            String g = tabel.getValueAt(row, 6).toString();
            String h = tabel.getValueAt(row, 7).toString();

            no_disposisi.setText(String.valueOf(a));
            no_agenda.setSelectedItem(b);
            no_surat.setText(String.valueOf(c));
            tanggal_masuk.setText(String.valueOf(d));
            kepada.setText(String.valueOf(e));
            keterangan.setText(String.valueOf(f));
            status_surat.setText(String.valueOf(g));
            balasan.setText(String.valueOf(h));

        }   catch (Exception e) {
            JOptionPane.showMessageDialog(null, stat);
        }

    }//GEN-LAST:event_tabelMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
try {
            String NamaFile = "./src/laporan/Laporan_Disposis.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            HashMap hash = new HashMap(2);
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, hash, koneksi);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception ex) {
            System.out.println(ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Disposisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Disposisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Disposisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Disposisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 Form_Disposisi dis = new Form_Disposisi();
                dis.setExtendedState(MAXIMIZED_BOTH);
                dis.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea balasan;
    private javax.swing.JComboBox cb_cari;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField kepada;
    private javax.swing.JTextArea keterangan;
    private javax.swing.JComboBox no_agenda;
    private javax.swing.JTextField no_disposisi;
    private javax.swing.JTextField no_surat;
    private javax.swing.JTextField status_surat;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField tanggal_masuk;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
