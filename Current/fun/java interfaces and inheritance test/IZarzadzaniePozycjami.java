public interface IZarzadzaniePozycjami {

    public Pozycja znajdzPozycjePoTytule(String tytul);
    public Pozycja znajdzPozycjePoId(int id);
    public void wypiszWszystkiePozycje();
}
