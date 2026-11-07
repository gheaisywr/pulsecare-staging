
package model;

public abstract class Pegawai {

    private String nip;
    private String namaLengkap;
    private String password;
    private String statusAkun;

    public Pegawai() {
    }

    public Pegawai(String nip, String namaLengkap, String password, String statusAkun) {
        this.nip = nip;
        this.namaLengkap = namaLengkap;
        this.password = password;
        this.statusAkun = statusAkun;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatusAkun() {
        return statusAkun;
    }

    public void setStatusAkun(String statusAkun) {
        this.statusAkun = statusAkun;
    }

    public boolean aktif() {
        return "Aktif".equalsIgnoreCase(statusAkun);
    }

    public abstract String getPeran();
}
