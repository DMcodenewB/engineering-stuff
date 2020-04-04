import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

    public static void main(String[] args) {

        /*-----------------------------------------------------------
        * Wygenerowanie listy procesów (rozmiar zadany w kodzie)
        * -----------------------------------------------------------*/

       ArrayList<Process> lista = generateProcessList();
       System.out.println("Poczatkowa lista procesow: \n");
       printList(lista);

       ArrayList<Process> sortedList = sortList(lista);

        runFCFS(sortedList);
        runSJF(sortedList);
        runSRTF(sortedList);
        runRR(sortedList, 1);       //próba RR dla różnych kwantów czasu
        runRR(sortedList, 2);
        runRR(sortedList, 5);
        runRR(sortedList, 10);
        runRR(sortedList, 15);
        runRR(sortedList, 20);
        runRR(sortedList, 30);
        runRR(sortedList, 40);

        System.out.println("===================================================================\n");

        /*-----------------------------------------------------------------------------------
         * Testowa lista czterech procesów dla sprawdzenia działania programu na małej próbie
         * ----------------------------------------------------------------------------------*/

        ArrayList<Process> listaTest = new ArrayList<Process>();
        listaTest.add(new Process(1, 0, 5));
        listaTest.add(new Process(2, 0, 3));
        listaTest.add(new Process(3, 1, 1));
        listaTest.add(new Process(4, 2, 2));

        ArrayList<Process> sortedListaTest = sortList(listaTest);

        runFCFS(sortedListaTest);
        runSJF(sortedListaTest);
        runSRTF(sortedListaTest);
        runRR(sortedListaTest, 1);     //próba RR dla różnych kwantów czasu
        runRR(sortedListaTest, 2);
        runRR(sortedListaTest, 3);
        runRR(sortedListaTest, 4);




    }

    public static ArrayList<Process> generateProcessList() {                         //metoda generująca listę procesów
        int randomAT;
        int randomBT;
        ArrayList<Process> list = new ArrayList<Process>();

        for (int i = 0; i < 1000; i++) {                                              //określenie liczby procesów
            randomAT = ThreadLocalRandom.current().nextInt(0, 10000);   //ustalenie zakresu dla arrival_time
            randomBT = ThreadLocalRandom.current().nextInt(1, 30);      //ustalenie zakresu dla burst_time (oboma wartościami można manipulować z poziomu kodu)
            list.add(new Process(i + 1, randomAT, randomBT));                     //dodanie wygenerowanego procesu do listy
        }
        return list;
    }

    public static void printList(ArrayList<Process> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
        System.out.println("=======================================");
    }

    public static void runFCFS(ArrayList<Process> list) {
        calculateWTs(list);
    }
    public static void runSJF(ArrayList<Process> list){
        calculateWTsSJF(list);
    }
    public static void runSRTF(ArrayList<Process> list){
        calculateWTsSRTF(list);
    }
    public static void runRR(ArrayList<Process> list, int quant){
        calculateWTsRR(list, quant);
    }

    public static ArrayList<Process> sortList(ArrayList<Process> list){            //metoda sortująca listę Procesów

        int index = 0;                      //bieżący indeks listy
        int startIndex = 0;                 //indeks elementu od którego zaczynamy sortowanie
        int endIndex = list.size();         //indeks elementu na którym kończymy sortowanie
        Process left, right;                //pola Process przechowujące referencję na elementy listy
        boolean swap = true;                //wartość logiczna informująca czy podczas przechodzenia po liście doszło do zamiany elementów wewnątrz struktury

        System.out.println("Sortuje liste... ");
        while(swap) {                       //jeśli zaszła zamiana elementów, to sortuj dalej
            swap = false;                   //tę wartość zmienimy jeśli w dalszej części metody zajdzie zmiana
            for (index = startIndex; index < endIndex - 1; index++) {     //sortujemy "od lewej do prawej", zaczynając od elementu, który jest nieposortowany
                left = list.get(index);
                right = list.get(index + 1);                              //przypisanie elementów do zmiennych
                if (left.getArrival_time() > right.getArrival_time()) {   //i porównanie wartości pól ich czasów nadejścia
                    Collections.swap(list, index, index + 1);          //metoda klasy Collections, zamienia miejscami dwa elementy listy.
                    swap = true;                                          //doszło do zamiany, więc zmieniamy stan zmiennej swap
                }
            }
            if(swap == false) break;                                      //jeśli do zamiany nie doszło, to lista jest posortowana i można zakończyć działanie metody

            swap = false;                                                 //przygotowanie do sortowania w drugą stronę listy
            endIndex--;                                                   //ostatni element listy na pewno jest na swoim miejscu, wynika to z kodu powyżej

            for (index = endIndex - 1; index >= startIndex; index--) {    //sortujemy "od prawej do lewej", zaczynając od elementu, który jest nieposortowany
                left = list.get(index);
                right = list.get(index + 1);
                if (left.getArrival_time() > right.getArrival_time()) {
                    Collections.swap(list, index, index + 1);          //analogicznie jak wyżej
                    swap = true;
                }
            }
            startIndex++;                                                 //element na lewym końcu na pewno jest posortowany
        }
        System.out.println("Posortowana lista procesow: \n");
        printList(list);
        return list;                                                      //zwracana jest posortowana lista
    }

    public static ArrayList<Process> copyList(ArrayList<Process> list){
        ArrayList<Process> temp = new ArrayList<Process>();                            // utworzenie zastępczej listy procesów
        Process proc;
        for (int i = 0; i < list.size(); i++) {
            proc = new Process(list.get(i).getId(), list.get(i).getArrival_time(), list.get(i).getBurst_time());        //głęboka kopia listy wejściowej
            temp.add(proc);
        }
        return temp;
    }




    public static void calculateWTs(ArrayList<Process> list){
        int czas_uni = list.get(0).getArrival_time();                          //to tak, jakby potraktować czas nadejścia pierwszego procesu jako początek symulacji
        double averageWT;                                                      //średni czas oczekiwania
        double totalWT = 0;                                                    //całkowity czas oczekiwania
        double size = list.size();
        Process proc;

        for(int i = 0; i < size; i++){
            proc = list.get(i);                                                //bierzemy proces z listy
            if(proc.getArrival_time() <= czas_uni)                             //sprawdzamy czy proces nadszedł w trakcie wykonywania innego procesu
            totalWT +=  czas_uni - proc.getArrival_time();                     //jeśli tak, to dodajemy czas oczekiwania procesu do sumy czasu oczekiwania
            else totalWT += 0;                                                 //jeśli nie, to czas oczekiwania procesu wynosi 0
            czas_uni +=  proc.getBurst_time();                                 //zwiększamy czas "uniwersalny" o czas wykonywania procesu
        }
        averageWT = totalWT/size;                                              //obliczamy średni czas oczekiwania

        System.out.println("Algorytm FCFS:\nCałkowity czas oczekiwania: " + totalWT + "ms"
                + "\nSredni czas oczekiwania: " + averageWT + "ms\n");
    }

    public static void calculateWTsSJF(ArrayList<Process> list){
       ArrayList<Process> temporalList = copyList(list);
       int czas_uni = temporalList.get(0).getArrival_time();
       double averageWT;
       double totalWT = 0;
       double size = temporalList.size();
       Process proc;                                                //jak w FCFS

       while(!temporalList.isEmpty()) {                             //w tym algorytmie będę usuwał wykonane procesy z listy
            proc = find_shortestSJF(temporalList, czas_uni);       //metoda find_shortestSJF znajduje proces o najkrótszym burst_time z listy procesów, które już nadeszły
            if(proc.getArrival_time() <= czas_uni)                 //kalkulacja czasu oczekiwania na tej samej zasadzie co przy FCFS
                totalWT +=  czas_uni - proc.getArrival_time();
            else totalWT += 0;
           czas_uni +=  proc.getBurst_time();
            temporalList.remove(proc);                              //usunięcie obsłużonego procesu z listy
        }
        averageWT = totalWT/size;                                   //obliczenie średniego czasu oczekiwania
        System.out.println("Algorytm SJF bez wywlaszczenia: \nCałkowity czas oczekiwania: " + totalWT + "ms"
                + "\nSredni czas oczekiwania: " + averageWT + "ms\n");
    }

    public static Process find_shortestSJF (ArrayList<Process> list, int czas_uni) {

        Process proc1 = list.get(0);                                //biorę pierwszy z elementów listy aby rozpocząć porównywanie z innymi
        for(int i=1; i<list.size(); i++)
        {
            if(list.get(i).getArrival_time() <= czas_uni) {         //sprawdzam, czy proces "już nadszedł"
                if( proc1.getBurst_time() > list.get(i).getBurst_time()) {  //jeśli rozpatrywany proces ma mniejszy burst_time niż obecny,
                    proc1 = list.get(i);                                    //to nadpisuję wartość zmiennej proc1
                }
            }
        }
        return proc1;                                                       //wartość zwracana: obiekt Process o najkrótszym burst_time
    }

    public static void calculateWTsSRTF(ArrayList<Process> list){
        ArrayList<Process> temporalList = copyList(list);
        int czas_uni = temporalList.get(0).getArrival_time();
        double averageWT;
        double totalWT = 0;
        double size = temporalList.size();
        Process proc;                                                          //jak wyżej

        while(!temporalList.isEmpty()) {                                       //różnica pomiędzy algorytmami SJF a SRTF polega na porównywaniu remaining_burst_time
            proc = find_shortestSRTF(temporalList, czas_uni);                  //znalezienie procesu o najkrótszym pozostałym czasie wykonania
            proc.setRemaining_burst_time(proc.getRemaining_burst_time() - 1);  //działam umownie, jeden krok = jedna ms
            czas_uni++;
            if(proc.getRemaining_burst_time() == 0) {
                if(proc.getArrival_time() <= czas_uni)
                    totalWT += czas_uni - proc.getBurst_time() - proc.getArrival_time(); //ponieważ proces mógł być wywłaszczany, to nie można po prostu odjąć
                else totalWT += 0;                                                       //arrival_time od czas_uni, trzeba uwzględnić też czas wykonywania
                temporalList.remove(proc);                                     //usunięcie wykonanego procesu z listy
            }
        }
        averageWT = totalWT/size;
        System.out.println("Algorytm SRTF (SJF z wywłaszczaniem): \nCałkowity czas oczekiwania: " + totalWT + "ms"
                + "\nSredni czas oczekiwania: " + averageWT + "ms\n");


    }

    public static Process find_shortestSRTF(ArrayList<Process> list, int time) {

        Process proc = list.get(0);                                     //analogicznie jak dla SJF, tylko porównuję remaining_burst_time
        for(int i=1; i < list.size(); i++)
        {
            if( list.get(i).getArrival_time() <= time){
                if( proc.getRemaining_burst_time() > list.get(i).getRemaining_burst_time()) {
                    proc = list.get(i);
                }
            }
        }
        return proc;
    }

    public static void calculateWTsRR(ArrayList<Process> list, int quant){
        ArrayList<Process> processList = copyList(list);                                //lista zgłaszających się procesów
        ArrayList<Process> processQueue = new ArrayList<Process>();                           //kolejka obsługiwanych procesów
        int czas_uni = 0;
        double averageWT;
        double totalWT = 0;
        double size = processList.size();
        Process currentProcess;

        while(!(processList.isEmpty() && processQueue.isEmpty())){                   //wykonujemy algorytm do momentu obsłużenia wszystkich procesów
            for (int i = 0; i < processList.size(); i++){
                if(processList.get(i).getArrival_time() <= czas_uni){                //dodajemy do kolejki pierwszy z procesów, który pojawił się do momentu czas_uni
                    processQueue.add(0, processList.get(i));
                    processList.remove(i);                                           //usuwamy ten proces z listy oczekujących
                    i = processList.size();                                          //chcemy wyjść z pętli
                } else {                                                             //jeśli nie znaleziono takiego procesu, to
                    czas_uni++;                                                      //zwiększamy czas o 1
                    i--;                                                             //i wracamy na początek listy
                }
            }

            if(processQueue.get(0).getRemaining_burst_time() > quant){               //jeśli proces z kolejki ma pozostały czas wykonania większy niż kwant czasu
                czas_uni += quant;
                currentProcess = processQueue.get(0);                                //przepisanie referencji, więc operuję na oryginalnym procesie
                processQueue.remove(0);                                        //zmienna currentProcess jest potrzebna, ponieważ Proces, który reprezentuje, przebywa poza kolejką
                currentProcess.setRemaining_burst_time(currentProcess.getRemaining_burst_time() - quant);   // zmniejszenie czasu wykonywania o kwant
                processQueue.add(currentProcess);                                    //dodanie tego procesu na koniec kolejki
            } else if (processQueue.get(0).getRemaining_burst_time() == quant){      //jeśli proces trwa tyle samo co kwant czasu, to po prostu go "wykonuję" i usuwam
                czas_uni += quant;
                totalWT += czas_uni - processQueue.get(0).getArrival_time() - processQueue.get(0).getBurst_time(); // ponieważ proces mógł mieć początkowo większy burst_time
                processQueue.remove(0);                                        // niż quant, to mogło nastąpić wywłaszczenie - tak jak w SRTF
            } else {
                czas_uni += processQueue.get(0).getRemaining_burst_time();           //powiększam czas uniwersalny jedynie o czas wykonywania procesu
                totalWT += czas_uni - processQueue.get(0).getArrival_time() - processQueue.get(0).getBurst_time();//jak w powyższym warunku
                processQueue.remove(0);

            }
        }
        averageWT = totalWT/size;
        System.out.println("Algorytm RR (kwant " + quant + "ms):\nCałkowity czas oczekiwania: " + totalWT + "ms"
                + "\nSredni czas oczekiwania: " + averageWT + "ms\n");



    }
  }

