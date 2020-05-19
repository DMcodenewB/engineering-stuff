public class ChiSqClass {
    private int[][] dataArray;              //zbiór danych wejściowych typu int
    private String[][] stringArray;         //zbiór danych wejściowych razem z opisami wierszy/kolumn typu String
    private double[][] expectedValueTable;  //zbiór wartości oczekiwanych typu double
    private int degreesOfFreedom;           //liczba stopni swobody
    private double chiSquare;               //wartość statystyki chi^2

    public ChiSqClass(String[][] inputArray){                                           //konstruktor
        this.stringArray = new String[inputArray.length][inputArray[0].length];         //przepisanie 1:1 wartości ze zczytanego pliku
        this.dataArray = new int[stringArray.length - 1][stringArray[0].length - 1];    //przechowywanie tylko wartości liczbowych

        for(int i = 0; i < stringArray.length; i++){                    //wypełnienie zbioru stringArray
            for(int j = 0; j < stringArray[0].length; j++){
                stringArray[i][j] = inputArray[i][j];
            }
        }
        for(int i = 0; i < dataArray.length; i++){                      //wypełnienie zbioru dataArray wartościami typu int
            for(int j = 0; j < dataArray[0].length; j++){               //które pochodzą ze zbioru stringArray
                dataArray[i][j] = Integer.parseInt(stringArray[i+1][j+1]);
            }
        }
    }

    private void setChiSquare(double chi){      //settery dla pewności
        this.chiSquare = chi;
    }
    private void setDegreesOfFreedom(int degs){
        this.degreesOfFreedom = degs;
    }

    public void run(){                      //metoda główna klasy
        printPartSumTable();                //wyświetlenie tabeli sum częściowych
        makeExpectedValueTable();           //utworzenie i wyświetlenie tabeli wartości oczekiwanych
        chiSqStatistic();                   //obliczenie statystyki chi^2
        System.out.println("Prawdopodobieństwo: " + calculateP(chiSquare, degreesOfFreedom));     //obliczenie prawdopodobieństwa dla podanych wartości
    }

    private int[] calculatePartSums(){                                           //metoda wewnętrzna, obliczająca sumy częściowe dla każdego wiersza i kolumny
        int[] resultArr = new int[dataArray.length + dataArray[0].length + 1];   //tablica przechowująca sumy częściowe
        int sum;                                                                 //obecna suma częściowa
        for(int i = 0; i < dataArray.length; i++){
            sum = 0;
            for (int j = 0; j < dataArray[0].length; j++){                       //obliczenie sumy częściowej dla wiersza
                sum += dataArray[i][j];
            }
            resultArr[i] = sum;                                                  //zapisanie sumy częściowej dla wiersza w tabeli
        }
        for(int j = 0; j < dataArray[0].length; j++){
            sum = 0;
            for (int i = 0; i < dataArray.length; i++){                          //obliczenie sumy częściowej dla kolumny
                sum += dataArray[i][j];
            }
            resultArr[j + dataArray.length] = sum;    //zapisanie sumy częściowej dla kolumny w tabeli, wpisujemy na miejscu dataArray.length + j
        }                                             //gdyż tyle miejsc w tablicy jest już zajętych
        sum = 0;
        for(int i = 0; i < dataArray.length; i++){
            sum += resultArr[i];                        //ostatnie miejsce w tablicy jest przeznaczone dla sumy sum częściowych ze wszystkich wierszy/kolumn
        }
        resultArr[dataArray.length + dataArray[0].length] = sum;   //zapisanie tej sumy w tabeli
        return resultArr;       //metoda zwraca tablicę sum częściowych
    }

    private void printPartSumTable(){                            //metoda, która wyświetla tablicę 2D danych wejściowych razem z sumami częściowymi dla tych danych
        int[] partSumTab = calculatePartSums();                             //odwołanie do metody obliczającej sumy częściowe
        String[][] outputStringTab = new String[stringArray.length+1][stringArray[0].length+1];  //utworzenie tablicy, która będzie zawierać dane i sumy częściowe
        for(int i = 0; i < stringArray.length; i++){
            for(int j = 0; j < stringArray[0].length; j++){
                outputStringTab[i][j] = stringArray[i][j];       //przepisanie wartości z tablicy stringArray do outputStringTab (tutaj nic się nie zmienia)
            }
        }
        outputStringTab[0][stringArray[0].length] = "SUMA";      //dopisanie odpowiednich nazw dla nowej kolumny i wiersza
        outputStringTab[stringArray.length][0] = "SUMA";
        for(int i = 1; i < stringArray.length; i++){
            outputStringTab[i][stringArray[0].length] = Integer.toString(partSumTab[i-1]);   //w tym miejscu zaczynamy uzupełniać tablicę o sumy częściowe
        }
        for(int j = 1; j < stringArray[0].length; j++){
            outputStringTab[stringArray.length][j] = Integer.toString(partSumTab[j + dataArray.length - 1]);
        }
        outputStringTab[stringArray.length][stringArray[0].length] = Integer.toString(partSumTab[dataArray.length + dataArray[0].length]); //prawy dolny róg tablicy, zawiera sumę całkowitą wartości

        for(int i = 0; i < outputStringTab.length; i++){
            for (int j = 0; j < outputStringTab[0].length; j++){
                System.out.print(String.format("%-16s", outputStringTab[i][j]));        //wyświetlenie tablicy
            }
            System.out.println();
        }
    }

    private void makeExpectedValueTable(){           //metoda, która tworzy i wyświetla tablicę wartości oczekiwanych
        int[] partSumTab = calculatePartSums();
        expectedValueTable = new double[dataArray.length][dataArray[0].length];             //tablica ma wymiary takie, jak tablica danych wejściowych liczbowych
        System.out.println("\nTabela wartości oczekiwanych dla wprowadzonych danych:\n");
        for (int i = 0; i < expectedValueTable.length; i++){
            for(int j = 0; j < expectedValueTable[0].length; j++){      //wypełnienie tablicy według sposobu podanego w treści zadania
                expectedValueTable[i][j] = ((double) partSumTab[i] * (double) partSumTab[dataArray.length + j] / (double) partSumTab[dataArray.length+dataArray[0].length]);
                System.out.print(String.format("%-10s",String.format("%.2f", expectedValueTable[i][j])));       //wyświetlenie tablicy
            }
            System.out.println();
        }
    }

    private void chiSqStatistic(){              //metoda obliczająca statystykę chi^2 dla podanego zbioru danych
        double chiSq = 0;

        for(int i = 0; i < expectedValueTable.length; i++){
            for (int j = 0; j < expectedValueTable[0].length; j++){
                chiSq += (Math.pow(dataArray[i][j] - expectedValueTable[i][j], 2))/expectedValueTable[i][j];    //zsumowanie wartości według równania podanego w treści zadania
            }
        }
        setChiSquare(chiSq);    //zmiana wartości pola chiSquare na obliczoną wartość statystyki

        setDegreesOfFreedom((expectedValueTable.length-1)*(expectedValueTable[0].length-1));    //obliczenie liczby stopni swobody

        System.out.println("\nWartość statystyki Chi^2 dla wprowadzonych danych: " + chiSquare + "\nLiczba stopni swobody: " + degreesOfFreedom);
    }

    private double calculateP(double chiSq, int degs) {          //metoda obliczająca prawdopodobieństwo na podstawie statystyki.
        if (chiSq > 1000 || degs > 1000) {                       //metoda została przetłumaczona z języka JavaScript, z kodu podanego w zadaniu
            double q = Norm((Math.pow(chiSq/(double)degs, 1.0/3.0) + 2.0 / (9.0 * degs) - 1.0) / Math.sqrt(2.0/(9.0 * degs))) / 2.0;
            if (chiSq > degs)
                return q;
            return 1 - q;
        }
        double p = Math.exp(-0.5 * chiSq);
        if ((degs % 2) == 1)
            p *= Math.sqrt(2 * chiSq / Math.PI);
        int k = degs;
        while (k >= 2) {
            p *= chiSq / k;
            k -= 2;
        }
        double t = p;
        int a = degs;
        while (t > 1e-15 * p) {
            a += 2;
            t *= chiSq / a;
            p += t;
        }
        return 1 - p;
    }

    private double Norm(double z) {         //metoda pomocnicza dla metody calculateP()
        double output;
        double q = z * z;
        if(Math.abs(z) > 7){
            output = (1.0 - (1.0/q) + (3.0/(q*q))) * Math.exp(-q/2.0) / (Math.abs(z) * Math.sqrt(Math.PI/2.0));
            return output;
        }
        else
            return calculateP(q, 1);

    }


}
