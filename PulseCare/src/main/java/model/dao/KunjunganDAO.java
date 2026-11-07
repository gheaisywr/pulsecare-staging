package dao;

import config.KoneksiDB;
import model.RiwayatKunjungan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class KunjunganDAO {

    // Menampilkan seluruh data kunjungan
    public List<RiwayatKunjungan> semua() throws SQLException {
        List<RiwayatKunjungan> daftar = new ArrayList<>();

        String sql = "SELECT * FROM riwayat_kunjungan "
                   + "ORDER BY tanggal_pendaftaran DESC, waktu_pendaftaran DESC";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RiwayatKunjungan kunjungan = ambilData(rs);
                daftar.add(kunjungan);
            }
        }
        return daftar;
    }
    // Menampilkan kunjungan berdasarkan tanggal
    public List<RiwayatKunjungan> berdasarkanTanggal(LocalDate tanggal)
            throws SQLException {
        List<RiwayatKunjungan> daftar = new ArrayList<>();

        String sql = "SELECT * FROM riwayat_kunjungan "
                   + "WHERE tanggal_pendaftaran = ? "
                   + "ORDER BY waktu_pendaftaran";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(tanggal));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RiwayatKunjungan kunjungan = ambilData(rs);
                    daftar.add(kunjungan);
                }
            }
        }
        return daftar;
    }
    // Menambahkan pendaftaran kunjungan
    public boolean tambah(RiwayatKunjungan kunjungan)
            throws SQLException {

        String sql = "INSERT INTO riwayat_kunjungan "
                   + "(id_kunjungan, nip_petugas, id_pasien, id_poli, "
                   + "waktu_pendaftaran, nomor_antrian, status_kunjungan, "
                   + "tanggal_pendaftaran, keluhan) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kunjungan.getIdKunjungan());
            ps.setString(2, kunjungan.getNipPetugas());
            ps.setString(3, kunjungan.getIdPasien());
            ps.setString(4, kunjungan.getIdPoli());
            ps.setTimestamp(5, Timestamp.valueOf(kunjungan.getWaktuPendaftaran()));
            ps.setString(6, kunjungan.getNomorAntrian());
            ps.setString(7, kunjungan.getStatusKunjungan());
            ps.setDate(8, Date.valueOf(kunjungan.getTanggalPendaftaran()));
            ps.setString(9, kunjungan.getKeluhan());
            return ps.executeUpdate() > 0;
        }
    }
    // Memperbarui status kunjungan
    public boolean ubahStatus(String idKunjungan, String status)
            throws SQLException {

        String sql = "UPDATE riwayat_kunjungan "
                   + "SET status_kunjungan = ? "
                   + "WHERE id_kunjungan = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.toLowerCase());
            ps.setString(2, idKunjungan);

            return ps.executeUpdate() > 0;
        }
    }
    // Memeriksa apakah pasien masih memiliki kunjungan aktif
    public boolean adaKunjunganAktif(String idPasien)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM riwayat_kunjungan "
                   + "WHERE id_pasien = ? "
                   + "AND status_kunjungan IN ('menunggu', 'diperiksa') "
                   + "AND tanggal_pendaftaran = CURDATE()";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPasien);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    // Mengubah hasil query menjadi objek RiwayatKunjungan
    private RiwayatKunjungan ambilData(ResultSet rs)
            throws SQLException {

        RiwayatKunjungan kunjungan = new RiwayatKunjungan();

        kunjungan.setIdKunjungan(rs.getString("id_kunjungan"));
        kunjungan.setNipPetugas(rs.getString("nip_petugas"));
        kunjungan.setIdPasien(rs.getString("id_pasien"));
        kunjungan.setIdPoli(rs.getString("id_poli"));
        kunjungan.setWaktuPendaftaran(rs.getTimestamp("waktu_pendaftaran").toLocalDateTime());
        kunjungan.setNomorAntrian(rs.getString("nomor_antrian"));
        kunjungan.setStatusKunjungan(rs.getString("status_kunjungan"));
        kunjungan.setTanggalPendaftaran(rs.getDate("tanggal_pendaftaran").toLocalDate());
        kunjungan.setKeluhan(rs.getString("keluhan"));
        return kunjungan;
    }
    public int[] statistikHariIni() throws SQLException {
        int[] statistik = new int[5];

        String sql = "SELECT "
                + "COUNT(*) AS total, "
                + "SUM(status_kunjungan = 'menunggu') AS menunggu, "
                + "SUM(status_kunjungan = 'diperiksa') AS diperiksa, "
                + "SUM(status_kunjungan = 'selesai') AS selesai, "
                + "SUM(status_kunjungan = 'batal') AS batal "
                + "FROM riwayat_kunjungan "
                + "WHERE tanggal_pendaftaran = CURDATE()";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                statistik[0] = rs.getInt("total");
                statistik[1] = rs.getInt("menunggu");
                statistik[2] = rs.getInt("diperiksa");
                statistik[3] = rs.getInt("selesai");
                statistik[4] = rs.getInt("batal");
            }
        }

        return statistik;
    }
    public List<Object[]> antreanPetugasHariIni() throws SQLException {
        List<Object[]> daftar = new ArrayList<>();

        String sql = "SELECT rk.nomor_antrian, "
                + "p.nama_lengkap, pol.nama_poli, "
                + "rk.status_kunjungan "
                + "FROM riwayat_kunjungan rk "
                + "JOIN pasien p ON rk.id_pasien = p.id_pasien "
                + "JOIN poliklinik pol ON rk.id_poli = pol.id_poli "
                + "WHERE rk.tanggal_pendaftaran = CURDATE() "
                + "ORDER BY "
                + "CASE "
                + "WHEN rk.status_kunjungan = 'menunggu' THEN 1 "
                + "WHEN rk.status_kunjungan = 'diperiksa' THEN 2 "
                + "WHEN rk.status_kunjungan = 'selesai' THEN 3 "
                + "ELSE 4 END, "
                + "rk.waktu_pendaftaran ASC";
        
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] data = {
                    rs.getString("nomor_antrian"),
                    rs.getString("nama_lengkap"),
                    rs.getString("nama_poli"),
                    rs.getString("status_kunjungan")
                };

                daftar.add(data);
            }
        }

        return daftar;
    }
    
    // Mengambil statistik kunjungan dokter hari ini berdasarkan poliklinik
    public int[] statistikDokterHariIni(String idPoli) throws SQLException {
        int[] statistik = new int[4];

        String sql = "SELECT "
                + "COUNT(*) AS total, "
                + "SUM(status_kunjungan = 'menunggu') AS menunggu, "
                + "SUM(status_kunjungan = 'diperiksa') AS diperiksa, "
                + "SUM(status_kunjungan = 'selesai') AS selesai "
                + "FROM riwayat_kunjungan "
                + "WHERE tanggal_pendaftaran = CURDATE() "
                + "AND id_poli = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPoli);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    statistik[0] = rs.getInt("total");
                    statistik[1] = rs.getInt("menunggu");
                    statistik[2] = rs.getInt("diperiksa");
                    statistik[3] = rs.getInt("selesai");
                }
            }
        }

        return statistik;
    }
    
    // Mengambil antrean seluruh poliklinik untuk dashboard dokter
