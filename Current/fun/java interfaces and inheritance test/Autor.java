
public class Autor extends Osoba{

    private String narodowosc;

    public Autor(){};
    public Autor(String imie, String nazwisko, String narodowosc){
        super(imie, nazwisko);
        this.narodowosc = narodowosc;
    }

    public String wypiszInfo() {
        return imie + " " + nazwisko + "(narodowosc: " + narodowosc + ")";
    }
}
