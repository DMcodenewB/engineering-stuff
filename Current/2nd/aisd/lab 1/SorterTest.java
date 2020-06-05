import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

//Metod podajMax(), podajMin(), podajMediane() proszę używać na posortowanej liście, w przeciwnym razie wyniki będą nieprawidłowe.


public class SorterTest {

    public static File plik1 = new File("z1data1.csv");
    public static File plik2 = new File("z1data2.csv");

    public static void main(String[] args){

        OperationClass object1 = new OperationClass();
        OperationClass object2 = new OperationClass();                      //inicjalizacja dwóch obiektów służących do operacji na listach

        object1.listaInteger = kopia(czytajPlik(plik1));                    //skopiowanie obu list zczytanych z pliku do list wewnętrznych
        object2.listaInteger = kopia(czytajPlik(plik2));                    //posiadanych przez obiekty


        System.out.println("Testowa lista nr 1 przed posortowaniem: ");
        for (Integer i : object1.listaInteger){
            System.out.print(String.format("%2d |", i));
        }

        System.out.println("\nLiczba posiadanych elementow: " + object1.podajLiczbeElementow());
        object1.sortujListe(object1.listaInteger);

        System.out.println("Element najmniejszy: " + object1.podajMin());
        System.out.println("Element najwiekszy: " + object1.podajMax());
        System.out.println("Mediana: " + object1.podajMediane());

        System.out.println("Lista po posortowaniu: ");
        for (Integer i : object1.listaInteger){
            System.out.print(String.format("%2d |", i));
        }

        System.out.println("\n-----------------------------------------------------\n");

        System.out.println("Testowa lista nr 2 przed posortowaniem: ");
        for (Integer i : object2.listaInteger){
            System.out.print(String.format("%2d |", i));
        }

        System.out.println("\nLiczba posiadanych elementow: " + object2.podajLiczbeElementow());
        object1.sortujListe(object2.listaInteger);

        System.out.println("Element najmniejszy: " + object2.podajMin());
        System.out.println("Element najwiekszy: " + object2.podajMax());
        System.out.println("Mediana: " + object2.podajMediane());

        System.out.println("Lista po posortowaniu: ");
        for (Integer i : object2.listaInteger){
            System.out.print(String.format("%2d |", i));
        }
        System.out.println("\n-----------------------------------------------------\n");
    }


    public static ArrayList<Integer> czytajPlik(File plik){                  //metoda czytająca z pliku
        ArrayList<Integer> zczytanaLista = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(plik));
            String line;
            String[] splitLine;


            while((line = reader.readLine()) != null){                      //reader czyta linię po linii, dopóki nie trafi na pustą linię (zakładam że jest to koniec pliku)
                splitLine = line.split(",");                         //Integery są oddzielone od siebie znakiem ','
                for (String s : splitLine) {
                    zczytanaLista.add(Integer.parseInt(s));                 //dodanie zczytanych elementów do listy
                }
            }

            reader.close();                                                 //obowiązkowe zamknięcie readera

        }catch(Exception e){
            e.printStackTrace();
        }
        return zczytanaLista;                                               //metoda zwraca ArrayListę Integerów.
    }

    public static ArrayList<Integer> kopia(ArrayList<Integer> listaWejscie){ //metoda, która głęboko kopiuje listę elementów
        ArrayList<Integer> nowaLista = new ArrayList<>();

        for(Integer element : listaWejscie){
            nowaLista.add(element);
        }

        return nowaLista;
    }




}
