public class Room {             //pokoj, albo szafa, nie ma tutaj różnicy, bo i tak przypada jedna szafa na jeden pokój

    private String label;       //nazwa pokoju
    private int index;
    private String[] frequencyTab;
    private boolean visited;

    public Room(String label, int index, String[] tab){     //klasa Room (Pokój/Szafa)
        this.label = label;     //nazwa pokoju
        this.index = index;     //numer pokoju
        this.frequencyTab = tab;    //tablica częstości odwiedzania innych pokoi
    }

    public String getLabel(){
        return label;
    }
    public int getIndex(){
        return index;
    }
    public String[] getFrequencyTab(){
        return frequencyTab;
    }


    public String toString(){       //metoda toString, wyświetlająca pola obiektu
        String outputTab = "[";
        for(String freq : frequencyTab){
            if(freq.equals("X")) outputTab += String.format("%5s", 0);
            else outputTab += String.format("%5s", Integer.parseInt(freq));
        }
        outputTab += "]";
        return String.format("Nazwa: %2s", label) + String.format("\tindex: %2s", index) + "\tTablica czestosci: " + outputTab;
    }


}
