import java.util.ArrayList;
import java.util.List;

public class Biblioteka implements IZarzadzaniePozycjami{

    private String adres;
    private List<Katalog> listaKatalogow = new ArrayList<>();
    private List<Bibliotekarz> listaBibliotekarzy = new ArrayList<>();

    public Biblioteka(){};
    public Biblioteka(String adres){
        this.adres = adres;
    }
    public void dodajBibliotekarza(Bibliotekarz bib){
        listaBibliotekarzy.add(bib);
    }
    public void wypiszBibliotekarzy(){
        for(Bibliotekarz bib : listaBibliotekarzy){
            System.out.println(bib.wypiszInfo());
        }
    }
    public void dodajKatalog(Katalog kat){
        listaKatalogow.add(kat);
    }
    public void dodajPozycje(Pozycja poz, String dzialTematyczny){
        for(Katalog kat : listaKatalogow){
            if (kat.getDzialTematyczny().equals(dzialTematyczny)){
                kat.dodajPozycje(poz);
            }
        }
    }
    public Pozycja znajdzPozycjePoTytule(String tytul){
        Pozycja found = null;
        for(Katalog kat : listaKatalogow){
            found = kat.znajdzPozycjePoTytule(tytul);
            if(found != null) return found;
        }
        return found;
    }
    public Pozycja znajdzPozycjePoId(int id){
        Pozycja found = null;
        for(Katalog kat : listaKatalogow){
            found = kat.znajdzPozycjePoId(id);
            if(found != null) return found;
        }
        return found;

    }
    public void wypiszWszystkiePozycje(){
        for(Katalog kat : listaKatalogow){
            System.out.println("Katalog: " + kat.getDzialTematyczny());
            kat.wypiszWszystkiePozycje();
        }
    }


}
