import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class HashTest {

    public static void main(String[] args){

        Sorts object1 = new Sorts();
        ArrayList<String> stringList = czytajPlik(new File("z3data1.txt"));       //zczytanie tekstu z pliku
        ArrayList<Word> wordList = toWordList(stringList);                                  //utworzenie listy obiektów typu Word

        String[] wordsToFind = {"mars", "ogilvy", "sky", "meteor"};                         //lista słów do znalezienia

        for(String w : wordsToFind){                                                        //wykonanie przeszukiwania listy na dwa sposoby
            findFirstWordOne(stringList, w);
            findFirstWordTwo(wordList, w, object1);
            System.out.println("========================================\n");
        }


    }
    public static ArrayList<String> czytajPlik(File plik){                  //metoda czytająca z pliku
        ArrayList<String> zczytanaLista = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(plik));
            String line;
            String[] splitLine;


            while((line = reader.readLine()) != null){                      //reader czyta linię po linii, dopóki nie trafi na pustą linię (zakładam że jest to koniec pliku)
                splitLine = line.split(" ");                         //Wyrazy oddzielone są spacją
                for (String s : splitLine) {
                    zczytanaLista.add(s);                                  //dodanie zczytanych elementów do listy
                }
            }

            reader.close();                                                 //obowiązkowe zamknięcie readera

        }catch(Exception e){
            e.printStackTrace();
        }
        return zczytanaLista;                                               //metoda zwraca ArrayListę Integerów.
    }
    public static ArrayList<Word> toWordList(ArrayList<String> list){       //utworzenie listy Wordów z listy Stringów
        ArrayList<Word> temp = new ArrayList<>();
        int index = 0;
        for(String s : list){
            temp.add(new Word(s, index++));
        }
        return temp;
    }

    public static void findFirstWordOne(ArrayList<String> list, String word){       //metoda przeszukiwania sekwencyjnego
        int index = 0;
        int compareCounter = 0;

        System.out.println("Looking for word \"" + word + "\" using linear search...");
        for(String s : list){                         //przejście po liście w poszukiwaniu pierwszego wystąpienia słowa
            index++;
            compareCounter++;
            if(s.equals(word)){
                System.out.println("Word \"" + word + "\" was found as a " + index + " word in file.\nComparisons: " + String.format("%5d", compareCounter) + "\n");
                return;
            }
        }
        System.out.println("Word \"" + word + "\" was not found in file.\n");
    }
    public static void findFirstWordTwo(ArrayList<Word> list, String word, Sorts object){       //metoda z wyszukiwaniem binarnym
        object.wordList = Sorts.kopia(list);
        object.combSort(object.wordList);
        int hashcode = word.hashCode();

        System.out.println("Looking for word \"" + word + "\" using binary search...");
        int[] results = binary(object.wordList, hashcode);                                      //tablica [pozycja znalezionego elementu, ilość porównań]
        int wordFound = results[0];
        int comparisons = results[1];

        if(wordFound == -1){
            System.out.println("Word " + word + " was not found in file.\n");           //-1 oznacza że nie znaleziono szukanego słowa w tekście
        }else{
            /*------------------------------------------------
            Ze względu na sposób sortowania listy, (najpierw hashCode rosnąco, a w drugiej kolejności pozycja w tekście rosnąco),
            występuje pewna ilość kolizji zachodzących przez użycie funkcji hashCode() na tych samych Stringach. Poniższa pętla polega na znalezieniu
            słowa o najniższym indeksie, gdy już znamy hashcode. Jest to metoda mocno zmniejszająca wydajność tego wyszukiwania w przypadku,
            gdy poszukiwane jest słowo często występujące w tekście (np "the", "of"). Jednak przeciętna wydajność tego
            algorytmu dla listy w miarę losowych słów i tak jest znacznie wyższa w porównaniu do metody wyszukiwania sekwencyjnego.
            --------------------------------------------------
             */
            if(wordFound > 0){
                while(object.wordList.get(wordFound - 1).getIndex() < object.wordList.get(wordFound).getIndex()
                        && object.wordList.get(wordFound-1).getHashCode() == hashcode) {
                    wordFound--;
                    comparisons++;
                }
            }
            int finalIndex = object.wordList.get(wordFound).getIndex() + 1; //zwiększenie indeksu o 1, bo pozycje w tekście są numerowane od 1
            System.out.println("Word \"" + word + "\" was found as a " + finalIndex + " word in file.\nComparisons: "
                    + String.format("%5d", comparisons) + "\n");
        }

    }

    public static int[] binary(ArrayList<Word> list, int hashcode){ //metoda wyszukiwania binarnego
        int startIndex = 0;
        int endIndex = list.size();
        int middle;
        int compareCounter = 0;

        while (startIndex <= endIndex) {
            middle = startIndex + (endIndex - startIndex) / 2;

            if (list.get(middle).getHashCode() == hashcode){
                compareCounter++;
                return new int[]{middle, compareCounter};
            }
            compareCounter++;
            if (list.get(middle).getHashCode() < hashcode)
                startIndex = middle + 1;

            else
                endIndex = middle - 1;
        }

        return new int[]{-1, compareCounter};
    }

}
