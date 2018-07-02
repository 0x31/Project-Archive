package com.reilly.comp2100_assignment_2_2016.Expressions;

/**
 * Created by ***REMOVED*** on 28/04/2016.
 */

public class Value {

    /*public enum Unit {
        UNIT,METER,KILOMETER,CENTIMETER,MILIMETER;
    }*/

    public double value;
    public Unit unit;

    public Value(double value, String unitS) {
        this.value = value;
        //if(unitS.equals("u")) unit=Unit.Number.UNIT;
        if(unitS.equals("m")) unit=new Unit.Distance(Unit.Prefix.BASE);
        else if(unitS.equals("km")) unit=new Unit.Distance(Unit.Prefix.kilo);
        else if(unitS.equals("cm")) unit=new Unit.Distance(Unit.Prefix.centi);
        else if(unitS.equals("mm")) unit=new Unit.Distance(Unit.Prefix.milli);
        else if(unitS.equals("B")) unit=new Unit.Bytes(Unit.Prefix.BASE);
        else if(unitS.equals("KB")) unit=new Unit.Bytes(Unit.Prefix.kilo);
        else if(unitS.equals("MB")) unit=new Unit.Bytes(Unit.Prefix.mega);
        else if(unitS.equals("GB")) unit=new Unit.Bytes(Unit.Prefix.giga);
        else unit = Unit.Number.UNIT;
    }

    public Value(double value, Value old) {
        this.value = value;
        this.unit = old.unit;
    }

    public Value(double value, Value[] olds) {
        this.value = value;
        this.unit = olds[0].unit;
    }

    public Value(double value) {
        this.value = value;
        unit=Unit.Number.UNIT;
    }

    @Override
    public String toString() {
        return value+unit.getShort();
    }

    static public Value[] convert(Value[] values) throws Expression.EvaluateError {
        Value greaterV = null;
        for (Value value : values) {
            if(greaterV==null || value.unit.getValue()>greaterV.unit.getValue()) {
                greaterV = value;
            }
        }
        Value[] valuesR = new Value[values.length];
        for(int i = 0; i<values.length; i++) {
            Value vi = values[i];
            System.out.println(vi.value + ", "+vi.unit);
            valuesR[i] = new Value(vi.unit.convertTo(vi.value,greaterV.unit),greaterV);
        }
        return valuesR;
    }

}
