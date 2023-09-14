package org.mayank.go;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Doomsday Fuel
Making fuel for the LAMBCHOP’s reactor core is a tricky process because of the exotic matter involved. It starts as raw ore, then during processing, begins randomly changing between forms, eventually reaching a stable form. There may be multiple stable forms that a sample could ultimately reach, not all of which are useful as fuel.

Commander Lambda has tasked you to help the scientists increase fuel creation efficiency by predicting the end state of a given ore sample. You have carefully studied the different structures that the ore can take and which transitions it undergoes. It appears that, while random, the probability of each structure transforming is fixed. That is, each time the ore is in 1 state, it has the same probabilities of entering the next state (which might be the same state). You have recorded the observed transitions in a matrix. The others in the lab have hypothesized more exotic forms that the ore can become, but you haven’t seen all of them.

Write a function solution(m) that takes an array of array of nonnegative ints representing how many times that state has gone to the next state and return an array of ints for each terminal state giving the exact probabilities of each terminal state, represented as the numerator for each state, then the denominator for all of them at the end and in simplest form. The matrix is at most 10 by 10. It is guaranteed that no matter which state the ore is in, there is a path from that state to a terminal state. That is, the processing will always eventually end in a stable state. The ore starts in state 0. The denominator will fit within a signed 32-bit integer during the calculation, as long as the fraction is simplified regularly.

For example, consider the matrix m:

[
  [0,1,0,0,0,1],  # s0, the initial state, goes to s1 and s5 with equal probability
  [4,0,0,3,2,0],  # s1 can become s0, s3, or s4, but with different probabilities
  [0,0,0,0,0,0],  # s2 is terminal, and unreachable (never observed in practice)
  [0,0,0,0,0,0],  # s3 is terminal
  [0,0,0,0,0,0],  # s4 is terminal
  [0,0,0,0,0,0],  # s5 is terminal
]
So, we can consider different paths to terminal states, such as:

s0 -> s1 -> s3
s0 -> s1 -> s0 -> s1 -> s0 -> s1 -> s4
s0 -> s1 -> s0 -> s5
Tracing the probabilities of each, we find that

s2 has probability 0
s3 has probability 3/14
s4 has probability 1/7
s5 has probability 9/14
So, putting that together, and making a common denominator, gives an answer in the form of

[s2.numerator, s3.numerator, s4.numerator, s5.numerator, denominator] which is [0, 3, 2, 9, 14].

 */
public class Problem3_2 {

    static class Fraction implements Comparable<Fraction>{
        int numerator;
        int denominator = 1;

        public Fraction(int numerator, int denominator){
            this.numerator = numerator;
            if(numerator!=0)
                this.denominator = denominator;
            simplify(this);
        }
        void simplify(Fraction f){
            int hcf = hcf(Math.abs(f.numerator), Math.abs(f.denominator));
            this.numerator = this.numerator/hcf;
            this.denominator = this.denominator/hcf;
            if(this.denominator<1){
                this.denominator=-1*this.denominator;
                this.numerator = -1*this.numerator;
            }
        }
        int hcf(int d1, int d2){
            if(d2 == 0)
                return d1;
            return hcf(d2, d1%d2);
        }
        int lcm(int l1, int l2){
            int lcm = (l1/hcf(l1,l2))*l2;
            return lcm;
        }

        public Fraction subtractBy(Fraction f){
            int lcm = lcm(f.denominator,this.denominator);
            Fraction f1 = new Fraction(this.numerator*(lcm/this.denominator) - (lcm/f.denominator*f.numerator),
                    lcm);
            return f1;
        }
        public Fraction addWith(Fraction f){
            int lcm = lcm(f.denominator,this.denominator);
            Fraction f1 = new Fraction(this.numerator*(lcm/this.denominator) + (lcm/f.denominator*f.numerator),
                    lcm);
            return f1;
        }
        public Fraction multiplyWith(Fraction f){
            int hcf1 = hcf(this.numerator, f.denominator);
            int hcf2 = hcf(this.denominator, f.numerator);
            return new Fraction((this.numerator/hcf1)*(f.numerator/hcf2),
                    (f.denominator/hcf1)*(this.denominator/hcf2));
        }
        public Fraction divideBy(Fraction f){
            int hcf1 = hcf(this.numerator, f.numerator);
            int hcf2 = hcf(this.denominator, f.denominator);
            return new Fraction((this.numerator/hcf1)*(f.denominator/hcf2),
                    (f.numerator/hcf1)*(this.denominator/hcf2));
        }