public List<Object[]> antreanSemuaPoliHariIni() throws SQLException {

    List<Object[]> daftar = new ArrayList<>();

    String sql = """
        SELECT
            rk.nomor_antrian,
            p.nama_lengkap,
            pol.nama_poli,
            rk.status_kunjungan
        FROM riwayat_kunjungan rk
        JOIN pasien p
            ON rk.id_pasien = p.id_pasien
        JOIN poliklinik pol
            ON rk.id_poli = pol.id_poli
        WHERE rk.tanggal_pendaftaran = CURDATE()
          AND rk.status_kunjungan IN ('menunggu', 'diperiksa')
        ORDER BY
            CASE
                WHEN rk.status_kunjungan = 'diperiksa' THEN 1
                WHEN rk.status_kunjungan = 'menunggu' THEN 2
                ELSE 3
            END,
            rk.waktu_pendaftaran ASC
    """;

    try (Connection conn = KoneksiDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            daftar.add(new Object[]{
                rs.getString("nomor_antrian"),
                rs.getString("nama_lengkap"),
                rs.getString("nama_poli"),
                rs.getString("status_kunjungan")
            });
        }
    }

    return daftar;
}

public List<Object[]> antreanSemuaPoliDokterHariIni()
        throws SQLException {

    List<Object[]> daftar = new ArrayList<>();

    String sql = """
        SELECT
            rk.nomor_antrian,
            rk.id_kunjungan,
            p.nama_lengkap,
            pol.nama_poli,
            rk.status_kunjungan
        FROM riwayat_kunjungan rk
        JOIN pasien p
            ON rk.id_pasien = p.id_pasien
        JOIN poliklinik pol
            ON rk.id_poli = pol.id_poli
        WHERE rk.tanggal_pendaftaran = CURDATE()
          AND rk.status_kunjungan IN ('menunggu', 'diperiksa')
        ORDER BY rk.waktu_pendaftaran ASC
    """;

    try (Connection conn = KoneksiDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            daftar.add(new Object[]{
                rs.getString("nomor_antrian"),
                rs.getString("id_kunjungan"),
                rs.getString("nama_lengkap"),
                rs.getString("nama_poli"),
                rs.getString("status_kunjungan")
            });
        }
    }

    return daftar;
}

    // Mengambil data pasien yang sedang diperiksa
    public Object[] pasienSedangDiperiksa(String idPoli)
            throws SQLException {

        String sql = "SELECT rk.nomor_antrian, "
        + "p.nama_lengkap, p.id_pasien, "
        + "p.jenis_kelamin, p.status_bpjs "
        + "FROM riwayat_kunjungan rk "
        + "JOIN pasien p ON rk.id_pasien = p.id_pasien "
        + "WHERE rk.tanggal_pendaftaran = CURDATE() "
        + "AND rk.id_poli = ? "
        + "AND rk.status_kunjungan = 'diperiksa' "
        + "ORDER BY rk.waktu_pendaftaran ASC "
        + "LIMIT 1";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPoli);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                        rs.getString("nomor_antrian"),
                        rs.getString("nama_lengkap"),
                        rs.getString("id_pasien"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("status_bpjs")
                    };
                }
            }
        }

        return null;
    }
        // Mengambil antrean pasien yang masih menunggu
       public List<Object[]> antreanDokterHariIni(String idPoli)
            throws SQLException {

        List<Object[]> daftar = new ArrayList<>();

        String sql = "SELECT rk.nomor_antrian, "
                + "rk.id_kunjungan, p.nama_lengkap, "
                + "rk.status_kunjungan "
                + "FROM riwayat_kunjungan rk "
                + "JOIN pasien p ON rk.id_pasien = p.id_pasien "
                + "WHERE rk.tanggal_pendaftaran = CURDATE() "
                + "AND rk.id_poli = ? "
                + "AND rk.status_kunjungan IN ('menunggu', 'diperiksa', 'selesai') "
                + "ORDER BY rk.waktu_pendaftaran ASC";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idPoli);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    daftar.add(new Object[]{
                        rs.getString("nomor_antrian"),
                        rs.getString("id_kunjungan"),
                        rs.getString("nama_lengkap"),
                        rs.getString("status_kunjungan")
                    });
                }
            }
        }

        return daftar;
    }
       
       public String nomorAntreanBerikutnya() throws SQLException {

        String sql = """
            SELECT COALESCE(
                MAX(CAST(nomor_antrian AS UNSIGNED)), 0
            ) + 1 AS nomor
            FROM riwayat_kunjungan
            WHERE tanggal_pendaftaran = CURDATE()
        """;

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return String.format("%03d", rs.getInt("nomor"));
            }
        }

        return "001";
    }
        // Menyimpan pendaftaran kunjungan dan membuat nomor antrean otomatis
    public String[] tambahPendaftaran(
            String nipPetugas,
            String idPasien,
            String idPoli,
            String keluhan) throws SQLException {

        try (Connection conn = KoneksiDB.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Membuat ID kunjungan berikutnya
                String sqlId = "SELECT MAX(CAST(SUBSTRING(id_kunjungan, 3) "
                        + "AS UNSIGNED)) AS nomor FROM riwayat_kunjungan";

                int nomorId = 0;

                try (PreparedStatement ps = conn.prepareStatement(sqlId);
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nomorId = rs.getInt("nomor");
                    }
                }

                String idKunjungan = String.format("KJ%03d", nomorId + 1);

                // Membuat nomor antrean berikutnya pada hari ini
                String sqlAntrean = "SELECT MAX(CAST(nomor_antrian AS UNSIGNED)) "
                        + "AS nomor FROM riwayat_kunjungan "
                        + "WHERE tanggal_pendaftaran = CURDATE()";

                int nomorAntrean = 0;

                try (PreparedStatement ps = conn.prepareStatement(sqlAntrean);
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nomorAntrean = rs.getInt("nomor");
                    }
                }

                String antrean = String.format("%03d", nomorAntrean + 1);

                // Menyimpan kunjungan
                String sqlInsert = "INSERT INTO riwayat_kunjungan "
                        + "(id_kunjungan, nip_petugas, id_pasien, id_poli, "
                        + "waktu_pendaftaran, nomor_antrian, status_kunjungan, "
                        + "tanggal_pendaftaran) "
                        + "VALUES (?, ?, ?, ?, NOW(), ?, 'menunggu', CURDATE())";

                try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                    ps.setString(1, idKunjungan);
                    ps.setString(2, nipPetugas);
                    ps.setString(3, idPasien);
                    ps.setString(4, idPoli);
                    ps.setString(5, antrean);

                    ps.executeUpdate();
                }

                conn.commit();

                return new String[]{idKunjungan, antrean};

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
            // Mengambil data kunjungan hari ini untuk halaman Perbarui Status
        public List<Object[]> daftarPerbaruiStatusHariIni()
                throws SQLException {

            List<Object[]> daftar = new ArrayList<>();

            String sql = "SELECT rk.id_kunjungan, rk.nomor_antrian, "
                    + "p.nama_lengkap, pol.nama_poli, "
                    + "rk.status_kunjungan "
                    + "FROM riwayat_kunjungan rk "
                    + "JOIN pasien p ON rk.id_pasien = p.id_pasien "
                    + "JOIN poliklinik pol ON rk.id_poli = pol.id_poli "
                    + "WHERE rk.tanggal_pendaftaran = CURDATE() "
                    + "ORDER BY rk.waktu_pendaftaran ASC";

            try (Connection conn = KoneksiDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    daftar.add(new Object[]{
                        rs.getString("id_kunjungan"),
                        rs.getString("nomor_antrian"),
                        rs.getString("nama_lengkap"),
                        rs.getString("nama_poli"),
                        rs.getString("status_kunjungan")
                    });
                }
            }

            return daftar;
        }
        // Mengambil detail kunjungan berdasarkan ID kunjungan
public Object[] detailKunjunganUntukPemeriksaan(String idKunjungan)
        throws SQLException {

    String sql = "SELECT rk.id_kunjungan, "
            + "rk.nomor_antrian, "
            + "p.id_pasien, p.nama_lengkap, "
            + "p.tanggal_lahir, p.jenis_kelamin, "
            + "p.status_bpjs, pol.nama_poli, "
            + "hk.keluhan "
            + "FROM riwayat_kunjungan rk "
            + "JOIN pasien p ON rk.id_pasien = p.id_pasien "
            + "JOIN poliklinik pol ON rk.id_poli = pol.id_poli "
            + "LEFT JOIN hasil_kunjungan hk "
            + "ON rk.id_kunjungan = hk.id_kunjungan "
            + "WHERE rk.id_kunjungan = ?";

    try (Connection conn = KoneksiDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, idKunjungan);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Object[]{
                    rs.getString("id_kunjungan"),
                    rs.getString("nomor_antrian"),
                    rs.getString("id_pasien"),
                    rs.getString("nama_lengkap"),
                    rs.getDate("tanggal_lahir"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("status_bpjs"),
                    rs.getString("nama_poli"),
                    rs.getString("keluhan")
                };
            }
        }
    }

    return null;
}
}