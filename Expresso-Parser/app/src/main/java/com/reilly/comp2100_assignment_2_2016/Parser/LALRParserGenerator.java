package com.parser.Parser;

import com.parser.Parser.LRParser;
import com.parser.Parser.LRTable;

import java.util.ArrayList;
import java.util.List;

import static com.parser.Parser.LRTable.isTerminal;

/**
 *
 * LALRParserGenerator creates a Parser given a grammar as a String
 *
 *
 * References: http://web.cs.dal.ca/~sjackson/lalr1.html
 *
 */
public class LALRParserGenerator {

    public static class GrammarError extends Exception {
        public GrammarError(String msg) {super(msg);}
    }

    //List<ItemSet> genItemLists = new ArrayList<>();

    public static class ExtendedSymbol {
        int before;
        String symbol;
        int after;

        // Performance reasons:
        int index;

        public ExtendedSymbol(int before, String symbol, int after) {
            this.before = before;
            this.symbol = symbol;
            this.after = after;
        }

        @Override
        public String toString() {
            return Integer.toString(before)+symbol+Integer.toString(after);
        }

        @Override public boolean equals(Object other) {
            boolean result = false;
            if (other instanceof ExtendedSymbol) {
                ExtendedSymbol otherES = (ExtendedSymbol) other;
                result = (this.before==otherES.before) && (this.symbol.equals(otherES.symbol)) && (this.after==((ExtendedSymbol) other).after);
            }
            return result;
        }
    }

    static public class Transition {
        String left;
        String[] right;
        int pointer = 0;
        List<Integer> hidden = new ArrayList<>();

        // Public constructors
        public Transition(String left, String[] right) { this.left = left; this.right = right; }
        public Transition(String left, String[] right,List<Integer> hidden) {
            this.left = left; this.right = right;
            this.hidden = hidden;
        }
        public Transition(Transition old, int pointer) {
            this.left = old.left;
            this.right = old.right;
            this.pointer = pointer;
            this.hidden = old.hidden;
        }
        @Override
        public String toString() {
            String ret = left + " ->";
            for(int i=0;i<right.length;i++) {
                if(pointer==i) { ret+=" •"; } // • represents points in the itemset transition table.
                ret+=" "+right[i];
            }
            if(pointer==right.length) { ret+=" •"; }
            return ret;
        }
        public boolean hasNext() {
            return pointer < right.length;
        }
        public String next() {
            return right[pointer];
        }
        @Override public boolean equals(Object other) {
            boolean result = false;
            if (other instanceof Transition) {
                Transition otherT = (Transition) other;
                result = right.equals(otherT.right) && left.equals(otherT.left) && pointer==otherT.pointer;
                //(this.toString().equals(other.toString()));
            }
            return result;
        }
    }

    public static class ExtendedTransition {
        ExtendedSymbol left;
        List<ExtendedSymbol> right = new ArrayList<>();
        Transition originalTransition;
        public ExtendedTransition(List<ItemSet> itemSets, Transition old, int old_set_n) {
            this.originalTransition = old;
            ItemSet old_set = itemSets.get(old_set_n);
            left = new ExtendedSymbol(old_set_n,old.left,old_set.next_transition(itemSets, old.left));
            int old_n = old_set_n;
            for(String symbol : old.right) {
                int next = old_set.next_transition(itemSets, symbol);
                old_set = itemSets.get(next);
                right.add(new ExtendedSymbol(old_n,symbol,next));
                old_n = next;
            }
        }
        @Override
        public String toString() {
            String ret = left.toString() + " ->";
            for(int i=0;i<right.size();i++) {
                ret+=" "+right.get(i).toString();
            }
            return ret;
        }
    }

