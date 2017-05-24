package algorithms.part1.unionfind;

import java.util.Scanner;

/**
 *
 * @author Heron Sanches
 */
public class UnionMain {
   
   
   /**Execution: <br>
    * 1 - mvn clean package <br>
    * 2 - mvn exec:java -Dexec.mainClass="algorithms.part1.unionfind.UnionMain" -Dexec.args="[0] [1]"
    * @param args [0]: QF({@linkplain QuickFind}), QU({@linkplain QuickUnion}) <br>
    * [1]: path under /src/main/resources/, e.g., /txt/resourcePathFile.txt, without the "/src/main/resources/"
   */
   public static void main(String[] args) {
      
      TxtUtils txtUtils = new TxtUtils(args[1]);
      Scanner input = txtUtils.getInput();
      UnionI algorithm = null;
      
      switch(args[0]){
         
         case "QF": //Quick Find
            algorithm = new QuickFind(input.nextInt());
         break;
         
         case "QU": //Quick Union
            algorithm = new QuickUnion(input.nextInt());
         break;
         
         case "WQU"://Weighted Quick Union
            algorithm = new WeightedQuickUnion(input.nextInt());
         break;
         
         case "WQUPC": //Weighted Quick Union Path Compressed
            algorithm = new WeightedQuickUnionPathCompression(input.nextInt());
         break;
         
      }
      
      while(input.hasNext())
         algorithm.union(input.nextInt(), input.nextInt()); 

      txtUtils.closeInput();
      /*algorithm.printComponents();
      System.out.println();*/
      
   }
   
   
}