import java.util.ArrayList;
import java.util.Collections;

public class Sorts {


    private int size;                    //liczba elementów listy
    private int compareCounter;
    private int swapCounter;
    public ArrayList<Word> wordList;     //wewnętrzna lista Wordów na której będą przeprowadzane operacje

    public void setSize(int liczbaElem) { this.size = liczbaElem; }

    public int updateSize(){
        setSize(wordList.size());
        return size;
    }



    public static ArrayList<Word> kopia(ArrayList<Word> listaWejscie){          //metoda, która głęboko kopiuje listę elementów
        ArrayList<Word> nowaLista = new ArrayList<>();

        for(Word element : listaWejscie){
            nowaLista.add(new Word(element.getWord(), element.getIndex()));
        }

        return nowaLista;
    }

    public void combSort(ArrayList<Word> listaWejscie){

        int size = listaWejscie.size();
        int odstep = size;                                      //aby móc odpowiednio zmniejszać odstęp, inicjuję wartosć początkową jako rozmiar listy
        boolean swap = true;
        Word left, right;
        compareCounter = 0;                                     //resetuję liczniki, aby pod koniec sortowania pokazały prawidłowy wynik
        swapCounter = 0;


        while(swap == true || odstep != 1){                     //dopóki porównywane elementy nie sąsiadują ze sobą lub w poprzedniej iteracji pętli doszło do zamiany

            odstep = znajdzOdstep(odstep);                      //zmniejszam odstęp, aby mieć możliwość dokładniejszego porównania wartości
            swap = false;                                       //dzięki temu sprawdzę czy w tej iteracji dojdzie do zamiany elementów

            for (int i = 0; i < size - odstep; i++)             //dopóki element porównywany z "prawej" strony nie będzie ostatnim elementem listy
            {
                left = listaWejscie.get(i);                     //biorę dwa elementy, oddalone od siebie o 'odstęp'
                right = listaWejscie.get(i + odstep);

                if (left.compareTo(right))                      //metoda compareTo znajduje się w klasie Word
                {
                    Collections.swap(listaWejscie, i, i + odstep);
                    swap = true;
                    swapCounter++;
                }
                compareCounter++;                               //doszło do porównania elementów, więc inkrementuję licznik porównań
            }

        }

        /*System.out.println(String.format("%8s","CombSort") + String.format("%19d", compareCounter)
                + String.format("%12d", swapCounter));*/
    }
    public int znajdzOdstep(int odstep){                        //metoda zmniejszająca odstęp pomiędzy porównywanymi elementami
        odstep = (odstep * 10) / 13;                            // 1/1.3 to optymalny iloraz wyznaczony empirycznie
        if (odstep < 1)
            return 1;                                           //elementy sąsiadują ze sobą
        return odstep;
    }
}
