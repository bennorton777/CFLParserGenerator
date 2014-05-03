import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ben on 5/2/14.
 */
public class Parser {
    Set<NonTerminal> nT = new HashSet<NonTerminal>();
    Set<Terminal> t = new HashSet<Terminal>();
    List<Rule> rules = new ArrayList<Rule>();
    NonTerminal startSymbol;

    // filename must be a csv
    public Parser(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String startLine = br.readLine();
        String startString = startLine.split("%")[1].trim().split(" ")[1].trim();
        String nonTerminals = br.readLine();
        String tokens = br.readLine();
        // We expect a blank line here.
        br.readLine();
        // The next line is just the string "Rules"
        br.readLine();
        String line = "";
        List<String> ruleLines = new ArrayList<String>();
        while (line != null) {
            line = br.readLine();
            if (line != null) {
                ruleLines.add(line);
            }
        }

        // get terminals and nonTerminals
        String[] tokenList = tokens.split("%")[1].trim().split(",");
        String[] nTList = nonTerminals.split("%")[1].trim().split(",");
        for (int i=1; i<tokenList.length; i++) {
            t.add(new Terminal(tokenList[i].trim()));
        }
        t.add(new Terminal(tokens.split("%")[1].trim().split(" ")[1].trim().split(",")[0].trim()));
        for (int i=1; i<nTList.length; i++) {
            nT.add(new NonTerminal(nTList[i].trim()));
        }
        nT.add(new NonTerminal(nonTerminals.split("%")[1].trim().split(" ")[1].trim().split(",")[0].trim()));
        for (NonTerminal n : nT) {
            if (n.getName().equals(startString)) {
                n.makeStartVar();
                startSymbol = n;
            }
        }
        for (String ruleLine : ruleLines) {
            String cleanRule = ruleLine.split("%")[1].trim();
            String rValue = cleanRule.split("->")[1].trim();
            String lValue = cleanRule.split("->")[0].trim();
            List<CFGObject> rSymbols = new ArrayList<CFGObject>();
            for (String s : rValue.split(" ")) {
                CFGObject o = null;
                if ("epsilon".equals(s)) {
                    o = new Epsilon();
                }
                else if (isTerminal(s)) {
                    for (Terminal term : t) {
                        if (s.equals(term.getName())) {
                            o = term;
                        }
                    }
                }
                else {
                    for (NonTerminal nonTerm : nT) {
                        if (s.equals(nonTerm.getName())) {
                            o = nonTerm;
                        }
                    }
                }
                rSymbols.add(o);
            }
            NonTerminal lSymbol = null;
            for (NonTerminal nonTerm : nT) {
                if (lValue.equals(nonTerm.getName())) {
                    lSymbol = nonTerm;
                }
            }
            rules.add(new Rule(lSymbol, rSymbols));
        }
        for (NonTerminal n : nT) {
            for (Rule rule : rules) {
                if (rule.getlValue().getName().equals(n.getName())) {
                    n.addToRules(rule);
                }
            }
        }

        computeFirstAndFollowSets();

        System.err.print("\t");
        for (Terminal terminal : t) {
            System.err.print(t + "\t");
        }
        System.err.println();
        for (NonTerminal n : nT) {
            System.err.print(n + "\t");
            for (Terminal terminal : t) {
                if (n.getFirstSet().containsKey(terminal)) {
                    System.err.print(n.getFirstSet().get(terminal));
                }
            }
            System.err.println();
        }


    }

    public void computeFirstAndFollowSets() {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (NonTerminal n : nT) {
                for (Rule rule : n.getRules()) {
                    for (int i=0; i<rule.getrSymbols().size(); i++) {
                        CFGObject symbol = rule.getrSymbols().get(i);
                        if (!symbol.isNonTerminal() && !symbol.isEpsilon() && !n.getFirstSet().containsKey((Terminal) symbol)) {
                            changed = true;
                            n.getFirstSet().put((Terminal) symbol, rule);
                            break;
                        }
                        else if (!symbol.isEpsilon() && !symbol.equals(n) && symbol.isNonTerminal()) {
                            NonTerminal nSymbol = (NonTerminal) symbol;
                            for (Terminal t : nSymbol.getFirstSet().keySet()) {
                                if (!n.getFirstSet().containsKey(t)) {
                                    for (Terminal terminal : ((NonTerminal) symbol).getFirstSet().keySet()){
                                        n.getFirstSet().put(terminal, rule);
                                    }
                                    changed = true;
                                }
                            }
                            if (!nSymbol.canBeEpsilon()) {
                                break;
                            }
                        }
                        if (
                                (i == rule.getrSymbols().size() -1) &&
                                (symbol.isEpsilon() || (symbol.isNonTerminal() && ((NonTerminal) symbol).canBeEpsilon()))
                                && (!n.getFirstSet().containsKey(new Epsilon()))
                                ) {
                            changed = true;
                            n.getFirstSet().put(new Epsilon(), rule);
                        }
                    }
                }
            }
        }
        for (Rule rule : rules) {
            for (int i=0; i<rule.getrSymbols().size() - 1; i++) {
                if (rule.getrSymbols().get(i).isNonTerminal()) {
                    NonTerminal n = (NonTerminal) rule.getrSymbols().get(i);
                    CFGObject o = rule.getrSymbols().get(i+1);
                    if (!o.isNonTerminal() && !o.isEpsilon()) {
                        n.getFollowSet().add((Terminal) o);
                    }
                    else if (o.isNonTerminal()) {
                        for (Terminal terminal : ((NonTerminal) o).getFirstSet().keySet())
                        n.getFollowSet().add(terminal);
                    }
                }
            }
        }
    }

    public boolean isTerminal(String string) {
        return t.contains(new Terminal(string));
    }
}
