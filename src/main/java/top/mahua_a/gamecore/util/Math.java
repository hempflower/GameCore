package top.mahua_a.gamecore.util;

public class Math {
    public static boolean between(double val0,double val1,double input){
        return java.lang.Math.min(val0, val1) <= input && java.lang.Math.max(val0, val1) >= input;
    }
}
