public class MainClass {    //klasa sterująca, wrzucam tu instrukcje podane w zadaniu.

    public static void main(String[] args){
        Utilities u1 = new Utilities("z6data.csv");

        u1.run(300, 60, 1000);
        u1.run(300, 60, 10000);
        u1.run(3000, 300, 100);
        u1.run(3000, 300, 1000);

    }

}