    public static class ItemSet {
        List<List<Transition>> rules = new ArrayList<>();
        List<Transition> all_rules = new ArrayList<>();
        // public constructor
        public ItemSet(List<Transition> all_transitions, List<Transition> my_transitions) {
            for(Transition transition : my_transitions) {
                List<Transition> current_rules  = new ArrayList<>();
                current_rules.add(transition);
                if(!all_rules.contains(transition)) {
                    all_rules.add(transition);
                }

                int i = 0;
                while(i < current_rules.size()) {
                    Transition current = current_rules.get(i);
                    if(current.pointer < current.right.length) {
                        String next = current.right[current.pointer];
                        if (!isTerminal(next)) {
                            List<Transition> relevant_transitions = getTransitions(all_transitions, next);
                            for(Transition relevant : relevant_transitions) {
                                Transition to_add = new Transition(relevant, 0);
                                if(!current_rules.contains(to_add)) {
                                    current_rules.add(to_add);
                                }
                                if(!all_rules.contains(to_add)) {
                                    all_rules.add(to_add);
                                }
                            }
                        }
                    }
                    i++;
                }

                rules.add(current_rules);
            }
        }
        public int next_transition(List<ItemSet> itemSets, String symbol) {
            int transition = -1;

            List<Transition> next_steps = new ArrayList<>();
            for(Transition trans : this.all_rules) { // Generated from constructor
                if(trans.hasNext() && trans.next().equals(symbol)) {
                    next_steps.add(new Transition(trans, trans.pointer+1)); // add transition to next_steps if its next exists and equals passed in symbol
                }
            }

            for(int i = 0; i < itemSets.size(); i++) {
                if(itemSets.get(i).equivalent(next_steps)) transition = i; // gets location of last matching itemset in itemSets and returns index
            }
            return transition;
        }
        public boolean contains(Transition to_match) {
            boolean ret = false;
            for(List<Transition> transitions : rules) {
                if(transitions.get(0).equals(to_match)) ret = true;
            }
            return ret;
        }
        public boolean equivalent(List<Transition> list) {
            boolean ret = true;
            if(list.size() != rules.size()) ret = false;
            for(Transition list_i : list) {
                if(!this.contains(list_i)) {ret = false;}
            }
            return ret;
        }
    }

    static public List<Transition> stringToTransitions(String grammar) {
        List<Transition> transitions = new ArrayList<>();
        for(String rules : grammar.split("\n")) { // For each grammar rule in gramar.txt
            if(rules.equals("") || rules.charAt(0)=='#') { // check if it is a blank line or a comment
                continue; // if it is continue check on new line
            }
            String[] rulesArray = (rules+" |").split(" "); // splits the grammar line by " " and pipes a " " + | to the end
            String left = rulesArray[0];
            int i=2; // first instance of possibilities in grammar format. EG $_Number -> int | float | hexint | binint | octint . int would be at array index 2
            int old_i=2;
            List<Integer> hidden = new ArrayList<>();
            while(i < rulesArray.length) {
                if(rulesArray[i].length()==0) { // empty index skip
                    continue;
                }
                if(rulesArray[i].charAt(0)=='_') { // matches for hidden elements
                    hidden.add(i-old_i);
                    rulesArray[i] = rulesArray[i].substring(1); // skip that element in the arrray
                }
                if(rulesArray[i].equals("|")) { //
                    String[] right = new String[i-old_i];
                    System.arraycopy(rulesArray,old_i,right,0,i-old_i); // copy string from old_i to end
                    transitions.add(new Transition(left,right,new ArrayList<>(hidden))); // add to transition
                    hidden = new ArrayList<>(); // clear hidden
                    old_i = i+1; // add 1 to old_i
                }
                i++; // increment location in grammar.txt array
            }
        }
        transitions.add(0, new Transition("$START",new String[] {transitions.get(0).left})); // place start at the beggining of the transition list
        return transitions;
    }

    static public List<String> getSymbols(List<Transition> transitions) throws GrammarError {
        List<String> symbols = new ArrayList<>();
        for(Transition transition: transitions) {
            if(!symbols.contains(transition.left)) symbols.add(transition.left); // for each transition check if it contains that string
            for(String right : transition.right) { // for each string in the transition array
                if(!symbols.contains(right)) symbols.add(right); // add the string to symbols
            }
        }
        if(symbols.contains("$")) {
            throw new GrammarError("Can't use $ pattern in grammar. Replace with \\$");
        }
        symbols.add("$"); // got all symbols add terminal char
        return symbols;
    }

    // for each transition left that matches return that transition
    static public List<Transition> getTransitions(List<Transition> transitions, String left) {
        List<Transition> ret = new ArrayList<>();
        for(Transition transition: transitions) {
            if(transition.left.equals(left)) {
                ret.add(transition);
            }
        }
        return ret;
    }

    static public List<ItemSet> createItemLists(List<Transition> all_transitions, List<String> symbols) {

        List<ItemSet> itemLists = new ArrayList<>();

        List<Transition> first = new ArrayList<>();
        // add firstSet which contains first transition to itemLists
        first.add(new Transition(all_transitions.get(0),0));
        ItemSet firstSet = new ItemSet(all_transitions, first);
        itemLists.add(firstSet);
        //List<String>

        int i = 0;
        while(i < itemLists.size()) { // for each itemSet in itemLists
            ItemSet current = itemLists.get(i);
            for(String symbol : symbols) {
                List<Transition> next_steps = new ArrayList<>();
                for(Transition transition : current.all_rules) {
                    if(transition.hasNext() && transition.next().equals(symbol)) { // check if it has a next transition
                        next_steps.add(new Transition(transition, transition.pointer+1)); // it does add that transition to next_steps
                    } // repeat for all transitions for every symbol
                }
                boolean exists = false;
                for(ItemSet item : itemLists) {
                    if(item.equivalent(next_steps)) exists = true; // it there is an itemSet that matches the next transition set
                }
                if(next_steps.size()>0 && !exists) { // add all itemsets that have a transition set to itemLists
                    ItemSet current_set = new ItemSet(all_transitions, next_steps);
                    itemLists.add(current_set);
                }
            }
            i++;
        }

        return itemLists;
    }

