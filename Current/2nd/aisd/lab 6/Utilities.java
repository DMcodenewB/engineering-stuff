import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Utilities {        //klasa z wieloma pomocnymi metodami
    String[][] inputData;       //String[][] zawierający dane zczytane z pliku
    ArrayList<Room> roomList = new ArrayList<>();   //lista pokoi powstałych z inputData

    public Utilities(String pathname){          //konstruktor
        this.inputData = readFile(pathname);    //czytanie pliku
        makeRooms();      //metoda tworząca pokoje razem z tablicami częstości odwiedzania danych pokoi
    }

    public void run(int populationSize, int worstMembers, int iterations){      //metoda główna, służy do uruchomienia całego programu. Na wejście przyjmuje parametry podane w treści zadania.
        ArrayList<Member> firstPopulation = makeFirstPopulation(populationSize);        //utworzenie początkowej populacji rozwiązań
        ArrayList<Member> population = copyMemberList(firstPopulation);                 //głęboka kopia populacji początkowej, aby można było na niej przeprowadzać dalsze operacje.
        ArrayList<Integer> iterationsToPrint = new ArrayList<>();                   //pomocnicza lista, która przechowuje mi numery iteracji, przy których powinno się wydrukować stan populacji.
        double meanFitness = 0;           //inicjacja początkowej średniej jakości populacji.

        for(int i = 0; i < 10; i++){
            iterationsToPrint.add(iterations*i/10);     //wrzucenie kontrolnych numerów iteracji do listy
        }

        System.out.println("--------------------------------------------------------\n\nTest: "
                            + populationSize + " osobnikow, " + worstMembers + " najgorszych odpada, " + iterations + " iteracji"
                            + "\n\n--------------------------------------------------------");

        for(int i = 0; i < iterations; i++){    //dopóki nie ziterujemy tyle razy ile jest zadane
            combSort(population);               //posortowanie populacji
            meanFitness = calculateMeanFitness(population);   //obliczenie MFC
            updateFitness(population, meanFitness);           //zaktualizowanie jakości dla każdego osobnika, względem nowej wartości MFC
            if(iterationsToPrint.contains(i)) printTheFittest(population, i, meanFitness);  //sprawdzenie czy można pokazać stan po iteracji zawartej w liście
            //----------//
            population = cloneAndRepopulate(population, worstMembers);      //repopulacja i klonowanie zgodnie z instrukcją
        }

        combSort(population);             //za pętlą znajduje się ostatni zestaw tych samych instrukcji, aby dokończyć ostatnią iterację.
        meanFitness = calculateMeanFitness(population);
        updateFitness(population, meanFitness);
        printTheFittest(population, iterations, meanFitness); //ostatnie wyświetlenie najlepszych osobników.
    }

    private void printTheFittest(ArrayList<Member> population, int iteration, double meanFitness) {     //wyświetlenie danych 5 najlepszych osobników po i-tej iteracji
        System.out.println("Stan po " + iteration + " iteracjach: \n");
        for(int i = 0; i < 5; i++){
            System.out.println(population.get(i).toString());
        }
        System.out.println("\nSrednia wartosc funkcji celu: " + meanFitness + "\n");
    }

    private ArrayList<Member> cloneAndRepopulate(ArrayList<Member> population, int worstMembers) {       //metoda, która klonuje i dodaje rozwiązania do nowej populacji
        ArrayList<Member> clonedPopulation = new ArrayList<>();
        Member newMember;

        for(int i = 0; i < population.size() - worstMembers; i++){
            for(int j = 0; j < (int)population.get(i).getFitness(); j++){         //to jest warunek, aby jak najwięcej miejsc w nowej populacji zajęły rozwiązania najlepsze.
                newMember = new Member(population.get(i).path, population.get(i).getTotal());
                newMember = tryMutation(newMember);                              //próba mutacji osobnika
                clonedPopulation.add(newMember);                                 //dodanie osobnika do listy (zmutowanego lub nie)
            }
        }

        ArrayList<Member> newPopulation = makeFirstPopulation(worstMembers);    //korzystam sobie z tej metody, bo nie chcę dublować kodu, a spełnia ona swoje zadanie jak trzeba.

        for(int i = 0; i < newPopulation.size(); i++){              //dodanie losowo wygenerowanych rozwiązań do populacji.
            clonedPopulation.add(newPopulation.get(i));
        }

        return clonedPopulation;            //zwracana jest nowa generacja rozwiązań.
    }

    private Member tryMutation(Member member){                      //próba zmutowania osobnika
        Member mutatedMember = new Member(member.path, member.getTotal());
        int rand1 = ThreadLocalRandom.current().nextInt(0,member.path.size());
        int rand2 = ThreadLocalRandom.current().nextInt(0,member.path.size());      //dwa losowe indeksy, które będą podmieniane.

        Collections.swap(mutatedMember.path, rand1, rand2);                 //zamiana elementów listy o podanych indeksach.
        mutatedMember.setTotal(mutatedMember.calculateTotalDistance());     //ponowne obliczenie jakości zmutowanego osobnika.

        if(member.getTotal() < mutatedMember.getTotal()) return member;     //sprawdzenie, czy zmutowany osobnik jest lepszy od oryginału
        return mutatedMember;
    }

    private void updateFitness(ArrayList<Member> population, double meanFitness) {  //zaktualizowanie jakości rozwiązania dla całej populacji
        for(Member m : population){
            m.setFitness(meanFitness/(double)m.getTotal());     //zgodnie z instruckją, MFC/FC(i)
        }
    }

    private double calculateMeanFitness(ArrayList<Member> population) {         //obliczenie średniej jakości populacji
        double meanFitness = 0;

        for(Member m : population){
            meanFitness += m.getTotal();
        }

        meanFitness /= population.size();           //nic specjalnego w tej metodzie, zwykłe wyliczenie średniej
        return meanFitness;
    }


    private void makeRooms(){               //utworzenie pokoi o podanych niżej nazwach (label)
        int i = 97;                 // w kodzie ASCII 97 oznacza "a", iterujemy w górę, zatem nadajemy kolejne małe litery alfabetu.
        for(String[] line : inputData){
            roomList.add(new Room(Character.toString((char)i++), i-98, line));      //utworzenie obiektu typu Room, nadanie nazwy, indeksu oraz tablicy częstości odwiedzania innych pokoi
        }
    }

    private String[][] readFile(String pathname){        //metoda czytająca dane z pliku, używana już przeze mnie w poprzednich programach
        String[][] tab2d = null;                         //zwracana jest tablica 2D
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

    private ArrayList<Member> makeFirstPopulation(int numberOfMembers){     //metoda, która tworzy początkową populację rozwiązań
        ArrayList<Member> population = new ArrayList<>();
        ArrayList<Room> roomListCopy = copyList(roomList);

        for(int i = 0; i < numberOfMembers; i++){
            Collections.shuffle(roomListCopy);                  //losowe przemieszanie pokoi, aby rozwiązanie było jak najbardziej unikalne
            population.add(new Member(copyList(roomListCopy))); //dodanie osobnika do populacji
        }

        return population;
    }

    private ArrayList<Room> copyList(ArrayList<Room> list){     //metoda, która wykonuje głęboką kopię listy wejściowej obiektów typu Pokój (Room)
        ArrayList<Room> copy = new ArrayList<>();
        for(Room r : list){
            copy.add(new Room(r.getLabel(), r.getIndex(), r.getFrequencyTab()));
        }
        return copy;
    }
    private ArrayList<Member> copyMemberList(ArrayList<Member> list){   //metoda, która wykonuje głęboką kopię listy wejściowej obiektów typu Member
        ArrayList<Member> copy = new ArrayList<>();
        for(Member m : list){
            copy.add(new Member(m.path, m.getTotal()));
        }
        return copy;
    }


    private void combSort(ArrayList<Member> listaWejscie){      //metoda sortująca populację po całkowitej pokonanej drodze.

        int size = listaWejscie.size();
        int odstep = size;                                      //aby móc odpowiednio zmniejszać odstęp, inicjuję wartosć początkową jako rozmiar listy
        boolean swap = true;
        Member left, right;


        while(swap == true || odstep != 1){                     //dopóki porównywane elementy nie sąsiadują ze sobą lub w poprzedniej iteracji pętli doszło do zamiany

            odstep = znajdzOdstep(odstep);                      //zmniejszam odstęp, aby mieć możliwość dokładniejszego porównania wartości
            swap = false;                                       //dzięki temu sprawdzę czy w tej iteracji dojdzie do zamiany elementów

            for (int i = 0; i < size - odstep; i++)             //dopóki element porównywany z "prawej" strony nie będzie ostatnim elementem listy
            {
                left = listaWejscie.get(i);                     //biorę dwa elementy, oddalone od siebie o 'odstęp'
                right = listaWejscie.get(i + odstep);

                if (left.compareTo(right))                      //metoda compareTo znajduje się w klasie Member
                {
                    Collections.swap(listaWejscie, i, i + odstep);
                    swap = true;
                }
            }

        }
    }
    private int znajdzOdstep(int odstep){                        //metoda zmniejszająca odstęp pomiędzy porównywanymi elementami, pomocnicza do combSort()
        odstep = (odstep * 10) / 13;                            // 1/1.3 to optymalny iloraz wyznaczony empirycznie
        if (odstep < 1)
            return 1;                                           //elementy sąsiadują ze sobą
        return odstep;
    }
}
