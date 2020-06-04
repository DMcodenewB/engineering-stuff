public class Simulation {


    public static void main(String[] args){                                 //metoda sterująca, utworzenie dwóch obiektów obsługujących pliki wejściowe

        UtilityClass menu = new UtilityClass("z5data1.csv");
        menu.run(new String[]{"d"});                                        //sprawdzane miasta, podane w treści zadania

        menu = new UtilityClass("z5data2.csv");
        menu.run(new String[]{"c","f","j","o"});                            //jak wyżej


        System.out.println("\n-------------------------------------------------\n\nKoniec programu");



    }





}
