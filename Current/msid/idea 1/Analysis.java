import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

public class Analysis {
    public static void main(String[] args) {

        Scanner start = new Scanner(System.in);
        System.out.println("1. Wczytaj dane z pliku\n2.Wprowadz dane");
        int input = Integer.parseInt(start.nextLine());
        switch(input){
            case(1):
                czytajRun(start);
                break;
            case(2):
                run(start);
                break;
        }
        start.close();
    }

    private static void czytajRun(Scanner sc) {

        System.out.print("Podaj nazwe pliku (bez rozszerzenia): ");
        String fileName = sc.nextLine();
        ArrayList<double[]> inputData = czytajPlik(fileName + ".txt");
        System.out.print("initial scope = ");
        double scope = Double.parseDouble(sc.nextLine());                           //suma różnic
        System.out.print("precision grade (integer) = ");
        int precision = Integer.parseInt(sc.nextLine());
        sc.close();


        ArrayList<double[]> output = new ArrayList<>();
        output = findQMin(inputData, output, scope, -scope, scope, precision);

        System.out.println("\nqMin = " + String.format("%.5f", output.get(0)[0])  + " for a = " + String.format("%.10f", output.get(0)[1]));

    }


    public static void run(Scanner sc) {
        ArrayList<double[]> inputData = new ArrayList<>();
        System.out.print("a = ");
        double a = Double.parseDouble(sc.nextLine());   //kierunkowy
        System.out.print("initial scope = ");
        double scope = Double.parseDouble(sc.nextLine());    //suma różnic
        System.out.print("number of points = ");
        int pointsNumber = Integer.parseInt(sc.nextLine());
        System.out.print("diversity percentage = ");
        double diversity = Double.parseDouble(sc.nextLine());
        System.out.print("precision grade (integer) = ");
        int precision = Integer.parseInt(sc.nextLine());
        sc.close();

        inputData = generateDataList(a, diversity/100, pointsNumber);


        /*for(double[] elem : inputData){
            System.out.println("(" + (int)elem[0] + "," + String.format("%.6f",elem[1]) + ")");
        }
*/
        //ArrayList<double[]> output = new ArrayList<>();
        //output = findQMin(inputData, output, scope, -scope, scope, precision);

        double wantedA = 0;
        double sum1 = 0;
        double sum2 = 0;

        for(int i = 0; i < inputData.size(); i++){
            sum1 += inputData.get(i)[0] * inputData.get(i)[1];
            sum2 += Math.pow(inputData.get(i)[0], 2);
        }

        wantedA = sum1/sum2;
        System.out.println(wantedA);


        //System.out.println("\nqMin = " + String.format("%.5f", output.get(0)[0])  + " for a = " + String.format("%.10f", output.get(0)[1]));


    }
    public static ArrayList<double[]> czytajPlik(String fileName){                  //metoda czytająca z pliku
        ArrayList<double[]> zczytanaLista = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            String[] splitLine;
            double[] splitDouble;

            while((line = reader.readLine()) != null){                      //reader czyta linię po linii, dopóki nie trafi na pustą linię (zakładam że jest to koniec pliku)
                splitLine = line.split(",");                         //Double są oddzielone od siebie znakiem ','
                splitDouble = new double[splitLine.length];

                for(int i = 0; i < splitLine.length; i++){
                    splitDouble[i] = Double.parseDouble(splitLine[i]);
                }
                zczytanaLista.add(splitDouble);
            }

            reader.close();                                                 //obowiązkowe zamknięcie readera

        }catch(Exception e){
            e.printStackTrace();
        }
        return zczytanaLista;                                               //metoda zwraca ArrayListę double[].
    }

    public static ArrayList<double[]> generateDataList(double a, double diversity, double pointsNumber){
        ArrayList<double[]> list = new ArrayList<>();
        double[] element;
        for (double x = 0; x < pointsNumber; x++){
            element = new double[2];
            element[0] = x;
            if(Math.random() > 0.5)
            element[1] = a*x + diversity*x*Math.random();        //rozbieżność do 10%
            else element[1] = a*x + diversity*x*Math.random()*-1;
            list.add(element);
        }
        return list;
    }



    public static ArrayList<double[]> findQMin(ArrayList<double[]> inputData, ArrayList<double[]> output, double scope,
                                               double lowerBoundary, double upperBoundary,  double precision){

        double qMin = Integer.MAX_VALUE;
        double a = lowerBoundary;
        double aMin = a - 1;
        double q = 0;

        while(a < upperBoundary) {
            q = calculateQ(inputData, a);
            if (q < qMin){
                qMin = q;
                aMin = a;
            }
            //System.out.println("y = " + String.format("%.3f", a) + "x; Q = " + String.format("%.5f", q));
            a += scope/10;
        }

        if(precision > 0) findQMin(inputData, output, scope/10, aMin - scope/10, aMin+scope/10, --precision);

        System.out.println("qMin = " + String.format("%.5f", qMin)  + " for a = " + String.format("%.10f", aMin));
        output.add(new double[]{qMin, aMin});
        return output;
    }

    public static double calculateQ(ArrayList<double[]> list, double a){
        double squareQsum = 0;

        for(double[] elem : list){
            squareQsum += Math.pow((elem[1] - a*elem[0]), 2);
        }

        return squareQsum;
    }
}
