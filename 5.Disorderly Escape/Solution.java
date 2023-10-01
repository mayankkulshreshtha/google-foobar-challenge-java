import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;;
import java.util.ArrayList;
;
import java.util.Map;
import java.util.HashMap;



/**
 * Frankly speaking , i could not solve it, my brute force could pass 5 test casses.
 * i read about abstract algebra and Burnside’s Lemma for almost a week but to no success.
 * so i looked up for internet to understand the maths.
 * Took help from multiple options
 * https://medium.com/@chris.bell_/google-foobar-as-a-non-developer-level-5-a3acbf3d962b
 * https://groupprops.subwiki.org/wiki/Conjugacy_class_size_formula_in_symmetric_group
 * https://en.wikipedia.org/wiki/Partition_(number_theory)
 * https://www.youtube.com/watch?v=ezg5vNs58ic
 * once i understood the algorithm, i Coded it myself. but if you ask me i wasnt that happy :(
 */
public class Solution {

    public static BigInteger factorial(int n) {
        BigInteger r = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            r = r.multiply(BigInteger.valueOf(i));
        }
        return r;
    }
    static int hcf(int d1, int d2){
        if(d2 == 0)
            return d1;
        return hcf(d2, d1%d2);
    }
    public static int exponent(List<Integer> rlist, List<Integer> clist) {
        int sum = 0;
        for (int a : rlist) {
            for (int b : clist) {
                sum += hcf(a, b);
            }
        }
        return sum;
    }
    //https://groupprops.subwiki.org/wiki/Conjugacy_class_size_formula_in_symmetric_group
    static BigInteger conjugacy(List<Integer> l, int n){
        Map<Integer, Integer> m = new HashMap<>();
        for (Integer i: l) {
            m.put(i, m.getOrDefault(i,0)+1);
        }
        BigInteger c = BigInteger.ONE;
        for (Map.Entry<Integer, Integer> e:m.entrySet()) {
            c = c.multiply(factorial(e.getValue()).multiply(BigDecimal.valueOf(Math.pow(Double.valueOf(e.getKey()),Double.valueOf(e.getValue()))).toBigInteger()));
        }
        BigInteger f = factorial(n);
        return f.divide(c);
    }
    public static String solution(int w, int h, int s) {
        BigInteger divisior = factorial(w).multiply(factorial(h));
        List<Integer> hNums = new ArrayList<>();

        for (int i = 1; i <=h ; i++) {
            hNums.add(i);
        }
        List<List<Integer>> rlist = new ArrayList<>();
        numberPartitions(h, hNums,new ArrayList<>(),rlist);

        List<Integer> wNums = new ArrayList<>();
        for (int i = 1; i <=w ; i++) {
            wNums.add(i);
        }
        BigInteger result = BigInteger.ZERO;

        List<List<Integer>> clist = new ArrayList<>();
        numberPartitions(w, wNums,new ArrayList<>(),clist);


        for (List<Integer> r : rlist){
            for (List<Integer> c : clist) {
                int exp = exponent(r, c); //expo
                BigInteger C = conjugacy(r,h).multiply(conjugacy(c,w)); //multipying the cojugacy sums of number paritions
                //C1*C2*(pow(s,exp))
                result = result.add(C.multiply(new BigInteger(""+s).pow(exp)));
            }
        }
        // divide by the Modulus |G| in burnside's lemma
        return String.valueOf(result.divide(divisior));

    }

    static void numberPartitions(int n, List<Integer> nums, List<Integer> l, List<List<Integer>> list){
        int sum=l.stream().reduce(0, Integer::sum);
        if(sum==n){
            list.add(l);
            return;
        } else if(sum>n){
            return;
        }
        for(int i =0; i<n; i++){
            if(l.size()>0 && l.get(l.size()-1)>nums.get(i)){
                continue;
            }
            List<Integer> s = new ArrayList<>();
            s.addAll(l);
            s.add(nums.get(i));
            numberPartitions(n, nums, s,list);
        }

    }
    public static void main(String[] args) {
        System.out.println(solution(2,3,4));
    }

}