    static public List<ExtendedTransition> createExtendedTransitions(List<ItemSet> itemSets) {
        List<ExtendedTransition> extendedTransitions = new ArrayList<>();

        for(int i = 0; i < itemSets.size(); i++ ) {
            ItemSet itemSet = itemSets.get(i);
            for(Transition transition:itemSet.all_rules) { // for each transition in each itemSet's all_rules
                if(transition.pointer==0) { // check if pointer is 0
                    extendedTransitions.add(new ExtendedTransition(itemSets, transition, i)); // add to extendedTransitions list
                }
            }
        }

        return extendedTransitions;
    }

    static public List<ExtendedSymbol> getExtendedSymbols(List<ExtendedTransition> extendedTransitions) {
        List<ExtendedSymbol> extendedSymbols = new ArrayList<>();
        for(ExtendedTransition extendedTransition : extendedTransitions) { // for each extended Transition
            if(!extendedSymbols.contains(extendedTransition.left)) { // check if extended symbol doesn't already contain that transitions symbol
                extendedTransition.left.index = extendedSymbols.size(); // transition symbol index = symbols size
                extendedSymbols.add(extendedTransition.left); // add symbol to extended symbol
            } else {
                extendedTransition.left.index = extendedSymbols.indexOf(extendedTransition.left); // set index to index of matching symbol
            }
            for(ExtendedSymbol extendedSymbol : extendedTransition.right) { // repeat above but for each symbol in transition array
                if(!extendedSymbols.contains(extendedSymbol)) {
                    extendedSymbol.index = extendedSymbols.size();
                    extendedSymbols.add(extendedSymbol);
                } else {
                    extendedSymbol.index = extendedSymbols.indexOf(extendedSymbol);
                }
            }
        }
        return extendedSymbols;
    }


    static public List<List<String>> firstSets(List<ExtendedTransition> extendedTransitions, List<ExtendedSymbol> extendedSymbols) {
        List<List<String>> firstSets = new ArrayList<>();

        // Many loops to come

        // Populate list
        for(int i = 0; i < extendedSymbols.size(); i++) {
            firstSets.add(new ArrayList<String>());
        }

        // Check for non-terminal symbols
        for(int i = 0; i < extendedSymbols.size(); i++) {
            String symbol = extendedSymbols.get(i).symbol;
            if(isTerminal(symbol)) {
                firstSets.get(i).add(symbol);
            }
        }

        // Check for non-terminal symbols, repeating until convergence
        // TODO: this is not the fast way to do this
        boolean changed = true;
        while(changed) {
            changed = false;
            for (int i = 0; i < extendedTransitions.size(); i++) {

                ExtendedSymbol left = extendedTransitions.get(i).left;
                //int left_index = extendedSymbols.indexOf(left);
                int left_index = left.index;


                List<ExtendedSymbol> right = extendedTransitions.get(i).right;
                for (int j = 0; j < right.size(); j++) {

                    ExtendedSymbol symbol = right.get(j);
                    //int symbol_index = extendedSymbols.indexOf(symbol);
                    int symbol_index = symbol.index;

                    boolean containsEpsilon = false;
                    for(String s : firstSets.get(symbol_index)) {
                        if(!firstSets.get(left_index).contains(s)) {
                            changed = true;
                            firstSets.get(left_index).add(s);
                        }
                        if(s.equals(LRTable.epsilon)) {
                            containsEpsilon = true;
                        }
                    }
                    if (!containsEpsilon) break;
                }
            }
        }

        return firstSets;
    }

