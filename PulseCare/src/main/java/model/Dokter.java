
package model;

import java.time.LocalDate;

public class Dokter extends Pegawai {

    private LocalDate tanggalLahir;
    private String spesialisasi;
    private String idPoli;

    public Dokter() {
        super();
    }

    public Dokter(String nip, String namaLengkap, LocalDate tanggalLahir,
                  String spesialisasi, String password, String statusAkun,
                  String idPoli) {

        super(nip, namaLengkap, password, statusAkun);
        this.tanggalLahir = tanggalLahir;
        this.spesialisasi = spesialisasi;
        this.idPoli = idPoli;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(String idPoli) {
        this.idPoli = idPoli;
    }

    @Override
    public String getPeran() {
        return "Dokter";
    }
}
