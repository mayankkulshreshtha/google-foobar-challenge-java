import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;

public class Problem1 {

    public static int[] solution(int[] data, int n){
        int length = data.length;
        List<Integer> dataList = new ArrayList<>();
        Map<Integer, Integer> mapWithCount = new HashMap<>();
        for(int i=0; i<length; i++){
            int val = data[i];
            dataList.add(val);
            if(mapWithCount.containsKey(val)){
                mapWithCount.put(val, mapWithCount.get(val)+1);
            } else
                mapWithCount.put(val,1);
        }
        System.out.println(mapWithCount);
        Iterator<Integer> iterator = dataList.listIterator();
        while (iterator.hasNext()){
            int val = iterator.next();
            if(mapWithCount.get(val)>n){
                iterator.remove();
            }
        }
        System.out.println(dataList);
        return dataList.stream().mapToInt(x->x).toArray();
    }
    public static void main(String[] args) {
        Problem1 soln = new Problem1();
        int[] d= soln.solution(new int[]{1,2,2,3,3,3,4,5,5}
        ,1);
        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i]+", ");
        }
    }
}

