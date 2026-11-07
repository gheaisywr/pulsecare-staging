/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import controller.KunjunganController;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class PerbaruiStatus extends javax.swing.JPanel {
private final KunjunganController kunjunganController = new KunjunganController();
private String halamanAsal = "dashboardPetugas";
private String idKunjunganTerpilih = null;
    /**
     * Creates new form PerbaruiStatus
     */
    public PerbaruiStatus() {
        initComponents();
        
        jComboBox1.setModel(
            new javax.swing.DefaultComboBoxModel<>(
                new String[]{
                    "Menunggu",
                    "Diperiksa",
                    "Selesai",
                    "Batal"
                }
            )
        );

        aturTabel();
        muatTabelKunjunganHariIni();

        jTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pilihKunjungan();
            }
        });

        jButton2.addActionListener(e -> simpanStatus());
    }
    
    public void muatDataHariIni() {
    idKunjunganTerpilih = null;
        lblKunjunganTerpilih.setText(
            "Belum ada kunjungan dipilih"
        );

        muatTabelKunjunganHariIni();
    }
    
    public void setInfoPengguna(String nama, String nip) {
        namaAdmin.setText(nama);
        usernameAdmin.setText(nip);
    }
    
    private void aturTabel() {

        DefaultTableModel model = new DefaultTableModel(
            new String[]{
                "ID Kunjungan",
                "Antrean",
                "Pasien",
                "Poliklinik",
                "Status Saat Ini"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable2.setModel(model);
        jTable2.setRowHeight(35);
        jTable2.setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_SELECTION
        );
    }
    private void muatTabelKunjunganHariIni() {

        DefaultTableModel model =
            (DefaultTableModel) jTable2.getModel();

        model.setRowCount(0);

        try {
            List<Object[]> daftar =
                kunjunganController.daftarPerbaruiStatusHariIni();

            for (Object[] data : daftar) {

                String status = data[4].toString();

                status = status.substring(0, 1).toUpperCase()
                        + status.substring(1).toLowerCase();

                model.addRow(new Object[]{
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    status
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal memuat data kunjungan: " + e.getMessage()
            );
        }
    }
    private void pilihKunjungan() {

        int baris = jTable2.getSelectedRow();

        if (baris == -1) {
            idKunjunganTerpilih = null;
            lblKunjunganTerpilih.setText("Belum ada kunjungan dipilih");
            return;
        }

        int modelRow = jTable2.convertRowIndexToModel(baris);

        DefaultTableModel model =
                (DefaultTableModel) jTable2.getModel();

        idKunjunganTerpilih =
                model.getValueAt(modelRow, 0).toString();

        String antrean =
                model.getValueAt(modelRow, 1).toString();

        String nama =
                model.getValueAt(modelRow, 2).toString();

        lblKunjunganTerpilih.setText(
                idKunjunganTerpilih + " - " + nama
                + " (antrean " + antrean + ")"
        );

        jComboBox1.setSelectedItem(
                model.getValueAt(modelRow, 4).toString()
        );
    }
    private void simpanStatus() {

        if (idKunjunganTerpilih == null) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih kunjungan terlebih dahulu!"
            );
            return;
        }

        String status = jComboBox1.getSelectedItem().toString();

        try {
            boolean berhasil =
                kunjunganController.ubahStatus(
                    idKunjunganTerpilih,
                    status
                );

            if (berhasil) {
                JOptionPane.showMessageDialog(
                    this,
                    "Status kunjungan berhasil diperbarui!"
                );

                idKunjunganTerpilih = null;
                lblKunjunganTerpilih.setText("Belum ada kunjungan dipilih");

                muatTabelKunjunganHariIni();

            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Status kunjungan gagal diperbarui!"
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Terjadi kesalahan: " + e.getMessage()
            );
        }
    }
    public void setHalamanAsal(String halaman) {
        this.halamanAsal = halaman;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnDashboardDokter = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        btnAntreanDokter = new javax.swing.JButton();
        btnRiwayatKunjungan = new javax.swing.JButton();
        btnPerbaruiStatus = new javax.swing.JButton();
        namaAdmin = new javax.swing.JLabel();
        usernameAdmin = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        lblKunjunganTerpilih = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setPreferredSize(new java.awt.Dimension(806, 550));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(244, 247, 248));
        jPanel3.setEnabled(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(806, 550));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(22, 63, 76));
        jLabel2.setText("Perbarui Status");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(100, 127, 140));
        jLabel3.setText("Pilih kunjungan hari ini lalu ubah statusnya");

        jPanel6.setBackground(new java.awt.Color(11, 59, 68));
        jPanel6.setPreferredSize(new java.awt.Dimension(176, 550));

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Keluar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PulseCare");

        btnDashboardDokter.setBackground(new java.awt.Color(20, 96, 108));
        btnDashboardDokter.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDashboardDokter.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboardDokter.setText("Dashboard");
        btnDashboardDokter.setBorder(null);
        btnDashboardDokter.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDashboardDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardDokterMouseClicked(evt);
            }
        });
        btnDashboardDokter.addActionListener(this::btnDashboardDokterActionPerformed);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo PulseCare sedang tanpa tulisan.png"))); // NOI18N

        btnAntreanDokter.setBackground(new java.awt.Color(20, 96, 108));
        btnAntreanDokter.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAntreanDokter.setForeground(new java.awt.Color(255, 255, 255));
        btnAntreanDokter.setText("Antrean Dokter");
        btnAntreanDokter.setBorder(null);
        btnAntreanDokter.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAntreanDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAntreanDokterMouseClicked(evt);
            }
        });
        btnAntreanDokter.addActionListener(this::btnAntreanDokterActionPerformed);

        btnRiwayatKunjungan.setBackground(new java.awt.Color(20, 96, 108));
        btnRiwayatKunjungan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRiwayatKunjungan.setForeground(new java.awt.Color(255, 255, 255));
        btnRiwayatKunjungan.setText("Riwayat Kunjungan");
        btnRiwayatKunjungan.setBorder(null);
        btnRiwayatKunjungan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRiwayatKunjungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRiwayatKunjunganMouseClicked(evt);
            }
        });
        btnRiwayatKunjungan.addActionListener(this::btnRiwayatKunjunganActionPerformed);

        btnPerbaruiStatus.setBackground(new java.awt.Color(186, 234, 242));
        btnPerbaruiStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPerbaruiStatus.setForeground(new java.awt.Color(20, 96, 108));
        btnPerbaruiStatus.setText("Perbarui Status");
        btnPerbaruiStatus.setBorder(null);
        btnPerbaruiStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPerbaruiStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPerbaruiStatusMouseClicked(evt);
            }
        });
        btnPerbaruiStatus.addActionListener(this::btnPerbaruiStatusActionPerformed);

        namaAdmin.setBackground(new java.awt.Color(255, 255, 255));
        namaAdmin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        namaAdmin.setForeground(new java.awt.Color(255, 255, 255));
        namaAdmin.setText("Nama Lengkap");

        usernameAdmin.setText("Username");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pulsecare_profile_icon_50.png"))); // NOI18N
        jLabel10.setText("jLabel10");

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel9))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnDashboardDokter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAntreanDokter, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnRiwayatKunjungan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                .addComponent(btnPerbaruiStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addGap(15, 15, 15)
                                            .addComponent(usernameAdmin))
                                        .addComponent(namaAdmin))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(22, 22, 22)))
                        .addGap(27, 27, 27))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btnDashboardDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAntreanDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPerbaruiStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(btnRiwayatKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaAdmin)
                .addGap(4, 4, 4)
                .addComponent(usernameAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(20, 96, 108));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", "", null},
                {"", "", "", "", null},
                {"", "", "", "", null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(20, 96, 108), 1, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblKunjunganTerpilih.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKunjunganTerpilih.setForeground(new java.awt.Color(22, 63, 76));
        lblKunjunganTerpilih.setText("Belum ada kunjungan dipilih");
        jPanel4.add(lblKunjunganTerpilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 290, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(100, 127, 140));
        jLabel24.setText("Status");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 160, 20));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menunggu", "Diperiksa", "Selesai" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);
        jPanel4.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 130, 30));

        jButton2.setBackground(new java.awt.Color(20, 96, 108));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Simpan Status");
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 120, 30));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(100, 127, 140));
        jLabel25.setText("Kunjungan Terpilih");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 160, 20));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 816, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnPerbaruiStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerbaruiStatusActionPerformed
    MainFrame frame = (MainFrame)
            javax.swing.SwingUtilities.getWindowAncestor(this);

    frame.tampil("perbaruiStatus");
    }//GEN-LAST:event_btnPerbaruiStatusActionPerformed

    private void btnPerbaruiStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerbaruiStatusMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPerbaruiStatusMouseClicked

    private void btnRiwayatKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatKunjunganActionPerformed
    MainFrame frame = (MainFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

    if (frame != null) {
        frame.tampil("riwayatKunjunganDokter");
    }
    }//GEN-LAST:event_btnRiwayatKunjunganActionPerformed

    private void btnRiwayatKunjunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatKunjunganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRiwayatKunjunganMouseClicked

    private void btnAntreanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntreanDokterActionPerformed
        MainFrame frame = (MainFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        frame.tampil("antreanDokter");
    }//GEN-LAST:event_btnAntreanDokterActionPerformed

    private void btnAntreanDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAntreanDokterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAntreanDokterMouseClicked

    private void btnDashboardDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardDokterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDashboardDokterMouseClicked

    private void btnDashboardDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardDokterActionPerformed
    MainFrame frame = (MainFrame)
            javax.swing.SwingUtilities.getWindowAncestor(this);

    if (frame != null) {
        frame.tampil(halamanAsal);
    }
    }//GEN-LAST:event_btnDashboardDokterActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAntreanDokter;
    private javax.swing.JButton btnDashboardDokter;
    private javax.swing.JButton btnPerbaruiStatus;
    private javax.swing.JButton btnRiwayatKunjungan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblKunjunganTerpilih;
    private javax.swing.JLabel namaAdmin;
    private javax.swing.JLabel usernameAdmin;
    // End of variables declaration//GEN-END:variables

    void setInfoPetugas(String nipPetugas, String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}