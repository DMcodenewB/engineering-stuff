import java.util.ArrayList;
import java.util.Collections;

public class OperationClass {


    private int liczbaElem;     //liczba elementów listy
    private Integer min;        //element najmniejszy
    private Integer max;        //element największy
    private double median;      //mediana (double, ponieważ wynik może być liczbą niecałkowitą)

    public ArrayList<Integer> listaInteger;     //wewnętrzna lista Integerów, na której będą przeprowadzane operacje

    //ponieważ pola są prywatne, to settery służą jedynie do zmiany ich wartości

    public void setLiczbaElem(int liczbaElem) { this.liczbaElem = liczbaElem; }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    //metody właściwe, wykorzystujące listę wewnętrzną oraz settery
    //przypominam że wartość zwracana jest prawidłowa dopiero dla posortowanej listy

    public int podajLiczbeElementow(){
        setLiczbaElem(listaInteger.size());
        return liczbaElem;
    }

    public Integer podajMin(){
        setMin(listaInteger.get(0));
        return min;
    }

    public Integer podajMax(){
        setMax(listaInteger.get(listaInteger.size() - 1));
        return max;
    }

    public double podajMediane(){
        double product1 = listaInteger.get((listaInteger.size()/2));        //element środkowy lub "prawy" od środka dla listy o parzystym rozmiarze
        double product2 = listaInteger.get((listaInteger.size()/2)-1);      //element "lewy" od środka dla listy o parzystym rozmiarze

        if((listaInteger.size())%2!=0){                 //sprawdzenie czy lista zawiera (nie)parzystą liczbę elementów
            setMedian(product1);
        }else{
            setMedian((product1+product2)/2);
        }
        return median;
    }

    public ArrayList<Integer> sortujListe(ArrayList<Integer> listaWejscie){            //metoda sortująca listę Integerów

        int index = 0;                      //bieżący indeks listy
        int startIndex = 0;                 //indeks elementu od którego zaczynamy sortowanie
        int endIndex = listaWejscie.size(); //indeks elementu na którym kończymy sortowanie
        Integer left, right;                //pola Integer przechowujące referencję na elementy listy
        boolean swap = true;                //wartość logiczna informująca czy podczas przechodzenia po liście doszło do zamiany elementów wewnątrz struktury

        System.out.println("Sortuje liste... ");
        while(swap) {                       //jeśli zaszła zamiana elementów, to sortuj dalej
            swap = false;                   //tę wartość zmienimy jeśli w dalszej części metody zajdzie zmiana
            for (index = startIndex; index < endIndex - 1; index++) {     //sortujemy "od lewej do prawej", zaczynając od elementu, który jest nieposortowany
                left = listaWejscie.get(index);
                right = listaWejscie.get(index + 1);                      //przypisanie elementów do zmiennych
                if (left > right) {                                       //i porównanie ich
                    Collections.swap(listaWejscie, index, index + 1);  //metoda klasy Collections, zamienia miejscami dwa elementy listy.
                    swap = true;                                          //doszło do zamiany, więc zmieniamy stan zmiennej swap
                }
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
                }
            }
            startIndex++;                                                 //element na lewym końcu na pewno jest posortowany
        }


        System.out.println("Lista posortowana. ");
        return listaWejscie;                                              //zwracana jest posortowana lista

    }

}
