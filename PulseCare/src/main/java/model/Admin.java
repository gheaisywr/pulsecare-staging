
package model;

public class Admin extends Pegawai {

    private String username;

    public Admin() {
        super();
    }

    public Admin(String nip, String namaLengkap, String username,
                 String password, String statusAkun) {

        super(nip, namaLengkap, password, statusAkun);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPeran() {
        return "Admin";
    }
}
