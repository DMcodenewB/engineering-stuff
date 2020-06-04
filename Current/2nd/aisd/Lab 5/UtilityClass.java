import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class UtilityClass {         //klasa, która zajmuje się działaniem całego programu

    String[][] stringTab;           //stringTab przechowuje tablicę zczytaną z pliku
    int[][] dataTab;                // dataTab przechowuje tę samą tablicę bez pierwszego wiersza i kolumny (same wartości liczbowe)


    public UtilityClass(String pathname){           //konstruktor parametryczny
        this.stringTab = readFile(pathname);
        this.dataTab = makeDataTab(stringTab);
        System.out.println("\n-------------------------------------------------\n\n"
                            +"Dane wejsciowe dla pliku "
                            + pathname + "\n\n");
    }


    public void run(String[] startingNodeNames){            //właściwa metoda obsługująca program
        ArrayList<Node> nodeList = new ArrayList<>();       //lista, do której dodane będą wszystkie miasta z pliku
        assignDistances(stringTab, nodeList);               //metoda nadająca węzłom tablice odległości

        for(String nodeName : startingNodeNames){
            calculateOptimalPath(nodeList, nodeName);       //wyszukiwanie cyklu Hamiltona dla każdego z zadanych miast sposobem zachłannym
        }


    }

    private void calculateOptimalPath(ArrayList<Node> nodeList, String startingNodeName) {      //metoda obliczająca wartość najkrótszej drogi zaczynając od podanego miasta
        ArrayList<Node> nodeListCopy = copyList(nodeList);

        int calculatedDistanceSum = 0;                                  //przebyty całkowity dystans
        int currentDistance;                                            //obecny dystans do kolejnego miasta, zmienna pomocnicza
        int minDistance;                                                //dystans do najbliższego miasta
        String path = startingNodeName;                                 //String, który będzie zawierał pokonaną ścieżkę
        String distanceFormula = "[ ";                                  //String, który będzie zawierał równanie pokonanej drogi

        Node currentNode = findNodeByName(nodeListCopy, startingNodeName);  //znalezienie węzła, od którego będzie startował program
        Node nextNode = null;                                           //węzeł, który będzie kandydował do miana "najbliższego"
        if (currentNode == null) {                                      //sprawdzenie, czy dany węzęł znajdował się na liście węzłów
            System.out.println("Nie znaleziono wezla o takiej nazwie");
            return;
        }
        currentNode.setVisited(true);                   //wybrany węzeł ustawiamy zawczasu jako odwiedzony

        do{
            minDistance = Integer.MAX_VALUE;            //aby zresetować odległość do najbliższego węzła

            for(int i = 0; i < currentNode.getDistances().length; i++){         //będziemy rozpatrywać każdy dystans z tabeli dla danego węzła
                currentDistance = currentNode.getDistances()[i];

                /* 1) Sprawdzam, czy dystans do następnego węzła jest mniejszy niż obecnie najmniejszy
                * 2) Sprawdzam, czy nie rozpatrujemy połączenia węzła z sobą samym (dystans == 0)
                * 3) Sprawdzam, czy rozpatrywany węzeł nie został już odwiedzony*/

                if(currentDistance < minDistance && currentDistance > 0 && !nodeListCopy.get(i).isVisited()){
                    minDistance = currentDistance;
                    nextNode = nodeListCopy.get(i);     //nadpisujemy referencję do węzła, który będzie węzłem startowym w kolejnej iteracji pętli do...while
                }
            }
            calculatedDistanceSum += minDistance;       //zwiększamy pokonaną drogę o odległość do najbliższego węzła od obecnego
            currentNode = nextNode;                     //najbliższy węzeł staje się obecnym rozpatrywanym
            currentNode.setVisited(true);               //węzeł zostaje oznaczony jako odwiedzony
            distanceFormula += Integer.toString(minDistance) + " + ";       //dodaję odległość od poprzedniego węzła do obecnego
            path += "->" + currentNode.getName();                           //zapisuję nazwę odwiedzonego węzła na ścieżce

        }while(!checkAllVisited(nodeListCopy));         /*Pętla wykonuje się dopóki wszystkie węzły zostaną odwiedzone*/

        /*Ponieważ węzeł startowy został początkowo oznaczony jako odwiedzony aby umożliwić poprawne działanie pętli,
        * poniższy kawałek kodu dopisuje jedynie podróż z ostatniego odwiedzonego węzła do węzła startowego*/

        int lastDistance = currentNode.getDistances()[nodeListCopy.indexOf(findNodeByName(nodeListCopy, startingNodeName))];  //odległość pomiędzy tymi węzłami
        calculatedDistanceSum += lastDistance;      //dodanie odległości do sumy
        distanceFormula += Integer.toString(lastDistance) + " ]";     //zakończenie równania
        path += "->" + startingNodeName;                              //zakończenie ścieżki

        System.out.println("\nZnaleziona sciezka dla punktu startu " + startingNodeName + ": " + String.format("%-50s",path)
                            + "\nRownanie sciezki: " + distanceFormula
                            + "\nCalkowita droga: " + String.format("%5d", calculatedDistanceSum));

    }

    private boolean checkAllVisited(ArrayList<Node> nodeList) {     //metoda, która sprawdza, czy wszystkie węzły z listy zostały odwiedzone
        boolean allVisited = true;
        for(Node node : nodeList){
            if(node.isVisited() == false) allVisited = false;
        }
        return allVisited;
    }

    private Node findNodeByName(ArrayList<Node> nodeList, String startingNodeName) {        //metoda znajdująca węzeł o podanej nazwie
        Node thisNode = null;

        for(Node node : nodeList){
            if(node.getName().equals(startingNodeName)){                //jeśli nazwa węzła jest zgodna z szukaną, to zwracamy referencję na ten węzeł
                thisNode = node;
                break;
            }
        }

        return thisNode;
    }

    private void assignDistances(String[][] stringTab, ArrayList<Node> nodeList) {      //metoda nadająca węzłom odpowiednie tablice odległości

        String[][] cutStringTab = new String[stringTab.length-1][stringTab[0].length];  //tablica na dane wejściowe - stringTab bez pierwszego wiersza
        String[] inputLine;                                                             //pojedynczy wiersz z tej tablicy


        for(int i = 0; i < cutStringTab.length; i++){
            for(int j = 0; j < cutStringTab[0].length; j++){
                cutStringTab[i][j] = stringTab[i+1][j];                 //przepisanie wartości z stringTab do cutStringTab
            }
        }


        for(String[] line : cutStringTab){          //dla każdej linii w tablicy cutStringTab
            inputLine = line;
            nodeList.add(new Node(inputLine[0], makeInputDistancesTab(inputLine)));     //utworzenie węzła na podstawie danych w inputLine i dodanie go do listy
        }

        printNodeList(nodeList);            //wyświetlenie zawartości listy dla sprawdzenia poprawności metody

    }

    private int[] makeInputDistancesTab(String[] tab){      //metoda pomocnicza dla metody assignDistances(), zwraca tablicę odległości typu int

        int[] outputTab = new int[tab.length - 1];

        for(int i = 0; i < outputTab.length; i++){
            outputTab[i] = Integer.parseInt(tab[i+1]);      //pierwszy element tablicy tab jest pomijany, gdyż jest nim nazwa miasta/węzła
        }

        return outputTab;
    }

    private int[][] makeDataTab(String[][] sTab){                    //metoda, która tworzy macierz odległości na podstawie tablicy 2d typu String[]

        int[][] newTab = new int[sTab.length - 1][sTab[0].length - 1];

        for(int i = 0; i < newTab.length; i++){                      //wypełnienie zbioru newTab wartościami typu int
            for(int j = 0; j < newTab[0].length; j++){               //które pochodzą z tablicy stringTab
                newTab[i][j] = Integer.parseInt(sTab[i+1][j+1]);
            }
        }

        return newTab;
    }

    private int numberOfLines(String pathname){        //metoda znajdująca liczbę linii w czytanym pliku tekstowym
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(pathname));
            int lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String[][] readFile(String pathname){        //metoda czytająca dane z pliku
        String[][] tab2d = null;                                 //zwracana jest tablica 2D
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathname));
            String s;
            s = reader.readLine();
            String[] line = s.split(",");
            tab2d = new String[numberOfLines(pathname)][line.length];       //wymiary tablicy 2D: [ilość wierszy w pliku] x [ilość elementów w wierszu]
            tab2d[0] = line;

            for(int i = 1; i < tab2d.length; i++){
                s = reader.readLine();
                tab2d[i] = s.split(",");          //kolejne wiersze dzielone są na tablice Stringów i przypisywane do odpowiednich pól w tablicy 2D
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tab2d;
    }

    private ArrayList<Node> copyList(ArrayList<Node> nodeList) {      //metoda pomocnicza, która wykonuje głęboką kopię listy węzłów
        ArrayList<Node> tempList = new ArrayList<>();

        for(Node node: nodeList){
            tempList.add(new Node(node.getName(), node.getDistances()));
        }

        return tempList;
    }

    public void printNodeList(ArrayList<Node> list){      //metoda wyświetlająca zawartość listy węzłów
        for(Node node : list){
            System.out.println(node.toString());
        }
    }
}
