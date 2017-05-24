package algorithms.part1.unionfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Heron Sanches
 */
public class WeightedQuickUnionPathCompression implements UnionI{

   private final int n;
   private final int data[];
   private final int dataSizes[];
   private int qttComponents;

   
   public WeightedQuickUnionPathCompression(int n){
      
      this.n = n;
      this.data = new int[n];
      dataSizes = new int[n];
      this.qttComponents = n;
      
      for(int i=0; i<n; i++){
         
         data[i] = i;
         dataSizes[i] = 1;
         
      }
      
   }
   
   
   /**This method finds a site on data array (represents the components) and returns your value.
    *@param p site.*/
   @Override
   public int find(int p){
      return data[p];
   }
   
   
   /**@param p site, that is represented by an index element on data array, in this case the array represents a forest.
    * @return the root of the tree - the array (data[]) index.*/
   public int root(int p){
      
      int newP;
      
      while( (newP = find(p)) != p){
         
         data[p] = root(newP); //path compression
         p = newP;
         
      }
      
      return p;
      
   }
   
   
   /**@param p site, that is represented by an index element on data array.
    * @param q site.
    * @return true if the two sites are connected, otherwise returns false*/
   @Override
   public boolean connected(int p, int q){
      return root(p) == root(q);
   }
   
   
   /**This method connect these two sites to the same component, those components now have the same root tree.
    * @param p: site, that is represented by an index element on data array.
    * @param q: site.*/
   @Override
   public void union(int p, int q){

      if(connected(p, q))
         return;
      
      if(dataSizes[p] > dataSizes[q]){
         
         data[root(q)] = root(p); //q's root points to the p's root
         dataSizes[p] += dataSizes[q];
         dataSizes[q] = dataSizes[p];
         
      }else{
         
         data[root(p)] = root(q); //p's root points to the q's root
         dataSizes[p] += dataSizes[q];
         dataSizes[q] = dataSizes[p];
         
      }
      
      qttComponents--;
      
   }
   
   
   /**It prints all components.*/
   @Override
   public void printComponents(){
      
      /**Map<root, treeElements>*/
      Map<Integer,List<Integer>> forest = new HashMap<>();
      int root;
      
      for(int i=0; i<data.length; i++){
         
         root = root(i);
         
         if(!forest.containsKey(root)){//not contains this tree elements list
            
            forest.put(root, new ArrayList());
            forest.get(root).add(i);
            
         }else{ //add the element to the existent tree elements list
            forest.get(root).add(i);
         }
         
      }
      
      Iterator<List<Integer>> it = forest.values().iterator();
      String component;

      while(it.hasNext()){

         component = "";

         for(Integer el : it.next())
            component += ("-"+el);

         System.out.println(component.substring(1, component.length()));
         System.out.println();

      }
      
      String array = "";
      String arrayValues = "";
      
      for(int i=0; i<data.length; i++){
         
         array += i+"   ";
         arrayValues += data[i]+"   ";
         
      }
      
      System.out.printf("%s\n%s\n", array, arrayValues);
      System.out.println(qttComponents+" components.");
      
   }
   
   
}