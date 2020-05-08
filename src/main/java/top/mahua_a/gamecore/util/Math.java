package top.mahua_a.gamecore.util;

public class Math {
    public static boolean between(double val0,double val1,double input){
        if(val0 < val1){
            if(val0 <= input && val1 >= input){
                return true;
            }
        }else{
            if(val1 <= input && val0 >= input){
                return true;
            }
        }
        return false;
    }
}
