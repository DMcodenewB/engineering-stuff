import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ReplacingPages {

    public static void main(String[] args){
        /*VirtualMemory testMemory = new VirtualMemory(4);
        ArrayList<Integer> testRequestList = new ArrayList<>();

        testMemory.tab[0] = new Frame(0);
        testMemory.tab[1] = new Frame(0);
        testMemory.tab[2] = new Frame(0);
        testMemory.tab[3] = new Frame(0);

        testRequestList.add(7);
        testRequestList.add(4);
        testRequestList.add(2);
        testRequestList.add(6);
        testRequestList.add(8);
        testRequestList.add(3);
        testRequestList.add(7);
        testRequestList.add(5);
        testRequestList.add(9);
        testRequestList.add(2);

        runOPT(testMemory, testRequestList);
        runFIFO(testMemory, testRequestList);
        runLRU(testMemory, testRequestList);
        runRAND(testMemory, testRequestList);
        runSCA(testMemory, testRequestList);*/

         run();
    }
    public static void run(){
        Scanner sc = new Scanner(System.in);
        int memorySize;
        int pageRequestSize;
        VirtualMemory memory;
        ArrayList<Integer> requestList;

        System.out.print("Podaj wielkosc pamieci (ilosc ramek): ");
        memorySize = Integer.parseInt(sc.nextLine());
        System.out.print("Podaj rozmiar listy żądań stron: ");
        pageRequestSize = Integer.parseInt(sc.nextLine());

        memory = generateMemory(memorySize);
        requestList = generateList(pageRequestSize);

        printList(requestList);
        runOPT(memory, requestList);
        runFIFO(memory, requestList);
        runLRU(memory, requestList);
        runSCA(memory, requestList);
        runRAND(memory, requestList);


    }

    public static VirtualMemory generateMemory(int memorySize) {
        VirtualMemory temp = new VirtualMemory(memorySize);
        for(int i = 0; i < memorySize; i++){
            temp.tab[i] = new Frame(0);             //wypełnienie ramek zerami
        }
        return temp;
    }

    public static ArrayList<Integer> generateList(int pageRequestSize) {       //wygenerowanie listy żądań stron
        int randPage;
        ArrayList<Integer> reqList = new ArrayList();

        for(int i = 0; i < pageRequestSize; i++){
            randPage = ThreadLocalRandom.current().nextInt(1, pageRequestSize);     //żądania generowane są losowo
            reqList.add(randPage);
        }
        return reqList;
    }

    public static VirtualMemory copyMemory(VirtualMemory memory) {         //utworzenie głębokiej kopii pamięci
        VirtualMemory temp = new VirtualMemory(memory.size);
        temp.tab = new Frame[memory.size];

        for(int i = 0; i < temp.size; i++){
            temp.tab[i] = new Frame(memory.tab[i].pageNumber);
        }
        return temp;
    }
    public static ArrayList<Integer> copyList(ArrayList<Integer> list){        //utworzenie głębokiej kopii listy żądań
        ArrayList<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            tempList.add(new Integer(list.get(i).intValue()));
        }
        return tempList;
    }
    public static void printList(ArrayList<Integer> list){         //wypisanie listy żądań
        System.out.print("Aktualny stan listy zadan: ");
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + "  ");
        }
        System.out.println();
    }

    public static void runOPT(VirtualMemory memory, ArrayList<Integer> requestList){
        VirtualMemory tempMemory = copyMemory(memory);
        ArrayList<Integer> tempRequestList = copyList(requestList);
        int currentIndex = 0;
        int pageErrors = 0;
        int currentRequestedPage;
        int maxUseTime = 0;

        while(!tempRequestList.isEmpty()){                                                //obsługujemy wszystkie żądania z listy
            currentRequestedPage = tempRequestList.get(0);
            if(!tempMemory.contains(currentRequestedPage)) {                              //jeśli żądanej strony nie ma w pamięci
                pageErrors++;
                if(tempMemory.contains(0)){
                    tempMemory.tab[currentIndex].setPageNumber(currentRequestedPage);
                    currentIndex++;
                    currentIndex %= tempMemory.getSize();
                    tempRequestList.remove(0);
                }else{
                    maxUseTime = 0;
                    for(int i = 0; i < tempMemory.getSize(); i++){
                        if(maxUseTime < tempMemory.tab[i].getHowLongWithoutUsing()){
                            maxUseTime = tempMemory.tab[i].getHowLongWithoutUsing();
                            currentIndex = i;
                        }
                    }
                    tempMemory.tab[currentIndex].setPageNumber(currentRequestedPage);
                    tempRequestList.remove(0);
                }
            } else {
                tempRequestList.remove(0);
            }

            tempMemory.updateOPT(tempRequestList);
        }

        System.out.println("=======================================\nAlgorytm OPT\nLiczba zgłoszonych błędów stron: " + pageErrors
                + "\nStan pamięci na końcu zadania: ");
        tempMemory.printMemory();
    }

    public static void runFIFO(VirtualMemory memory, ArrayList<Integer> requestList) {
        VirtualMemory tempMemory = copyMemory(memory);
        ArrayList<Integer> tempRequestList = copyList(requestList);
        int currentIndex = 0;
        int currentRequestedPage;
        int pageErrors = 0;

        while(!tempRequestList.isEmpty()){
            currentRequestedPage = tempRequestList.get(0);
            if(!tempMemory.contains(currentRequestedPage)){
                tempMemory.tab[currentIndex].setPageNumber(currentRequestedPage);
                pageErrors++;
                currentIndex++;
                currentIndex %= tempMemory.getSize();
            }
            tempRequestList.remove(0);
        }

        System.out.print("=======================================\nAlgorytm FCFS\nLiczba zgłoszonych błędów stron: " + pageErrors
                                        + "\nStan pamięci na końcu zadania: ");
        tempMemory.printMemory();
    }

    public static void runLRU(VirtualMemory memory, ArrayList<Integer> requestList){
        VirtualMemory tempMemory = copyMemory(memory);
        ArrayList<Integer> tempRequestList = copyList(requestList);
        int currentIndex = 0;
        int pageErrors = 0;
        int currentRequestedPage;
        int tempIndex;

        for (int i = 0; i < tempMemory.size; i++){
            tempMemory.tab[i].setHowLongWithoutUsing(0);                                  // ustawiamy czas dla każdej strony na 0
        }

        while(!tempRequestList.isEmpty()){                                                //obsługujemy wszystkie żądania z listy
            currentRequestedPage = tempRequestList.get(0);
            if(!tempMemory.contains(currentRequestedPage)) {                              //jeśli żądanej strony nie ma w pamięci
                pageErrors++;
                tempMemory.findLRU().setPageNumber(currentRequestedPage);
                tempMemory.findLRU().setHowLongWithoutUsing(0);
                tempRequestList.remove(0);
            } else {
                for(int i = 0; i < tempMemory.getSize(); i++){
                    if(tempMemory.tab[i].getPageNumber() == currentRequestedPage){
                        tempMemory.tab[i].setHowLongWithoutUsing(0);
                        tempRequestList.remove(0);
                        break;
                    }
                }
            }
            for(int i = 0; i < tempMemory.size; i++){
                tempMemory.tab[i].howLongWithoutUsing++;
            }
        }

        System.out.println("=======================================\nAlgorytm LRU\nLiczba zgłoszonych błędów stron: " + pageErrors
                + "\nStan pamięci na końcu zadania: ");
        tempMemory.printMemory();



    }

    public static void runSCA(VirtualMemory memory, ArrayList<Integer> requestList) {
        VirtualMemory tempMemory = copyMemory(memory);
        ArrayList<Integer> tempRequestList = copyList(requestList);
        int currentIndex = 0;
        int currentRequestedPage;
        int pageErrors = 0;

        while(!tempRequestList.isEmpty()){
            currentRequestedPage = tempRequestList.get(0);
            if(!tempMemory.contains(currentRequestedPage)){                             // jeśli nie ma to dodaj z bitem 1
                pageErrors++;
                if(tempMemory.tab[currentIndex].getBit() == 0){
                    tempMemory.tab[currentIndex].pageNumber = currentRequestedPage;
                } else {
                    currentIndex = findNearest0bit(tempMemory, currentIndex);
                    tempMemory.tab[currentIndex].pageNumber = currentRequestedPage;
                }

            } else {                                                                                                    //jeśli jest to zmień bit na 1
                currentIndex = findThisFrame(tempMemory, currentIndex, currentRequestedPage);
                tempMemory.tab[currentIndex].setBit(1);
            }
            currentIndex++;
            currentIndex %= tempMemory.size;
            tempRequestList.remove(0);
        }
        System.out.print("=======================================\nAlgorytm SCA\nLiczba zgłoszonych błędów stron: " + pageErrors
                + "\nStan pamięci na końcu zadania: ");
        tempMemory.printMemory();
    }

    public static void runRAND(VirtualMemory memory, ArrayList<Integer> requestList){
        VirtualMemory tempMemory = copyMemory(memory);
        ArrayList<Integer> tempRequestList = copyList(requestList);
        int currentRequestedPage;
        int pageErrors = 0;
        int randomFrame;

        while(!tempRequestList.isEmpty()){
            currentRequestedPage = tempRequestList.get(0);
            if(!tempMemory.contains(currentRequestedPage)){
                pageErrors++;
                randomFrame = ThreadLocalRandom.current().nextInt(0, tempMemory.size);
                tempMemory.tab[randomFrame].pageNumber = currentRequestedPage;
            }
            tempRequestList.remove(0);
        }
        System.out.println("=======================================\nAlgorytm RAND\nLiczba zgłoszonych błędów stron: " + pageErrors
                + "\nStan pamięci na końcu zadania: ");
        tempMemory.printMemory();
    }

    public static int findThisFrame(VirtualMemory tempMemory, int currentIndex, int currentRequestedPage) {
        while(tempMemory.tab[currentIndex].pageNumber != currentRequestedPage){
            currentIndex++;
            currentIndex %= tempMemory.size;
        }
        return currentIndex;
    }
    public static int findNearest0bit(VirtualMemory tempMemory, int currentIndex) {
        while(tempMemory.tab[currentIndex].bit != 0){
            tempMemory.tab[currentIndex].bit = 0;
            currentIndex++;
            currentIndex %= tempMemory.size;
        }
        return currentIndex;
    }




}
