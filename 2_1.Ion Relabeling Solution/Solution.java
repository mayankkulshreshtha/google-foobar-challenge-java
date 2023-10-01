public class Solution {

    static int[] solution1(int h, int[] q){
        int p[] = new int[q.length];
        int totalNodes = (int) (Math.pow(2,h)-1);

        for (int i = 0; i < q.length; i++) {
            if(q[i]>=totalNodes){
                p[i]=-1;
                continue;
            }
            int last = totalNodes;
            int val = q[i];
            int level = h;
            while(level>=1){
                int left = (int) (last - (Math.pow(2, level-1)));
                int right = last-1;
                if(val == left || val == right){
                    p[i]=last;
                    break;
                }
                if(left>val){
                    last=left;
                } else if(right>val){
                    last = right;
                }
                level--;
            }

        }

        return p;
    }
    static void parentMap(int h){
        int totalNodes = (int) (Math.pow(2,h)-1);

    }
    public static int[] solution(int h, int[] q) {
        int []p = new int[q.length];
        int totalNodes = (int) (Math.pow(2,h) -1);

        for(int i = 0; i<q.length; i++){
            if(q[i]>=totalNodes){
                p[i]=-1;
                continue;
            }
            int last = totalNodes;
            int val = q[i];
            int level = h;
            while(level>=1){
                int left = (int) (last - (Math.pow(2, level-1)));
                int right = last - 1;
                if(val == left || val == right){
                    p[i]=last;
                    break;
                }
                if(left>val){
                    last = left;
                } else if(right>val) {
                    last = right;
                }
                level--;
            }
        }
        return p;
    }
    public static void main(String[] args) {
        int []res = Problem2_1.solution(6, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63});
        for (int i = 0; i < res.length; i++) {
            System.out.println((i+1)+", "+res[i]);
        }
    }
}
