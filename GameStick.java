import java.util.Scanner;

public class GameStick {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // ===== Pemilihan Mode Permainan =====
        System.out.println("=== GAME TONGKAT ===");
        System.out.println("Pilih Mode Permainan:");
        System.out.println("1. Easy");
        System.out.println("2. Hard");
        System.out.print("Masukkan pilihan (1 atau 2): ");
        int mode = in.nextInt(); // Input mode disimpan dalam variabel 'mode'

        // ===== Menentukan jumlah tongkat secara acak (10–30) =====
        int tongkat = (int) (Math.random() * 21) + 10;
        System.out.println("\nTongkat awal: " + tongkat);

        // ===== Menentukan giliran pertama berdasarkan mode dan jumlah tongkat =====
        boolean user = tentukanGiliranAwal(mode, tongkat);

        // ===== Gameplay: Giliran bergantian antara user dan komputer =====
        while (tongkat > 1) {
            System.out.println("\nJumlah tongkat saat ini: " + tongkat);

            if (user) {
                // Giliran user mengambil tongkat
                int ambil = ambilTongkatUser(in, tongkat);
                tongkat -= ambil; // Kurangi jumlah tongkat
                user = false;     // Ganti giliran ke komputer
            } else {
                // Giliran komputer mengambil tongkat
                int ambil = ambilTongkatKomputer(mode, tongkat);
                System.out.println("Komputer mengambil " + ambil + " tongkat");
                tongkat -= ambil; // Kurangi jumlah tongkat
                user = true;      // Ganti giliran ke user
            }
        }

        // ===== Menentukan dan menampilkan hasil akhir permainan =====
        tampilkanHasil(user);
    }

    // ===== Fungsi untuk menentukan siapa yang jalan dulu =====
    public static boolean tentukanGiliranAwal(int mode, int tongkat) {
        if (mode == 2) {
            // Mode Hard: jika tongkat % 4 == 1 (posisi kalah), biarkan user mulai duluan
            return (tongkat % 4 == 1);
        } else {
            // Mode Easy: biarkan user mulai jika posisi bukan kalah
            return (tongkat % 4 != 1);
        }
    }

    // ===== Fungsi untuk menerima input tongkat dari user =====
    public static int ambilTongkatUser(Scanner in, int tongkat) {
        int ambil = 0;
        while (true) {
            System.out.print("Ambil 1, 2, atau 3 tongkat: ");
            ambil = in.nextInt();
            // Validasi input: harus 1–3 dan tidak boleh melebihi tongkat tersisa
            if (ambil >= 1 && ambil <= 3 && ambil < tongkat) {
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
        return ambil;
    }

    // ===== Fungsi strategi komputer dalam mengambil tongkat =====
    public static int ambilTongkatKomputer(int mode, int tongkat) {
        int ambil;
        if (mode == 2) {
            // Mode Hard: strategi optimal - sisakan tongkat agar user berada di posisi 4n + 1
            ambil = (tongkat - 1) % 4;
            if (ambil == 0) ambil = 3; // fallback jika hasil 0
        } else {
            // Mode Easy: komputer bermain lemah agar user menang
            if (tongkat % 4 == 0) {
                ambil = 1; // sengaja pilih angka kecil
            } else {
                ambil = (4 - (tongkat % 4)) % 4; // strategi salah
                if (ambil == 0) ambil = 1;
            }
        }

        // Jangan sampai komputer mengambil semua tongkat terakhir
        if (ambil >= tongkat) {
            ambil = tongkat - 1;
        }

        return ambil;
    }

    // ===== Fungsi untuk menampilkan hasil akhir permainan =====
    public static void tampilkanHasil(boolean user) {
        if (user) {
            // Jika giliran terakhir adalah user, maka user harus ambil tongkat terakhir → kalah
            System.out.println("\nTersisa 1 tongkat. Anda harus mengambilnya.");
            System.out.println("Anda kalah! Komputer menang.");
        } else {
            // Jika giliran terakhir adalah komputer, maka komputer kalah
            System.out.println("\nTersisa 1 tongkat. Komputer harus mengambilnya.");
            System.out.println("Komputer kalah! Anda menang.");
        }
    }
}
