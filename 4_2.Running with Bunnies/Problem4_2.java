package org.mayank.go;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
Running with Bunnies====================

You and the bunny workers need to get out of this collapsing death trap of a space station -- and fast! Unfortunately, some of the bunnies have been weakened by their long work shifts and can't run very fast. Their friends are trying to help them, but this escape would go a lot faster if you also pitched in. The defensive bulkhead doors have begun to close, and if you don't make it through in time, you'll be trapped! You need to grab as many bunnies as you can and get through the bulkheads before they close. The time it takes to move from your starting point to all of the bunnies and to the bulkhead will be given to you in a square matrix of integers. Each row will tell you the time it takes to get to the start, first bunny, second bunny, ..., last bunny, and the bulkhead in that order. The order of the rows follows the same pattern (start, each bunny, bulkhead). The bunnies can jump into your arms, so picking them up is instantaneous, and arriving at the bulkhead at the same time as it seals still allows for a successful, if dramatic, escape. (Don't worry, any bunnies you don't pick up will be able to escape with you since they no longer have to carry the ones you did pick up.) You can revisit different spots if you wish, and moving to the bulkhead doesn't mean you have to immediately leave -- you can move to and from the bulkhead to pick up additional bunnies if time permits.In addition to spending time traveling between bunnies, some paths interact with the space station's security checkpoints and add time back to the clock. Adding time to the clock will delay the closing of the bulkhead doors, and if the time goes back up to 0 or a positive number after the doors have already closed, it triggers the bulkhead to reopen. Therefore, it might be possible to walk in a circle and keep gaining time: that is, each time a path is traversed, the same amount of time is used or added.Write a function of the form solution(times, time_limit) to calculate the most bunnies you can pick up and which bunnies they are, while still escaping through the bulkhead before the doors close for good. If there are multiple sets of bunnies of the same size, return the set of bunnies with the lowest worker IDs (as indexes) in sorted order. The bunnies are represented as a sorted list by worker ID, with the first bunny being 0. There are at most 5 bunnies, and time_limit is a non-negative integer that is at most 999.For instance, in the case of[  [0, 2, 2, 2, -1],  # 0 = Start  [9, 0, 2, 2, -1],  # 1 = Bunny 0  [9, 3, 0, 2, -1],  # 2 = Bunny 1  [9, 3, 2, 0, -1],  # 3 = Bunny 2  [9, 3, 2, 2,  0],  # 4 = Bulkhead]and a time limit of 1, the five inner array rows designate the starting point, bunny 0, bunny 1, bunny 2, and the bulkhead door exit respectively. You could take the path:Start End Delta Time Status    -   0     -    1 Bulkhead initially open    0   4    -1    2    4   2     2    0    2   4    -1    1    4   3     2   -1 Bulkhead closes    3   4    -1    0 Bulkhead reopens; you and the bunnies exitWith this solution, you would pick up bunnies 1 and 2. This is the best combination for this space station hallway, so the solution is [1, 2].

Languages=========
To provide a Java solution, edit Solution.java
To provide a Python solution, edit solution.py

Test cases==========
Your code should pass the following test cases.Note that it may also be run against hidden test cases not shown here.

-- Java cases --
Input:Solution.solution({{0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}}, 3)Output:    [0, 1]

Input:Solution.solution({{0, 2, 2, 2, -1}, {9, 0, 2, 2, -1}, {9, 3, 0, 2, -1}, {9, 3, 2, 0, -1}, {9, 3, 2, 2, 0}}, 1)Output:    [1, 2]
-- Python cases --
Input:solution.solution([[0, 1, 1, 1, 1], [1, 0, 1, 1, 1], [1, 1, 0, 1, 1], [1, 1, 1, 0, 1], [1, 1, 1, 1, 0]], 3)Output:    [0, 1]

Input:solution.solution([[0, 2, 2, 2, -1], [9, 0, 2, 2, -1], [9, 3, 0, 2, -1], [9, 3, 2, 0, -1], [9, 3, 2, 2, 0]], 1)Output:    [1, 2]
 */
public class Problem4_2 {

    static int[][] shortestPath(int[][] times){
        int[] distances = new int[times.length];
        for(int i=0; i<distances.length;i++){
            distances[i]=Integer.MAX_VALUE;
        }
        distances[0]=0;
        for(int i=0; i<times.length;i++){
            for(int j=0; j<times.length;j++){
                for(int k=0; k<times.length;k++){
                    times[j][k]=Math.min(times[j][k],times[j][i]+times[i][k]);
                }
            }
        }
        return times;
    }
    static void allpathsOfGivenLength(char[] chars, String p, int n, int r, List<String> list){
        if(r==0){
            list.add(p);
            return;
        }
        for(int i =0; i<n; i++){
            if(p.contains(""+chars[i])){
                continue;
            }
            String s=p+chars[i];
            allpathsOfGivenLength(chars, s, n,r-1,list);
        }
    }
    static List<String> validPathsInInGivenTime(int [][]times, List<String> paths, int  time){
        List<String> validPaths = new ArrayList<>();
        for(String path:paths){
            int timeOfThePath = 0;
            for(int i = 0;i<path.length();i++){
                if(i==0){
                    timeOfThePath = times[0][Character.getNumericValue(path.charAt(i))];
                } else {
                    timeOfThePath += times[Character.getNumericValue(path.charAt(i-1))][Character.getNumericValue(path.charAt(i))];
                }
            }
            timeOfThePath += times[Character.getNumericValue(path.charAt(path.length()-1))][times.length-1];
            if(time>=timeOfThePath){
                validPaths.add(path);
                break;
            }
        }
        return validPaths;
    }
    public static int[] solution(int[][] times, int times_limit) {
        times = shortestPath(times);
        boolean negativeCycleExists = false;
        for(int i=0; i<times[0].length;i++){
            for(int j=0; j<times[0].length;j++){
                if(times[0][j]>times[0][i]+times[i][j]){
                    negativeCycleExists = true;
                    break;
                }
            }
            if(negativeCycleExists){
                break;
            }
        }
        if(negativeCycleExists){
            int all[] = new int[times.length-2];
            for(int i=0; i<all.length;i++){
                all[i]=i;
            }
            return all;
        }
        StringBuilder s = new StringBuilder();
        for(int i=1; i<times.length-1;i++){
            s.append(i);
        }
        List<String> validPaths = new ArrayList<>();
        for(int i=s.length();i>0;i--){
            List<String> paths = new ArrayList<>();
            allpathsOfGivenLength(s.toString().toCharArray(),"",s.length(),i,paths);
            validPaths = validPathsInInGivenTime(times, paths, times_limit);
            if(validPaths != null && validPaths.size()>0){
                break;
            }
        }
        List<Integer> res = new ArrayList<>();
        if(validPaths.size()>0){
            String r = validPaths.get(0);
            for(char c:r.toCharArray()){
                res.add(Character.getNumericValue(c)-1);
            }

        }
        Collections.sort(res);
        return res.stream().mapToInt(x->x).toArray();
    }
    public static void main(String[] args) {
        //int graph[][] = {{0, 3,4}, {9, 4}};

        int graph[][] ={{0, 2, 2, 2, -1}, {9, 0, 2, 2, -1}, {9, 3, 0, 4, -1}, {9, 3, 1, 0, -1}, {9, 3, 3, 2, 0}};
        //int graph[][] ={{0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}};

        int []res = solution(graph,1);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
        /*
        int [][]res= shortestPathMatrix(graph);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(res[i][j]+", ");
            }
            System.out.println();
        }

         */
    }
}
