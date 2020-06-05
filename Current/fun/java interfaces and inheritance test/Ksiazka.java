import java.util.ArrayList;
import java.util.List;

public class Ksiazka extends Pozycja {

    private int liczbaStron;
    private List<Autor> listaAutorow = new ArrayList<>();

    public Ksiazka(){};

    public Ksiazka(String tytul, int id, String wydawnictwo, int rokWydania, int liczbaStron, Autor[] listaAutorow){
        super(tytul, id, wydawnictwo, rokWydania);
        this.liczbaStron = liczbaStron;
        for(Autor autor : listaAutorow){
            dodajAutora(autor);
        }
    }

    public void dodajAutora(Autor autor){
        listaAutorow.add(autor);
    }

    private String wypiszAutorow(){
        String autorzy = "";
        for(Autor autor : listaAutorow){
            autorzy += autor.wypiszInfo() + " , ";
        }
        return autorzy;
    }

    @Override
    public String wypiszInfo(){
        return "Tytul: " + String.format("%-30s",tytul) + "id: " + String.format("%-10d", id) + "Wydawnictwo: "
                + String.format("%-20s",wydawnictwo) + "Rok wydania: " + String.format("%-10d", rokWydania)
                + "Liczba Stron: " + String.format("%-10d", liczbaStron) + "Autorzy: " + wypiszAutorow();
    }
}
