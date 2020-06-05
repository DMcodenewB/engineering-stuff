public class Bibliotekarz extends Osoba{
    private String dataZatrudnienia;
    private double wynagrodzenie;

    public Bibliotekarz(){};
    public Bibliotekarz(String imie, String nazwisko, String dataZatrudnienia, double wynagrodzenie){
        super(imie, nazwisko);
        this.dataZatrudnienia = dataZatrudnienia;
        this.wynagrodzenie = wynagrodzenie;
    }

    public String wypiszInfo(){
        return imie + " " + nazwisko + " zatrudniony " + dataZatrudnienia + " wynagrodzenie " + wynagrodzenie;
    }
}
