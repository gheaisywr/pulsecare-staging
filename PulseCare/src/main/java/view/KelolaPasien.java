/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import dao.PasienDAO;
import model.Pasien;
import model.Akun;
//import view.DaftarkanKunjungan;
//import view.RiwayatKunjungan;
/**
 *
 * @author Lenovo
 */
public class KelolaPasien extends javax.swing.JPanel {
    
    
    private String nipPetugas;
    
    private void aturTombolSidebar(javax.swing.JButton tombol, boolean aktif) {
        tombol.setBackground(
            aktif
                ? java.awt.Color.WHITE
                : new java.awt.Color(194, 234, 223)
        );


        tombol.setForeground(new java.awt.Color(27, 106, 141));
        tombol.setOpaque(true);
        tombol.setContentAreaFilled(true);
        tombol.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(5, 12, 5, 12)
        );
        tombol.setFocusPainted(false);
        tombol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tombol.setIconTextGap(10);
        tombol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    /**
     * Creates new form DashboardPetugas
     */
    public KelolaPasien() {
        initComponents();

         // Membatasi lebar label informasi pasien terpilih
        terpilih.setPreferredSize(
            new java.awt.Dimension(220, terpilih.getPreferredSize().height)
        );

        terpilih.setMinimumSize(
            new java.awt.Dimension(220, terpilih.getPreferredSize().height)
        );

        terpilih.setMaximumSize(
            new java.awt.Dimension(220, terpilih.getPreferredSize().height)
        );
        // Mengaktifkan scroll horizontal khusus tabel pasien
        jTable1.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_OFF
        );

        // Mengatur scrollbar pada JScrollPane yang benar
        jScrollPane2.setHorizontalScrollBarPolicy(
            javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        jScrollPane2.setVerticalScrollBarPolicy(
            javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        
        cariPasien.setText("Cari nama atau NIK");
        cariPasien.setForeground(java.awt.Color.GRAY);

        cariPasien.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (cariPasien.getText().equals("Cari nama atau NIK")) {
                    cariPasien.setText("");
                    cariPasien.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (cariPasien.getText().trim().isEmpty()) {
                    cariPasien.setText("Cari nama atau NIK");
                    cariPasien.setForeground(java.awt.Color.GRAY);
                }
            }
        });
        
                    
        aturTampilanTabel();
        muatDataPasien();
        aturLebarKolom();
        
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int baris = jTable1.getSelectedRow();

                if (baris != -1) {
                    String nama = jTable1.getValueAt(baris, 1).toString();
                    terpilih.setText("Terpilih: " + nama);
                } else {
                    terpilih.setText("Terpilih");
                }
            }
        });


