import java.util.ArrayList;
import java.util.List;

public class Katalog implements IZarzadzaniePozycjami {

    private String dzialTematyczny;
    private List<Pozycja> listaPozycji = new ArrayList<>();

    public Katalog(){};
    public Katalog(String dzialTematyczny){
        this.dzialTematyczny = dzialTematyczny;
    }

    public void dodajPozycje(Pozycja pozycja){
        listaPozycji.add(pozycja);
    }

    public void wypiszWszystkiePozycje(){
        for(Pozycja pozycja : listaPozycji){
            System.out.println(pozycja.wypiszInfo());
        }
        System.out.println();
    }

    public Pozycja znajdzPozycjePoTytule(String tytul){
        Pozycja found = null;
        for(Pozycja poz : listaPozycji){
            if(poz.tytul.equals(tytul)) {
                found = poz;
            }
        }
        return found;
    }
    public Pozycja znajdzPozycjePoId(int id){
        Pozycja found = null;
        for(Pozycja poz : listaPozycji){
            if(poz.id == id) {
                found = poz;
            }
        }
        return found;
    }

    public String getDzialTematyczny(){
        return dzialTematyczny;
    }

}
