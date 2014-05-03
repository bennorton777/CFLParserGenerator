/**
 * Created by ben on 5/2/14.
 */
public class Terminal extends CFGObject {
    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Terminal(String name) {
        _name = name;
    }

    public boolean equals(Object o) {
        try {
            Terminal t = (Terminal) o;
            return t.getName().equals(_name);
        }
        catch (Exception e) {
            return false;
        }
    }

    public int hashCode() {
        return _name.hashCode();
    }
    private String _name = "";

    @Override
    public boolean isNonTerminal() {
        return false;
    }

    @Override
    public boolean isEpsilon() {
        return _name.equals("epsilon");
    }

    public String toString() {
        return _name;
    }
    public boolean isTerminal() {
        return true;
    }
}