// Mengatur warna nama petugas
    namaPengguna.setForeground(java.awt.Color.WHITE);
    usernamePengguna.setForeground(java.awt.Color.WHITE);

     
    }
    
    public void setInfoPetugas(String nip, String nama) {
        this.nipPetugas = nip;  

        namaPengguna.setText(nama);
        usernamePengguna.setText(nip);

        namaPengguna.setForeground(java.awt.Color.WHITE);
        usernamePengguna.setForeground(java.awt.Color.WHITE);
    }
    
    private void bukaHalaman(String namaHalaman) {

        MainFrame frame = (MainFrame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        if (frame == null) {
            return;
        }

        // Pastikan session petugas masih tersedia
        Akun akun = frame.getAkunPetugasAktif();

        if (akun == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Session petugas tidak ditemukan. Silakan login kembali!"
            );
            frame.tampil("login");
            return;
        }

        // Pertahankan informasi petugas
        setInfoPetugas(akun.getNip(), akun.getNama());

        frame.tampil(namaHalaman);
    }
    
    private void muatDataPasien() {

        PasienDAO dao = new PasienDAO();

        String[] kolom = {
            "ID",
            "Nama Lengkap",
            "NIK",
            "Tgl Lahir",
            "Jenis Kelamin",
            "BPJS"
        };

        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            List<Pasien> daftar = dao.semua();

            for (Pasien pasien : daftar) {
                model.addRow(new Object[]{
                    pasien.getIdPasien(),
                    pasien.getNamaLengkap(),
                    pasien.getNik(),
                    pasien.getTanggalLahir(),
                    pasien.getJenisKelamin(),
                    pasien.getStatusBpjs()
                });
            }

            jTable1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal memuat data pasien: " + e.getMessage(),
                "Kesalahan Database",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void aturLebarKolom() {

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(130);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
    }
    private void aturTampilanTabel() {

        // Warna dasar tabel
        jTable1.setBackground(java.awt.Color.WHITE);
        jTable1.setForeground(new java.awt.Color(51, 65, 85));

        // Font dan tinggi baris
        jTable1.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
        );
        jTable1.setRowHeight(38);

        // Garis pemisah baris
        jTable1.setShowGrid(true);
        jTable1.setGridColor(new java.awt.Color(226, 238, 235));

        // Header tabel
        javax.swing.table.JTableHeader header =
            jTable1.getTableHeader();

        header.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
        );
        header.setOpaque(true);
        header.setDefaultRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {

                @Override
                public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
                ) {
                    javax.swing.JLabel label =
                        (javax.swing.JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column
                        );

                    label.setBackground(new java.awt.Color(220, 247, 239));
                    label.setForeground(new java.awt.Color(27, 106, 141));
                    label.setFont(
                        new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
                    );
                    label.setHorizontalAlignment(
                        javax.swing.SwingConstants.LEFT
                    );
                    label.setBorder(
                        javax.swing.BorderFactory.createEmptyBorder(0, 12, 0, 5)
                    );
                    label.setOpaque(true);

                    return label;
                }
            }
        );
        header.setPreferredSize(new java.awt.Dimension(0, 40));
        header.setReorderingAllowed(false);

        // Warna saat baris dipilih
        jTable1.setSelectionBackground(
            new java.awt.Color(220, 240, 235)
        );
        jTable1.setSelectionForeground(
            new java.awt.Color(27, 106, 141)
        );

        // Pengaturan JScrollPane
        jScrollPane2.setBorder(
            javax.swing.BorderFactory.createEmptyBorder()
        );
        jScrollPane2.getViewport().setBackground(
            java.awt.Color.WHITE
        );

        // Tinggi header dan isi tabel
        jTable1.setFillsViewportHeight(true);
    }

    
    private void aktifkanMenu(javax.swing.JButton menuAktif) {

        aturTombolSidebar(menuAktif, true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BtnDashboard = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        BtnPasien = new javax.swing.JButton();
        BtnDaftar = new javax.swing.JButton();
        BtnRiwayat = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        namaPengguna = new javax.swing.JLabel();
        usernamePengguna = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        cariPasien = new javax.swing.JTextField();
        tombolCari = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        terpilih = new javax.swing.JLabel();
        btnHapus = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();

        jScrollPane1.setViewportView(jTextPane1);

        setMaximumSize(new java.awt.Dimension(1000, 650));
        setPreferredSize(new java.awt.Dimension(806, 550));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(11, 59, 68));
        jPanel10.setAutoscrolls(true);
        jPanel10.setPreferredSize(new java.awt.Dimension(200, 0));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo PulseCare sedang tanpa tulisan.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PulseCare");

        BtnDashboard.setBackground(new java.awt.Color(20, 96, 108));
        BtnDashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        BtnDashboard.setText("Dashboard");
        BtnDashboard.setBorder(null);
        BtnDashboard.addActionListener(this::BtnDashboardActionPerformed);

        jPanel9.setBackground(new java.awt.Color(20, 96, 108));
        jPanel9.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        BtnPasien.setBackground(new java.awt.Color(186, 234, 242));
        BtnPasien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnPasien.setForeground(new java.awt.Color(20, 96, 108));
        BtnPasien.setText("Data Pasien");
        BtnPasien.setBorder(null);
        BtnPasien.addActionListener(this::BtnPasienActionPerformed);

        BtnDaftar.setBackground(new java.awt.Color(20, 96, 108));
        BtnDaftar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnDaftar.setForeground(new java.awt.Color(255, 255, 255));
        BtnDaftar.setText("Daftarkan Kunjungan");
        BtnDaftar.setBorder(null);
        BtnDaftar.addActionListener(this::BtnDaftarActionPerformed);

        BtnRiwayat.setBackground(new java.awt.Color(20, 96, 108));
        BtnRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnRiwayat.setForeground(new java.awt.Color(255, 255, 255));
        BtnRiwayat.setText("Riwayat Kunjungan");
        BtnRiwayat.setBorder(null);
        BtnRiwayat.addActionListener(this::BtnRiwayatActionPerformed);

        jButton6.setBackground(new java.awt.Color(229, 57, 53));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Keluar");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton6.addActionListener(this::jButton6ActionPerformed);

        namaPengguna.setBackground(new java.awt.Color(255, 255, 255));
        namaPengguna.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        namaPengguna.setForeground(new java.awt.Color(255, 255, 255));
        namaPengguna.setText("Nama Lengkap");

        usernamePengguna.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        usernamePengguna.setForeground(new java.awt.Color(255, 255, 255));
        usernamePengguna.setText("Username");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pulsecare_profile_icon_50.png"))); // NOI18N
        jLabel13.setText("jLabel10");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(BtnDaftar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel11))
                            .addComponent(jLabel6))
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(namaPengguna))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(usernamePengguna)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(BtnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnDaftar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaPengguna)
                .addGap(4, 4, 4)
                .addComponent(usernamePengguna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 549));

        jPanel3.setBackground(new java.awt.Color(244, 247, 248));

        jLabel4.setBackground(new java.awt.Color(20, 96, 108));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(20, 96, 108));
        jLabel4.setText("Kelola Data Pasien");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 96, 108)));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", ""
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(20, 96, 108));
        jLabel10.setText("Cari, tambah, ubah, dan hapus data pasien");

        cariPasien.setForeground(new java.awt.Color(102, 102, 102));
        cariPasien.setText(" Cari nama atau NIK");
        cariPasien.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(20, 96, 108), 1, true));
        cariPasien.addActionListener(this::cariPasienActionPerformed);

        tombolCari.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tombolCari.setForeground(new java.awt.Color(20, 96, 108));
        tombolCari.setText("Cari");
        tombolCari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 96, 108)));
        tombolCari.addActionListener(this::tombolCariActionPerformed);

        jButton1.setBackground(new java.awt.Color(20, 96, 108));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tambah Pasien");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        terpilih.setForeground(new java.awt.Color(20, 96, 108));
        terpilih.setText("Baris terpilih: ID & Nama lengkap");

        btnHapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(204, 0, 0));
        btnHapus.setText("Hapus");
        btnHapus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51), 2));
        btnHapus.addActionListener(this::btnHapusActionPerformed);

        btnUbah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUbah.setForeground(new java.awt.Color(20, 96, 108));
        btnUbah.setText("Edit");
        btnUbah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 96, 108), 2));
        btnUbah.addActionListener(this::btnUbahActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel4)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(terpilih, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(cariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tombolCari, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(123, 123, 123)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tombolCari, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(terpilih))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 640, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void cariPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariPasienActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int baris = jTable1.getSelectedRow();

        // Memastikan ada pasien yang dipilih
        if (baris == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Silakan pilih pasien yang ingin dihapus!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Mengambil ID dan nama pasien dari tabel
        String idPasien = jTable1.getValueAt(baris, 0).toString();
        String namaPasien = jTable1.getValueAt(baris, 1).toString();

        // Konfirmasi penghapusan
        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus pasien:\n" + namaPasien + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                PasienDAO dao = new PasienDAO();

                boolean berhasil = dao.hapus(idPasien);

                if (berhasil) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Data pasien berhasil dihapus!",
                        "Berhasil",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    // Memuat ulang tabel
                    muatDataPasien();

                    // Mengembalikan label pilihan
                    terpilih.setText("Terpilih");

                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Data pasien tidak ditemukan atau gagal dihapus.",
                        "Gagal",
                        JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Gagal menghapus data pasien: " + e.getMessage(),
                    "Kesalahan Database",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tombolCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolCariActionPerformed
        String keyword = cariPasien.getText().trim();

       // Jika form kosong atau masih berisi placeholder
       if (keyword.isEmpty() || keyword.equals("Cari nama atau NIK")) {
           muatDataPasien();
           return;
       }

       PasienDAO dao = new PasienDAO();

       String[] kolom = {
           "ID", "Nama Lengkap", "NIK",
           "Tgl Lahir", "Jenis Kelamin", "BPJS"
       };

       DefaultTableModel model = new DefaultTableModel(kolom, 0) {
           @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
       };

       try {
           List<Pasien> daftar = dao.cari(keyword);

           for (Pasien pasien : daftar) {
               model.addRow(new Object[]{
                   pasien.getIdPasien(),
                   pasien.getNamaLengkap(),
                   pasien.getNik(),
                   pasien.getTanggalLahir(),
                   pasien.getJenisKelamin(),
                   pasien.getStatusBpjs()
               });
           }

           jTable1.setModel(model);
           aturLebarKolom();

           if (daftar.isEmpty()) {
               JOptionPane.showMessageDialog(
                   this,
                   "Data pasien tidak ditemukan.",
                   "Informasi",
                   JOptionPane.INFORMATION_MESSAGE
               );
           }

       } catch (SQLException e) {
           JOptionPane.showMessageDialog(
               this,
               "Gagal mencari pasien: " + e.getMessage(),
               "Kesalahan Database",
               JOptionPane.ERROR_MESSAGE
           );
       }
    }//GEN-LAST:event_tombolCariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FormPasien form = new FormPasien(null, true);

        form.setLocationRelativeTo(this);
        form.setVisible(true);

        muatDataPasien();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
           int baris = jTable1.getSelectedRow();

        // Memastikan pasien sudah dipilih
        if (baris == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Silakan pilih pasien yang ingin diubah!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Mengambil data pasien dari tabel
        String id = jTable1.getValueAt(baris, 0).toString();
        String nama = jTable1.getValueAt(baris, 1).toString();
        String nik = jTable1.getValueAt(baris, 2).toString();

        java.time.LocalDate tanggal =
            (java.time.LocalDate) jTable1.getValueAt(baris, 3);

        String jk = jTable1.getValueAt(baris, 4).toString();
        String bpjs = jTable1.getValueAt(baris, 5).toString();

        Pasien pasien = new Pasien(
            id, nama, nik, tanggal, jk, bpjs
        );

        // Membuka popup Edit
        java.awt.Frame parent =
            (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);

        FormPasien form = new FormPasien(parent, true, pasien);

        form.setVisible(true);

        // Memuat ulang tabel setelah popup ditutup
        muatDataPasien();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void BtnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDashboardActionPerformed
        bukaHalaman("dashboardPetugas");
    }//GEN-LAST:event_BtnDashboardActionPerformed

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
        bukaHalaman("kelolaPasien");
    }//GEN-LAST:event_BtnPasienActionPerformed

    private void BtnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDaftarActionPerformed
    
    MainFrame frame = (MainFrame)
        javax.swing.SwingUtilities.getWindowAncestor(this);

        if (frame != null) {
            Akun akun = frame.getAkunPetugasAktif();

            if (akun == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Session petugas tidak ditemukan!"
                );
                return;
            }

            frame.tampilkanDaftarkanKunjungan();
        }
    }//GEN-LAST:event_BtnDaftarActionPerformed

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
        MainFrame frame = (MainFrame)
            javax.swing.SwingUtilities.getWindowAncestor(this);

        if (frame != null) {
            Akun akun = frame.getAkunPetugasAktif();

            if (akun == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Session petugas tidak ditemukan!"
                );
                return;
            }

            setInfoPetugas(akun.getNip(), akun.getNama());

            frame.tampilkanRiwayatKunjungan("dashboardPetugas");
        }
    }//GEN-LAST:event_BtnRiwayatActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin keluar dari aplikasi?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            MainFrame frame = (MainFrame) javax.swing.SwingUtilities
            .getWindowAncestor(this);

            frame.tampil("login");
        }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnDaftar;
    private javax.swing.JButton BtnDashboard;
    private javax.swing.JButton BtnPasien;
    private javax.swing.JButton BtnRiwayat;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnUbah;
    private javax.swing.JTextField cariPasien;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel namaPengguna;
    private javax.swing.JLabel terpilih;
    private javax.swing.JButton tombolCari;
    private javax.swing.JLabel usernamePengguna;
    // End of variables declaration//GEN-END:variables
}
