/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class EditRiwayat extends javax.swing.JDialog {
    private final java.util.Map<String, String> dataPasien =
            new java.util.HashMap<>();

    private final java.util.Map<String, String> dataPoli =
            new java.util.HashMap<>();
    
private final java.util.List<String> daftarIdPasien =
        new java.util.ArrayList<>();

private final java.util.List<String> daftarIdPoli =
        new java.util.ArrayList<>();
    
    private String idKunjungan;

    private static class PilihanData {
        String id;
        String nama;

        PilihanData(String id, String nama) {
            this.id = id;
            this.nama = nama;
        }

        @Override
        public String toString() {
            return nama;
        }
    }

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(EditRiwayat.class.getName());

    public EditRiwayat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    public EditRiwayat(
        java.awt.Frame parent,
        boolean modal,
        String idKunjungan,
        String nipPetugas,
        String idPasien,
        String idPoli,
        LocalDate tanggal,
        String status) {

        this(parent, modal);

        this.idKunjungan = idKunjungan;

        NipPetugas.setText(nipPetugas);
        NipPetugas.setEditable(false);

        kolomTanggalLahir.setText(tanggal.toString());

        muatPasien(idPasien);
        muatPoliklinik(idPoli);

        kolomSpesialisasi.setModel(
            new DefaultComboBoxModel<>(
                new String[]{"menunggu", "diperiksa", "selesai", "batal"}
            )
        );

        kolomSpesialisasi.setSelectedItem(status);

        btnBatal1.setVisible(false);

        setTitle("Edit Riwayat Kunjungan");
        setLocationRelativeTo(parent);
    }
    
    private void muatPasien(String idTerpilih) {

        PilihPasien.removeAllItems();
        daftarIdPasien.clear();

        String sql = """
            SELECT id_pasien, nama_lengkap
            FROM pasien
            ORDER BY nama_lengkap
        """;

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int indeksTerpilih = -1;

            while (rs.next()) {

                String id = rs.getString("id_pasien");
                String nama = rs.getString("nama_lengkap");

                daftarIdPasien.add(id);
                PilihPasien.addItem(nama);

                if (id.equals(idTerpilih)) {
                    indeksTerpilih = daftarIdPasien.size() - 1;
                }
            }

            if (indeksTerpilih >= 0) {
                PilihPasien.setSelectedIndex(indeksTerpilih);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memuat pasien: " + e.getMessage()
            );
        }
    }

    private void muatPoliklinik(String idTerpilih) {

        PilihPoliklinik.removeAllItems();
        daftarIdPoli.clear();

        String sql = """
            SELECT id_poli, nama_poli
            FROM poliklinik
            ORDER BY nama_poli
        """;

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int indeksTerpilih = -1;

            while (rs.next()) {

                String id = rs.getString("id_poli");
                String nama = rs.getString("nama_poli");

                daftarIdPoli.add(id);
                PilihPoliklinik.addItem(nama);

                if (id.equals(idTerpilih)) {
                    indeksTerpilih = daftarIdPoli.size() - 1;
                }
            }

            if (indeksTerpilih >= 0) {
                PilihPoliklinik.setSelectedIndex(indeksTerpilih);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memuat poliklinik: " + e.getMessage()
            );
        }
    }
    
    private void pilihBerdasarkanId(
        javax.swing.JComboBox<PilihanData> combo,
        String id) {

        for (int i = 0; i < combo.getItemCount(); i++) {

            PilihanData data = combo.getItemAt(i);

            if (data.id.equals(id)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
    

    private void pasangPlaceholder(
            javax.swing.JTextField kolom, String teks) {

        kolom.setText(teks);
        kolom.setForeground(java.awt.Color.GRAY);

        kolom.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (kolom.getText().equals(teks)) {
                    kolom.setText("");
                    kolom.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (kolom.getText().isEmpty()) {
                    kolom.setText(teks);
                    kolom.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
 /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        judulForm = new javax.swing.JLabel();
        labelNama = new javax.swing.JLabel();
        NipPetugas = new javax.swing.JTextField();
        labelNIK = new javax.swing.JLabel();
        kolomTanggalLahir = new javax.swing.JTextField();
        labelTanggalLahir = new javax.swing.JLabel();
        labelJenisKelamin = new javax.swing.JLabel();
        kolomSpesialisasi = new javax.swing.JComboBox<>();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        PilihPasien = new javax.swing.JComboBox<>();
        labelNIK1 = new javax.swing.JLabel();
        PilihPoliklinik = new javax.swing.JComboBox<>();
        btnBatal1 = new javax.swing.JButton();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form Kelola Akun");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(244, 247, 248));
        jPanel1.setPreferredSize(new java.awt.Dimension(450, 650));

        jPanel2.setBackground(new java.awt.Color(11, 59, 68));

        judulForm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        judulForm.setForeground(new java.awt.Color(255, 255, 255));
        judulForm.setText("Edit Riwayat Kunjungan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(judulForm)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(judulForm)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        labelNama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNama.setText("NIP Petugas");

        NipPetugas.setForeground(new java.awt.Color(102, 102, 102));
        NipPetugas.setText("Masukkan Nama lengkap");

        labelNIK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNIK.setText("Pilih Pasien");

        kolomTanggalLahir.setForeground(new java.awt.Color(102, 102, 102));
        kolomTanggalLahir.setText("YYYY-MM-DD");

        labelTanggalLahir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelTanggalLahir.setText("Tanggal Pendaftaran");

        labelJenisKelamin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelJenisKelamin.setText("Status Kunjungan");

        kolomSpesialisasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Jenis Kelamin", "Perempuan", "Laki-laki" }));
        kolomSpesialisasi.addActionListener(this::kolomSpesialisasiActionPerformed);

        btnBatal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Batal");
        btnBatal.addActionListener(this::btnBatalActionPerformed);

        btnSimpan.setBackground(new java.awt.Color(20, 96, 108));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        PilihPasien.addActionListener(this::PilihPasienActionPerformed);

        labelNIK1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelNIK1.setText("Pilih Poliklinik");

        PilihPoliklinik.addActionListener(this::PilihPoliklinikActionPerformed);

        btnBatal1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBatal1.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal1.setText("Tambah Pasien");
        btnBatal1.addActionListener(this::btnBatal1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBatal1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelNIK1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelJenisKelamin, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTanggalLahir, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNama, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNIK, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NipPetugas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(kolomSpesialisasi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kolomTanggalLahir, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PilihPasien, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PilihPoliklinik, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NipPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelNIK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PilihPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelNIK1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PilihPoliklinik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelTanggalLahir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelJenisKelamin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kolomSpesialisasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed

        try {
            // Ambil pilihan pasien dan poliklinik
            String pasien = (String) PilihPasien.getSelectedItem();
            String poli = (String) PilihPoliklinik.getSelectedItem();

            // Ambil tanggal dan status
            String tanggalInput = kolomTanggalLahir.getText().trim();

            String status = (String) kolomSpesialisasi.getSelectedItem();

            // Validasi input
            if (pasien == null || poli == null
                    || tanggalInput.isEmpty() || status == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Semua data wajib dipilih atau diisi!"
                );
                return;
            }

            // Ambil ID berdasarkan posisi pilihan ComboBox
            int indeksPasien = PilihPasien.getSelectedIndex();
            int indeksPoli = PilihPoliklinik.getSelectedIndex();

            if (indeksPasien < 0 || indeksPoli < 0
                    || indeksPasien >= daftarIdPasien.size()
                    || indeksPoli >= daftarIdPoli.size()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Data pasien atau poliklinik tidak valid!"
                );
                return;
            }

            String idPasien = daftarIdPasien.get(indeksPasien);
            String idPoli = daftarIdPoli.get(indeksPoli);

            // Validasi tanggal
            LocalDate tanggal = LocalDate.parse(tanggalInput);

            if (tanggal.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Tanggal kunjungan tidak boleh melebihi hari ini!"
                );
                return;
            }

            // Query update
            String sql = """
                UPDATE riwayat_kunjungan
                SET id_pasien = ?,
                    id_poli = ?,
                    tanggal_pendaftaran = ?,
                    status_kunjungan = ?
                WHERE id_kunjungan = ?
            """;

            try (Connection conn = KoneksiDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, idPasien);
                ps.setString(2, idPoli);
                ps.setDate(3, java.sql.Date.valueOf(tanggal));
                ps.setString(4, status);
                ps.setString(5, idKunjungan);

                if (ps.executeUpdate() > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Riwayat kunjungan berhasil diperbarui!"
                    );

                    dispose();

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Data kunjungan tidak ditemukan."
                    );
                }
            }

        } catch (java.time.format.DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Format tanggal harus YYYY-MM-DD!"
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Gagal memperbarui data: " + e.getMessage()
            );
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void kolomSpesialisasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kolomSpesialisasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kolomSpesialisasiActionPerformed

    private void PilihPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PilihPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PilihPasienActionPerformed

    private void PilihPoliklinikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PilihPoliklinikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PilihPoliklinikActionPerformed

    private void btnBatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBatal1ActionPerformed

    /**
     * @param args the command line arguments
     */ 
        /* Create and display the dialog */
        public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditRiwayat dialog = new EditRiwayat(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField NipPetugas;
    private javax.swing.JComboBox<String> PilihPasien;
    private javax.swing.JComboBox<String> PilihPoliklinik;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBatal1;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel judulForm;
    private javax.swing.JComboBox<String> kolomSpesialisasi;
    private javax.swing.JTextField kolomTanggalLahir;
    private javax.swing.JLabel labelJenisKelamin;
    private javax.swing.JLabel labelNIK;
    private javax.swing.JLabel labelNIK1;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelTanggalLahir;
    // End of variables declaration//GEN-END:variables
}