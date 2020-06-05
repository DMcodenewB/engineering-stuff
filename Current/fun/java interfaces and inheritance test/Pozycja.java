public abstract class Pozycja {

    protected String tytul;
    protected int id;
    protected String wydawnictwo;
    protected int rokWydania;

    public Pozycja(){};

    public Pozycja(String tytul, int id, String wydawnictwo, int rokWydania) {
        this.tytul = tytul;
        this.id = id;
        this.wydawnictwo = wydawnictwo;
        this.rokWydania = rokWydania;
    }

    public String wypiszInfo(){
        return "Tytul: " + tytul + "\tid: " + id + "\tWydawnictwo: "
                + wydawnictwo + "\tRok wydania: " + rokWydania;
    }
}
