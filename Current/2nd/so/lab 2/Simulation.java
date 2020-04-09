import java.util.*;

public class Simulation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int diskSize = 0;

        while (!(diskSize > 0)) {
            System.out.println("Podaj rozmiar dysku (ilosc blokow) : ");
            diskSize = Integer.parseInt(sc.nextLine());
            if(diskSize <= 0) System.out.println("Rozmiar dysku musi byc wiekszy od 0!");
        }

        ArrayList<Request> inputList = new ArrayList<>();
        inputList = generateRequestQueue(inputList, sc, diskSize);                        //generuję listę zgłoszeń i przypisuję ją do inputList

        System.out.println("\n--------------------------------------------------------\n");

        for(int i = 0; i < inputList.size(); i++){
            System.out.println(inputList.get(i).toString());                              //wyświetlenie wygenerowanej listy zgłoszeń
        }
        System.out.println("\n--------------------------------------------------------\n");

        runFCFS(inputList, false);                                             //przeprowadzenie czterech symulacji nie uwzgledniając
        runSSTF(inputList, false);                                             //zgłoszeń czasu rzeczywistego
        runSCAN(inputList, diskSize, false);
        runCSCAN(inputList, diskSize, false);

        System.out.println("\nUwzględniając zgłoszenia czasu rzeczywistego:\n");

        runFCFS(inputList, true);
        runSSTF(inputList, true);
        runSCAN(inputList, diskSize, true);
        runCSCAN(inputList, diskSize, true);
    }




    private static ArrayList<Request> generateRequestQueue(ArrayList<Request> inputList, Scanner sc, int diskS) {  //metoda generująca listę zgłoszeń

        System.out.println("Podaj liczbe zgloszen : ");
        int requestNum = Integer.parseInt(sc.nextLine());                                       //liczba zgłoszeń
        System.out.println("Ile procent zgloszen stanowia te czasu rzeczywistego? : ");
        double priorPercent = Double.parseDouble(sc.nextLine())/100;                            //ile procent zgłoszeń jest czasu rzeczywistego
        double atRatio = -1;
        while (!(atRatio >= 0 && atRatio <= 2)) {                                               //"częstotliwość" nadchodzenia zgłoszeń
            System.out.println("Jak czesto maja sie pojawiac zgloszenia? (od 0 do 2) : ");
            atRatio = Double.parseDouble(sc.nextLine());
        }

        Random rand = new Random();
        int arrTime;

        for(int i = 0; i < requestNum; i++){                                //pętla, w której tworzone są zgłoszenia
            arrTime = (int)(Math.random()*diskS*atRatio);
            if(i < requestNum*priorPercent){
                inputList.add(new Request((int)(Math.random()*(diskS)), arrTime, true, arrTime + 10*i));    //czasu rzeczywistego
            }
            else{
                inputList.add(new Request((int)(Math.random()*(diskS)), arrTime, false, 0));
            }
        }

        return inputList;                                                  //zwracana jest lista zgłoszeń
    }

    private static ArrayList<Request> copyList(ArrayList<Request> inputList) {      //metoda wykonująca głęboką kopię listy wejściowej
        ArrayList<Request> copy = new ArrayList<>();
        for(int i = 0; i < inputList.size(); i++){
            copy.add(new Request(inputList.get(i).getLocation(), inputList.get(i).getArrivalTime(), inputList.get(i).isPrioritized(), inputList.get(i).getDeadline()));
        }
        return copy;
    }

    public static ArrayList<Request> sortList(ArrayList<Request> inputList){       //metoda sortująca, którą napisałem na potrzeby innego projektu
        int size = inputList.size();                                        //wykorzystuje ona algorytm Comb Sort, o niższej złożoności niż Bubble Sort
        int odstep = size;                                       //aby móc odpowiednio zmniejszać odstęp pomiędzy porównywanymi obiektami,
        boolean swap = true;                                     //inicjuję wartosć początkową jako rozmiar listy
        Request left, right;


        while(swap == true || odstep != 1) {                     //dopóki porównywane elementy nie sąsiadują ze sobą lub w poprzedniej iteracji pętli doszło do zamiany

            odstep = znajdzOdstep(odstep);                       //zmniejszam odstęp, aby mieć możliwość dokładniejszego porównania wartości
            swap = false;                                        //dzięki temu sprawdzę czy w tej iteracji dojdzie do zamiany elementów

            for (int i = 0; i < size - odstep; i++)              //dopóki element porównywany z "prawej" strony nie będzie ostatnim elementem listy
            {
                left = inputList.get(i);                         //biorę dwa elementy, oddalone od siebie o 'odstęp'
                right = inputList.get(i + odstep);

                if (left.getArrivalTime() > right.getArrivalTime())                //jeśli "lewy" element ma ArrivalTime większy od "prawego"
                {
                    Collections.swap(inputList, i, i + odstep);                 //to zamieniam elementy miejscami
                    swap = true;
                }
            }

        }
        return inputList;                                         //zwracana jest posortowana lista zgłoszeń
    }
    public static int znajdzOdstep(int odstep){                        //metoda zmniejszająca odstęp pomiędzy porównywanymi elementami
        odstep = (odstep * 10) / 13;                            // 1/1.3 to optymalny iloraz wyznaczony empirycznie
        if (odstep < 1)
            return 1;                                           //elementy sąsiadują ze sobą
        return odstep;
    }

    private static void runFCFS(ArrayList<Request> inputList, boolean prioritized) {        //algorytm FCFS
        ArrayList<Request> copy = sortList(copyList(inputList));                            //wykonanie kopii listy wejściowej
        ArrayList<Request> queue = new ArrayList<>();                                       //kolejka zgłoszeń
        int time = 0;                                                                       //czas uniwersalny
        long headMoves = 0;                                                                 //ilość przesunięć głowicy
        Request current;
        int currentPos = copy.get(0).getLocation();                                         //głowicę ustawiam na miejscu pierwszego zgłoszenia

        while(!copy.isEmpty() || !queue.isEmpty()) {                                        //dopóki wszystkie zgłoszenia nie zostaną obsłużone
            queue = update(copy, queue, time, prioritized);                                 //wczytanie do kolejki zgłoszeń, które nadeszły do czasu "time"
            if(!queue.isEmpty()){
                current = queue.get(0);
                headMoves += Math.abs(current.getLocation() - currentPos);                  //obsłużenie wszystkich zgłoszeń z kolejki
                currentPos = current.getLocation();
                queue.remove(0);
            }
            time++;                                                                         //inkrementacja czasu
        }
        System.out.println("Algorytm FCFS: suma przemieszczen glowicy dla  " + inputList.size() + " zgloszen: " + headMoves);
    }

    private static void runSSTF(ArrayList<Request> inputList, boolean prioritized) {    //algorytm SSTF
        ArrayList<Request> copy = sortList(copyList(inputList));
        ArrayList<Request> queue = new ArrayList<>();
        int time = 0;
        long headMoves = 0;
        Request current;
        int currentPos = copy.get(0).getLocation();

        while(!copy.isEmpty() || !queue.isEmpty()) {
            queue = updateSSTF(copy, queue, time, currentPos, prioritized);  //wczytanie zgłoszeń na zasadzie algorytmu SSTF (więcej informacji poniżej)
            if(!queue.isEmpty()) {
                current = queue.get(0);
                headMoves += Math.abs(current.getLocation() - currentPos);   //obsłużenie zgłoszeń z kolejki
                currentPos = current.getLocation();
                queue.remove(0);
            }
            time++;                                                          //inkrementacja czasu
        }
        System.out.println("Algorytm SSTF: suma przemieszczen glowicy dla  " + inputList.size() + " zgloszen: " + headMoves);
    }

    private static void runSCAN(ArrayList<Request> inputList, int diskSize, boolean prioritized) {  //algorytm SCAN
        ArrayList<Request> copy = sortList(copyList(inputList));
        ArrayList<Request> queue = new ArrayList<>();
        int time = copy.get(0).getArrivalTime();
        long headMoves = 0;
        boolean direction = false;                      //określenie kierunku skanowania (true - rosnąco, false - malejąco)
        int currentPos = copy.get(0).getLocation();

        while(!copy.isEmpty() || !queue.isEmpty()) {
            if (diskSize != 1) {                        //jeśli rozmiar dysku jest większy niż 1 - w innym wypadku nie przesuwam głowicy
                if (currentPos == diskSize - 1) {       //jeśli głowica dojedzie do krawędzi dysku
                    direction = false;                  //zmiana kierunku skanowania
                    if (!queue.isEmpty()) time = queue.get(0).getArrivalTime();
                    else if (!copy.isEmpty()) time = copy.get(0).getArrivalTime();      //przejście do najbliższego zgłoszenia w kolejce
                    else time++;

                }

                if (currentPos == 0) {                  //ta sama instrukcja, tylko dla drugiej krawędzi dysku
                    direction = true;
                    if (!queue.isEmpty()) time = queue.get(0).getArrivalTime();
                    else if (!copy.isEmpty()) time = copy.get(0).getArrivalTime();
                    else time++;
                }
            } else {
                if (!queue.isEmpty()) time = queue.get(0).getArrivalTime();
                else if (!copy.isEmpty()) time = copy.get(0).getArrivalTime();
                else time++;
            }
            queue = update(copy, queue, time, prioritized);              //wczytanie kolejki zgłoszeń
            if(!queue.isEmpty()) {
                if (prioritized && queue.get(0).isPrioritized() && queue.get(0).getLocation() != currentPos) {//zestaw instrukcji do określenia zachowania głowicy
                    if(currentPos < queue.get(0).getLocation() && direction) headMoves += queue.get(0).getLocation()- currentPos;
                    else if(currentPos < queue.get(0).getLocation() && direction == false) headMoves += currentPos + queue.get(0).getLocation();
                    else if(currentPos > queue.get(0).getLocation() && direction) headMoves += 2*diskSize + currentPos - queue.get(0).getLocation();
                    else headMoves += currentPos - queue.get(0).getLocation();
                    currentPos = queue.get(0).getLocation();
                }
            }

            //kod na usuniecie z kolejki wszystkich procesow, ktorych location == currentPos

            ArrayList<Request> requestsToRemove = new ArrayList<>();
            for(Request req : queue){
                if(req.getLocation() == currentPos){
                    requestsToRemove.add(req);
                    time++;
                }
            }
            queue.removeAll(requestsToRemove);

            currentPos = updatePos(direction, currentPos, diskSize);
            if (diskSize != 1) headMoves++;

        }
        System.out.println("Algorytm SCAN: suma przemieszczen glowicy dla  " + inputList.size() + " zgloszen: " + headMoves);
    }


    private static void runCSCAN(ArrayList<Request> inputList, int diskSize, boolean prioritized) {         //Algorytm C-SCAN
        ArrayList<Request> copy = sortList(copyList(inputList));
        ArrayList<Request> queue = new ArrayList<>();
        int time = copy.get(0).getArrivalTime();
        long headMoves = 0;
        int currentPos = copy.get(0).getLocation();

        while(!copy.isEmpty() || !queue.isEmpty()) {
            if (currentPos == diskSize){                                        //podobna zasada działania, z tym że tutaj głowica porusza się zawsze "rosnąco"
                if (!queue.isEmpty()) time = queue.get(0).getArrivalTime();
                else if(!copy.isEmpty()) time = copy.get(0).getArrivalTime();
                else time++;
                currentPos = 0;                                                //przy osiągnięciu ostatniego bloku następuje powrót na początek dysku
            }

            queue = update(copy, queue, time, prioritized);                    //mniej więcej podobnie jak przy SCANie
            if(!queue.isEmpty()) {
                if (prioritized && queue.get(0).isPrioritized() && queue.get(0).getLocation() != currentPos) {
                    if(currentPos > queue.get(0).getLocation()) headMoves += diskSize - (currentPos - queue.get(0).getLocation());
                    else headMoves += queue.get(0).getLocation() - currentPos;
                    currentPos = queue.get(0).getLocation();
                }
            }

            //kod na usuniecie z kolejki wszystkich procesow, ktorych location == currentPos
            Collection requestsToRemove = new ArrayList<>();
            for(Request req : queue){
                if(req.getLocation() == currentPos){
                    requestsToRemove.add(req);
                    time++;
                }
            }
            queue.removeAll(requestsToRemove);

            if(diskSize != 1){
                currentPos++;
                headMoves++;
            }

        }

        System.out.println("Algorytm C-SCAN: suma przemieszczen glowicy dla  " + inputList.size() + " zgloszen: " + headMoves);

    }

    public static ArrayList<Request> update(ArrayList<Request> input, ArrayList<Request> queue, int time, boolean prioritized){
        //metoda uzupełniająca kolejkę zgłoszeń

        while(!input.isEmpty() && input.get(0).getArrivalTime() <= time){    //do kolejki brane są wszystkie zgłoszenia z arrivalTime <= time
            queue.add(input.get(0));
            input.remove(0);
        }

        if(prioritized) {                                                   //jeśli rozpatrywany jest wariant ze zgłoszeniami czasu rzeczywistego
            ArrayList<Request> prioritizedReqs = new ArrayList<>();

            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i).isPrioritized()) prioritizedReqs.add(queue.get(i));
            }
            queue.removeAll(prioritizedReqs);
                                                                            //pierwszeństwo mają zgłoszenia czasu rzeczywistego, sortowane po wartości deadline
            for (int i = 0; i < prioritizedReqs.size(); i++) {
                if (prioritizedReqs.get(i).getDeadline() < prioritizedReqs.get(0).getDeadline()) {
                    Collections.swap(prioritizedReqs, i, 0);
                }
            }

            prioritizedReqs.addAll(queue);

            return prioritizedReqs;                                         //zwrócenie listy posortowanych zgłoszeń
        }
        return queue;
    }

    private static ArrayList<Request> updateSSTF(ArrayList<Request> input, ArrayList<Request> queue, int time, int pos, boolean prioritized) {
        //metoda uzupełniająca kolejkę zgłoszeń dla algorytmu SSTF

        while(!input.isEmpty() && input.get(0).getArrivalTime() <= time){
            queue.add(input.get(0));
            input.remove(0);
        }
                                                    //sortowanie listy zgłoszeń względem bliskości głowicy dysku
        for(int i = 0; i < queue.size(); i++){
            if(Math.abs(queue.get(i).getLocation() - pos) < Math.abs(queue.get(0).getLocation() - pos)){
                Collections.swap(queue, i, 0);
            }
        }

        if(prioritized) {                           //wariant z algorytmem EDF
            ArrayList<Request> prioritizedReqs = new ArrayList<>();

            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i).isPrioritized()) prioritizedReqs.add(queue.get(i));
            }
            queue.removeAll(prioritizedReqs);

            for (int i = 0; i < prioritizedReqs.size(); i++) {
                if (prioritizedReqs.get(i).getDeadline() < prioritizedReqs.get(0).getDeadline()) {
                    Collections.swap(prioritizedReqs, i, 0);
                }
            }

            prioritizedReqs.addAll(queue);

            return prioritizedReqs;
        }
        return queue;
    }

    private static int updatePos(boolean direction, int currentPos, int diskSize) {     //metoda aktualizująca pozycję głowicy

        if(direction && currentPos != diskSize) currentPos++;
        else if(!direction && currentPos != 0) currentPos--;
        return currentPos;
    }

}
