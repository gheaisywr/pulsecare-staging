/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import controller.RiwayatKunjunganController;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class RiwayatKunjungan extends javax.swing.JPanel {
    
    private final RiwayatKunjunganController controller =
        new RiwayatKunjunganController();

    private String idKunjunganTerpilih = null;
    
    private String halamanDashboardAsal = "dashboardPetugas";

    public void setHalamanDashboardAsal(String halaman) {
        halamanDashboardAsal = halaman;
    }

    /**
     * Creates new form RiwayatKunjungan
     */
    public RiwayatKunjungan() {
        initComponents();
        aturTabel();
        aturAksiTombol();
        muatDataRiwayat();
    }
    
    private void aturTabel() {

        jTable2.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );

        jTable2.setRowSelectionAllowed(true);
        jTable2.setColumnSelectionAllowed(false);

        jTable2.setDefaultEditor(Object.class, null);

        BtnUpdate.setEnabled(false);
        BtnHapus.setEnabled(false);

        jTable2.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int baris = jTable2.getSelectedRow();

                if (baris >= 0) {
                    int modelRow = jTable2.convertRowIndexToModel(baris);

                    idKunjunganTerpilih = jTable2
                            .getModel()
                            .getValueAt(modelRow, 0).toString();

                    String namaPasien = jTable2
                            .getModel()
                            .getValueAt(modelRow, 2).toString();

                    lblKunjunganTerpilih.setText(
                        idKunjunganTerpilih + " - " + namaPasien
                    );

                    BtnUpdate.setEnabled(true);
                    BtnHapus.setEnabled(true);

                } else {
                    resetPilihan();
                }
            }
        });
    }
    
    private void bukaHalaman(String namaHalaman) {

        MainFrame frame = (MainFrame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        if (frame != null) {
            frame.tampil(namaHalaman);
        }
    }

    private void resetPilihan() {
        idKunjunganTerpilih = null;
        lblKunjunganTerpilih.setText(
            "Belum ada kunjungan dipilih"
        );

        BtnUpdate.setEnabled(false);
        BtnHapus.setEnabled(false);
    }
    
    private void muatDataRiwayat() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{
                "ID Kunjungan",
                "ID Pasien",
                "Nama Pasien",
                "Poliklinik",
                "Tanggal",
                "No. Antrean",
                "Status"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            RiwayatKunjunganController controller =
                    new RiwayatKunjunganController();

            List<Map<String, Object>> daftar = controller.semua();

            for (Map<String, Object> data : daftar) {
                model.addRow(new Object[]{
                    data.get("id_kunjungan"),
                    data.get("id_pasien"),
                    data.get("nama_lengkap"),
                    data.get("nama_poli"),
                    data.get("tanggal_pendaftaran"),
                    data.get("nomor_antrian"),
                    data.get("status_kunjungan")
                });
            }

            jTable2.setModel(model);
            resetPilihan();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal memuat riwayat kunjungan: " + e.getMessage()
            );
        }
    }
    
    private void aturAksiTombol() {

      BtnUpdate.addActionListener(e -> editKunjungan());

      BtnHapus.addActionListener(e -> hapusKunjungan());
  }

    private void editKunjungan() {

        if (idKunjunganTerpilih == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Pilih kunjungan yang ingin diedit!"
            );
            return;
        }

        String sql = """
            SELECT
                nip_petugas,
                id_pasien,
                id_poli,
                tanggal_pendaftaran,
                status_kunjungan
            FROM riwayat_kunjungan
            WHERE id_kunjungan = ?
        """;

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idKunjunganTerpilih);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String nipPetugas = rs.getString("nip_petugas");
                    String idPasien = rs.getString("id_pasien");
                    String idPoli = rs.getString("id_poli");

                    LocalDate tanggal = rs.getDate(
                            "tanggal_pendaftaran"
                    ).toLocalDate();

                    String status = rs.getString("status_kunjungan");

                    EditRiwayat popup = new EditRiwayat(
                            (java.awt.Frame) javax.swing.SwingUtilities
                                    .getWindowAncestor(this),
                            true,
                            idKunjunganTerpilih,
                            nipPetugas,
                            idPasien,
                            idPoli,
                            tanggal,
                            status
                    );

                    popup.setVisible(true);

                    // Muat ulang tabel setelah popup ditutup
                    muatDataRiwayat();

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Data kunjungan tidak ditemukan."
                    );
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal membuka data kunjungan: " + e.getMessage(),
                    "Kesalahan",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void hapusKunjungan() {

        if (idKunjunganTerpilih == null) {
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus kunjungan "
            + idKunjunganTerpilih + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean berhasil = controller.hapus(idKunjunganTerpilih);

            if (berhasil) {
                JOptionPane.showMessageDialog(
                    this,
                    "Data kunjungan berhasil dihapus."
                );
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Kunjungan tidak dapat dihapus karena sudah memiliki hasil pemeriksaan."
                );
            }

            muatDataRiwayat();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal menghapus kunjungan: " + e.getMessage(),
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void setInfoPengguna(String nama, String nip) {
        namaPengguna.setText(nama);
        usernamePengguna.setText(nip);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblKunjunganTerpilih = new javax.swing.JLabel();
        BtnHapus = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        BtnUpdate = new javax.swing.JButton();

        jToolBar1.setForeground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);

        jPanel2.setBackground(new java.awt.Color(244, 247, 248));
        jPanel2.setEnabled(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(806, 550));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(22, 63, 76));
        jLabel2.setText("Riwayat Kunjungan");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(100, 127, 140));
        jLabel3.setText("Data kunjungan pasien di Poliklinik anda");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        jPanel6.setBackground(new java.awt.Color(11, 59, 68));

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

        BtnPasien.setBackground(new java.awt.Color(20, 96, 108));
        BtnPasien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnPasien.setForeground(new java.awt.Color(255, 255, 255));
        BtnPasien.setText("Data Pasien");
        BtnPasien.setBorder(null);
        BtnPasien.addActionListener(this::BtnPasienActionPerformed);

        BtnDaftar.setBackground(new java.awt.Color(20, 96, 108));
        BtnDaftar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnDaftar.setForeground(new java.awt.Color(255, 255, 255));
        BtnDaftar.setText("Daftarkan Kunjungan");
        BtnDaftar.setBorder(null);
        BtnDaftar.addActionListener(this::BtnDaftarActionPerformed);

        BtnRiwayat.setBackground(new java.awt.Color(186, 234, 242));
        BtnRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnRiwayat.setForeground(new java.awt.Color(20, 96, 108));
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

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pulsecare_profile_icon_50.png"))); // NOI18N
        jLabel10.setText("jLabel10");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(BtnDaftar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnPasien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(BtnRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel11))
                            .addComponent(jLabel6)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(namaPengguna))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(usernamePengguna)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
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
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaPengguna)
                .addGap(4, 4, 4)
                .addComponent(usernamePengguna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 550));

        jPanel7.setBackground(new java.awt.Color(20, 96, 108));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", "", null, null, null},
                {"", "", "", "", null, null, null},
                {"", "", "", "", null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 550, 260));

        jTextField1.setText("30-10-2026");
        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 110, 30));

        jTextField2.setText("Cari nama atau ID");
        jTextField2.addActionListener(this::jTextField2ActionPerformed);
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 180, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menunggu ", "Diperiksa", "Selesai" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 120, 30));

        jButton2.setBackground(new java.awt.Color(20, 96, 108));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Terapkan");
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 110, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 96, 108)));
        jPanel3.setToolTipText("");

        lblKunjunganTerpilih.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKunjunganTerpilih.setForeground(new java.awt.Color(22, 63, 76));
        lblKunjunganTerpilih.setText("Belum ada kunjungan dipilih");

        BtnHapus.setBackground(new java.awt.Color(255, 0, 0));
        BtnHapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnHapus.setForeground(new java.awt.Color(255, 255, 255));
        BtnHapus.setText("Hapus Data");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(100, 127, 140));
        jLabel25.setText("Kunjungan Terpilih");

        BtnUpdate.setBackground(new java.awt.Color(20, 96, 108));
        BtnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BtnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        BtnUpdate.setText("Edit Data");
        BtnUpdate.addActionListener(this::BtnUpdateActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKunjunganTerpilih, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(BtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(BtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblKunjunganTerpilih)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 550, 70));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 806, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        editKunjungan();
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void BtnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDashboardActionPerformed
        bukaHalaman("dashboardPetugas");
    }//GEN-LAST:event_BtnDashboardActionPerformed

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
        bukaHalaman("kelolaPasien");
    }//GEN-LAST:event_BtnPasienActionPerformed

    private void BtnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDaftarActionPerformed
        MainFrame frame = (MainFrame)
        javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.tampilkanDaftarkanKunjungan();
    }//GEN-LAST:event_BtnDaftarActionPerformed

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
        bukaHalaman("riwayatKunjungan");
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
    private javax.swing.JButton BtnHapus;
    private javax.swing.JButton BtnPasien;
    private javax.swing.JButton BtnRiwayat;
    private javax.swing.JButton BtnUpdate;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblKunjunganTerpilih;
    private javax.swing.JLabel namaPengguna;
    private javax.swing.JLabel usernamePengguna;
    // End of variables declaration//GEN-END:variables

    public void setInfoPetugas(String nipPetugas, String namaPetugas) {
        namaPengguna.setText(namaPetugas);
        usernamePengguna.setText(nipPetugas);
    }
}
