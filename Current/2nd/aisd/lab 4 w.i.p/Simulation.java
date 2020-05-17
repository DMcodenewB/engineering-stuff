import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Simulation {

    public static void main(String[] args){
        String[][] tab2d;
        ChiSqClass ob1;
        tab2d = czytajPlik("z4data1.csv");
        ob1 = new ChiSqClass(tab2d);
        ob1.printPartSumTable();
        System.out.println("\n--------------------------------------------------------------\n");
        tab2d = czytajPlik("z4data2.csv");
        ob1 = new ChiSqClass(tab2d);
        ob1.printPartSumTable();
        System.out.println("\n--------------------------------------------------------------\n");
        tab2d = czytajPlik("z4data3.csv");
        ob1 = new ChiSqClass(tab2d);
        ob1.printPartSumTable();
        System.out.println("\n--------------------------------------------------------------\n");
        tab2d = czytajPlik("z4data4.csv");
        ob1 = new ChiSqClass(tab2d);
        ob1.printPartSumTable();
    }

    public static int numberOfLines(String pathname){
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

    public static String[][] czytajPlik(String pathname){
        String[][] tab2d = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathname));
            String s;
            s = reader.readLine();
            String[] line = s.split(",");
            tab2d = new String[numberOfLines(pathname)][line.length];
            tab2d[0] = line;

            for(int i = 1; i < tab2d.length; i++){
                s = reader.readLine();
                tab2d[i] = s.split(",");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tab2d;
    }
}
