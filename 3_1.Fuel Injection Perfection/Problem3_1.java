package org.mayank.go;

import java.math.BigInteger;

/*
Fuel Injection Perfection
Commander Lambda has asked for your help to refine the automatic quantum antimatter fuel injection system for her LAMBCHOP doomsday device. It's a great chance for you to get a closer look at the LAMBCHOP - and maybe sneak in a bit of sabotage while you're at it - so you took the job gladly.

Quantum antimatter fuel comes in small pellets, which is convenient since the many moving parts of the LAMBCHOP each need to be fed fuel one pellet at a time. However, minions dump pellets in bulk into the fuel intake. You need to figure out the most efficient way to sort and shift the pellets down to a single pellet at a time.

The fuel control mechanisms have three operations:

Add one fuel pellet Remove one fuel pellet Divide the entire group of fuel pellets by 2 (due to the destructive energy released when a quantum antimatter pellet is cut in half, the safety controls will only allow this to happen if there is an even number of pellets) Write a function called solution(n) which takes a positive integer as a string and returns the minimum number of operations needed to transform the number of pellets to 1. The fuel intake control panel can only display a number up to 309 digits long, so there won't ever be more pellets than you can express in that many digits.

For example: solution(4) returns 2: 4 -> 2 -> 1 solution(15) returns 5: 15 -> 16 -> 8 -> 4 -> 2 -> 1

Test cases

Inputs: (string) n = "4" Output: (int) 2

Inputs: (string) n = "15" Output: (int) 5
 */
public class Problem3_1 {
    public static int solution(String x) {
        int count = 0;
        BigInteger bi = new BigInteger(x);
        BigInteger bi1 = new BigInteger("1");
        BigInteger bi2 = new BigInteger("2");
        BigInteger bi3 = new BigInteger("3");
        BigInteger bi100 = new BigInteger("100");
        while(bi.compareTo(bi1)!=0){
            int mod100=bi.mod(bi100).intValue();

            if(mod100%2==0){
                bi = bi.divide(bi2);
            } else {
                if(!bi.equals(bi3) && ((mod100+1)/2)%2 == 0){
                    bi = bi.add(bi1);
                } else{
                    bi = bi.subtract(bi1);
                }
            }
            count++;
        }
        return count;
    }
    /*
    public static int solution(String x){
        int count = 0;
        BigInteger bi = new BigInteger(x);
        BigInteger bi1 = new BigInteger("1");
        BigInteger bi2 = new BigInteger("2");
        while(bi.intValue()>1){
            int mod10 = bi.intValue()%100;
            if(mod10%2==0){
                bi = bi.divide(bi2);
            } else {
                //int ones = mod10%10;
                //int tens = mod10/10;
                if(((mod10+1)/2)%2 ==0){
                    bi = bi.add(bi1);
                } else{
                    bi = bi.subtract(bi1);
                }
            }
            count++;
        }
        return count;
    }

     */
    public static void main(String[] args) {
        BigInteger b = new BigInteger("1");
        BigInteger bi1 = new BigInteger("1");
        System.out.println(b.compareTo(bi1));
        System.out.println(b.remainder(new BigInteger("100")));
        System.out.println(b.intValue());
        int res = Problem3_1.solution("4");//12345678901234567890
        System.out.println(res);
    }
}
