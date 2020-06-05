public class Word {

    private String word;                          //obiekt posiada 3 pola: słowo typu String, index czyli pozycję słowa w liście słów oraz jego hashCode
    private int index;
    private int hashCode;

    public Word(String word, int pos){
        this.word = word;
        this.index = pos;
        this.hashCode = word.hashCode();          //hashcode jest obliczany poprzez użycie funkcji String.hashCode()
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public String toString(){
        return "word: " + String.format("%14s", getWord()) + "\thashcode: " +
                String.format("%14d", getHashCode()) + "\tposition in text: " + String.format("%10d", getIndex());
    }


    //metoda porównująca dwa elementy, w pierwszej kolejności sortuje po hashCode, następnie po pozycji w tekście
    public boolean compareTo(Word other){
        if(this.getHashCode() > other.getHashCode()) return true;
        else if(this.getHashCode() == other.getHashCode() && this.getIndex() > other.getIndex()) return true;
        return false;
    }

}
