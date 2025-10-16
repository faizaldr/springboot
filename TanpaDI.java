class Mesin {
    public void nyalakan(){
        System.out.println("Mesin dinyalakan...");
    }
}

// mesin listrik
class MesinListrik{
    public void nyalakan(){
        System.out.println("mesin listrik menyala...");
    }
}

class Mobil {
    // buat mesin 
    private Mesin mesin = new Mesin();

    // jalankan mobil
    public void jalan(){
        mesin.nyalakan();
        System.out.println("Mobil berjalan...");
    }
}

public class TanpaDI {
    public static void main(String[] args) {
        Mobil mobil = new Mobil();
        mobil.jalan();
    }
}
