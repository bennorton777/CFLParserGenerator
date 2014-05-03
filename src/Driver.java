/**
 * Created by ben on 5/2/14.
 */
public class Driver {
    public static void main(String[] args) {
        try{
            Parser p = new Parser(args[0]);
        }
        catch (Exception e) {
            System.err.println("We encountered a problem.");
            e.printStackTrace();
        }
    }
}
