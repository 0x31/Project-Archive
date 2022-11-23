package com.parser.Expressions;

/**
 * Created on 18/05/2016.
 */
public interface Unit {

    class UnitError extends Exception {
        public UnitError(String msg) {super(msg);}
    }
    double getValue();
    String getShort();

    double convertTo(double value, Unit u2) throws Expression.EvaluateError;

    enum Prefix {
        BASE(1, ""),deca(1.0/10,"d"),centi(1.0/100,"c"),milli(1.0/1000,"m"),kilo(1000,"k"),mega(1000000,"M"),giga(1000000000,"G");
        double value;
        String shortS;
        Prefix(double value, String shortS) {this.value = value; this.shortS = shortS; }
    }


    enum Number implements Unit {
        UNIT;
        public double convertTo(double value, Unit u2) throws Expression.EvaluateError {
            return value;
        }
        public double getValue() { return 0; }
        public String getShort() {return "u";}
    }

    class Distance implements Unit {

        public Distance(Prefix p) {
            this.prefix = p;
            this.value = p.value;
            this.shortS = p.shortS+"m";
        }
        private Prefix prefix;
        private double value;
        private String shortS;
        public double convertTo(double value, Unit u2) throws Expression.EvaluateError {
            if(!(u2 instanceof Distance)) throw new Expression.EvaluateError("Incompatible classes "+this+" and "+u2);
            Distance d2 = (Distance) u2;
            System.out.println(this.value);
            System.out.println(d2.value);
            return value * this.value/d2.value;
        }
        public double getValue() { return value; }
        public String getShort() { return shortS; }
    }

    class Bytes implements Unit {

        public Bytes(Prefix p) {
            this.prefix = p;
            this.value = p.value;
            this.shortS = p.shortS+"B";
        }
        private Prefix prefix;
        private double value;
        private String shortS;
        public double convertTo(double value, Unit u2) throws Expression.EvaluateError {
            if(!(u2 instanceof Bytes)) throw new Expression.EvaluateError("Incompatible classes "+this+" and "+u2);
            Bytes d2 = (Bytes) u2;
            System.out.println(this.value);
            System.out.println(d2.value);
            return value * this.value/d2.value;
        }
        public double getValue() { return value; }
        public String getShort() { return shortS; }
    }

}
