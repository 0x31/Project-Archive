package com.reilly.comp2100_assignment_2_2016.Parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by ***REMOVED*** on 6/05/2016.
 */

public class LRTable implements Serializable {

    public static final String epsilon = "ε";


    public class Cell implements Serializable {

        int transition = -1;
        String action = "G";

        String next;
        int popCount = 0;
        List<Integer> hidden;

        public Cell(int transition, String action) {
            this.transition = transition;
            this.action = action;
        }

        @Override
        public String toString() {
            String ret = action;
            if(transition>-1) {ret+=transition; }
            while(ret.length()<3) { ret+=" "; };
            return ret;
        }
    }

    static public boolean isTerminal(String symbol) {
        return !symbol.matches("^\\$.+");
    }

    List<List<Cell>> rows = new ArrayList<>();
    List<String> symbols;

    // Public Constructor
    public LRTable(int rowCount, List<String> symbols) {
        this.symbols = symbols;
        for(int i = 0; i < rowCount; i++) {
            List<Cell> row = new ArrayList<>();
            for(String symbol : symbols) {
                row.add(new Cell(-1,"G"));
            }
            rows.add(row);
        }
    }

    public void setCell(int row, String symbol, int transition, String action) {
        Cell cell = this.rows.get(row).get(symbols.indexOf(symbol));
        cell.transition = transition;
        cell.action = action;
    }

    public void setCell(int row, String symbol, int transition, String action, String next, int popCount, List<Integer> hidden) {
        Cell cell = this.rows.get(row).get(symbols.indexOf(symbol));
        cell.transition = transition;
        cell.action = action;
        cell.next = next;
        cell.popCount = popCount;
        cell.hidden = hidden;
    }

    @Override
    public String toString() {
        String r = "   ";
        for(String symbol: symbols) r+=" "+symbol+"  ";
        for(int i=0;i<rows.size();i++) {
            r+="\n"+Integer.toString(i)+":";
            for(Cell cell: rows.get(i)) {
                r+=" "+cell.toString();
            }
        }
        return r;
    }

}
