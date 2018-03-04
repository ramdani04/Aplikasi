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

/**
 *
 * @author Tius
 */
public class Form_Data_Petugas extends javax.swing.JFrame {
private static Object ex;
    Connection conn;
    Statement stat;
    ResultSet res;
    String sql;
    public Connection koneksi;

    /**
     * Creates new form Form_Data_Petugas
     */
    public Form_Data_Petugas() {
        initComponents();
        setLocationRelativeTo(this);
        koneksi_database();
        tampil();
        autonumber(); 
//        fillcombo();       
    }

//    private void fillcombo(){
//        try {
//             res=stat.executeQuery("select * from tbl_petugas ORDER BY id");
//                while(res.next()){
//                    String hakses = res.getString("hak_akses");
//                    hak_akses.addItem(hakses);
//                }
//            
//        } catch (Exception e) {
//        }
//    }
    public void koneksi_database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            koneksi=DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            stat=koneksi.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
    
    public void tampil(){
        DefaultTableModel tbl = new DefaultTableModel();
        //judul kolom
        tbl.addColumn("Id Petugas");
        tbl.addColumn("Nama Depan");
        tbl.addColumn("Nama Belakang");
        tbl.addColumn("Hak Akses");
        tbl.addColumn("Status");
        tbl.addColumn("Password");
        tabel.setModel(tbl);
        try {
            res=stat.executeQuery("select * from tbl_petugas ORDER BY id");
                while(res.next()){
                    tbl.addRow(new Object[]{
                    res.getString("id"),
                    res.getString("nama_depan"),
                    res.getString("nama_belakang"),
                    res.getString("hak_akses"),
                    res.getString("status"),
                    res.getString("password"),
                    
                    });
                    tabel.setModel(tbl);
                    tabel.getColumnModel().getColumn(5).setMaxWidth(0);
                    tabel.getColumnModel().getColumn(5).setMinWidth(0);
                    tabel.getColumnModel().getColumn(5).setPreferredWidth(0);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        int jumlahrecord = tabel.getRowCount();
        jml.setText(""+jumlahrecord);
        
    }
    public void autonumber() {
        try {
            String sql = "Select right(id,2)as no_terakhir from tbl_petugas";
            res=stat.executeQuery(sql);
            if(res.first() == false) {
                id2.setText("PTS0001");
            } 
            else {
             res.last();
             int no = res.getInt(1) + 1;
             String cno = String.valueOf(no);
             int pjg_cno = cno.length();
             int i = 0;
             if (i < 2 - pjg_cno){
                 cno = "000" + cno;
                 id2.setText("PTS" + cno);
             }
                else if (i < 3 - pjg_cno){
                cno = "00" + cno;
                 id2.setText("PTS" + cno); 
                }
                else if (i < 4 - pjg_cno){
                cno = "0" + cno;
                 id2.setText("PTS" + cno); 
                }
                 else if (i < 5 - pjg_cno){
                cno = "" + cno;                  
                } 
             id2.setText("PTS" + cno);
            }
        } catch (Exception DBException){
            System.err.println("Error =" + DBException);
        }
    }
    private void simpan (){
        if(  id2.getText().length() == 0
          ||nama_depan2.getText().length() == 0
          ||nama_belakang2.getText().length() == 0
          ||status.getText().length() == 0
          ||password.getText().length() == 0)
       {
           JOptionPane.showMessageDialog(rootPane, "Isi Data dengan lengkap!", "Dialog Peringatan",
           JOptionPane.WARNING_MESSAGE);
           
       } else {
        try {stat.executeUpdate("insert into tbl_petugas values("
                 + ""+"'"+id2.getText()+"',"
                 + ""+"'"+nama_depan2.getText()+"',"
                 + ""+"'"+nama_belakang2.getText()+"',"
                 + ""+"'"+password.getText()+"',"
                 + ""+"'"+hak_akses.getSelectedItem()+"',"
                 + ""+"'"+status.getText()+"')");
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
    private void bersih(){
        nama_depan2.setText("");
        nama_belakang2.setText("");
        password.setText("************");
        hak_akses.setSelectedIndex(0);
        cb_cari.setSelectedIndex(0);
        txtcari.setText("");
        nama_depan2.requestFocus();
        
    }
    private void hapus(){
        if (JOptionPane.showConfirmDialog(this,
                " Apakah Anda Yakin Ingin Hapus ?","Konfirmasi",            
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)
            ==JOptionPane.YES_OPTION){
            
            try {
                stat.executeUpdate("delete from tbl_petugas where "
                + "id ='"+id2.getText()+"'");
                JOptionPane.showMessageDialog(null, 
                        "Barang Dengan Kode :\n"+id2.getText()+
                        " Berhasil di Hapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
 
        }
       tampil(); 
         autonumber(); 
         bersih();
            }
     private void edit(){
        try {stat.executeUpdate("update tbl_petugas set "
                    + "nama_depan='"+nama_depan2.getText()+"',"
                    + "nama_belakang='"+nama_belakang2.getText()+"',"
                    + "password='"+password.getText()+"',"            
                    + "hak_akses='"+hak_akses.getSelectedItem()+"',"   
                    + "status='"+status.getText()+"'"                   
                    + "where id ='"+id2.getText()+"'");
            
            JOptionPane.showMessageDialog(null, "Barang dengan kode : \n"
                    + " "+id2.getText()+" Berhasil Diubah!!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
      tampil(); 
         autonumber(); 
         bersih();
       
    }
    private void combobox() {
        if (cb_cari.getSelectedIndex() == 1) {
            sql = "select * from tbl_petugas "
                    + "where id like '%" + txtcari.getText() + "%' ORDER BY id";
        }else if (cb_cari.getSelectedIndex() == 2) {
            sql = "select * from tbl_petugas "
                    + "where nama_depan like '%" + txtcari.getText() + "%' ORDER BY id";
        }
        else if (cb_cari.getSelectedIndex() == 3) {
            sql = "select * from tbl_petugas "
                    + "where nama_belakang like '%" + txtcari.getText() + "%' ORDER BY id";
        }
        else if (cb_cari.getSelectedIndex() == 4) {
            sql = "select * from tbl_petugas "
                    + "where hak_akses like '%" + txtcari.getText() + "%' ORDER BY id";
        }
        else if (cb_cari.getSelectedIndex() == 4) {
            sql = "select * from tbl_petugas "
                    + "where status like '%" + txtcari.getText() + "%' ORDER BY id";
        }
    }
    //*******************************************************CARI DATA*****************************************//
    private void caidata() {
      if (cb_cari.getSelectedIndex() ==0) {
          JOptionPane.showMessageDialog(null, "Jenis Pencarian Belum Dipilih!");
      }else{
          DefaultTableModel tbl = new DefaultTableModel();
         tbl.addColumn("Id Petugas");
        tbl.addColumn("Nama Depan");
        tbl.addColumn("Nama Belakang");
        tbl.addColumn("Hak Akses");
        tbl.addColumn("Status");
        tbl.addColumn("Password");
          
          tabel.setModel(tbl);
          try {
              res = stat.executeQuery(sql);
              while (res.next()) {
                  tbl.addRow(new Object[]{
                      res.getString(1),
                      res.getString(2),
                      res.getString(3),
                      res.getString(5),
                      res.getString(6),
                      res.getString(4),
                  });
                  tabel.setModel(tbl);
                  tabel.getColumnModel().getColumn(5).setMaxWidth(0);
                    tabel.getColumnModel().getColumn(5).setMinWidth(0);
                    tabel.getColumnModel().getColumn(5).setPreferredWidth(0);
              }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(rootPane,"Ada Kesalahan!" + e);
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

        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        id2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        nama_depan2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        nama_belakang2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        hak_akses = new javax.swing.JComboBox();
        password = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        cb_cari = new javax.swing.JComboBox();
        jml = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PETUGAS");

        jPanel10.setBackground(new java.awt.Color(0, 153, 255));

        jPanel11.setBackground(new java.awt.Color(0, 153, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA PETUGAS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Handwriting", 0, 26))); // NOI18N

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tambah Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Id Petugas");

        id2.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Nama Depan");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nama Belakang");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Pasword");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/44-32.png"))); // NOI18N
        jButton9.setText("SIMPAN");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/18-32.png"))); // NOI18N
        jButton10.setText("EDIT");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/43-32.png"))); // NOI18N
        jButton11.setText("Hapus");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/YPS__email_mail_refresh_update_sendreceive-32.png"))); // NOI18N
        jButton12.setText("segar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addGap(3, 3, 3)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11)
                    .addComponent(jButton12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Hak Akses");

        hak_akses.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Pilih Akses **", "Admin", "Petugas" }));

        password.setText("jPasswordField1");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Status");

        status.setEditable(false);
        status.setText("TIDAK AKTIF");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(id2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nama_depan2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nama_belakang2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(password))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hak_akses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(status)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(id2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nama_depan2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nama_belakang2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hak_akses, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tampil Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pencarian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel6.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel6AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel6MousePressed(evt);
            }
        });

        jLabel1.setText("Cari");

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        cb_cari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Cari Berdasarkan **", "Id Petugas", "Nama Depan", "Nama Belakang", "Hak Akses", "Status" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcari)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtcari, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addComponent(cb_cari)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jml, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jml, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        simpan();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        edit();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        hapus();       // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        bersih(); tampil(); autonumber();       // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        try {
            int row = tabel.rowAtPoint(evt.getPoint());

            String a = tabel.getValueAt(row, 0).toString();
            String b = tabel.getValueAt(row, 1).toString();
            String c = tabel.getValueAt(row, 2).toString();
            String d = tabel.getValueAt(row, 3).toString();
            String e = tabel.getValueAt(row, 4).toString();
            String f = tabel.getValueAt(row, 5).toString();

            id2.setText(String.valueOf(a));
            nama_depan2.setText(String.valueOf(b));
            nama_belakang2.setText(String.valueOf(c));
            password.setText(String.valueOf(f));
            hak_akses.setSelectedItem(d);
            status.setText(String.valueOf(e));

        }   catch (Exception e) {
            JOptionPane.showMessageDialog(null, stat);
        }              // TODO add your handling code here:
    }//GEN-LAST:event_tabelMouseClicked

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        combobox();
        caidata();        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void jPanel6AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel6AncestorAdded
tampil();        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel6AncestorAdded

    private void jPanel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MousePressed
bersih(); tampil(); autonumber();           // TODO add your handling code here:
    }//GEN-LAST:event_jPanel6MousePressed

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
            java.util.logging.Logger.getLogger(Form_Data_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Data_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Data_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Data_Petugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Data_Petugas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cb_cari;
    private javax.swing.JComboBox hak_akses;
    private javax.swing.JTextField id2;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jml;
    private javax.swing.JTextField nama_belakang2;
    private javax.swing.JTextField nama_depan2;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField status;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
