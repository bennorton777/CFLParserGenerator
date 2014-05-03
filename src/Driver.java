/**
 * Created by ben on 5/2/14.
 */
public class Driver {
    public static void main(String[] args) {
        try{
            Parser p = new Parser("grammar.txt");
        }
        catch (Exception e) {
            System.err.println("We encountered a problem.");
            e.printStackTrace();
        }
    }
}
