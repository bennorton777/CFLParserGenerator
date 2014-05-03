import java.util.List;

/**
 * Created by ben on 5/2/14.
 */
public class Rule {
    public NonTerminal getlValue() {
        return lValue;
    }

    public void setlValue(NonTerminal lValue) {
        this.lValue = lValue;
    }

    public List<CFGObject> getrSymbols() {
        return rSymbols;
    }

    public void setrSymbols(List<CFGObject> rSymbols) {
        this.rSymbols = rSymbols;
    }

    private NonTerminal lValue;

    public Rule(NonTerminal lValue, List<CFGObject> rSymbols) {
        this.lValue = lValue;
        this.rSymbols = rSymbols;
    }
    public String toString() {
        String s = "";
        for (CFGObject o : rSymbols) {
            s += " " + o;
        }
        return s;
    }

    private List<CFGObject> rSymbols;

}
