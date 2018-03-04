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
public class Form_Surat_Masuk extends javax.swing.JFrame {

private static Object ex;
    Connection conn;
    Statement stat;
    ResultSet res;
    String sql;
    public Connection koneksi;
    /**
     * Creates new form Form_Surat_Masuk
     */
    public Form_Surat_Masuk() {
        initComponents();
   setLocationRelativeTo(this);
        koneksi_database();
        tampil_Surat_Masuk();
        autonumber_masuk();
        autonumber_nosurat();
        tampil_status();
    } public void koneksi_database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            koneksi=DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            stat=koneksi.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        }
    private void tampil_status(){
     try {
         koneksi_database();
         
         String sql = "select * from tbl_petugas where status = 'AKTIF'";
         res = stat.executeQuery(sql);
         while (res.next()) {
             id_petugas_masuk.setText(res.getString("nama_depan"));
         }
     } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
     }
 }
     private void bersih(){         
        cb_cari.setSelectedIndex(0); 
        jenis_surat_masuk.setSelectedIndex(0); 
       tanggal_terima_masuk.setCalendar(null);
        pengirim_masuk.setText("");
        perihal_masuk.setText(""); 
        txtcari.setText("");
        
    }
    public void tampil_Surat_Masuk(){
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
    public void autonumber_masuk() {
        try {
            String sql = "Select right(no_agenda,2)as no_terakhir from tbl_surat_masuk";
            res=stat.executeQuery(sql);
            if(res.first() == false) {
                no_agenda_masuk.setText("AGD0001");
            } 
            else {
             res.last();
             int no = res.getInt(1) + 1;
             String cno = String.valueOf(no);
             int pjg_cno = cno.length();
             int i = 0;
             if (i < 2 - pjg_cno){
                 cno = "000" + cno;
                 no_agenda_masuk.setText("AGD" + cno);
             }
                else if (i < 3 - pjg_cno){
                cno = "00" + cno;
                 no_agenda_masuk.setText("AGD" + cno); 
                }
                else if (i < 4 - pjg_cno){
                cno = "0" + cno;
                 no_agenda_masuk.setText("AGD" + cno); 
                }
                 else if (i < 5 - pjg_cno){
                cno = "" + cno;                  
                } 
             no_agenda_masuk.setText("AGD" + cno);
            }
        } catch (Exception DBException){
            System.err.println("Error =" + DBException);
        }
    }
    public void autonumber_nosurat() {
         Date date = new Date();
     SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
     String tgl = format.format(date);
        try {
            String sql = "Select right(no_surat,2)as no_terakhir from tbl_surat_masuk";
            res=stat.executeQuery(sql);
            if(res.first() == false) {
                no_surat_masuk.setText("SRT-"+tgl+"-001");
            } 
            else {
             res.last();
             int no = res.getInt(1) + 1;
             String cno = String.valueOf(no);
             int pjg_cno = cno.length();
             int i = 0;
             if (i < 2 - pjg_cno){
                 cno = "-01" + cno;
                 no_surat_masuk.setText("SRT-"+tgl + cno);
             }
                else if (i < 3 - pjg_cno){
                cno = "-1" + cno;
                 no_surat_masuk.setText("SRT-"+tgl + cno); 
                }
                else if (i < 4 - pjg_cno){
                cno = "-" + cno;
                 no_surat_masuk.setText("SRT-"+tgl + cno); 
                }
             no_surat_masuk.setText("SRT-"+tgl + cno);
            }
        } catch (Exception DBException){
            System.err.println("Error =" + DBException);
        }
    }
private void simpan (){
        SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
     String tanggal_keluarr = dateFormat_1.format(tanggal_terima_masuk.getDate());
        if(  no_agenda_masuk.getText().length() == 0)
       {
           JOptionPane.showMessageDialog(rootPane, "Isi Data dengan lengkap!", "Dialog Peringatan",
           JOptionPane.WARNING_MESSAGE);
           
       } else {
        try {stat.executeUpdate("insert into tbl_surat_masuk values("
                 + ""+"'"+no_agenda_masuk.getText()+"',"
                 + ""+"'"+id_petugas_masuk.getText()+"',"
                 + ""+"'"+jenis_surat_masuk.getSelectedItem()+"',"
                 + ""+"'"+tanggal_keluarr+"',"
                 + ""+"'"+no_surat_masuk.getText()+"',"
                 + ""+"'"+pengirim_masuk.getText()+"',"
                 + ""+"'"+perihal_masuk.getText()+"')");
                 JOptionPane.showMessageDialog(null,
                    "Data berhasi di simpan"); }
         catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Keterangan EROR : " + e);
         } 
        } 
        tampil_Surat_Masuk(); 
        autonumber_masuk(); 
        bersih();
    }
  
    private void hapus(){
        if (JOptionPane.showConfirmDialog(this,
                " Apakah Anda Yakin Ingin Hapus ?","Konfirmasi",            
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)
            ==JOptionPane.YES_OPTION){
            
            try {
                stat.executeUpdate("delete from tbl_surat_masuk where "
                + "no_agenda ='"+no_agenda_masuk.getText()+"'");
                JOptionPane.showMessageDialog(null, 
                        "Barang Dengan Kode :\n"+no_agenda_masuk.getText()+
                        " Berhasil di Hapus");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
 
        }
       tampil_Surat_Masuk(); 
         autonumber_masuk(); autonumber_nosurat();  
        bersih();
            }
   
    private void combobox() {
        if (cb_cari.getSelectedIndex() == 1) {
            sql = "select * from tbl_surat_masuk "
                    + "where no_agenda like '%" + txtcari.getText() + "%' ORDER BY no_agenda";
        }else if (cb_cari.getSelectedIndex() == 2) {
            sql = "select * from tbl_surat_masuk "
                    + "where no_surat like '%" + txtcari.getText() + "%' ORDER BY no_agenda";
        }
        else if (cb_cari.getSelectedIndex() == 3) {
            sql = "select * from tbl_surat_masuk "
                    + "where pengirim like '%" + txtcari.getText() + "%' ORDER BY no_agenda";
        }
        else if (cb_cari.getSelectedIndex() == 4) {
            sql = "select * from tbl_surat_masuk "
                    + "where tanggal_terima like '%" + txtcari.getText() + "%' ORDER BY no_agenda";
        }
    }
    //*******************************************************CARI DATA*****************************************//
    private void caidata() {
      if (cb_cari.getSelectedIndex() ==0) {
          JOptionPane.showMessageDialog(null, "Jenis Pencarian Belum Dipilih!");
      }else{
          DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("No Agenda");
        tbl.addColumn("Id");
        tbl.addColumn("Jenis Surat");
        tbl.addColumn("Tanggal Terima");
        tbl.addColumn("No Surat");
        tbl.addColumn("Pengirim");
        tbl.addColumn("Perihal");          
          tabel_masuk.setModel(tbl);
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
                  tabel_masuk.setModel(tbl);
              }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(rootPane,"Ada Kesalahan!" + e);
              bersih();
          }
      }
     
    }
    private void edit(){
        SimpleDateFormat dateFormat_1 = new SimpleDateFormat("YYYY-MM-dd");
     String tanggal_keluarr = dateFormat_1.format(tanggal_terima_masuk.getDate());
        try {stat.executeUpdate("update tbl_surat_masuk set "
                    + "id='"+id_petugas_masuk.getText()+"',"
                    + "jenis_surat='"+jenis_surat_masuk.getSelectedItem()+"',"
                    + "tanggal_terima='"+tanggal_keluarr+"',"
                    + "no_surat='"+no_surat_masuk.getText()+"',"
                    + "pengirim='"+pengirim_masuk.getText()+"',"
                    + "perihal='"+perihal_masuk.getText()+"'"                    
                    + "where no_agenda ='"+no_agenda_masuk.getText()+"'");
            
            JOptionPane.showMessageDialog(null, "Supplier dengan kode : \n"
                    + " "+no_agenda_masuk.getText()+" Berhasil Diubah!!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }  
        
       tampil_Surat_Masuk(); 
       autonumber_masuk();
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

        jPanel17 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        no_agenda_masuk = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tanggal_terima_masuk = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        no_surat_masuk = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        pengirim_masuk = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        perihal_masuk = new javax.swing.JTextArea();
        jenis_surat_masuk = new javax.swing.JComboBox();
        jPanel20 = new javax.swing.JPanel();
        simpan_masuk = new javax.swing.JButton();
        edit_masuk = new javax.swing.JButton();
        hapus_masuk = new javax.swing.JButton();
        segar_masuk = new javax.swing.JButton();
        id_petugas_masuk = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        cb_cari = new javax.swing.JComboBox();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabel_masuk = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SURAT MASUK");

        jPanel17.setBackground(new java.awt.Color(0, 153, 204));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SURAT MASUK", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Handwriting", 0, 26))); // NOI18N
        jPanel17.setPreferredSize(new java.awt.Dimension(1355, 682));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tambah Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("No Agenda");

        no_agenda_masuk.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("ID Petugas");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Jenis Surat");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tanggal Terima");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("No Surat");

        no_surat_masuk.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Pengirim");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Perihal");

        perihal_masuk.setColumns(20);
        perihal_masuk.setRows(5);
        jScrollPane2.setViewportView(perihal_masuk);

        jenis_surat_masuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Jenis Surat **", "Biasa", "Resmi" }));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        simpan_masuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/44-32.png"))); // NOI18N
        simpan_masuk.setText("SIMPAN");
        simpan_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan_masukActionPerformed(evt);
            }
        });

        edit_masuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/18-32.png"))); // NOI18N
        edit_masuk.setText("EDIT");
        edit_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_masukActionPerformed(evt);
            }
        });

        hapus_masuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/107-32.png"))); // NOI18N
        hapus_masuk.setText("Hapus");
        hapus_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapus_masukActionPerformed(evt);
            }
        });

        segar_masuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/YPS__email_mail_refresh_update_sendreceive-32.png"))); // NOI18N
        segar_masuk.setText("segar");
        segar_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segar_masukActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(simpan_masuk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edit_masuk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hapus_masuk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(segar_masuk))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(segar_masuk)
                    .addComponent(hapus_masuk)
                    .addComponent(edit_masuk)
                    .addComponent(simpan_masuk)))
        );

        id_petugas_masuk.setEditable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jenis_surat_masuk, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(id_petugas_masuk))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(no_surat_masuk))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tanggal_terima_masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(pengirim_masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(no_agenda_masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(no_agenda_masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(id_petugas_masuk, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jenis_surat_masuk, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tanggal_terima_masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(no_surat_masuk)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pengirim_masuk)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pencarian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Cari");

        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        cb_cari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "** Cari Berdasarkan **", "No Agenda", "No Surat", "Pengirim", "Tanggal Terima(yyyy-mm-dd)" }));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcari)
                .addGap(121, 121, 121))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cb_cari)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tampil Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icon32/print.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Keterangan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Jika Ingin merubah atau menghapus, anda harus mengkilk dulu tabel yang ada.");

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Di Pojok kanan atas Anda bisa langsung mencetak surat.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 1396, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simpan_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan_masukActionPerformed
        simpan();   autonumber_nosurat();    // TODO add your handling code here:
    }//GEN-LAST:event_simpan_masukActionPerformed

    private void edit_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_masukActionPerformed
        edit();    autonumber_nosurat();      // TODO add your handling code here:
    }//GEN-LAST:event_edit_masukActionPerformed

    private void hapus_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapus_masukActionPerformed
        hapus();       // TODO add your handling code here:
    }//GEN-LAST:event_hapus_masukActionPerformed

    private void segar_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segar_masukActionPerformed
        tampil_Surat_Masuk();  autonumber_masuk(); autonumber_nosurat();     bersih();  tampil_status();      // TODO add your handling code here:
    }//GEN-LAST:event_segar_masukActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        combobox();
        caidata();        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void tabel_masukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_masukMouseClicked
 
        try {
            int row = tabel_masuk.rowAtPoint(evt.getPoint());
            String a = tabel_masuk.getValueAt(row, 0).toString();
            String b = tabel_masuk.getValueAt(row, 1).toString();
            String c = tabel_masuk.getValueAt(row, 2).toString();
            String f = tabel_masuk.getValueAt(row, 4).toString();
            String g = tabel_masuk.getValueAt(row, 5).toString();
            String h = tabel_masuk.getValueAt(row, 6).toString();

            no_agenda_masuk.setText(String.valueOf(a));
            id_petugas_masuk.setText(String.valueOf(b));
            jenis_surat_masuk.setSelectedItem(c);
            no_surat_masuk.setText(String.valueOf(f));
            pengirim_masuk.setText(String.valueOf(g));
            perihal_masuk.setText(String.valueOf(h));
 

        }   catch (Exception e) {
            JOptionPane.showMessageDialog(null, stat);
        }
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_masukMouseClicked
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
    private void tabel_masukMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_masukMousePressed
        if(evt.getClickCount() == 1){ 
            tanggal_terima_masuk.setDate(getTanggalFromTable(tabel_masuk, 3));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tabel_masukMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
try {
            String NamaFile = "./src/laporan/Surat_Masukid.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_pengarsipan","root","");
            HashMap hash = new HashMap(2);
            hash.put("nosurat",no_surat_masuk.getText());
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, hash, koneksi);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception ex) {
            System.out.println(ex);
        }    // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(Form_Surat_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Surat_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Surat_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Surat_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                  Form_Surat_Masuk mua = new Form_Surat_Masuk();
                mua.setExtendedState(MAXIMIZED_BOTH);
                mua.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cb_cari;
    private javax.swing.JButton edit_masuk;
    private javax.swing.JButton hapus_masuk;
    private javax.swing.JTextField id_petugas_masuk;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JComboBox jenis_surat_masuk;
    private javax.swing.JTextField no_agenda_masuk;
    private javax.swing.JTextField no_surat_masuk;
    private javax.swing.JTextField pengirim_masuk;
    private javax.swing.JTextArea perihal_masuk;
    private javax.swing.JButton segar_masuk;
    private javax.swing.JButton simpan_masuk;
    private javax.swing.JTable tabel_masuk;
    private com.toedter.calendar.JDateChooser tanggal_terima_masuk;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables

   
}