    static public List<List<String>> followSets(List<ExtendedTransition> extendedTransitions, List<ExtendedSymbol> extendedSymbols, List<List<String>> firstSets) {
        List<List<String>> followSets = new ArrayList<>();

        // Many loops to come

        // Populate list
        for(int i = 0; i < extendedSymbols.size(); i++) {
            followSets.add(new ArrayList<String>());
        }

        followSets.get(0).add("$");

        // Create followSets, repeating until convergence
        // TODO: this is not the fast way to do this
        boolean changed = true;
        while(changed) {
            changed = false;
            for (int i = 0; i < extendedTransitions.size(); i++) {

                ExtendedTransition transition = extendedTransitions.get(i);
                ExtendedSymbol left = transition.left;
                //int left_index = extendedSymbols.indexOf(left);
                int left_index = left.index;
                List<String> left_followSet = followSets.get(left_index);


                for (int j = 0; j < transition.right.size() - 1; j++) {

                    ExtendedSymbol symbol_j = transition.right.get(j);
                    //int symbol_j_index = extendedSymbols.indexOf(symbol_j);
                    int symbol_j_index = symbol_j.index;

                    ExtendedSymbol symbol_k = transition.right.get(j + 1);
                    //int symbol_k_index = extendedSymbols.indexOf(symbol_k);
                    int symbol_k_index = symbol_k.index;

                    boolean containsEpsilon = false;
                    for (String symbol : firstSets.get(symbol_k_index)) {
                        if(symbol.equals(LRTable.epsilon)) {
                            containsEpsilon = true;
                            continue;
                        }

                        List<String> followSet = followSets.get(symbol_j_index);
                        if (!followSet.contains(symbol)) {
                            changed = true;
                            followSet.add(symbol);
                        }
                    }

                    if(containsEpsilon) {
                        for (String symbol : followSets.get(symbol_k_index)) {

                            List<String> followSet = followSets.get(symbol_j_index);
                            if (!followSet.contains(symbol)) {
                                changed = true;
                                followSet.add(symbol);
                            }
                        }
                    }

                }

                int j = transition.right.size()-1;
                ExtendedSymbol symbol_j = transition.right.get(j);
                //int symbol_j_index = extendedSymbols.indexOf(symbol_j);
                int symbol_j_index = symbol_j.index;

                for (String symbol : left_followSet) {

                    if (!followSets.get(symbol_j_index).contains(symbol)) {
                        changed = true;
                        followSets.get(symbol_j_index).add(symbol);
                    }

                }


            }

        }

        return followSets;
    }

    static public LRTable extendTable(List<Transition> transitions,
                                      List<ItemSet> itemSets,
                                      List<ExtendedTransition> extendedTransitions,
                                      List<String> symbols,
                                      List<ExtendedSymbol> extendedSymbols,
                                      List<List<String>> followSets) {

        LRTable lrTable = new LRTable(itemSets.size(), symbols);

        for(ItemSet itemSet : itemSets) { // for each itemSet in itemSets

            for(String symbol : symbols) { // for every symbol in symbols in each itemSet

                int transition = itemSet.next_transition(itemSets, symbol);
                String action = " ";
                if(transition>-1) {
                    action = "G"; // Goto
                    if (isTerminal(symbol)) {
                        action = "S"; // Else accept
                    }
                }

                lrTable.setCell(itemSets.indexOf(itemSet),symbol,transition,action); // populate each cell for each symbol and itemSet

            }
        }


        for(int i = 0; i < itemSets.size(); i++) {

            ItemSet itemSet = itemSets.get(i);

            boolean endRule = false;
            for(Transition transition : itemSet.all_rules) {
                if(transition.pointer == transition.right.length) endRule = true;
            }
            if(endRule) {
                lrTable.setCell(i,"$",-1,"$A"); // reached end of transition
            }

        }

        for(ExtendedTransition extendedTransition : extendedTransitions) {
            int original = transitions.indexOf(extendedTransition.originalTransition); //TODO
            int left_transition = extendedTransition.left.after;
            if(left_transition==-1) continue;

            int right_transition = extendedTransition.right.get(extendedTransition.right.size()-1).after;

            int index = extendedTransition.left.index;
            List<String> followSet = followSets.get(index);

            for(String symbol : followSet) {
                lrTable.setCell(right_transition,symbol,original,"R",extendedTransition.left.symbol,extendedTransition.right.size(),extendedTransition.originalTransition.hidden);
            }

        }


        return lrTable;
    }


    static public LRParser generate(String grammar) throws GrammarError {
        List<Transition> all_transitions = stringToTransitions(grammar); // list of transitions from grammar.txt
        List<String> genSymbols = getSymbols(all_transitions); // get symbols from each transition in all_transitions
        if(all_transitions.size()==0) {
            return null; // nothing to generate
        }

        List<ItemSet> genItemLists = createItemLists(all_transitions, genSymbols);
        List<ExtendedTransition> genExtendedTransitions = createExtendedTransitions(genItemLists);
        List<ExtendedSymbol> genExtendedSymbols = getExtendedSymbols(genExtendedTransitions);
        List<List<String>> genFirstSets = firstSets(genExtendedTransitions, genExtendedSymbols);
        List<List<String>> genFollowSets = followSets(genExtendedTransitions, genExtendedSymbols,genFirstSets);
        LRTable parseTable = extendTable(all_transitions,genItemLists,genExtendedTransitions, genSymbols, genExtendedSymbols, genFollowSets);
        //System.out.println(parseTable.toString());

        return new LRParser(parseTable, grammar.hashCode());
    }

}