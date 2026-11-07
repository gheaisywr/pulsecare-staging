
package model;

import java.time.LocalDate;

public class Petugas extends Pegawai {

    private LocalDate tanggalLahir;
    private String idShift;

    public Petugas() {
        super();
    }

    public Petugas(String nip, String namaLengkap, LocalDate tanggalLahir,
                   String password, String statusAkun, String idShift) {

        super(nip, namaLengkap, password, statusAkun);
        this.tanggalLahir = tanggalLahir;
        this.idShift = idShift;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getIdShift() {
        return idShift;
    }

    public void setIdShift(String idShift) {
        this.idShift = idShift;
    }

    @Override
    public String getPeran() {
        return "Petugas";
    }
}
