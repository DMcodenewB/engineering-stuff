import java.util.ArrayList;

public class Member {               //klasa Member, zawiera pola osobnika populacji
    ArrayList<Room> path;           //ścieżka, czyli kolejność odwiedzanych pokoi
    private int total;              //całkowita pokonana droga
    private double fitness;         //jakość osobnika, która podniesiona do kwadratu jest współczynnikiem klonowania.

    public Member(ArrayList<Room> path){        //konstruktor
        this.path = path;
        this.total = calculateTotalDistance();    //metoda która liczy całkowitą drogę
    }

    public Member(ArrayList<Room> path, int total){     //inny konstruktor, używany zwykle przy kopiowaniu, kiedy droga jest już znana
        this.path = path;
        this.total = total;
    }

    public double getFitness() {
        return fitness;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setFitness(double fitness) {
        this.fitness = Math.pow(fitness,2);     //jest podniesiona do potęgi 2, korzystam ze wskazówki podanej w treści zadania
    }

    public int calculateTotalDistance(){        //metoda obliczająca całkowitą drogę
        int totalDistance = 0;

        for(int i = 0; i < path.size()-1; i++){     //najpierw idziemy po kolei, zgodnie z kolejnymi symbolami pokoi, odczytując ich odległości od siebie.
            totalDistance += Math.abs(path.get(i).getIndex() - path.get(i+1).getIndex()) * Integer.parseInt(path.get(i).getFrequencyTab()[path.get(i+1).getIndex()]);
        }
        //ostatecznie, musimy też przejść od "ostatniej" szafy do "pierwszej". Jako że tutaj wystarczyło posłużyć się polem index, to nie wprowadzałem tu jakiejś flagi typu "który pokój został już odwiedzony."
        totalDistance += Math.abs(path.get(path.size() - 1).getIndex() - path.get(0).getIndex()) * Integer.parseInt(path.get(path.size() - 1).getFrequencyTab()[path.get(0).getIndex()]);

        return totalDistance; //zwracana jest całkowita droga dla rozwiązania
    }

    public String toString(){   //prosty toString
        String pathString = "";
        for(Room r : path){
            pathString += r.getLabel() + " ";
        }
        return "Droga: " + pathString + "\ttotal distance: " + total + "\t fitness: " + fitness;
    }

    public boolean compareTo(Member other) {        //compareTo, porównujący całkowitą drogę. Na podstawie tego odbywa się sortowanie populacji.
        return this.total > other.total;
    }
}
