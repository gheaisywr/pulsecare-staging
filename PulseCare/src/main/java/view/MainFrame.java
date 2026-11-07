/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.CardLayout;
import model.Akun;

/**
 *
 * @author Lenovo
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());
    private DashboardAdmin dashboardAdmin;
    private DashboardPetugas dashboardPetugas;
    private DashboardDokter dashboardDokter;
    private final RiwayatKunjungan riwayatKunjungan;
    private final AntreanDokter antreanDokter;
    private final KelolaAkun kelolaAkun;
    private Akun akunPetugasAktif;
    private Akun akunDokterAktif;
    private final DaftarkanKunjungan daftarkanKunjungan;
    private final FormPemeriksaan formPemeriksaan;
    private final PerbaruiStatus perbaruiStatus;
    private final RiwayatKunjunganDokter riwayatKunjunganDokter;
    
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        
        panelUtama.setLayout(new CardLayout());
        
        setResizable(false);
        
        panelUtama.add(new MenuUtama(), "menu");
        panelUtama.add(new Login(), "login");
        kelolaAkun = new KelolaAkun();
        panelUtama.add(kelolaAkun, "kelolaAkun");

        antreanDokter = new AntreanDokter();
        panelUtama.add(antreanDokter, "antreanDokter");        
        
        panelUtama.add(new KelolaPasien(), "kelolaPasien");
        
        daftarkanKunjungan = new DaftarkanKunjungan();
        panelUtama.add(daftarkanKunjungan, "daftarkanKunjungan");
        
        formPemeriksaan = new FormPemeriksaan();
        panelUtama.add(formPemeriksaan, "formPemeriksaan");
        
        perbaruiStatus = new PerbaruiStatus();
        panelUtama.add(perbaruiStatus, "perbaruiStatus");

        riwayatKunjungan = new RiwayatKunjungan();
        panelUtama.add(riwayatKunjungan, "riwayatKunjungan");
        
        riwayatKunjunganDokter = new RiwayatKunjunganDokter();
        panelUtama.add(
            riwayatKunjunganDokter,
            "riwayatKunjunganDokter"
        );
        
        CardLayout cl = (CardLayout) panelUtama.getLayout();

        cl.show(panelUtama, "menu");

        setLocationRelativeTo(null);
    }
    
    public void tampilkanDashboardAdmin(Akun akun) {
        if (dashboardAdmin != null) {
            panelUtama.remove(dashboardAdmin);
        }

        dashboardAdmin = new DashboardAdmin(akun);
        panelUtama.add(dashboardAdmin, "dashboardAdmin");

        java.awt.CardLayout cl =
            (java.awt.CardLayout) panelUtama.getLayout();

        cl.show(panelUtama, "dashboardAdmin");

        panelUtama.revalidate();
        panelUtama.repaint();
        
    }
    public void tampilkanDashboardPetugas(Akun akun) {
        akunPetugasAktif = akun;
        
        if (dashboardPetugas != null) {
            panelUtama.remove(dashboardPetugas);
        }

        dashboardPetugas = new DashboardPetugas();

        dashboardPetugas.setInfoPengguna(
            akun.getNama(),
            akun.getNip()
        );

        panelUtama.add(dashboardPetugas, "dashboardPetugas");

        CardLayout cl = (CardLayout) panelUtama.getLayout();
        cl.show(panelUtama, "dashboardPetugas");

        panelUtama.revalidate();
        panelUtama.repaint();
        
    }
        public Akun getAkunPetugasAktif() {
        return akunPetugasAktif;
    }
        public Akun getAkunDokterAktif() {
        return akunDokterAktif;
    }    
        public PerbaruiStatus getPerbaruiStatus() {
        return perbaruiStatus;
    }
        
    public void tampilkanDaftarkanKunjungan() {
        if (akunPetugasAktif == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Silakan login sebagai petugas terlebih dahulu!");
            return;
        }

        daftarkanKunjungan.setInfoPetugas(akunPetugasAktif.getNip());

        tampil("daftarkanKunjungan");
    }
    public void tampilkanDashboardDokter(Akun akun) {
        if (dashboardDokter != null) {
            panelUtama.remove(dashboardDokter);
        }

        dashboardDokter = new DashboardDokter(akun);
        dashboardDokter.setInfoPengguna(
            akun.getNama(),
            akun.getNip()
        );
        panelUtama.add(dashboardDokter, "dashboardDokter");

        CardLayout cl = (CardLayout) panelUtama.getLayout();
        cl.show(panelUtama, "dashboardDokter");

        panelUtama.revalidate();
        panelUtama.repaint();
        akunDokterAktif = akun;
        antreanDokter.setAkunDokter(akun);
    }
    
    public FormPemeriksaan getFormPemeriksaan() {
    return formPemeriksaan;
}
    public void tampilkanRiwayatKunjungan(String halamanDashboard) {
        riwayatKunjungan.setHalamanDashboardAsal(halamanDashboard);
        tampil("riwayatKunjungan");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelUtama = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PulseCare");
        setSize(new java.awt.Dimension(565, 416));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUtama.setPreferredSize(new java.awt.Dimension(806, 550));
        panelUtama.setRequestFocusEnabled(false);
        panelUtama.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 806, 550));

        setBounds(0, 0, 820, 558);
    }// </editor-fold>//GEN-END:initComponents
    public void tampil(String namaHalaman) {
        CardLayout cl =
                (CardLayout) panelUtama.getLayout();

        cl.show(panelUtama, namaHalaman);

        if (namaHalaman.equals("antreanDokter")) {
            antreanDokter.muatAntreanDokter();
        }
        
        if (namaHalaman.equals("perbaruiStatus")) {
            perbaruiStatus.muatDataHariIni();
        }

        if (namaHalaman.equals("dashboardPetugas")
                && dashboardPetugas != null) {
            dashboardPetugas.revalidate();
            dashboardPetugas.repaint();
        }
        
    if (namaHalaman.equals("riwayatKunjunganDokter")) {
        riwayatKunjunganDokter.refreshData();
    }

        panelUtama.revalidate();
        panelUtama.repaint();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : 
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | 
                 javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelUtama;
    // End of variables declaration//GEN-END:variables

}
