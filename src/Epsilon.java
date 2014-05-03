/**
 * Created by ben on 5/2/14.
 */
public class Epsilon extends CFGObject {
    @Override
    public boolean isNonTerminal() {
        return false;
    }
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
