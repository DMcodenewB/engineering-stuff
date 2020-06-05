public class Czasopismo extends Pozycja {

    private int numer;

    public Czasopismo(){};

    public Czasopismo(String tytul, int id, String wydawnictwo, int rokWydania, int numer) {
        super(tytul, id, wydawnictwo, rokWydania);
        this.numer = numer;
    }

    @Override
    public String wypiszInfo(){
        return "Tytul: " + String.format("%-30s",tytul) + "id: " + String.format("%-10d", id) + "Wydawnictwo: "
                + String.format("%-20s",wydawnictwo) + "Rok wydania: " + String.format("%-10d", rokWydania)
                + "Numer: " + String.format("%-10d", numer);
    }
}
