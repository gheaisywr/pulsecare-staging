/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.KunjunganDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class DashboardPetugas extends javax.swing.JPanel {
    private String nipPetugas;
    private Timer timerDashboard;
    
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
    public DashboardPetugas() {
        initComponents();

        aturTampilanTabel();
        aturKartuStatistik();
        muatStatistik();
        muatAntrean();
        timerDashboard = new Timer(5000, e -> {
            muatStatistik();
            muatAntrean();
        });

        timerDashboard.start();
        aturTanggalHariIni();
        aturShiftSaatIni();

        namaPengguna.setForeground(java.awt.Color.WHITE);
    }

    private void muatStatistik() {
        KunjunganDAO dao = new KunjunganDAO();

        try {
            int[] statistik = dao.statistikHariIni();

            totalHariini.setText(String.valueOf(statistik[0]));
            totalMenunggu.setText(String.valueOf(statistik[1]));
            totalDiperiksa.setText(String.valueOf(statistik[2]));
            totalSelesai.setText(String.valueOf(statistik[3]));
            totalBatal.setText(String.valueOf(statistik[4]));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal memuat statistik: " + e.getMessage()
            );
        }
    }

    private void muatAntrean() {
        KunjunganDAO dao = new KunjunganDAO();

        String[] kolom = {
            "No.",
            "Pasien",
            "Poliklinik",
            "Status"
        };

        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            List<Object[]> daftar = dao.antreanPetugasHariIni();

            for (Object[] data : daftar) {
                model.addRow(data);
            }

            jTable1.setModel(model);

            aturTampilanTabel();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Gagal memuat antrean: " + e.getMessage()
            );
        }
    }

    private void aturTanggalHariIni() {
        LocalDate tanggal = LocalDate.now();

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(
                "EEEE, dd MMMM yyyy",
                new Locale("id", "ID")
            );

        tanggalHariini.setText(tanggal.format(formatter));
    }
    
    private void aturShiftSaatIni() {
        LocalTime sekarang = LocalTime.now();

        if (!sekarang.isBefore(LocalTime.of(7, 0))
                && sekarang.isBefore(LocalTime.of(14, 0))) {
            shiftSaatini.setText("Shift Pagi");
        } else if (!sekarang.isBefore(LocalTime.of(14, 0))
                && sekarang.isBefore(LocalTime.of(21, 0))) {
            shiftSaatini.setText("Shift Siang");
        } else {
            shiftSaatini.setText("Shift Malam");
        }
    }

    public void setInfoPetugas(String nip, String nama, String shift) {
        this.nipPetugas = nip;

        namaPengguna.setText(nama);
        namaPengguna.setForeground(java.awt.Color.WHITE);

        usernamePengguna.setText(nip);
        shiftSaatini.setText(shift);
    }
    
    private void aturKartuStatistik() {

        javax.swing.JPanel panelKartu = new javax.swing.JPanel(
            new java.awt.GridLayout(0, 5, 18, 0)
        );

        panelKartu.setOpaque(false);

        panelKartu.add(hariIni);
        panelKartu.add(menunggu);
        panelKartu.add(jPanel1);
        panelKartu.add(jPanel2);
        panelKartu.add(jPanel5);
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
    
    private void bukaHalaman(String namaHalaman) {
        MainFrame frame = (MainFrame) javax.swing.SwingUtilities
                .getWindowAncestor(this);

        if (frame != null) {
            frame.tampil(namaHalaman);
        }
    }
    
    private void aktifkanMenu(javax.swing.JButton menuAktif) {

        aturTombolSidebar(menuAktif, true);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel10 = new javax.swing.JPanel();
        BtnDashboard = new javax.swing.JButton();
        BtnPasien = new javax.swing.JButton();
        BtnDaftar = new javax.swing.JButton();
        BtnRiwayat = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        namaPengguna = new javax.swing.JLabel();
        usernamePengguna = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        hariIni = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        totalHariini = new javax.swing.JLabel();
        menunggu = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        totalMenunggu = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        totalDiperiksa = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        totalSelesai = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        totalBatal = new javax.swing.JLabel();
        keloladataPasien = new javax.swing.JButton();
        daftarKunjungan = new javax.swing.JButton();
        perbaruiStatus = new javax.swing.JButton();
        tanggalHariini = new javax.swing.JLabel();
        shiftSaatini = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jScrollPane1.setViewportView(jTextPane1);

        setMaximumSize(new java.awt.Dimension(1000, 650));
        setPreferredSize(new java.awt.Dimension(806, 550));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(11, 59, 68));
        jPanel10.setPreferredSize(new java.awt.Dimension(175, 554));

        BtnDashboard.setBackground(new java.awt.Color(186, 234, 242));
        BtnDashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnDashboard.setForeground(new java.awt.Color(20, 96, 108));
        BtnDashboard.setText("Dashboard");
        BtnDashboard.setBorder(null);
        BtnDashboard.addActionListener(this::BtnDashboardActionPerformed);

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

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo PulseCare sedang tanpa tulisan.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PulseCare");

        namaPengguna.setBackground(new java.awt.Color(255, 255, 255));
        namaPengguna.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        namaPengguna.setForeground(new java.awt.Color(255, 255, 255));
        namaPengguna.setText("Nama Lengkap");

        usernamePengguna.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        usernamePengguna.setForeground(new java.awt.Color(255, 255, 255));
        usernamePengguna.setText("Username");

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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(BtnDaftar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnPasien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(BtnRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel11))
                            .addComponent(jLabel6)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(namaPengguna))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(usernamePengguna)))
                .addContainerGap(25, Short.MAX_VALUE))
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

        add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 550));

        jPanel3.setBackground(new java.awt.Color(244, 247, 248));

        jLabel4.setBackground(new java.awt.Color(20, 96, 108));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(22, 63, 76));
        jLabel4.setText("Dashboard Petugas");

        hariIni.setBackground(new java.awt.Color(255, 255, 255));
        hariIni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(186, 234, 242)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(20, 96, 108));
        jLabel1.setText("Hari Ini");

        totalHariini.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        totalHariini.setForeground(new java.awt.Color(22, 63, 76));
        totalHariini.setText("8");

        javax.swing.GroupLayout hariIniLayout = new javax.swing.GroupLayout(hariIni);
        hariIni.setLayout(hariIniLayout);
        hariIniLayout.setHorizontalGroup(
            hariIniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hariIniLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hariIniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(totalHariini))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        hariIniLayout.setVerticalGroup(
            hariIniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hariIniLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalHariini, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        menunggu.setBackground(new java.awt.Color(255, 255, 255));
        menunggu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(186, 234, 242)));
        menunggu.setForeground(new java.awt.Color(20, 96, 108));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(20, 96, 108));
        jLabel3.setText("Menunggu");

        totalMenunggu.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        totalMenunggu.setForeground(new java.awt.Color(153, 0, 51));
        totalMenunggu.setText("8");

        javax.swing.GroupLayout menungguLayout = new javax.swing.GroupLayout(menunggu);
        menunggu.setLayout(menungguLayout);
        menungguLayout.setHorizontalGroup(
            menungguLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menungguLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menungguLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menungguLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(totalMenunggu))
                    .addComponent(jLabel3))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        menungguLayout.setVerticalGroup(
            menungguLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menungguLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalMenunggu, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(186, 234, 242), 1, true));

        jLabel5.setBackground(new java.awt.Color(20, 96, 108));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(20, 96, 108));
        jLabel5.setText("Diperiksa");

        totalDiperiksa.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        totalDiperiksa.setForeground(new java.awt.Color(22, 63, 76));
        totalDiperiksa.setText("8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(totalDiperiksa))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalDiperiksa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(186, 234, 242)));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(20, 96, 108));
        jLabel7.setText("Selesai");

        totalSelesai.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        totalSelesai.setForeground(new java.awt.Color(0, 102, 0));
        totalSelesai.setText("8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(totalSelesai))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(186, 234, 242)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(20, 96, 108));
        jLabel8.setText("Batal");

        totalBatal.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        totalBatal.setForeground(new java.awt.Color(153, 0, 51));
        totalBatal.setText("8");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(totalBatal))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        keloladataPasien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        keloladataPasien.setForeground(new java.awt.Color(22, 63, 76));
        keloladataPasien.setText("Kelola Data Pasien");
        keloladataPasien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 96, 108), 2));
        keloladataPasien.addActionListener(this::keloladataPasienActionPerformed);

        daftarKunjungan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        daftarKunjungan.setForeground(new java.awt.Color(22, 63, 76));
        daftarKunjungan.setText("Daftarkan Kunjungan");
        daftarKunjungan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(20, 96, 108), 2, true));
        daftarKunjungan.addActionListener(this::daftarKunjunganActionPerformed);

        perbaruiStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        perbaruiStatus.setForeground(new java.awt.Color(22, 63, 76));
        perbaruiStatus.setText("Riwayat Kunjungan");
        perbaruiStatus.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(20, 96, 108), 2, true));
        perbaruiStatus.addActionListener(this::perbaruiStatusActionPerformed);

        tanggalHariini.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tanggalHariini.setForeground(new java.awt.Color(20, 96, 108));
        tanggalHariini.setText("Tanggal Hari ini");

        shiftSaatini.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        shiftSaatini.setForeground(new java.awt.Color(20, 96, 108));
        shiftSaatini.setText("Shift saat ini");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 96, 108)));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(20, 96, 108));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(20, 96, 108));
        jLabel9.setText("Antrean Hari Ini");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(keloladataPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(daftarKunjungan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(perbaruiStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(hariIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menunggu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tanggalHariini)
                                .addGap(18, 18, 18)
                                .addComponent(shiftSaatini)))
                        .addGap(36, 36, 36)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalHariini)
                    .addComponent(shiftSaatini))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(hariIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menunggu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keloladataPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(daftarKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(perbaruiStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 640, 550));
    }// </editor-fold>//GEN-END:initComponents

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

    private void keloladataPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keloladataPasienActionPerformed
    bukaHalaman("kelolaPasien");
    }//GEN-LAST:event_keloladataPasienActionPerformed

    private void daftarKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daftarKunjunganActionPerformed
        MainFrame frame = (MainFrame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.tampilkanDaftarkanKunjungan();
    }//GEN-LAST:event_daftarKunjunganActionPerformed

    private void perbaruiStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_perbaruiStatusActionPerformed
        MainFrame frame = (MainFrame) javax.swing.SwingUtilities
                .getWindowAncestor(this);

        if (frame != null) {
            frame.tampil("riwayatKunjungan");
        }
    }//GEN-LAST:event_perbaruiStatusActionPerformed

    private void BtnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRiwayatActionPerformed
    bukaHalaman("riwayatKunjungan");
    }//GEN-LAST:event_BtnRiwayatActionPerformed

    private void BtnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDaftarActionPerformed
        MainFrame frame = (MainFrame)
                javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.tampilkanDaftarkanKunjungan();
    }//GEN-LAST:event_BtnDaftarActionPerformed

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
    bukaHalaman("kelolaPasien");
    }//GEN-LAST:event_BtnPasienActionPerformed

    private void BtnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDashboardActionPerformed
    bukaHalaman("dashboardPetugas");
    }//GEN-LAST:event_BtnDashboardActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnDaftar;
    private javax.swing.JButton BtnDashboard;
    private javax.swing.JButton BtnPasien;
    private javax.swing.JButton BtnRiwayat;
    private javax.swing.JButton daftarKunjungan;
    private javax.swing.JPanel hariIni;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton keloladataPasien;
    private javax.swing.JPanel menunggu;
    private javax.swing.JLabel namaPengguna;
    private javax.swing.JButton perbaruiStatus;
    private javax.swing.JLabel shiftSaatini;
    private javax.swing.JLabel tanggalHariini;
    private javax.swing.JLabel totalBatal;
    private javax.swing.JLabel totalDiperiksa;
    private javax.swing.JLabel totalHariini;
    private javax.swing.JLabel totalMenunggu;
    private javax.swing.JLabel totalSelesai;
    private javax.swing.JLabel usernamePengguna;
    // End of variables declaration//GEN-END:variables
}
