

public class Node {             //klasa "Węzeł", która reprezentuje Miasto w problemie domokrążcy

    private String name;                 //nazwa węzła, np. "a"
    private int[] distances;             //tablica odległości do innych węzłów, uszeregowana tak, jak w pliku wejściowym
    private boolean visited = false;     //czy dany węzeł został już "odwiedzony" przy obliczaniu drogi

    public Node(String name, int[] distances){
        this.name = name;
        this.distances = distances;
    }

    //poniżej wybrane gettery i settery dla pól
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getDistances() {
        return distances;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }



    public String toString(){       //metoda toString, wyświetlająca pola obiektu
        String outputTab = "[";
        for(int dist : distances){
            outputTab += String.format("%5s", dist);
        }
        outputTab += "]";
        return String.format("Miasto: %2s", getName()) + "\tTablica odleglosci: " + outputTab;
    }


}
