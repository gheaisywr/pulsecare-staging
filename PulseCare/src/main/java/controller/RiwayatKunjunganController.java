package controller;

import dao.RiwayatKunjunganDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RiwayatKunjunganController {

    private final RiwayatKunjunganDAO riwayatDAO =
            new RiwayatKunjunganDAO();

    public List<Map<String, Object>> semua() throws SQLException {
        return riwayatDAO.semua();
    }
    
    public boolean hapus(String idKunjungan) throws SQLException {

        if (idKunjungan == null || idKunjungan.trim().isEmpty()) {
            return false;
        }

        return riwayatDAO.hapus(idKunjungan.trim());
    }
}