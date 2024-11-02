//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Database db = new Database("src/data/data.txt");
        System.out.println(db.createUser("zrekhi123", "12341234"));
    }
}