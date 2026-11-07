package dao;

import config.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RiwayatKunjunganDAO {

    public List<Map<String, Object>> semua() throws SQLException {
        List<Map<String, Object>> daftar = new ArrayList<>();

        String sql = """
            SELECT
                rk.id_kunjungan,
                p.id_pasien,
                p.nama_lengkap,
                pol.nama_poli,
                rk.tanggal_pendaftaran,
                rk.waktu_pendaftaran,
                rk.nomor_antrian,
                rk.status_kunjungan,
                hk.keluhan,
                hk.hasil_pemeriksaan,
                hk.diagnosa,
                hk.resep_obat,
                hk.catatan_tindak_lanjut
            FROM riwayat_kunjungan rk
            JOIN pasien p
                ON rk.id_pasien = p.id_pasien
            JOIN poliklinik pol
                ON rk.id_poli = pol.id_poli
            LEFT JOIN hasil_kunjungan hk
                ON rk.id_kunjungan = hk.id_kunjungan
            ORDER BY rk.tanggal_pendaftaran DESC,
                     rk.waktu_pendaftaran DESC
        """;

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();

                data.put("id_kunjungan", rs.getString("id_kunjungan"));
                data.put("id_pasien", rs.getString("id_pasien"));
                data.put("nama_lengkap", rs.getString("nama_lengkap"));
                data.put("nama_poli", rs.getString("nama_poli"));
                data.put("tanggal_pendaftaran", rs.getDate("tanggal_pendaftaran"));
                data.put("waktu_pendaftaran", rs.getTime("waktu_pendaftaran"));
                data.put("nomor_antrian", rs.getString("nomor_antrian"));
                data.put("status_kunjungan", rs.getString("status_kunjungan"));
                data.put("keluhan", rs.getString("keluhan"));
                data.put("hasil_pemeriksaan", rs.getString("hasil_pemeriksaan"));
                data.put("diagnosa", rs.getString("diagnosa"));
                data.put("resep_obat", rs.getString("resep_obat"));
                data.put("catatan_tindak_lanjut", rs.getString("catatan_tindak_lanjut"));

                daftar.add(data);
            }
        }

        return daftar;
    }
    
    public boolean hapus(String idKunjungan) throws SQLException {

        String sql = """
            DELETE FROM riwayat_kunjungan
            WHERE id_kunjungan = ?
            AND NOT EXISTS (
                SELECT 1
                FROM hasil_kunjungan
                WHERE hasil_kunjungan.id_kunjungan =
                      riwayat_kunjungan.id_kunjungan
            )
        """;

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idKunjungan);
            return ps.executeUpdate() > 0;
        }
    }
}