package algorithms.part1.unionfind;

/**
 *
 * @author Heron Sanches
 */
public final class QuickFind implements UnionI{
   
   private final int n;
   private final int data[];
   private int qttComponents;

   
   public QuickFind(int n){
      
      this.n = n;
      this.data = new int[n];
      this.qttComponents = n;
      
      for(int i=0; i<n; i++)
         data[i] = i;
      
   }
   
   
   /**This method finds a site on data array (represents the components) and returns your value.
    *@param p site.*/
   @Override
   public int find(int p){
      return data[p];
   }
   
   
   /**@param p site, that is represented by an index element on data array.
    * @param q site.
    * @return true if the two sites are connected, otherwise returns false*/
   @Override
   public boolean connected(int p, int q){
      return data[p] == data[q];
   }
   
   
   /**This method connect these two sites to the same component.
    * @param p: site, that is represented by an index element on data array.
    * @param q: site.*/
   @Override
   public void union(int p, int q){
      
      if(connected(p, q))
         return;
      
      int pv = find(p); //pv: p value
      int qv = find(q); //qv: q value
      
      for(int i=0; i<data.length; i++){ //change all data site values with "pv" to "qv";
         if(data[i] == pv)
            data[i] = qv;
      }
      
      qttComponents--;
      
   }
   
   
   /**It prints all components.*/
   @Override
   public void printComponents(){
               
      boolean empty;
      String component;
      
      for(int i=0; i<data.length; i++){
         
         component = "";
         empty = true;
         
         for(int j=0; j<data.length; j++){ //print one component
            
            if(data[j] == i){
               
               component += "-"+j;
               empty = false;
               
            }
            
         }
         
         if(!empty)
            System.out.println(component.substring(1, component.length())+"\n");
         
      }
      
      System.out.println();
      System.out.println(qttComponents+" components.");
      
   }
   
   
}