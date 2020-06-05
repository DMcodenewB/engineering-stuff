import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class SorterTest {

    public static void main(String[] args){

        System.out.println("Format wyswietlania wynikow:\n<nazwaPliku>, <liczbaElem>: "
                + "<Algorytm>\t\t<porownania>\t<zamiany>"
                + "\n--------------------------------------------------------------------\n");

        OperationClass object1 = new OperationClass();                           //inicjalizacja obiektu służącego do operacji na listach

        object1.listaInteger = czytajPlik(new File("z2data11.csv"));   //skopiowanie listy zczytanej z pliku do listy wewnętrznej obiektu
        object1.run("z2data11.csv");
        object1.listaInteger = czytajPlik(new File("z2data12.csv"));
        object1.run("z2data12.csv");
        object1.listaInteger = czytajPlik(new File("z2data13.csv"));
        object1.run("z2data13.csv");
        object1.listaInteger = czytajPlik(new File("z2data21.csv"));
        object1.run("z2data21.csv");
        object1.listaInteger = czytajPlik(new File("z2data22.csv"));
        object1.run("z2data22.csv");
        object1.listaInteger = czytajPlik(new File("z2data23.csv"));
        object1.run("z2data23.csv");
        object1.listaInteger = czytajPlik(new File("z2data31.csv"));
        object1.run("z2data31.csv");
        object1.listaInteger = czytajPlik(new File("z2data32.csv"));
        object1.run("z2data32.csv");
        object1.listaInteger = czytajPlik(new File("z2data33.csv"));
        object1.run("z2data33.csv");
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




}
