
public class Testowanie {

    public static void main(String[] args){
        Biblioteka biblioteka1 = new Biblioteka();

        Katalog katalog1 = new Katalog("Popularnonaukowe");
        Katalog katalog2 = new Katalog("Fikcja");

        Osoba autor1 = new Autor("John", "Newman", "amerykańska");
        Osoba autor2 = new Autor("Alfred", "Szklarski", "polska");
        Osoba autor3 = new Autor("Jonathan", "Brown", "angielska");
        Osoba autor4 = new Autor("Karol", "Marks", "niemiecka");
        Osoba bibliotekarz1 = new Bibliotekarz("Michał", "Kozłowski", "2020/10/02", 3200);
        Osoba bibliotekarz2 = new Bibliotekarz("Wiktor", "Dulski", "2018/12/03", 3500);

        Pozycja poz1 = new Ksiazka("Wzdłuż wybrzeża", 1134, "Znak", 1998, 359, new Autor[]{(Autor)autor1, (Autor)autor2});
        Pozycja poz2 = new Ksiazka("Owoce morza i jeziora", 2395, "Vegeta", 2005, 114, new Autor[]{(Autor)autor3});
        Pozycja poz3 = new Ksiazka("Wiek wszechświata a dinozaury", 1430, "Mercurius", 1976, 97, new Autor[]{(Autor)autor2, (Autor)autor4});
        Pozycja poz4 = new Czasopismo("Podróże małe i duże", 123, "Tatry", 2014, 18);
        Pozycja poz5 = new Czasopismo("W ostrym cieniu mgły", 2137, "Duda inc.", 2020, 3);

        biblioteka1.dodajBibliotekarza((Bibliotekarz)bibliotekarz1);
        biblioteka1.dodajBibliotekarza((Bibliotekarz)bibliotekarz2);

        katalog2.dodajPozycje(poz1);
        katalog1.dodajPozycje(poz2);
        katalog1.dodajPozycje(poz3);
        katalog1.dodajPozycje(poz4);
        katalog2.dodajPozycje(poz5);

        biblioteka1.dodajKatalog(katalog1);
        biblioteka1.dodajKatalog(katalog2);

        System.out.println("\n------------------\n");

        biblioteka1.wypiszWszystkiePozycje();

        System.out.println("\n------------------\n");

        System.out.println(biblioteka1.znajdzPozycjePoId(2137).wypiszInfo());

        System.out.println("\n------------------\n");

        biblioteka1.wypiszBibliotekarzy();

        System.out.println("\n------------------\n");

    }
}
