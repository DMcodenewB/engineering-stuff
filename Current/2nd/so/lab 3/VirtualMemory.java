import java.util.ArrayList;

public class VirtualMemory {

    Frame[] tab;                                //pamięć przechowuje tablicę Ramek o rozmiarze size
    int size;

    public VirtualMemory(int size){
        this.size = size;
        tab = new Frame[size];
    }

    public int getSize() {
        return size;
    }

public void printMemory(){                                   //wyświetlenie zawartości tablicy tab
        System.out.print("Aktualny stan pamieci: ");
        for(int i = 0; i < tab.length; i++){
            System.out.print(tab[i].pageNumber + "  ");
        }
        System.out.println();
}

public boolean contains(int key){            //sprawdzenie czy pamięć zawiera żądanie do danej strony
        boolean contains = false;
        for(int i = 0; i < size; i++) {
            if (tab[i].pageNumber == key)
                contains = true;
        }
        return contains;
}

public Frame findLRU(){                     //metoda do znajdowania najdłużej nieużywanej ramki
        int maxLRU = 0;
        int maxLRUIndex = 0;
        for(int i = 0; i < size; i++){           //czyli FIFO, najpierw wypełniane są "puste" ramki
            if(tab[i].pageNumber == 0){
                maxLRUIndex = i;
                return tab[maxLRUIndex];
            }
            if(tab[i].howLongWithoutUsing > maxLRU){       //jeśli ramka nie była używana dłużej niż obecna
                maxLRUIndex = i;
                maxLRU = tab[i].howLongWithoutUsing;       //to indeks tej strony jest przekazywany jako najdłużej nieużywana
            }
        }
        return tab[maxLRUIndex];        //zwracana jest najdłużej nieużywana ramka w pamięci (lub najbliższa pusta)
}
    public void updateOPT(ArrayList<Integer> list){
        for(int i = 0; i < size; i++){
            if(tab[i].getPageNumber() != 0) {
                for (int j = 0; j < list.size(); j++) {
                    if (tab[i].getPageNumber() == list.get(j).intValue()) {
                        tab[i].setHowLongWithoutUsing(j);
                        break;
                    }else{
                        tab[i].setHowLongWithoutUsing(list.size());
                    }
                }
            }
        }
    }

}
