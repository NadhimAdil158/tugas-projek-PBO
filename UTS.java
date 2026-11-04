import java.util.Scanner;

public class UTS {

    static Scanner input = new Scanner(System.in);

    // Konstanta tarif laundry
    static final double TARIF_REGULER = 7000;
    static final double TARIF_EXPRESS = 12000;
    static final int MAKS_TRANSAKSI = 100;

    static TransaksiLaundry[] daftarTransaksi =
            new TransaksiLaundry[MAKS_TRANSAKSI];

    static int jumlahTransaksi = 0;

    public static void main(String[] args) {
        int pilihan;

        do {
            System.out.println("\n=== SISTEM INFORMASI LAUNDRY ===");
            System.out.println("1. Tambah Transaksi");
            System.out.println("2. Lihat Semua Transaksi");
            System.out.println("3. Ubah Status Laundry");
            System.out.println("4. Laporan Pendapatan");
            System.out.println("5. Keluar");

            pilihan = bacaAngka("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tambahTransaksi();
                    break;
                case 2:
                    tampilkanTransaksi();
                    break;
                case 3:
                    ubahStatus();
                    break;
                case 4:
                    laporanPendapatan();
                    break;
                case 5:
                    System.out.println(
                        "Terima kasih telah menggunakan sistem laundry."
                    );
                    break;
                default:
                    System.out.println("Menu tidak tersedia!");
            }

        } while (pilihan != 5);

        input.close();
    }

    // Input angka tanpa Exception Handling
    static int bacaAngka(String pesan) {
        System.out.print(pesan);
        int angka = input.nextInt();
        input.nextLine();
        return angka;
    }

    // Input berat cucian tanpa Exception Handling
    static double bacaDesimal(String pesan) {
        System.out.print(pesan);
        double angka = input.nextDouble();
        input.nextLine();

        if (angka > 0) {
            return angka;
        } else {
            System.out.println("Angka harus lebih dari 0!");
            return bacaDesimal(pesan);
        }
    }

    // Menambah transaksi
    static void tambahTransaksi() {
        if (jumlahTransaksi >= MAKS_TRANSAKSI) {
            System.out.println("Penyimpanan transaksi penuh!");
            return;
        }

        System.out.print("Nama pelanggan: ");
        String nama = input.nextLine().trim();

        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong!");
            return;
        }

        System.out.print("Nomor telepon: ");
        String telepon = input.nextLine().trim();

        if (telepon.isEmpty()) {
            System.out.println("Nomor telepon tidak boleh kosong!");
            return;
        }

        Pelanggan pelanggan = new Pelanggan(nama, telepon);

        System.out.println("\nJenis layanan:");
        System.out.println("1. Reguler - Rp7.000/kg");
        System.out.println("2. Express - Rp12.000/kg");

        int jenis = bacaAngka("Pilih layanan: ");

        if (jenis != 1 && jenis != 2) {
            System.out.println("Jenis layanan tidak valid!");
            return;
        }

        double berat = bacaDesimal("Berat cucian (kg): ");

        // Overloading
        TransaksiLaundry transaksi =
                new TransaksiLaundry(pelanggan, jenis, berat);

        daftarTransaksi[jumlahTransaksi] = transaksi;
        jumlahTransaksi++;

        System.out.println("\nTransaksi berhasil ditambahkan!");

        // Menggunakan Method Overloading dengan parameter String
        transaksi.tampilkanInfo("DETAIL TRANSAKSI BARU");
    }

    // Menampilkan seluruh transaksi
    static void tampilkanTransaksi() {
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        for (int i = 0; i < jumlahTransaksi; i++) {
            System.out.println("\n-------------------------");
            daftarTransaksi[i].tampilkanInfo();
        }
    }

    // Mengubah status laundry
    static void ubahStatus() {
        if (jumlahTransaksi == 0) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        tampilkanTransaksi();

        int id = bacaAngka("\nMasukkan ID transaksi: ");

        for (int i = 0; i < jumlahTransaksi; i++) {
            if (daftarTransaksi[i].getId() == id) {

                System.out.println("\nPilih status baru:");
                System.out.println("1. Diproses");
                System.out.println("2. Selesai");
                System.out.println("3. Diambil");

                int pilihan = bacaAngka("Status baru: ");

                switch (pilihan) {
                    case 1:
                        daftarTransaksi[i].setStatus("Diproses");
                        break;
                    case 2:
                        daftarTransaksi[i].setStatus("Selesai");
                        break;
                    case 3:
                        daftarTransaksi[i].setStatus("Diambil");
                        break;
                    default:
                        System.out.println(
                            "Pilihan status tidak valid!"
                        );
                        return;
                }

                System.out.println("Status berhasil diperbarui.");
                return;
            }
        }

        System.out.println("ID transaksi tidak ditemukan.");
    }

    // Laporan pendapatan
    static void laporanPendapatan() {
        double totalPendapatan = 0;

        for (int i = 0; i < jumlahTransaksi; i++) {
            totalPendapatan +=
                    daftarTransaksi[i].getTotalBiaya();
        }

        System.out.println("\n=== LAPORAN LAUNDRY ===");
        System.out.println(
            "Jumlah transaksi: " + jumlahTransaksi
        );
        System.out.println(
            "Total pendapatan: Rp" + (int) totalPendapatan
        );
    }

    // Class pelanggan
    static class Pelanggan {
        private String nama;
        private String telepon;

        public Pelanggan(String nama, String telepon) {
            this.nama = nama;
            this.telepon = telepon;
        }

        public String getNama() {
            return nama;
        }

        public String getTelepon() {
            return telepon;
        }
    }

    // Class transaksi laundry
    static class TransaksiLaundry {

        private static int penghitungId = 0;

        private final int id;
        private final Pelanggan pelanggan;
        private final int jenisLayanan;
        private final double berat;
        private final double tarif;
        private final double totalBiaya;
        private String status;

        //OVERLOADING 
        public TransaksiLaundry(
                Pelanggan pelanggan,
                int jenisLayanan,
                double berat) {

            this(
                pelanggan,
                jenisLayanan,
                berat,
                jenisLayanan == 1
                    ? TARIF_REGULER
                    : TARIF_EXPRESS
            );
        }

        //OVERLOADING
        public TransaksiLaundry(
                Pelanggan pelanggan,
                int jenisLayanan,
                double berat,
                double tarif) {

            penghitungId++;
            this.id = penghitungId;
            this.pelanggan = pelanggan;
            this.jenisLayanan = jenisLayanan;
            this.berat = berat;
            this.tarif = tarif;
            this.totalBiaya = berat * tarif;
            this.status = "Diproses";
        }

        public int getId() {
            return id;
        }

        public double getTotalBiaya() {
            return totalBiaya;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        //OVERLOADING
        public void tampilkanInfo() {
            String layanan = (jenisLayanan == 1)
                    ? "Reguler" : "Express";

            System.out.println("ID Transaksi: " + id);
            System.out.println(
                "Nama: " + pelanggan.getNama()
            );
            System.out.println(
                "Telepon: " + pelanggan.getTelepon()
            );
            System.out.println("Layanan: " + layanan);
            System.out.println("Berat: " + berat + " kg");
            System.out.printf(
                "Tarif: Rp%,.0f%n", tarif
            );
            System.out.printf(
                "Total biaya: Rp%,.0f%n", totalBiaya
            );
            System.out.println("Status: " + status);
        }

        //OVERLOADING
        public void tampilkanInfo(String judul) {
            System.out.println("\n=== " + judul + " ===");
            tampilkanInfo();
        }
    }
}