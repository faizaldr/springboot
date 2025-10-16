package springbootoktober;

public class Produk {
    // atribut
    private String nama;
    private double harga;
    private int stok;

    // Method
    // konstruktor
    public Produk(String nama, double harga, int stok){
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // setter dan getter, berinteraksi - modifikasi data di dalam objek
    // ambil nama produk
    public String getNama(){
        return nama;
    }

    public double getHarga(){
        return harga;
    }

    public int getStok(){
        return stok;
    }

    // getInfo
    public void showInfo(){
        System.out.println(nama + " - Rp " + harga + " ("+stok+" tersedia)");
    }

    // setter, set stok
    public int decStok(){
        this.stok = this.stok - 1;
        return this.stok;
    }
}
