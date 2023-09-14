

/*
Ion Relabeling Solution
Oh no! Commander Lambda's latest experiment to improve the efficiency of her LAMBCHOP doomsday device has backfired spectacularly. She had been improving the structure of the ion flux converter tree, but something went terribly wrong and the flux chains exploded. Some of the ion flux converters survived the explosion intact, but others had their position labels blasted off. She's having her henchmen rebuild the ion flux converter tree by hand, but you think you can do it much more quickly - quickly enough, perhaps, to earn a promotion!

Flux chains require perfect binary trees, so Lambda's design arranged the ion flux converters to form one. To label them, she performed a post-order traversal of the tree of converters and labeled each converter with the order of that converter in the traversal, starting at 1. For example, a tree of 7 converters would look like the following:

       7
     /   \
   3      6
  /  \   / \
 1   2  4   5
Write a function answer(h, q) - where h is the height of the perfect tree of converters and q is a list of positive integers representing different flux converters - which returns a list of integers p where each element in p is the label of the converter that sits on top of the respective converter in q, or -1 if there is no such converter. For example, answer(3, [1, 4, 7]) would return the converters above the converters at indexes 1, 4, and 7 in a perfect binary tree of height 3, which is [3, 6, -1].

The domain of the integer h is 1 <= h <= 30, where h = 1 represents a perfect binary tree containing only the root, h = 2 represents a perfect binary tree with the root and two leaf nodes, h = 3 represents a perfect binary tree with the root, two internal nodes and four leaf nodes (like the example above), and so forth. The lists q and p contain at least one but no more than 10000 distinct integers, all of which will be between 1 and 2^h-1, inclusive.

The test cases provided are:

Inputs:

(int) h = 3
(int list) q = [7, 3, 5, 1]
Output:

(int list) [-1, 7, 6, 3]
Inputs:

(int) h = 5
(int list) q = [19, 14, 28]
Output:

(int list) [21, 15, 29]
 */

public class Problem2_1 {

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
