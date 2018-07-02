package com.reilly.comp2100_assignment_2_2016.Parser;

import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.Binint_builtin;
import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.Character_builtin;
import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.Hexint_builtin;
import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.Octint_builtin;
import com.reilly.comp2100_assignment_2_2016.Expressions.Expression;
import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.Float_builtin;
import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.Int_builtin;
import com.reilly.comp2100_assignment_2_2016.Expressions.Builtins.String_builtin;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by ***REMOVED*** on 6/05/2016.
 * Commented by Reilly
 */

public class LRParser implements Parser, Serializable {

    LRTable lrTable;

    public int grammarHash;

    public LRParser(LRTable lrTable, int grammarHash) {
        this.lrTable = lrTable;
        this.grammarHash = grammarHash;
    }

    public List<String> getExpected() {
        return lrTable.symbols;
    }

    @Override
    public Expression parse(Tokenizer tokenizer) throws SyntaxError {

        List<Integer> parsed = new ArrayList<>();
        Stack<Integer> parseStack = new Stack<>();
        // First pass set row 0
        parseStack.push(0);
        //Stack<RoseTree> treeStack = new Stack<>();

        Stack<Expression> expressionStack = new Stack<>();

        boolean accept = false;

        // Loop until accepting state. i.e action.action.equals("$A")
        while(!accept) {
            List<LRTable.Cell> row = lrTable.rows.get(parseStack.peek());
            Token tokenT = tokenizer.peek();
            String token;
            if(tokenT == null) {
                // Use $ for EOF symbol
                token = "$";
            } else {
                token = tokenT.tokenString.replaceAll("^\\$","\\$").replaceAll("^ε","\\ε"); // If string starts with \$ replace with ε , replace \$ with ε
            }

            // Symbols get filled from argument call
            int tokenIndex = lrTable.symbols.indexOf(token);
            if(tokenIndex==-1) { // Symbol not recognised. Symbol list is generated from LALRPaserGenerator
                throw new SyntaxError("Unrecognised "+tokenT.potential+" at position "+tokenT.pointer, tokenT.position,tokenT.potential.length());
            }
            LRTable.Cell action = row.get(tokenIndex);

            if(action.action.equals("S")) {

                Expression expression;
                switch (tokenT.tokenType) {
                    case integer_token:
                        expression = new Int_builtin(tokenT.value); break;
                    case hexint_token:
                        expression = new Hexint_builtin(tokenT.value); break;
                    case binint_token:
                        expression = new Binint_builtin(tokenT.value); break;
                    case octint_token:
                        expression = new Octint_builtin(tokenT.value); break;
                    case string_token:
                        expression = new String_builtin(tokenT.value); break;
                    case float_token:
                        expression = new Float_builtin(tokenT.value); break;
                    default:
                        expression = new Character_builtin(tokenT.value); break;
                }

                parseStack.push(action.transition);
                //treeStack.push(new RoseTree(token));
                expressionStack.push(expression);
                tokenizer.next();
            } else if(action.action.equals("R")) {


                String goto_s = action.next;

                //RoseTree rule = new RoseTree(action.next);
                Expression[] expressions = new Expression[action.popCount-action.hidden.size()];
                Class[] types = new Class[action.popCount-action.hidden.size()];

                parsed.add(action.transition);

                // Seperate counter j in case of hidden Expressions
                int j = action.popCount-action.hidden.size()-1;
                for(int i = action.popCount-1; i >= 0; i--) {
                    //rule.add(treeStack.pop());
                    parseStack.pop();
                    if(!action.hidden.contains(i)) {
                        expressions[j] = expressionStack.pop();
                        types[j] = Expression.class;
                        j--;
                    } else {
                        expressionStack.pop();
                    }
                }

                // TODO: replace hardcoded string
                // Perhaps get the first bit from the current class's
                String prefix = Expression.class.getName().replaceAll("[A-Za-z]*$","");
                String className = prefix+"Customs."+goto_s.substring(1)+"_Expression"; // Load custom expressions. Name hardcoded
                if(goto_s.substring(1,2).equals("_")) {
                    className = prefix+"Builtins.Transition_Expression";
                }
                Class cl;
                try {
                    // WARNING: Create class from string.
                    cl = Class.forName(className);
                    Constructor con = cl.getConstructor(types);
                    Object xyz = con.newInstance(expressions);
                    expressionStack.push((Expression) xyz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    //TODO: replace with GrammarError (create GrammarError first)
                    throw new SyntaxError("CLASS NOT FOUND: "+goto_s.substring(1));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    //TODO: replace with GrammarError
                    throw new SyntaxError("CLASS PARAM NUMBER ERROR: "+goto_s.substring(1)+", "+(action.popCount-action.hidden.size()));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


                List<LRTable.Cell> tmp_row = lrTable.rows.get(parseStack.peek());
                LRTable.Cell tmp_cell = tmp_row.get(lrTable.symbols.indexOf(goto_s));

                //treeStack.push(rule);
                parseStack.push(tmp_cell.transition);

            } else if(action.action.equals("$A")) {
                /** ACCEPTING STATE */
                accept = true;
            } else {


                /* CHECK FOR EPSILON TRANSITION */
                if(lrTable.symbols.contains(LRTable.epsilon)) {
                    tokenIndex = lrTable.symbols.indexOf(LRTable.epsilon);
                    action = row.get(tokenIndex);
                    if(!action.action.equals(" ")) {
                        parseStack.push(action.transition);
                        expressionStack.push(new Character_builtin(LRTable.epsilon));
                        //tokenizer.next();
                        continue;
                    }
                }

                /* ERROR REPORTING */
                // TODO: clean this section up (e.g. cheking tokenT==null twice)
                List<String> expected = getExpected(parseStack.peek());
                String eofWarning = "Incomplete expression.";
                String unexpectedWarning = "";
                if(tokenT!=null) {
                    unexpectedWarning = "Unexpected " + tokenT.potential;
                }
                if(expected.size()==1 && (lrTable.isTerminal(expected.get(0)))) {
                    eofWarning = "Expected "+expected.get(0);
                    unexpectedWarning = "Expected "+expected.get(0);
                }
                if(expected.contains(")")) {
                    eofWarning = " Mismatched bracket.";
                    unexpectedWarning = " Mismatched bracket";
                } else if (expected.contains(",") || expected.size()>10) { // arbitrary number
                    eofWarning = " Missing parameter.";
                    unexpectedWarning = " Missing parameter";
                }

                if (tokenT == null) {
                    throw new SyntaxError(eofWarning);
                } else {
                    throw new SyntaxError(unexpectedWarning+" at position " + tokenT.pointer + ".", tokenT.position,tokenT.potential.length());
                }


            }
        }

        return expressionStack.pop();
    }
    // For a row check each cell for the shift command then return that grammar symbol
    public List<String> getExpected(int rowN) {
        List<String> exp = new ArrayList<>();
        List<LRTable.Cell> row = lrTable.rows.get(rowN);
        for(int i = 0; i<row.size(); i++) {
            LRTable.Cell cell = row.get(i);
            if(cell.action.equals("S")) {
                exp.add(lrTable.symbols.get(i));
            }
        }

        return exp;
    }


    @Override
    public int hashCode() {
        return grammarHash;
    }
}