        @Override
        public int compareTo(Fraction fraction){
            int tn = this.numerator;
            int fn = fraction.numerator;
            if(this.denominator != fraction.denominator){
                tn*=fraction.denominator;
                fn*=this.denominator;
            }
            if(tn>fn){
                return 1;
            } else if(tn<fn){
                return -1;
            } else
                return 0;
        }
    }
    public static Fraction[][] inverseOfMatrix(Fraction[][]f){
        int length = f.length;
        Fraction[][] augMatrix = new Fraction[f.length][2*f.length];
        for(int i=0;i<length;i++){
            for (int j = 0; j <length; j++) {
                augMatrix[i][j]=f[i][j];
            }
        }
        for(int i=0;i<length;i++){
            for (int j = 0; j <2*length; j++) {
                if(j==(i+length)){
                    augMatrix[i][j]=new Fraction(1,1);
                } else if(augMatrix[i][j]==null){
                    augMatrix[i][j]=new Fraction(0,1);
                }
            }
        }
        /*
        for(int i=length-1; i>0; i--){
            if(augMatrix[i-1][0].compareTo(augMatrix[i][0]) == -1){
                Fraction[] tempArr = augMatrix[i];
                augMatrix[i] = augMatrix[i-1];
                augMatrix[i-1] = tempArr;
            }
        }
        */
        for(int i=0;i<length;i++){
            for (int j = 0; j <length; j++) {
                if(j!=i){
                    Fraction temp = augMatrix[j][i].divideBy(augMatrix[i][i]);
                    for(int k=0;k<2*length;k++){
                        augMatrix[j][k] = augMatrix[j][k].subtractBy(augMatrix[i][k].multiplyWith(temp));
                    }
                }
            }
        }
        for(int i=0;i<length;i++){
            Fraction temp = augMatrix[i][i];
            for (int j = 0; j <2*length; j++) {
                augMatrix[i][j] = augMatrix[i][j].divideBy(temp);
            }
        }
        Fraction[][] inverseMatrix = new Fraction[length][length];
        for(int i=0;i<length;i++){
            int col =0;
            for (int j = length; j <2*length; j++) {
                inverseMatrix[i][col]= augMatrix[i][j];
                col++;
            }
        }
        return inverseMatrix;

    }
    public static int[] solution(int[][] m) {
        List<Integer> absorbingStatesRows = new ArrayList<>();
        List<Integer> transientStatesRows = new ArrayList<>();
        Map<Integer, Integer>stateDenominator = new HashMap<>();
        for(int i=0; i<m.length;i++){
            boolean isAbsorbingState = true;
            int totalTrips = 0;
            for (int j = 0; j < m[i].length; j++) {
                if(m[i][j]>0){
                    isAbsorbingState = false;
                    totalTrips+=m[i][j];
                }
            }
            if(isAbsorbingState){
                absorbingStatesRows.add(i);
            } else{
                transientStatesRows.add(i);
                stateDenominator.put(i, totalTrips);
            }
        }
        if(absorbingStatesRows.contains(0)){
            int[] tr = new int[absorbingStatesRows.size()+1];
            tr[0]=1;
            tr[absorbingStatesRows.size()] =1;
            for (int i = 1; i < absorbingStatesRows.size(); i++) {
                tr[i]=0;
            }
            return tr;
        }
        Fraction[][]f = new Fraction[transientStatesRows.size()][transientStatesRows.size()];
        Fraction[][]r = new Fraction[transientStatesRows.size()][absorbingStatesRows.size()];
        Fraction iZero = new Fraction(0,1);
        Fraction iOne = new Fraction(1,1);
        int rowNum = 0;
        for(Integer i:transientStatesRows){
            int colNum = 0;
            for(Integer j:transientStatesRows){
                Fraction fraction = new Fraction(m[i][j], stateDenominator.get(i));
                if(i==j){
                    f[rowNum][colNum] = iOne.subtractBy(fraction);
                } else {
                    f[rowNum][colNum] = iZero.subtractBy(fraction);
                }
                colNum++;
            }
            rowNum++;
        }
        rowNum = 0;
        for(Integer i:transientStatesRows){
            int colNum = 0;
            for(Integer j:absorbingStatesRows){
                r[rowNum][colNum] = new Fraction(m[i][j], stateDenominator.get(i));
                colNum++;
            }
            rowNum++;
        }

        Fraction[][]fi = inverseOfMatrix(f);
        Fraction res[][] = multiplication(fi,r);
        List<Integer> ds = new ArrayList<>();
        for (int i = 0; i < res[0].length; i++) {
            ds.add(res[0][i].denominator);
        }
        int lcm = lcm(ds);
        int []result = new int[res[0].length+1];
        for (int i = 0; i < res[0].length; i++) {
            result[i] = (lcm/res[0][i].denominator)*res[0][i].numerator;
        }
        result[result.length-1] = lcm;

        return result;
    }

    static Fraction[][] multiplication(Fraction[][]a, Fraction[][]b){
        Fraction[][]res = new Fraction[a.length][b[0].length];
        for(int i=0;i<a.length;i++){
            for (int j = 0; j <b[0].length; j++) {
                for(int k=0;k<b.length;k++){
                    if(res[i][j] == null){
                        res[i][j] = a[i][k].multiplyWith(b[k][j]);
                    } else {
                        res[i][j] = res[i][j].addWith(a[i][k].multiplyWith(b[k][j]));
                    }
                }
            }
        }
        return res;
    }
    static int hcf(int d1, int d2){
        if(d2 == 0)
            return d1;
        return hcf(d2, d1%d2);
    }
    static int lcm(List<Integer> ds){
        int lcm = ds.get(0);
        for(int i=1;i<ds.size();i++){
            int d1 = lcm;
            int d2 = ds.get(i);
            int hcf = hcf(d1,d2);
            lcm = (lcm*ds.get(i))/hcf;
        }
        return lcm;
    }
    public static void main(String[] args) {
        int[][] arr2 = {{0, 1, 0, 0, 0, 1}, {4, 0, 0, 3, 2, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
        int[][] arr3 = {
                {1,103,100,100,100},
                {100,1,104,100,100},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}

        };
        int[] res = solution(arr3);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]+", ");
        }
    }
}
