
import java.util.Scanner;

public class UTS {

    static Scanner input = new Scanner(System.in);

    static final int MAX = 100;
    static final double TARIF_REGULER = 7000;
    static final double TARIF_EXPRESS = 12000;

    static int jumlahTransaksi = 0;

    static int[] id = new int[MAX];
    static String[] nama = new String[MAX];
    static String[] telepon = new String[MAX];
    static int[] jenis = new int[MAX];
    static double[] berat = new double[MAX];
    static double[] tarif = new double[MAX];
    static double[] total = new double[MAX];
    static String[] status = new String[MAX];

    public static void main(String[] args) {
        int pilihan;

        do {
            System.out.println("\n=== SISTEM LAUNDRY ===");
            System.out.println("1. Tambah Transaksi");
            System.out.println("2. Tampilkan Transaksi");
            System.out.println("3. Ubah Status");
            System.out.println("4. Laporan Pendapatan");
            System.out.println("5. Keluar");
            System.out.print("Pilih: ");

            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1: tambahTransaksi(); break;
                case 2: tampilkanTransaksi(); break;
                case 3: ubahStatus(); break;
                case 4: laporanPendapatan(); break;
                case 5: System.out.println("Terima kasih."); break;
                default: System.out.println("Pilihan tidak tersedia.");
            }
        } while (pilihan != 5);
    }

    static void tambahTransaksi() {
        if (jumlahTransaksi >= MAX) {
            System.out.println("Data penuh.");
            return;
        }

        System.out.print("Nama: ");
        String n = input.nextLine();

        System.out.print("Telepon: ");
        String t = input.nextLine();

        int j;
        do {
            System.out.println("1. Reguler (Rp7000/kg)");
            System.out.println("2. Express (Rp12000/kg)");
            System.out.print("Pilih layanan: ");
            j = input.nextInt();
        } while (j != 1 && j != 2);

        System.out.print("Berat (kg): ");
        double b = input.nextDouble();
        input.nextLine();

        if (b <= 0) {
            System.out.println("Berat harus lebih dari 0.");
            return;
        }

        TransaksiLaundry tr = new TransaksiLaundry(n, t, j, b);

        int i = jumlahTransaksi;
        id[i] = tr.getId();
        nama[i] = tr.getNama();
        telepon[i] = tr.getTelepon();
        jenis[i] = tr.getJenis();
        berat[i] = tr.getBerat();
        tarif[i] = tr.getTarif();
        total[i] = tr.getTotal();
        status[i] = tr.getStatus();

        jumlahTransaksi++;
        tr.tampilkanInfo();
    }

    static void tampilkanTransaksi() {
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        for (int i = 0; i < jumlahTransaksi; i++) {
            TransaksiLaundry tr = new TransaksiLaundry(
                id[i], nama[i], telepon[i], jenis[i],
                berat[i], tarif[i], total[i], status[i]
            );

            tr.tampilkanInfo("Transaksi " + (i + 1));
        }
    }

    static void ubahStatus() {
        tampilkanTransaksi();

        if (jumlahTransaksi == 0) return;

        System.out.print("Nomor transaksi: ");
        int nomor = input.nextInt();
        input.nextLine();

        if (nomor < 1 || nomor > jumlahTransaksi) {
            System.out.println("Nomor tidak ditemukan.");
            return;
        }

        int i = nomor - 1;
        TransaksiLaundry tr = new TransaksiLaundry(
            id[i], nama[i], telepon[i], jenis[i],
            berat[i], tarif[i], total[i], status[i]
        );

        tr.setStatus("Selesai");
        status[i] = tr.getStatus();

        System.out.println("Status berhasil diubah.");
    }

    static void laporanPendapatan() {
        double pendapatan = 0;

        for (int i = 0; i < jumlahTransaksi; i++) {
            if (status[i].equals("Selesai")) {
                pendapatan += total[i];
            }
        }

        System.out.println("Jumlah transaksi: " + jumlahTransaksi);
        System.out.println("Pendapatan: Rp" + pendapatan);
    }

    static class TransaksiLaundry {
        private static int nomorId = 1;

        private int id;
        private String nama, telepon, status;
        private int jenis;
        private double berat, tarif, total;

        TransaksiLaundry(String nama, String telepon, int jenis, double berat) {
            this.id = nomorId++;
            this.nama = nama;
            this.telepon = telepon;
            this.jenis = jenis;
            this.berat = berat;

            if (jenis == 1) {
                tarif = TARIF_REGULER;
            } else {
                tarif = TARIF_EXPRESS;
            }

            total = berat * tarif;
            status = "Proses";
        }

        TransaksiLaundry(int id, String nama, String telepon, int jenis,
                         double berat, double tarif, double total, String status) {
            this.id = id;
            this.nama = nama;
            this.telepon = telepon;
            this.jenis = jenis;
            this.berat = berat;
            this.tarif = tarif;
            this.total = total;
            this.status = status;
        }

        public int getId() { return id; }
        public String getNama() { return nama; }
        public String getTelepon() { return telepon; }
        public int getJenis() { return jenis; }
        public double getBerat() { return berat; }
        public double getTarif() { return tarif; }
        public double getTotal() { return total; }
        public String getStatus() { return status; }

        public void setStatus(String status) {
            if (status.equals("Proses") || status.equals("Selesai")) {
                this.status = status;
            } else {
                System.out.println("Status tidak valid.");
            }
        }

        public void tampilkanInfo() {
            tampilkanInfo("Informasi Transaksi");
        }

        public void tampilkanInfo(String judul) {
            System.out.println("\n--- " + judul + " ---");
            System.out.println("ID: " + id);
            System.out.println("Nama: " + nama);
            System.out.println("Telepon: " + telepon);
            System.out.println("Layanan: " + (jenis == 1 ? "Reguler" : "Express"));
            System.out.println("Berat: " + berat + " kg");
            System.out.println("Tarif: Rp" + tarif);
            System.out.println("Total: Rp" + total);
            System.out.println("Status: " + status);
        }
    }
}
