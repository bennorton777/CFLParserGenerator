import java.util.*;

/**
 * Created by ben on 5/2/14.
 */
public class NonTerminal extends CFGObject {
    private Map<Terminal, Rule> _firstSet = new HashMap<Terminal, Rule>();
    private boolean _isStartVar;

    public List<Rule> getRules() {
        return _rules;
    }

    private List<Rule> _rules = new ArrayList<Rule>();

    public void addToRules(Rule rule) {
        _rules.add(rule);
    }

    public NonTerminal(String name) {
        _name = name;
        _isStartVar = false;
    }

    public boolean equals(Object o) {
        try {
            NonTerminal nT = (NonTerminal) o;
            return nT.getName().equals(_name);
        }
        catch (Exception e) {
            return false;
        }
    }

    public int hashCode() {
        return _name.hashCode();
    }

    private Set<Terminal> _followSet = new HashSet<Terminal>();

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Set<Terminal> getFollowSet() {
        return _followSet;
    }

    public void setFollowSet(Set<Terminal> followSet) {
        _followSet = followSet;
    }

    public Map<Terminal, Rule> getFirstSet() {
        return _firstSet;
    }

    public void setFirstSet(Map<Terminal, Rule> firstSet) {
        _firstSet = firstSet;
    }
    public boolean isStartVar() {
        return _isStartVar;
    }
    public void makeStartVar() {
        _isStartVar = true;
    }

    private String _name = "";


    @Override
    public boolean isNonTerminal() {
        return true;
    }

    @Override
    public boolean isEpsilon() {
        return false;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
    public String toString() {
        return _name;
    }

    public boolean canBeEpsilon() {
        for (Rule rule : _rules) {
            if (rule.getrSymbols().size() == 1 && rule.getrSymbols().get(0).isEpsilon()) {
                return true;
            }
        }
        return false;
    }
}
