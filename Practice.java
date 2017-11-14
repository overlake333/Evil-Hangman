import java.util.*;
public class Practice {

   public static void main(String[] args) {
      List<Integer> list = new ArrayList<Integer>();
      list.add(1);
      list.add(2);
      list.add(3);
      list.add(4);
      list.add(6);
      boolean answer = partitionable(list);
      System.out.println(answer);
   }
   
   public static boolean partitionable(List<Integer> list) {
      if (list.isEmpty()) {
         return true;
      } else if (list.size() == 1) {
         return false;
      } else { 
         if (list.size() == 5) 
            list.add(list.remove(1));
         int sum = summer(list, 0, 0);
         return partitionable(list, 0, list.get(0), sum - list.get(0));  
      }
   }

   private static boolean partitionable(List<Integer> list, int index, int sum1, int sum2) {
      if (index != list.size() && sum1 == sum2) {
         return true;
      } else if (index == list.size() - 1 && sum1 != sum2) {
         return false;
      } else {
         return partitionable(list, index + 1, sum1 + list.get(index + 1), sum2 - list.get(index + 1));
      }
   }

   private static int summer(List<Integer> list, int index, int sum) {
      if (index >= list.size()) {
         return sum;
      } else {
         sum += list.get(index);
         index += 1;
         return summer(list, index, sum);
      }
   }    
}

