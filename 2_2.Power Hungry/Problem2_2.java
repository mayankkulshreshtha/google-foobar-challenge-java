

import java.math.BigInteger;
/*
Power Hungry

Commander Lambda's space station is HUGE. And huge space stations take a LOT of power. Huge space stations with doomsday devices take even more power. To help meet the station's power needs, Commander Lambda has installed solar panels on the station's outer surface. But the station sits in the middle of a quasar quantum flux field, which wreaks havoc on the solar panels. You and your team of henchmen has been assigned to repair the solar panels, but you can't take them all down at once without shutting down the space station (and all those pesky life support systems!).

You need to figure out which sets of panels in any given array you can take offline to repair while still maintaining the maximum amount of power output per array, and to do THAT, you'll first need to figure out what the maximum output of each array actually is. Write a function answer(xs) that takes a list of integers representing the power output levels of each panel in an array, and returns the maximum product of some non-empty subset of those numbers. So for example, if an array contained panels with power output levels of [2, -3, 1, 0, -5], then the maximum product would be found by taking the subset: xs[0] = 2, xs[1] = -3, xs[4] = -5, giving the product 2*(-3)*(-5) = 30. So answer([2,-3,1,0,-5]) will be "30".

Each array of solar panels contains at least 1 and no more than 50 panels, and each panel will have a power output level whose absolute value is no greater than 1000 (some panels are malfunctioning so badly that they're draining energy, but you know a trick with the panels' wave stabilizer that lets you combine two negative-output panels to produce the positive output of the multiple of their power values). The final products may be very large, so give the answer as a string representation of the number.

Languages

To provide a Python solution, edit solution.py To provide a Java solution, edit solution.java

Test cases

Inputs: (int list) xs = [2, 0, 2, 2, 0] Output: (string) "8"

Inputs: (int list) xs = [-2, -3, 4, -5] Output: (string) "60"
 */
public class Problem2_2 {
    /*
    sol2
    public static String solution(int[] xs) {
        int countNegative = 0;
        int biggestNegative=Integer.MIN_VALUE;
        boolean noPositive=true;
        BigInteger result = BigInteger.ONE;
        for(int i=0; i<xs.length;i++){
            if(xs[i]==0){
                continue;
            }
            result = result.multiply(BigInteger.valueOf(xs[i]));
            if(xs[i]<0){
                countNegative++;
                if(xs[i]>biggestNegative){
                    biggestNegative=xs[i];
                }
            }
            if(xs[i]>0){
                noPositive = false;
            }
        }
        if(noPositive && countNegative<2){
            return "0";
        }
        if(countNegative%2==1){
            result = result.divide(BigInteger.valueOf(biggestNegative));
        }
        return result.toString();
    }
    */
    /*
    //Sol1
    public static String solution(int[] xs){
        //String res="";
        int countNegative=0;
        int biggestNegative=Integer.MIN_VALUE;
        boolean noPositive = true;
        for (int i = 0; i < xs.length; i++) {
            if(xs[i]<0){
                countNegative++;
                if(xs[i]>biggestNegative){
                    biggestNegative=xs[i];
                }
            }
            if(xs[i]>0){
                noPositive = false;
            }
        }
        if(noPositive && countNegative<2){
            return "0";
        }
        BigInteger result = BigInteger.ONE;
        boolean skipDone = countNegative%2==1?false:true;
        for (int i = 0; i < xs.length; i++) {
            if(xs[i]==0) {
                continue;
            }
            if(xs[i]<0 && !skipDone && xs[i]==biggestNegative){
                skipDone=true;
            } else{
                result =  result.multiply(BigInteger.valueOf(xs[i]));
            }
        }

        return result.toString();
    }

     */
    public static String solution(int[] xs) {
        if(xs.length==1){
            return ""+xs[0];
        }
        int countNegative = 0;
        int biggestNegative=Integer.MIN_VALUE;
        boolean noPositive=true;
        BigInteger result = BigInteger.ONE;
        for(int i=0; i<xs.length;i++){
            if(xs[i]==0){
                continue;
            }
            result = result.multiply(BigInteger.valueOf(xs[i]));
            if(xs[i]<0){
                countNegative++;
                if(xs[i]>biggestNegative){
                    biggestNegative=xs[i];
                }
            }
            if(xs[i]>0){
                noPositive = false;
            }
        }
        if(noPositive && countNegative<2){
            return "0";
        }
        if(countNegative%2==1){
            result = result.divide(BigInteger.valueOf(biggestNegative));
        }
        return result.toString();
    }
    public static void main(String[] args) {
        //String res = Problem2_2.solution(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE,Integer.MIN_VALUE});
        String res = Problem2_2.solution(new int[]{});

        System.out.println(res);

    }
}
