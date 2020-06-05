import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;

public class OperationClass {


    private int liczbaElem;     //liczba elementów listy
    private int compareCounter;
    private int swapCounter;

    public ArrayList<Integer> listaInteger;     //wewnętrzna lista Integerów, na której będą przeprowadzane operacje

    //ponieważ pola są prywatne, to settery służą jedynie do zmiany ich wartości

    public void setLiczbaElem(int liczbaElem) { this.liczbaElem = liczbaElem; }

    //metody właściwe, wykorzystujące listę wewnętrzną oraz settery

    public int podajLiczbeElementow(){
        setLiczbaElem(listaInteger.size());
        return liczbaElem;
    }

    public void run(String nazwaPliku){
        System.out.print(nazwaPliku + ", " + podajLiczbeElementow() + " elementow: ");

        cocktailSort(kopia(listaInteger));
        combSort(kopia(listaInteger));


    }

    public static ArrayList<Integer> kopia(ArrayList<Integer> listaWejscie){ //metoda, która głęboko kopiuje listę elementów
        ArrayList<Integer> nowaLista = new ArrayList<>();

        for(Integer element : listaWejscie){
            nowaLista.add(element);
        }

        return nowaLista;
    }



    public void cocktailSort(ArrayList<Integer> listaWejscie){            //metoda sortująca listę Integerów

        int index = 0;                      //bieżący indeks listy
        int startIndex = 0;                 //indeks elementu od którego zaczynamy sortowanie
        int endIndex = listaWejscie.size(); //indeks elementu na którym kończymy sortowanie
        Integer left, right;                //pola Integer przechowujące referencję na elementy listy
        boolean swap = true;                //wartość logiczna informująca czy podczas przechodzenia po liście doszło do zamiany elementów wewnątrz struktury
        compareCounter = 0;
        swapCounter = 0;

        while(swap) {                       //jeśli zaszła zamiana elementów, to sortuj dalej
            swap = false;                   //tę wartość zmienimy jeśli w dalszej części metody zajdzie zmiana
            for (index = startIndex; index < endIndex - 1; index++) {     //sortujemy "od lewej do prawej", zaczynając od elementu, który jest nieposortowany
                left = listaWejscie.get(index);
                right = listaWejscie.get(index + 1);                      //przypisanie elementów do zmiennych
                if (left > right) {                                       //i porównanie ich
                    Collections.swap(listaWejscie, index, index + 1);  //metoda klasy Collections, zamienia miejscami dwa elementy listy.
                    swap = true;
                    swapCounter++;                                        //doszło do zamiany, więc zmieniam stan zmiennej swap i inkrementuję licznik
                }
                compareCounter++;                                         //nastąpiło porównanie więc powiększam compareCounter
            }
            if(swap == false) break;                                      //jeśli do zamiany nie doszło, to lista jest posortowana i można zakończyć działanie metody

            swap = false;                                                 //przygotowanie do sortowania w drugą stronę listy
            endIndex--;                                                   //ostatni element listy na pewno jest na swoim miejscu, wynika to z kodu powyżej

            for (index = endIndex - 1; index >= startIndex; index--) {    //sortujemy "od prawej do lewej", zaczynając od elementu, który jest nieposortowany
                left = listaWejscie.get(index);
                right = listaWejscie.get(index + 1);
                if (left > right) {
                    Collections.swap(listaWejscie, index, index + 1);  //analogicznie jak wyżej
                    swap = true;
                    swapCounter++;
                }
                compareCounter++;
            }
            startIndex++;                                                 //element na lewym końcu na pewno jest posortowany
        }


        System.out.println(String.format("%-1s", "CocktailSort") + String.format("%15d", compareCounter)
                + String.format("%12d", swapCounter));
    }

    public void combSort(ArrayList<Integer> listaWejscie){

        int size = listaWejscie.size();
        int odstep = size;                                      //aby móc odpowiednio zmniejszać odstęp, inicjuję wartosć początkową jako rozmiar listy
        boolean swap = true;
        Integer left, right;
        compareCounter = 0;                                     //resetuję liczniki, aby pod koniec sortowania pokazały prawidłowy wynik
        swapCounter = 0;

        while(swap == true || odstep != 1){                     //dopóki porównywane elementy nie sąsiadują ze sobą lub w poprzedniej iteracji pętli doszło do zamiany

            odstep = znajdzOdstep(odstep);                      //zmniejszam odstęp, aby mieć możliwość dokładniejszego porównania wartości
            swap = false;                                       //dzięki temu sprawdzę czy w tej iteracji dojdzie do zamiany elementów

            for (int i = 0; i < size - odstep; i++)             //dopóki element porównywany z "prawej" strony nie będzie ostatnim elementem listy
            {
                left = listaWejscie.get(i);                     //biorę dwa elementy, oddalone od siebie o 'odstęp'
                right = listaWejscie.get(i + odstep);

                if (left > right)                               //jeśli "lewy" element jest większy od "prawego"
                {
                    Collections.swap(listaWejscie, i, i + odstep);
                    swap = true;
                    swapCounter++;                              //to zamieniam elementy miejscami i inkrementuję licznik zamian
                }
                compareCounter++;                               //doszło do porównania elementów, więc inkrementuję licznik porównań
            }

        }

        System.out.println(String.format("%37s","CombSort") + String.format("%19d", compareCounter)
                + String.format("%12d", swapCounter));
    }

    public int znajdzOdstep(int odstep){                        //metoda zmniejszająca odstęp pomiędzy porównywanymi elementami
        odstep = (odstep * 10) / 13;                            // 1/1.3 to optymalny iloraz wyznaczony empirycznie
        if (odstep < 1)
            return 1;                                           //elementy sąsiadują ze sobą
        return odstep;
    }
}
